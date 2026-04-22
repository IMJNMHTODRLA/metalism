package _RedGold__.main.commands.user.ranking.listeners

import java.util.*

internal object GlobalConst {
    val getRanking = { page: Int, n: Int -> (page * 16) + n }
    val getSlot = { n: Int -> (n / 4) * 9 + (n % 4) * 2 + 1 }
    val getTeleportRanking = { n: Int -> n / 16 }

    abstract class BaseData<T : Comparable<T>>(
        val keys: Array<UUID>,
        val size: Int,
        private val valueGetter: (Int) -> T
    ) {
        private val indexMap = keys.withIndex().associateTo(HashMap(keys.size)) { it.value to it.index }

        operator fun get(index: Int) = SingleRankData(index, keys[index], valueGetter(index))
        operator fun get(uuid: UUID) = indexMap[uuid]?.let { get(it) }
    }

    data class SingleRankData <T : Comparable<T>> (
        val rank: Int,
        val key: UUID,
        val value: T,
    )

    class LongRankingData(
        keys: Array<UUID> = emptyArray(),
        val values: LongArray = longArrayOf()
    ) : BaseData<Long>(keys, values.size, { values[it] })

    class IntRankingData(
        keys: Array<UUID> = emptyArray(),
        val values: IntArray = intArrayOf()
    ) : BaseData<Int>(keys, values.size, { values[it] })

    class FloatRankingData(
        keys: Array<UUID> = emptyArray(),
        val values: FloatArray = floatArrayOf()
    ) : BaseData<Float>(keys, values.size, { values[it] })
}
