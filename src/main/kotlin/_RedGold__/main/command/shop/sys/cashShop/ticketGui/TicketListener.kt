package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.itemDamage
import _RedGold__.main.function.Gui.itemPotion
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
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
import java.security.SecureRandom

@RequireJavaPlugin
@RequireListener
class TicketListener(private val plugin: JavaPlugin) : Listener {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2948FC")}§l§oM
        ${rgb("2F4BFC")}§l§oE
        ${rgb("344FFC")}§l§oT
        ${rgb("3A53FD")}§l§oA
        ${rgb("3F57FD")}§l§oL
        ${rgb("455AFD")}§l§oI
        ${rgb("4A5EFD")}§l§oS
        ${rgb("5062FD")}§l§oM 
        ${rgb("5B69FE")}§l§oC
        ${rgb("606DFE")}§l§oA
        ${rgb("6671FE")}§l§oS
        ${rgb("6B75FE")}§l§oH 
        ${rgb("767CFE")}§l§oS
        ${rgb("7C80FF")}§l§oH
        ${rgb("8184FF")}§l§oO
        ${rgb("8787FF")}§l§oP
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    private val random = SecureRandom()

    private val maxDeath = 13
    private val maxKill = 13
    private val maxJoin = 13
    private val maxStyle = 14

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is TicketHolder) {
            val player = event.whoClicked as Player
            val gui = event.inventory
            val holder = gui.holder as TicketHolder
            val clickType = event.click
            val slot = event.slot
            val isRoulette = holder.isRoulette

            val buyTimes = getData(plugin, player, "ticket/buy").toInt()
            val getTicket = getData(plugin, player, "ticket/get").toInt()

            event.isCancelled = true

            if (slot != 13) return

            if (clickType == ClickType.LEFT) {
                if (isRoulette) return

                if (buyTimes >= 10) {
                    player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 주에 구매해주세요."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                val cash = getData(plugin, player, "cash").toLong()

                if (cash < 100) {
                    player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: ${(100 - cash).toFormat()}캐시"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "cash", cash - 100)
                addHoldGold(plugin, 1_000_000)

                saveData(plugin, player, "ticket/buy", buyTimes + 1)
                saveData(plugin, player, "ticket/get", getTicket + 1)
                TicketGui(plugin).openGui(player, 0f)
            } else if (clickType == ClickType.RIGHT) {
                if (isRoulette) return

                if (getTicket <= 0) {
                    player.sendMessage(gc("&c뽑기권이 부족합니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "ticket/get", getTicket - 1)
                holder.isRoulette = true

                val background = getItem(
                    "magenta_stained_glass_pane",
                    prefix
                )

                for (i in 0 until gui.size) gui.setItem(i, background)

                val resultValue: MutableList<String> = mutableListOf()
                for (i in 0..4) {
                    val successType = random.nextInt(2)
                    //0 = fail, 1 = success
                    val cosmeticType = random.nextInt(500)
                    //00~49 = 칭호, 50~149 = 접속, 150~324, 킬, 325~499
                    val cosmeticNum = when(cosmeticType) {
                        in 0..49 -> random.nextInt(maxStyle)
                        in 50..149 -> random.nextInt(maxJoin)
                        in 150..324 -> random.nextInt(maxKill)
                        in 325..499 -> random.nextInt(maxDeath)
                        else -> 0
                    }

                    val all = "$successType|$cosmeticType|$cosmeticNum"
                    resultValue.add(all)
                }
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder is TicketHolder) {
            val player = event.player as Player
            val holder = event.inventory.holder as TicketHolder
            val isPreviewing = holder.isPreviewing

            if (isPreviewing) {
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    player.playSound(player.location, Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f)
                    TicketGui(plugin).openGui(player, 0f)
                }, 1)
            }
        }
    }
}