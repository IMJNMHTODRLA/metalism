package _RedGold__.main.commands.user.mailbox.listeners.mailboxGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.mailBoxManager.MailBoxData

class MailboxHolder(
    val page: Long,
    var mailList: List<MailBoxData>? = null
) : EasyHolder(4 * 9) {
    override fun title() = "메일함($page)"
}