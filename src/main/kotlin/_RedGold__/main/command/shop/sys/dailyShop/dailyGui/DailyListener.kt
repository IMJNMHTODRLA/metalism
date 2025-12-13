package _RedGold__.main.command.shop.sys.dailyShop.dailyGui

import _RedGold__.main.command.mission.sys.dailyGui.DailyUpdate
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Material.*
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom
import java.util.*

@RequireJavaPlugin
@RequireListener
class DailyListener(private val plugin: JavaPlugin) : Listener {
    private fun buy(player: Player, id: String, name: String, removeGold: Long, buyId: Int, buyTimes: Int, buyMax: Int) {
        val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
        val target = ItemStack(material)

        if (buyTimes >= buyMax) {
            player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 날에 구매해주세요."))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
            return
        }

        val gold = getData(plugin, player, "gold").toLong()

        if (gold >= removeGold) {
            player.inventory.addItem(target)

            player.sendMessage(gc("&a${name}을(를) 구매했습니다."))
            saveData(plugin, player, "gold", gold - removeGold)
            addHoldGold(plugin, removeGold)
            saveData(plugin, player, "daily_shop/$buyId", buyTimes + 1)

            DailyUpdate(plugin).onMission1(player)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
            DailyGui(plugin).openGui(player)
        } else {
            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(removeGold - gold).toFormat()}골드"))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is DailyHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as DailyHolder
            val clickType = event.click
            val slot = event.slot
            val buyTimes = holder.buyTimes
            event.isCancelled = true

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                when (slot) {
                    10 -> buy(player, "experience_bottle", "경험치 병", 4000, 0, buyTimes[0], 20)
                    11 -> buy(player, "firework_rocket", "폭죽 로켓", 4000, 1, buyTimes[1], 20)
                    12 -> buy(player, "apple", "사과", 1000, 2, buyTimes[2], 10)
                    13 -> buy(player, "ghast_tear", "가스트의 눈물", 10000, 3, buyTimes[3], 1)

                    14 -> {
                        if (buyTimes[4] >= 1) {
                            player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 날에 구매해주세요."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        if (gold >= 10000) {
                            val random = SecureRandom().nextInt(5000, 50000)

                            player.sendMessage(gc("&a${random.toLong().toFormat()}골드을(를) 획득 했습니다."))

                            saveData(plugin, player, "gold", gold - 10000 + random)
                            addHoldGold(plugin, 10000L)
                            addMakeGold(plugin, random.toLong())
                            saveData(plugin, player, "daily_shop/4", buyTimes[4] + 1)

                            player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 2f)
                            DailyUpdate(plugin).onMission1(player)
                            DailyGui(plugin).openGui(player)
                        } else {
                            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(10000 - gold).toFormat()}골드"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        }
                    }
                    15 -> {
                        if (buyTimes[5] >= 1) {
                            player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 날에 구매해주세요."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        if (gold >= 15000) {
                            player.sendMessage(gc("&a${player.level + 1}레벨로 증가했습니다."))

                            player.giveExpLevels(1)
                            saveData(plugin, player, "gold", gold - 15000)
                            addHoldGold(plugin, 15000L)
                            saveData(plugin, player, "daily_shop/5", buyTimes[5] + 1)

                            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
                            DailyUpdate(plugin).onMission1(player)
                            DailyGui(plugin).openGui(player)
                        } else {
                            player.sendMessage(gc("&c골드가 부족합니다. 필요 금액: ${(10000 - gold).toFormat()}골드"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        }
                    }
                    16 -> {
                        if (buyTimes[6] >= 1) {
                            player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 날에 구매해주세요."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        player.sendMessage(gc("&a10,000골드을(를) 획득 했습니다."))

                        saveData(plugin, player, "gold", gold + 10000)
                        addMakeGold(plugin, 10000L)
                        saveData(plugin, player, "daily_shop/6", buyTimes[6] + 1)
                        DailyUpdate(plugin).onMission1(player)

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
                        DailyGui(plugin).openGui(player)
                    }
                }
            }
        }
    }
}