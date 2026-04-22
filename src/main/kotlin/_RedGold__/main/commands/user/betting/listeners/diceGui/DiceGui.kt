package _RedGold__.main.commands.user.betting.listeners.diceGui

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import _RedGold__.main.commands.user.betting.listeners.coinGui.CoinConst
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class DiceGui {
    fun openGui(player: Player) {
        val gui = DiceHolder().inventory
        gui.item(BACKGROUND)

        repeat(3) { i ->
            gui.item[19 + i] = DiceConst.ITEM(i)
            gui.item[25 - i] = DiceConst.ITEM(i)
        }

        gui.item[22] = getItem(
            Material.EMERALD,
            "&7&l주사위 굴리기",
            listOf("", "&7&l주사위를 굴릴려면 클릭해주세요.")
        )

        repeat(4) { i ->
            val gold = GlobalConst.DEFAULT_GOLD * 10.pow(i)

            gui.item[48 - i] = getItem(
                Material.RED_STAINED_GLASS_PANE,
                "&c-${gold.toFormat()} 골드",
                listOf("", "&7클릭 시 베팅 금액에서 ${gold.toFormat()} 골드가 회수됩니다.")
            )

            gui.item[50 + i] = getItem(
                Material.GREEN_STAINED_GLASS_PANE,
                "&a+${gold.toFormat()} 골드",
                listOf("", "&7클릭 시 베팅 금액에서 ${gold.toFormat()} 골드를 추가합니다.")
            )
        }

        gui.item[49] = getItem(
            Material.GRAY_STAINED_GLASS_PANE,
            GlobalConst.BET_GOLD_MESSAGE.fill(
                "gold" to 0
            ).gc(),
        )

        player.openInventory(gui)
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}