package _RedGold__.main.commands.user.menu.menuGui

import _RedGold__.main.commands.user.betting.Betting
import _RedGold__.main.commands.user.chest.Chest
import _RedGold__.main.commands.user.cosmetic.Cosmetic
import _RedGold__.main.commands.user.discord.Discord
import _RedGold__.main.commands.user.home.Home
import _RedGold__.main.commands.user.mission.Mission
import _RedGold__.main.commands.user.ranking.Ranking
import _RedGold__.main.commands.user.rtp.Rtp
import _RedGold__.main.commands.user.shop.Shop
import _RedGold__.main.commands.vip.enderchest.EnderChest
import _RedGold__.main.functions.onCommand
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MenuListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MenuHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        when (event.slot) {
            10 -> player.onCommand<Rtp>()
            11 -> player.onCommand<Shop>()
            12 -> player.onCommand<Ranking>()
            13 -> player.onCommand<Chest>()
            14 -> player.performCommand("boost") //TODO: 만들자1
            15 -> player.onCommand<Betting>()
            16 -> player.onCommand<Home>()

            19 -> player.performCommand("back") //TODO: 만들자2
            20 -> player.performCommand("event") //TODO: 만들자3
            21 -> player.onCommand<Mission>("daily")
            22 -> player.onCommand<Cosmetic>()
            23 -> player.onCommand<EnderChest>()
            24 -> player.onCommand<Discord>()
        }
    }
}