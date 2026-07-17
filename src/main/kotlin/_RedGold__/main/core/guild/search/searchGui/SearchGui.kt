package _RedGold__.main.core.guild.search.searchGui

import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.search.getGuildInfoList
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object SearchGui {
    fun openGui(player: Player, keyword: String?, page: Int = 0) {
        if (playerCooldownMsg(player)) return

        taskAsync {
            val start = getStartN(page)
            val infoList = getGuildInfoList(start, MAX_N, keyword)

            task {
                val gui = SearchHolder(page, keyword, infoList).inventory

                gui.item[0..44] = BACKGROUND
                gui.item[45..gui.end] = BACKGROUND_1

                repeat(MAX_N) { i ->
                    val slot = getSlot(i)
                    val info = infoList.getOrNull(i)?: return@repeat

                    gui.item[slot] = getItem(
                        Material.BOOK,
                        "&e&l${info.name} &f&l길드&8(${info.id})",
                        "",
                        "&e&l클릭 시 위 길드로 가입을 할 수 있습니다."
                    )
                }

                gui.item[45] = getItem(
                    Material.RED_STAINED_GLASS_PANE,
                    "&c&l이전 페이지로 이동"
                )

                gui.item[49] = getItem(
                    Material.BOOK,
                    "&8&l현재 페이지: ($page)"
                )

                gui.item[53] = getItem(
                    Material.GREEN_STAINED_GLASS_PANE,
                    "&a&l다음 페이지로 이동"
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}