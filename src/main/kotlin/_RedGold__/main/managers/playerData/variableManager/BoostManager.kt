package _RedGold__.main.managers.playerData.variableManager

import org.bukkit.inventory.ItemStack

sealed class BoostSealed {
    abstract val max: Int
    abstract val period: Long

    abstract fun getEnum(): BoostEnum

    data class StarterPackage(
        override val max: Int = 1,
        override val period: Long = 4005072000, //127년

        val giveCrystal: Int = 360,
        val giveItem: List<ItemStack> = listOf()
    ) : BoostSealed() {
        override fun getEnum() = BoostEnum.STARTER_PACKAGE
    }

    data class MonthlyPackage(
        override val max: Int = 1,
        override val period: Long = 2_592_000, //30일

        val giveCrystal: Int = 360,
        val dailyCrystal: Int = 40,
        val multipleExp: Double = 1.2
    ) : BoostSealed() {
        override fun getEnum() = BoostEnum.MONTHLY_PACKAGE
    }
}

enum class BoostEnum {
    STARTER_PACKAGE,
    MONTHLY_PACKAGE;

    fun toSealed(): BoostSealed = when (this) {
        STARTER_PACKAGE -> BoostSealed.StarterPackage()
        MONTHLY_PACKAGE -> BoostSealed.MonthlyPackage()
    }
}
