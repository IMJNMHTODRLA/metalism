package _RedGold__.main.core.guild.expManager.expBenefits

import kotlin.math.pow

data class GuildLevelBenefits(
    val fallDamageDec: Double,
    val attackDamage: Double,

    val shareChestSize: Int,
    val maxMembers: Int,

    val killReward: Long,
)

fun failDamageDecBenefits(level: Int) = (0.006 * level).coerceAtMost(MAX_DAMAGE_PERCENT)
fun attackDamageBenefits(level: Int) = (0.003 * level).coerceAtMost(MAX_ATTACK_PERCENT)

fun shareChestSizeBenefits(level: Int) = (level / 2).coerceAtMost(MAX_CHEST_SIZE) //TODO: 이거 만들어야 한다
fun getMaxMembers(level: Int) = (level / 4).coerceAtMost(MAX_MEMBERS) //TODO: 이거 만들어야 한다

fun killRewardBenefits(level: Int) = (7 * level.toDouble().pow(1.1)).toLong()

fun getAllLevelBenefits(level: Int) = GuildLevelBenefits(
    failDamageDecBenefits(level),
    attackDamageBenefits(level),

    shareChestSizeBenefits(level),
    getMaxMembers(level),

    killRewardBenefits(level)
)