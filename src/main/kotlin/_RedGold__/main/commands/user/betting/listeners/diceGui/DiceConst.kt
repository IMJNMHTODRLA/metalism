package _RedGold__.main.commands.user.betting.listeners.diceGui

import _RedGold__.main.functions.Gui.getItem
import org.bukkit.Material

object DiceConst {
    const val MAX_BET = 7_500_000

    val ITEM = { i: Int ->
        getItem(
            Material.GOLD_NUGGET,
            "&e${i + 1}번",
            listOf("", "&7클릭 시 ${i + 1}번으로 선택 됩니다."),
            i + 1
        )
    }
}