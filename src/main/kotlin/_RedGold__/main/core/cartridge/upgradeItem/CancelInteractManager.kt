package _RedGold__.main.core.cartridge.upgradeItem

import _RedGold__.main.Main
import _RedGold__.main.functions.getId
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType.BOOLEAN

const val IS_CANCEL_INTERACT = "is_cancel_interact"
@SetSlowInit val IsCancelInteract by lazy { NamespacedKey(Main.instance, IS_CANCEL_INTERACT) }

@RequireListener
class IsCancelInteractListener : Listener {
    private fun ItemStack?.isDisabled() =
        this?.itemMeta?.getId(IsCancelInteract, BOOLEAN)?: false

    private fun cancelIf(condition: Boolean, cancelAction: () -> Unit) {
        if (condition) cancelAction()
    }

    private fun Cancellable.cancelIf(condition: Boolean) {
        if (condition) isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPrepareCraft(event: PrepareItemCraftEvent) =
        cancelIf(event.inventory.matrix.any { it.isDisabled() }) { event.inventory.result = null }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerInteract(event: PlayerInteractEvent) = event.cancelIf(event.item.isDisabled())

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) =
        event.cancelIf(event.player.inventory.itemInMainHand.isDisabled())

    @EventHandler(priority = EventPriority.LOWEST)
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val player = event.damager as? Player?: return
        event.cancelIf(player.inventory.itemInMainHand.isDisabled())
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockPlace(event: BlockPlaceEvent) =
        event.cancelIf(event.itemInHand.isDisabled())

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockBreak(event: BlockBreakEvent) =
        event.cancelIf(event.player.inventory.itemInMainHand.isDisabled())

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBlockBreak(event: PlayerItemConsumeEvent) =
        event.cancelIf(event.player.inventory.itemInMainHand.isDisabled())
}