package _RedGold__.main.commands.user.mailbox.listeners.infoGui

import _RedGold__.main.commands.user.mailbox.listeners.GlobalConst
import _RedGold__.main.commands.user.mailbox.listeners.mailboxGui.MailboxGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui.SetGoodHolder
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.ifRun
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.mailBoxManager.readMailId
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class InfoListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? InfoHolder?: return

        plugin.task(1) {
            if (!player.isOnline) return@task
            MailboxGui(plugin).openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is InfoHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as InfoHolder

        val clickType = event.click
        val slot = event.slot

        val mailData = holder.mailData

        if (
            clickType.isLeftClick &&
            slot == 13
        ) GlobalConst.openMail(player, mailData)
    }
}