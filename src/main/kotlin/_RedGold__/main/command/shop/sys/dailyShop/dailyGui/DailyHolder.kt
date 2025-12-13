package _RedGold__.main.command.shop.sys.dailyShop.dailyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class DailyHolder(
    val buyTimes: MutableList<Int>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextMidnight = nowKST.withHour(0).withMinute(0).withSecond(0).withNano(0)

        if (!nextMidnight.isAfter(nowKST)) nextMidnight = nextMidnight.plusDays(1)

        val remainingSeconds = Duration.between(nowKST, nextMidnight).seconds
        val hours = remainingSeconds / 3600
        val minutes = (remainingSeconds % 3600) / 60

        return Bukkit.createInventory(this, 3 * 9, "일일 상점(초기화 까지 ${hours}시간 ${minutes}분 남음)")
    }
}