package _RedGold__.main.commands.user.boost.listeners.settings.vip.ggColorGui

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class GgColorListener(private val plugin: JavaPlugin) : Listener {
    private fun setDataByGG(player: Player, display: String, value: Int, page: Int) {
        saveData(plugin, player, "gg_color", value)

        player.sendMessage(gc("&f&l${display}(으)로 설정하였습니다."))

        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
        GgColorGui(plugin).openGui(player, page)
    }

    private fun preview(player: Player, color: String) {
        player.sendMessage(gc(
            "${getPlayerRankPrefix(player)} ${player.name}&f: ${color}GG"
        ))

        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f)
        player.closeInventory()
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is GgColorHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as GgColorHolder

            val slot = event.slot
            val clickType = event.click

            val page = holder.page
            val isPlus = holder.isPlusRank

            event.isCancelled = true

            if (slot == 27 && page == 2) {
                GgColorGui(plugin).openGui(player, 1)
                return
            }

            if (slot == 35 && page == 1) {
                GgColorGui(plugin).openGui(player, 2)
                return
            }

            if (clickType == ClickType.LEFT &&
                (slot in 11..25 && page == 1 && !isPlus) ||
                (slot in 10..12 && page == 2 && !isPlus)
            ) {
                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.5f)
                player.sendMessage(gc("&cVIP 전용 설정입니다."))
                return
            }

            if (clickType == ClickType.LEFT && page == 1) {
                when (slot) {
                    10 -> setDataByGG(player, "기본", 0, page)
                    11 -> setDataByGG(player, "검은색", 1, page)
                    12 -> setDataByGG(player, "어두운 파란색", 2, page)
                    13 -> setDataByGG(player, "어두운 초록색", 3, page)
                    14 -> setDataByGG(player, "어두운 청록색", 4, page)
                    15 -> setDataByGG(player, "어두운 빨간색", 5, page)
                    16 -> setDataByGG(player, "어두운 보라색", 6, page)
                    19 -> setDataByGG(player, "황금색", 7, page)
                    20 -> setDataByGG(player, "회색", 8, page)
                    21 -> setDataByGG(player, "어두운 회색", 9, page)
                    22 -> setDataByGG(player, "파란색", 10, page)
                    23 -> setDataByGG(player, "초록색", 11, page)
                    24 -> setDataByGG(player, "밝은 청록색", 12, page)
                    25 -> setDataByGG(player, "빨간색", 13, page)
                }
                return
            }

            if (clickType == ClickType.LEFT && page == 2) {
                when (slot) {
                    10 -> setDataByGG(player, "밝은 보라색", 14, page)
                    11 -> setDataByGG(player, "노란색", 15, page)
                    12 -> setDataByGG(player, "하얀색", 16, page)
                }
                return
            }

            if (clickType == ClickType.RIGHT && page == 1) {
                when (slot) {
                    10 -> preview(player, "&f")
                    11 -> preview(player, "&0&l")
                    12 -> preview(player, "&1&l")
                    13 -> preview(player, "&2&l")
                    14 -> preview(player, "&3&l")
                    15 -> preview(player, "&4&l")
                    16 -> preview(player, "&5&l")
                    19 -> preview(player, "&6&l")
                    20 -> preview(player, "&7&l")
                    21 -> preview(player, "&8&l")
                    22 -> preview(player, "&9&l")
                    23 -> preview(player, "&a&l")
                    24 -> preview(player, "&b&l")
                    25 -> preview(player, "&c&l")
                }
                return
            }

            if (clickType == ClickType.RIGHT && page == 2) {
                when (slot) {
                    10 -> preview(player, "&d&l")
                    11 -> preview(player, "&e&l")
                    12 -> preview(player, "&f&l")
                }
                return
            }
        }
    }
}