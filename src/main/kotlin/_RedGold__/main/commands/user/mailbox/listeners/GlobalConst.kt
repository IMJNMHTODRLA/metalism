package _RedGold__.main.commands.user.mailbox.listeners

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.ifRun
import _RedGold__.main.managers.mailBoxManager.MailBoxData
import _RedGold__.main.managers.mailBoxManager.readMailId
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player

object GlobalConst {
    const val COOLDOWN_TIME = 2
    fun openMail(player: Player, mailData: MailBoxData): Boolean {
        (mailData.isExpiry).ifRun {
            player.fail("&c만료된 메일입니다.")
            return false
        }
        (mailData.isRead).ifRun {
            player.fail("&c이미 읽은 메일입니다.")
            return false
        }

        readMailId.add(mailData.id)

        mailData.item?.let { player.inv += it }
        mailData.giveGold?.let { player.data.gold += it }
        mailData.giveCrystal?.let { player.data.crystal += it }

        player.good("&a보상을 획득 하였습니다.")
        return true
    }
}