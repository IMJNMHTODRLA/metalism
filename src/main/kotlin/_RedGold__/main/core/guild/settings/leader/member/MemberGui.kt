package _RedGold__.main.core.guild.settings.leader.member

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object MemberGui {
    fun openGui(player: Player, page: Int = 0) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Leader(uuid)?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            val membersData = getMemberData(id, page)

            task {
                val targetDatas = membersData.players.map { Bukkit.getOfflinePlayer(it) }

                val gui = MemberHolder(targetDatas, page).inventory
                gui.item(BACKGROUND)

                targetDatas.forEachIndexed { i, target ->
                    val slot = getSlot(i)
                    val name = target.name?: "&7&l알 수 없음...&e&l"

                    gui.item[slot] = getPlayerSkull(
                        target.uniqueId,
                        "&e&l${name}님",
                        "",
                        "&f&l클릭 시 &e&l길드원 상세정보 &f&l창으로 이동됩니다."
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