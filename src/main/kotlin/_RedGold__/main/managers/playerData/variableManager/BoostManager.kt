package _RedGold__.main.managers.playerData.variableManager

import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.ReinforceImportEnum
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.randomReinforceItem
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

    data object LimitCrystalPackage : BoostSealed() {
        override val getEnum = BoostEnum.LimitCrystalPackage

        override val max: Int = 5
        override val period: Long = 2_592_000

        val giveCrystal: List<Int> = listOf(
            6000, 6000, 12_000, 12_000, 24_000
        )
    }

    data object StarterPackage : BoostSealed() {
        override val getEnum = BoostEnum.STARTER_PACKAGE

        override val max: Int = 1
        override val period: Long = 4005072000 //127년

        const val GIVE_GOLD: Int = 5_000_000
        const val GIVE_CRYSTAL: Int = 240
        val giveItem: List<ItemStack> = listOf(
            ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2),
            ItemStack(Material.NETHERITE_INGOT, 10),
            ItemStack(Material.ENDER_CHEST, 16),
            ItemStack(Material.EXPERIENCE_BOTTLE, 64),
            ItemStack(Material.EXPERIENCE_BOTTLE, 64),
            randomReinforceItem(10, ReinforceImportEnum.BEGINNER),
            randomReinforceItem(5, ReinforceImportEnum.INTERMEDIA)
        )
    }

    data object MonthlyPackage : BoostSealed() {
        override val getEnum = BoostEnum.MONTHLY_PACKAGE

        override val max: Int = 1
        override val period: Long = 2_592_000 //30일

        const val GIVE_CRYSTAL: Int = 360
        const val DAILY_CRYSTAL: Int = 40
        val dailyItem: List<ItemStack> = listOf(
            ItemStack(Material.EXPERIENCE_BOTTLE, 32),
            randomReinforceItem(1, ReinforceImportEnum.GENERAL),
        )

        const val MULTIPLE_EXP: Double = 1.25
    }
}

enum class BoostEnum {
    LimitCrystalPackage,

    STARTER_PACKAGE,
    MONTHLY_PACKAGE;
}
