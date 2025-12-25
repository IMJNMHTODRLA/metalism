package _RedGold__.main.command.shop.sys.cashShop.deathGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.itemDamage
import _RedGold__.main.function.Gui.itemPotion
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.KillRespawn.ChatApply.applyDeath
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.ShulkerBox
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType

@RequireJavaPlugin
@RequireListener
class DeathListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is DeathHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            event.isCancelled = true

            fun buy(id: Int, removeCash: Long) {
                val cash = getData(plugin, player, "cash").toLong()

                if (cash < removeCash) {
                    player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: ${(removeCash - cash).toFormat()}캐시"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "cash", cash - removeCash)
                saveData(plugin, player, "death_sound", id)
                applyDeath[player.uniqueId] = id

                addHoldGold(plugin, removeCash * 10_000)

                player.sendMessage(gc("&a사망 사운드 구매가 완료되었습니다."))

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                DeathGui(plugin).openGui(player, 0f)
            }

            fun preview(sound: Sound?, pitch: Float = 1.0f) {
                player.playSound(player.location, sound?: return, 1f, pitch)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 0)
                    11 -> buy(1, 400)
                    12 -> buy(2, 400)
                    13 -> buy(3, 400)
                    14 -> buy(4, 450)
                    15 -> buy(5, 450)
                    16 -> buy(6, 450)

                    19 -> buy(7, 450)
                    20 -> buy(8, 500)
                    21 -> buy(9, 550)
                    22 -> buy(10, 400)
                    23 -> buy(11, 400)
                    24 -> buy(12, 500)
                    25 -> buy(13, 800)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview(null)
                    11 -> preview(Sound.AMBIENT_UNDERWATER_ENTER)
                    12 -> preview(Sound.AMBIENT_CAVE)
                    13 -> preview(Sound.WEATHER_RAIN)
                    14 -> preview(Sound.ENTITY_COW_DEATH)
                    15 -> preview(Sound.ENTITY_BAT_DEATH)
                    16 -> preview(Sound.ENTITY_PIG_DEATH)

                    19 -> preview(Sound.BLOCK_ANVIL_LAND)
                    20 -> preview(Sound.ITEM_TOTEM_USE, 2.0f)
                    21 -> preview(Sound.ENTITY_GENERIC_EXPLODE)
                    22 -> preview(Sound.ENTITY_GENERIC_EAT)
                    23 -> preview(Sound.ENTITY_GENERIC_EXTINGUISH_FIRE)
                    24 -> preview(Sound.BLOCK_VAULT_BREAK)
                    25 -> preview(Sound.MUSIC_CREDITS)
                }
                return
            }
        }
    }
}