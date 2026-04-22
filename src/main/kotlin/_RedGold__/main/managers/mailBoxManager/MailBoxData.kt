package _RedGold__.main.managers.mailBoxManager

import _RedGold__.main.functions.TimeTool.now
import org.bukkit.inventory.ItemStack
import java.util.UUID

data class MailBoxData(
    val id: Int,
    val uuid: UUID,

    val sender: String,
    val title: String,
    val content: String,

    val item: ItemStack?,

    val giveGold: Long?,
    val giveCrystal: Int?,

    val sendAt: Long,
    val duration: Long
) {
    val isExpiry get() = sendAt < (now - duration)
    val isRead get() = id in readMailId
}
