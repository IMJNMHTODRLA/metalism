package _RedGold__.main.command.style.sys

import _RedGold__.main.command.mission.sys.achievementGui.AchievementGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Material.ENCHANTED_BOOK
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class StyleListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is StyleHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as StyleHolder
            val clickType = event.click
            val slot = event.slot
            val isBuy = holder.isBuy
            val rank = getPlayerRankPrefix(player)
            event.isCancelled = true

            fun select(id: Int, whereGoing: Int = 1) {
                if (!isBuy[id]) {
                    if (whereGoing == 0) AchievementGui(plugin).openGui(player)
                    else _RedGold__.main.command.shop.sys.cashShop.styleGui.StyleGui(plugin).openGui(player)
                    return
                }

                saveData(plugin, player, "style/apply", id)
                applyStyle[player.uniqueId] = id

                player.sendMessage(gc("&a칭호가 선택이 완료되었습니다."))

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                StyleGui(plugin).openGui(player, 0f)
            }

            fun preview(id: Int) {
                player.sendMessage(gc("${symmetry[id]} $rank ${player.name}&f: 칭호 테스트 메시지 입니다."))
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                player.closeInventory()
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> {
                        saveData(plugin, player, "style/apply", -1)
                        applyStyle[player.uniqueId] = -1
                        player.sendMessage(gc("&a칭호가 선택이 완료되었습니다."))

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                        StyleGui(plugin).openGui(player, 0f)
                    }
                    11 -> select(0, 0)
                    12 -> select(1, 0)
                    13 -> select(2, 0)
                    14 -> select(3, 0)
                    15 -> select(4, 0)
                    16 -> select(5)

                    19 -> select(6)
                    20 -> select(7)
                    21 -> select(8)
                    22 -> select(9)
                    23 -> select(10)
                    24 -> select(11)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> {
                        player.sendMessage(gc("$rank ${player.name}&f: 칭호 테스트 메시지 입니다."))
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                        player.closeInventory()
                    }
                    11 -> preview(0)
                    12 -> preview(1)
                    13 -> preview(2)
                    14 -> preview(3)
                    15 -> preview(4)
                    16 -> preview(5)

                    19 -> preview(6)
                    20 -> preview(7)
                    21 -> preview(8)
                    22 -> preview(9)
                    23 -> preview(10)
                    24 -> preview(11)
                }
                return
            }
        }
    }
}