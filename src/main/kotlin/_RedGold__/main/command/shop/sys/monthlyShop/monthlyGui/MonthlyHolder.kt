package _RedGold__.main.command.shop.sys.monthlyShop.monthlyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class MonthlyHolder(
    val isBuy: Boolean
) : InventoryHolder {
    override fun getInventory(): Inventory {
        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

        var nextMonthly = nowKST.withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        // 이미 지났으면 다음 달 1일로
        if (!nextMonthly.isAfter(nowKST)) {
            nextMonthly = nextMonthly.plusMonths(1)
        }

        val remainingSeconds = Duration.between(nowKST, nextMonthly).seconds
        val remainingDays = remainingSeconds / (24 * 60 * 60)
        val hours = (remainingSeconds % (24 * 60 * 60)) / 3600
        val minutes = (remainingSeconds % 3600) / 60

        return Bukkit.createInventory(this, 3 * 9, "월간 상점(초기화 까지 ${remainingDays}일 ${hours}시간 ${minutes}분 남음)")
    }
}