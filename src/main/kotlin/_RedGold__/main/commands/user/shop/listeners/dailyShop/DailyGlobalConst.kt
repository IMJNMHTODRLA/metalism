package _RedGold__.main.commands.user.shop.listeners.dailyShop

import _RedGold__.main.core.gacha.item.skill.reinforceManager.MAX_REINFORCE_TYPE

object DailyGlobalConst {
    val getSkillItem = { base: Int, n: Int ->
        base * 5 + n
    }

    val range = 0..<MAX_REINFORCE_TYPE
}