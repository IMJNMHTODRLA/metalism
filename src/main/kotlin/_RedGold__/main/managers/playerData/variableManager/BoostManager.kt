package _RedGold__.main.managers.playerData.variableManager

import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum
import _RedGold__.main.core.gacha.item.skill.reinforceManager.randomReinforceItem
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

sealed class BoostSealed {
    abstract val max: Int
    abstract val period: Long
    abstract val getEnum: BoostEnum
    //TODO: 월간 패키지는 리셋 할 때 1일 마다 리셋, 근데 구매 횟수만 리셋

    fun isLimitReach(player: Player): Boolean {
        val boostData = player.data.boostMap[getEnum]?: return false
        val expirationAt = boostData.expirationAt
        val amount = boostData.amount

        val remainingPeriod = expirationAt - now
        val isPurMax = max - amount

        return (remainingPeriod > 0 && isPurMax <= 0)
    }

    data class LimitCrystalPackage(
        override val max: Int = 5,
        override val period: Long = 2_592_000,

        val giveCrystal: List<Int> = listOf(
            4800, 4800, 10_800, 10_800, 24_000
        ),
    ) : BoostSealed() { override val getEnum = BoostEnum.LimitCrystalPackage }

    data class StarterPackage(
        override val max: Int = 1,
        override val period: Long = 4005072000, //127년

        val giveGold: Int = 500_000,
        val giveCrystal: Int = 240,
        val giveItem: List<ItemStack> = listOf(
            ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2),
            ItemStack(Material.NETHERITE_INGOT, 10),
            ItemStack(Material.ENDER_CHEST, 16),
            ItemStack(Material.EXPERIENCE_BOTTLE, 64),
            ItemStack(Material.EXPERIENCE_BOTTLE, 64),
            randomReinforceItem(10, ReinforceImportEnum.BEGINNER),
            randomReinforceItem(5, ReinforceImportEnum.INTERMEDIA)
        )
    ) : BoostSealed() { override val getEnum = BoostEnum.STARTER_PACKAGE }

    data class MonthlyPackage(
        override val max: Int = 1,
        override val period: Long = 2_592_000, //30일

        val giveCrystal: Int = 120,
        val dailyCrystal: Int = 40,
        val dailyItem: List<ItemStack> = listOf(
            ItemStack(Material.EXPERIENCE_BOTTLE, 16),
            randomReinforceItem(1, ReinforceImportEnum.GENERAL),
        ),

        val multipleExp: Double = 1.2
    ) : BoostSealed() { override val getEnum = BoostEnum.MONTHLY_PACKAGE }
}

enum class BoostEnum {
    LimitCrystalPackage,

    STARTER_PACKAGE,
    MONTHLY_PACKAGE;
}
