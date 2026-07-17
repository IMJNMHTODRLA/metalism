package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.killGui

import _RedGold__.main.Main.Gacha.GACHA_POINT_TO_GOLD_TIMES
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
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

            fun buy(id: Int, removePoint: Long) {
                val ticketPoint = getData(plugin, player, "ticket/point").toInt()

                if (ticketPoint < removePoint) {
                    player.fail("&c뽑기 포인트가 부족합니다. 필요 뽑기 포인트: ${(removePoint - ticketPoint).toFormat()} 뽑기 포인트")
                    return
                }

                saveData(plugin, player, "ticket/point", ticketPoint - removePoint)
                saveData(plugin, player, "kill_sound", id)
                applyKill[player.uniqueId] = id

                addHoldGold(plugin, ticketPoint * GACHA_POINT_TO_GOLD_TIMES)

                player.good("&a킬 사운드 구매가 완료되었습니다.")
                KillGui(plugin).openGui(player, 0f)
            }

            fun preview(sound: Sound?, pitch: Float = 1.0f) {
                player.playSound(player.location, sound?: return, 1f, pitch)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 0)
                    11 -> buy(1, 14)
                    12 -> buy(2, 14)
                    13 -> buy(3, 14)
                    14 -> buy(4, 14)
                    15 -> buy(5, 14)
                    16 -> buy(6, 14)

                    19 -> buy(7, 14)
                    20 -> buy(8, 15)
                    21 -> buy(9, 14)
                    22 -> buy(10, 19)
                    23 -> buy(11, 16)
                    24 -> buy(12, 18)
                    25 -> buy(13, 14)
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