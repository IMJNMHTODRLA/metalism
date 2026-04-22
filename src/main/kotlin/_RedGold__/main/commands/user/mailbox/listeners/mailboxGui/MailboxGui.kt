package _RedGold__.main.commands.user.mailbox.listeners.mailboxGui

import _RedGold__.main.commands.user.mailbox.listeners.GlobalConst
import _RedGold__.main.commands.user.mailbox.listeners.GlobalValue
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.afterWith
import _RedGold__.main.functions.remainingWith
import _RedGold__.main.managers.mailBoxManager.getMail
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MailboxGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, page: Long) {
        val uuid = player.uniqueId

        val cooldown = GlobalValue.dbCooldown[uuid]?: 0
        if (!cooldown.afterWith(GlobalConst.COOLDOWN_TIME)) {
            player.sendMsg("&c${cooldown.remainingWith(GlobalConst.COOLDOWN_TIME)}초 후에 다시 시도해주세요.")
            return
        }
        GlobalValue.dbCooldown[uuid] = now

        val gui = MailboxHolder(page).inventory

        gui.item(BACKGROUND)
        gui.item[27..gui.end] = BACKGROUND_1

        plugin.taskAsync {
            val mailList = getMail(uuid, page)

            plugin.task {
                (gui.holder as MailboxHolder).mailList = mailList

                mailList.forEachIndexed { i, mail ->
                    gui.item[i] = MailboxConst.setMail(mail)
                }

                player.inv + gui
                player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)
            }
        }
    }
}