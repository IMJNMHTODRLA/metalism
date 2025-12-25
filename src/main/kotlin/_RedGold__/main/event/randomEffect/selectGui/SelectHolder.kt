package _RedGold__.main.event.randomEffect.selectGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectHolder(
    val openTime: Long,
    var isClose: Boolean = false
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "난이도 선택(2분 안에 선택을 해주세요.) - 난이도 선택시 선택횟수가 증가합니다.(선택 안함 빼고)")
    }
}