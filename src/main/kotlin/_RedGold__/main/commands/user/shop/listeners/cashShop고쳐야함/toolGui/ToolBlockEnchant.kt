package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui

import _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui.ToolListener.ToolListenerObject.itemId
import _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui.ToolListener.ToolListenerObject.toolKeyId
import _RedGold__.main.functions.Gui.getStringId
import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import org.bukkit.event.inventory.PrepareAnvilEvent
import org.bukkit.event.inventory.PrepareGrindstoneEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent

@RequireListener
class ToolBlockEnchant : Listener {
    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        val inventory = event.inventory
        val firstItem = inventory.getItem(0)?: return
        val secondItem = inventory.getItem(0)?: return
        val firstItemId = firstItem.getStringId(toolKeyId)
        val secondItemId = secondItem.getStringId(toolKeyId)

        if (itemId.contains(firstItemId) || itemId.contains(secondItemId)) event.result = null
    }

    @EventHandler
    fun onPrepareEnchant(event: PrepareItemEnchantEvent) {
        val item = event.item.getStringId(toolKeyId)
        if (itemId.contains(item)) event.isCancelled = true
    }

    @EventHandler
    fun onEnchantItem(event: EnchantItemEvent) {
        val item = event.item.getStringId(toolKeyId)
        if (itemId.contains(item)) event.isCancelled = true
    }

    @EventHandler
    fun onPrepareResult(event: PrepareItemCraftEvent) {
        val items = event.inventory.matrix.filterNotNull()
        for (item in items) {
            if (itemId.contains(item.getStringId(toolKeyId))) {
                event.inventory.result = null
                return
            }
        }
    }

    @EventHandler
    fun onPrepareGrindstone(event: PrepareGrindstoneEvent) {
        val inventory = event.inventory
        val firstItem = inventory.getItem(0)?: return
        val secondItem = inventory.getItem(1)?: return
        val firstItemId = firstItem.getStringId(toolKeyId)
        val secondItemId = secondItem.getStringId(toolKeyId)

        if (itemId.contains(firstItemId) || itemId.contains(secondItemId)) event.result = null
    }
}