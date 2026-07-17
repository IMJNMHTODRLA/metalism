package _RedGold__.main.managers.playerData

import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.PluginException
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KProperty

fun <T> logger(uuid: UUID) = { prop: KProperty<*>, old: T, new: T -> "$uuid 님의 ${prop.name} 변경: $old -> $new" }

object PlayerManager {
    private val dataMap = ConcurrentHashMap<UUID, PlayerData>()

    fun getAllData(): Collection<PlayerData> = dataMap.values

    operator fun get(uuid: UUID) = dataMap[uuid]
    operator fun set(uuid: UUID, data: PlayerData) { dataMap[uuid] = data }
    fun getOrLoad(uuid: UUID, loader: (UUID) -> PlayerData) = dataMap.computeIfAbsent(uuid, loader)

    fun load(uuid: UUID, data: PlayerData) { dataMap[uuid] = data }
    fun unload(uuid: UUID) { dataMap.remove(uuid) }
}

val Player.data get() = PlayerManager[uniqueId]
    ?: throw PluginException("${name}의 데이터가 로드되지 않았습니다!", ExceptionSeverity.HIGH)
    //set(value) = PlayerManager[uniqueId] = value