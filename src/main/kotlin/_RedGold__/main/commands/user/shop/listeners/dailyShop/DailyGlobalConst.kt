package _RedGold__.main.commands.user.shop.listeners.dailyShop

import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.maxReinforceType

object DailyGlobalConst {
    val getSkillItem = { base: Int, n: Int ->
        base * 5 + n
    }

    val range = 0..<maxReinforceType
}