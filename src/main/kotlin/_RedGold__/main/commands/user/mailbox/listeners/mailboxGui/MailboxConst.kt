package _RedGold__.main.commands.user.mailbox.listeners.mailboxGui

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.mailBoxManager.MailBoxData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object MailboxConst {
    fun setMail(mail: MailBoxData): ItemStack {
        return getItem(
            Material.BOOK,
            "&f&l${mail.title}",
            listOf("",
                "&f&l보낸이: ${mail.sender}",
                "&f&l내용: ${mail.content}",
                "",
                "&a&l좌클릭 시 우편함 보상 획득이 가능합니다.",
                "&e&l우클릭 시 우편함 보상 상세 확인이 가능합니다."
            )
        )
    }
}