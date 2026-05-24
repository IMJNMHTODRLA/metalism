package _RedGold__.main.commands.user.mailbox.listeners.mailboxGui

import _RedGold__.main.commands.user.mailbox.listeners.GlobalConst
import _RedGold__.main.commands.user.mailbox.listeners.infoGui.InfoGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MailboxListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MailboxHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as MailboxHolder

        val clickType = event.click
        val slot = event.slot

        val mailList = holder.mailList?: return
        val page = holder.page

        when(slot) {
            in 0..26 -> {
                val mailData = mailList.getOrNull(slot)?: return

                if (clickType.isLeftClick) GlobalConst.openMail(player, mailData)
                else if (clickType.isRightClick) InfoGui().openGui(player, page, mailData)
            }

            27 -> MailboxGui().openGui(player, (page - 1).coerceAtLeast(0))
            35 -> MailboxGui().openGui(player, page + 1)
        }
    }
}