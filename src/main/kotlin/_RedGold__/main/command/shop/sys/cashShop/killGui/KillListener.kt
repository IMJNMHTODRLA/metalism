package _RedGold__.main.command.shop.sys.cashShop.killGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.KillRespawn.ChatApply.applyDeath
import _RedGold__.main.sys.KillRespawn.ChatApply.applyKill
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class KillListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is KillHolder) {
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
                saveData(plugin, player, "kill_sound", id)
                applyKill[player.uniqueId] = id

                addHoldGold(plugin, removeCash * 10_000)

                player.sendMessage(gc("&a킬 사운드 구매가 완료되었습니다."))

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                KillGui(plugin).openGui(player, 0f)
            }

            fun preview(sound: Sound?, pitch: Float = 1.0f) {
                player.playSound(player.location, sound?: return, 1f, pitch)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 0)
                    11 -> buy(1, 650)
                    12 -> buy(2, 600)
                    13 -> buy(3, 600)
                    14 -> buy(4, 600)
                    15 -> buy(5, 650)
                    16 -> buy(6, 650)

                    19 -> buy(7, 650)
                    20 -> buy(8, 700)
                    21 -> buy(9, 650)
                    22 -> buy(10, 950)
                    23 -> buy(11, 800)
                    24 -> buy(12, 900)
                    25 -> buy(13, 700)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview(null)
                    11 -> preview(Sound.ITEM_MACE_SMASH_GROUND_HEAVY)
                    12 -> preview(Sound.BLOCK_HONEY_BLOCK_FALL)
                    13 -> preview(Sound.BLOCK_SLIME_BLOCK_BREAK)
                    14 -> preview(Sound.ENTITY_ZOMBIE_DEATH)
                    15 -> preview(Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR)
                    16 -> preview(Sound.ENTITY_ZOGLIN_DEATH)

                    19 -> preview(Sound.BLOCK_ANVIL_USE)
                    20 -> preview(Sound.UI_TOAST_CHALLENGE_COMPLETE)
                    21 -> preview(Sound.ENTITY_SHEEP_AMBIENT)
                    22 -> preview(Sound.ITEM_GOAT_HORN_SOUND_1)
                    23 -> preview(Sound.ITEM_TRIDENT_RIPTIDE_1)
                    24 -> preview(Sound.ITEM_TRIDENT_THUNDER)
                    25 -> preview(Sound.ENTITY_GENERIC_DRINK)
                }
                return
            }
        }
    }
}