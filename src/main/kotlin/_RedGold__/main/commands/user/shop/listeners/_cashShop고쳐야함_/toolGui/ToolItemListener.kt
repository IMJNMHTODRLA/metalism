package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.toolGui

import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.toolGui.ToolListener.ToolListenerObject.itemId
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.toolGui.ToolListener.ToolListenerObject.toolKeyId
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Gui.addHealth
import _RedGold__.main.functions.Gui.addItemDamage
import _RedGold__.main.functions.Gui.addPotion
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getStringId
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireListener
@RequireJavaPlugin
class ToolItemListener(private val plugin: JavaPlugin) : Listener {
    private val isUseType5: MutableMap<UUID, Long> = ConcurrentHashMap()

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        val item = player.inventory.itemInMainHand

        if (item.getStringId(toolKeyId) != itemId[0]) return
        if (!block.type.name.contains("ORE")) return

        event.isDropItems = false
        val itemName = when(block.type) {
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE -> "iron_ingot"
            Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE -> "copper_ingot"
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE -> "gold_ingot"

            Material.ANCIENT_DEBRIS -> "netherite_scrap"
            else -> return
        }
        block.world.dropItemNaturally(block.location, getItem(itemName))
    }

    @EventHandler
    fun onFastBreak(event: PlayerInteractEvent) {
        if (event.action != Action.LEFT_CLICK_BLOCK) return
        val player = event.player
        val block = event.clickedBlock?: return
        val world = block.world
        val item = player.inventory.itemInMainHand

        if (item.getStringId(toolKeyId) != itemId[1]) return

        plugin.task(2) {
            if (block.type == Material.AIR) return@task

            val dropItems = block.getDrops(item).toList()
            block.type = Material.AIR

            plugin.task(40) {
                dropItems.forEach {dropItem ->
                    world.dropItemNaturally(block.location, dropItem)
                }
            }
        }
    }

    @EventHandler
    fun onArmorChange(event: PlayerArmorChangeEvent) {
        val player = event.player
        val newItem = event.newItem
        val itemMeta = newItem.itemMeta

        if (newItem.getStringId(toolKeyId) != itemId[2]) return
        val gold = getData(plugin, player, "gold").toLong()
        if (gold < 15000) {
            player.fail("&c골드가 부족합니다. 필요 골드: ${(15000 - gold).toFormat()} 골드")
            return
        }

        saveData(plugin, player, "gold", gold - 15000)
        player.addPotion(PotionEffectType.SPEED, 10, 3)
        newItem.itemMeta = addItemDamage(itemMeta, 5)

        player.sendMsg("&a15,000골드를 사용하여 신속 IV(10초)를 지급하였습니다!")
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
    }

    @EventHandler
    fun onEntityUseTotem(event: EntityResurrectEvent) {
        val player = event.entity as? Player?: return
        val hand = event.hand?: return
        val item = player.inventory.getItem(hand)

        if (item.getStringId(toolKeyId) != itemId[3]) return

        val gold = getData(plugin, player, "gold").toLong()
        if (gold < 100_000) {
            player.fail("&c골드가 부족합니다. 필요 골드: ${(100_000 - gold).toFormat()} 골드")
            return
        }
        saveData(plugin, player, "gold", gold - 100_000)

        player.absorptionAmount += 5.0
        player.addPotion(PotionEffectType.STRENGTH, 15, 0)
        player.addPotion(PotionEffectType.SPEED, 25, 1)

        player.sendMsg("&a100,000골드를 사용하여 체력 5칸을 회복하고 힘 I(15초), 신속 II(25초)를 지급하였습니다!")
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
    }

    @EventHandler
    fun onEntityUseTotemType4(event: EntityResurrectEvent) {
        val player = event.entity as? Player?: return
        val hand = event.hand?: return
        val item = player.inventory.getItem(hand)

        if (item.getStringId(toolKeyId) != itemId[4]) return

        player.addHealth(10.0)
        player.addPotion(PotionEffectType.STRENGTH, 10, 5)
        player.addPotion(PotionEffectType.REGENERATION, 10, 4)

        player.sendMsg("&a체력 10칸을 회복하고 힘 VI(10초), 재생 V(10초)를 지급하였습니다!")
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)

        plugin.task(200) {
            player.damage(9.0)
            player.addPotion(PotionEffectType.SLOWNESS, 10, 3)
            player.addPotion(PotionEffectType.WITHER, 30, 1)

            player.sendMsg("&4체력 9칸이 깎이고 속도 감속 IV(10초), 위더 II(30초)가 지급되었습니다!!!")
            player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_SHOOT, 1f, 1f)
        }
    }

    @EventHandler
    fun onSwapOfHand(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        val offHandItem = event.offHandItem
        val now = System.currentTimeMillis() / 1000
        if (offHandItem.getStringId(toolKeyId) != itemId[5]) return

        event.setOffHandItem(null)
        isUseType5[player.uniqueId] = now + 300
        player.addPotion(PotionEffectType.FIRE_RESISTANCE, 300, 0)

        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f)
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player?: return
        val victim = event.entity as? LivingEntity?: return
        val now = System.currentTimeMillis() / 1000

        if ((isUseType5[attacker.uniqueId]?: 0L) < now) return
        victim.fireTicks = 180
    }
}