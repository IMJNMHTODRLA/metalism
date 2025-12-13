package _RedGold__.main.command.shop.sys.monthlyShop.monthlyGui

import _RedGold__.main.command.mission.sys.achievementGui.AchievementUpdate
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
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
class MonthlyListener(private val plugin: JavaPlugin) : Listener {
    private fun buy(player: Player, id: String, name: String, removeGold: Long) {
        val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
        val target = ItemStack(material)

        val gold = getData(plugin, player, "gold").toLong()

        if (gold >= removeGold) {
            player.inventory.addItem(target)

            player.sendMessage(gc("&a${name}을(를) 구매했습니다."))
            saveData(plugin, player, "gold", gold - removeGold)
            addHoldGold(plugin, removeGold)
            saveData(plugin, player, "monthly_shop", 1)

            AchievementUpdate(plugin).onMission3(player)
            AchievementUpdate(plugin).onMission4(player)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
            MonthlyGui(plugin).openGui(player)
        } else {
            player.sendMessage(gc("&c돈이 부족합니다. 필요 금액: ${(removeGold - gold).toFormat()}원"))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is MonthlyHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as MonthlyHolder
            val clickType = event.click
            val slot = event.slot
            val isBuy = holder.isBuy
            event.isCancelled = true

            if (slot in 12..14 && isBuy) {
                player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 달에 구매해주세요."))
                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                return
            }

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                when (slot) {
                    12 -> buy(player, "elytra", "겉날개", 1200000)
                    13 -> buy(player, "shulker_box", "셜커 상자", 1000000)
                    14 -> buy(player, "name_tag", "이름표", 100000)
                }
            }
        }
    }
}