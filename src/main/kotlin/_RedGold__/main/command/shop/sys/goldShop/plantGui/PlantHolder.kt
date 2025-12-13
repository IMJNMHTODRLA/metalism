package _RedGold__.main.command.shop.sys.goldShop.plantGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.time.DayOfWeek
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

class PlantHolder(
    val sellTimes: MutableList<Int>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

        var nextWeekly = nowKST.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        // 이미 지났으면 다음 주로
        if (!nextWeekly.isAfter(nowKST)) nextWeekly = nextWeekly.plusWeeks(1)

        val remainingSeconds = Duration.between(nowKST, nextWeekly).seconds
        val remainingDays = remainingSeconds / (24 * 60 * 60)
        val hours = (remainingSeconds % (24 * 60 * 60)) / 3600
        val minutes = (remainingSeconds % 3600) / 60

        return Bukkit.createInventory(this, 4 * 9, "농작물 상점(초기화 까지 ${remainingDays}일 ${hours}시간 ${minutes}분 남음)")
    }
}