package _RedGold__.main.command.ranking.sys.killGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class KillHolder(
    var page: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        val now = System.currentTimeMillis() / 1000
        val checkingFuck = waitUpdate - now

        val minute = checkingFuck / 60
        val second = checkingFuck % 60

        return Bukkit.createInventory(this, 6 * 9, "킬 순위($page) 새로고침까지 ${minute}분 ${second}초)")
    }
}