package _RedGold__.main.commands.user.betting.listeners.coinGui

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
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
import kotlin.math.pow

class CoinGui {
    fun openGui(player: Player) {
        val gui = CoinHolder().inventory
        gui.item(BACKGROUND)

        gui.item[20] = getItem(
            Material.EMERALD,
            "&a앞면",
            listOf("", "&7클릭 시 앞면으로 선택 됨과 동시에 도박이 시작 됩니다.")
        )

        gui.item[22] = getItem(
            Material.BLACK_CONCRETE,
            "&8알 수 없음",
            listOf("", "&c도박이 시작되지 않았습니다.")
        )

        gui.item[24] = getItem(
            Material.REDSTONE,
            "&c뒷면",
            listOf("", "&7클릭 시 뒷면으로 선택 됨과 동시에 도박이 시작 됩니다.")
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