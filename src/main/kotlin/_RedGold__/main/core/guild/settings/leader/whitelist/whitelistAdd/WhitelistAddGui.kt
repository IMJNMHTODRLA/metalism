package _RedGold__.main.core.guild.settings.leader.whitelist.whitelistAdd

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.settings.leader.whitelist.WhitelistData
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

object WhitelistAddGui {
    fun openGui(player: Player, name: String) {
        val target = Bukkit.getPlayer(name)?: Bukkit.getOfflinePlayer(name)
        openGui(player, target)
    }

    fun openGui(player: Player, uuid: UUID) {
        val target = Bukkit.getPlayer(uuid)?: Bukkit.getOfflinePlayer(uuid)
        openGui(player, target)
    }

    fun openGui(player: Player, target: OfflinePlayer) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId
        val targetUUID = target.uniqueId
        val name = target.name

        taskAsync {
            val id = getGuildId2Leader(uuid)?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            task {
                val gui = WhitelistAddHolder(target, WhitelistData(id, targetUUID)).inventory
                gui.item(BACKGROUND)

                gui.item[13] = getPlayerSkull(
                    targetUUID,
                    "&e&l${name}님",
                    "",
                    "&f&l클릭 시 화이트리스트에 &e&l${name}님&f&l을 &a&l추가&f&l합니다."
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}