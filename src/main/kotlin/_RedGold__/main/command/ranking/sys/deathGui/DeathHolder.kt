package _RedGold__.main.command.ranking.sys.deathGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import kotlin.math.floor

class DeathHolder(
    var page: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        val now = System.currentTimeMillis() / 1000
        val checkingFuck = waitUpdate - now

        val minute = checkingFuck / 60
        val second = checkingFuck % 60

        return Bukkit.createInventory(this, 6 * 9, "사망 순위($page) 새로고침까지 ${minute}분 ${second}초)")
    }
}