package _RedGold__.main.commands.user.mailbox.listeners.infoGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.mailBoxManager.MailBoxData

class InfoHolder(
    val returnPage: Long,
    val mailData: MailBoxData
) : EasyHolder(3 * 9) {
    override fun title() = "메일함($returnPage|INFO)/ESC를 눌러 돌아가기"
}