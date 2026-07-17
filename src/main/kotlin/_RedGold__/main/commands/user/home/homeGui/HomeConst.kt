package _RedGold__.main.commands.user.home.homeGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.dataManager.HomeData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object HomeConst {
    fun getSlot(i: Int) = 2 + i
    fun getIndex(i: Int) = i - 2

    fun buyAmount(i: Int) = when(i) {
        0 -> 30_000
        1 -> 40_000
        2 -> 90_000
        3 -> 190_000
        4 -> 300_000

        else -> Long.MAX_VALUE
    }

    fun getNotBuyHome(i: Int) = getItem(
        Material.LIGHT_GRAY_BED,
        "&c&l${i + 1}&f&l번 홈",
        listOf("",
            "&c&l홈을 구매하지 않아 지정이 불가능 합니다.",
            "&7&l클릭 시 ${buyAmount(i).toFormat()} 골드를 소모하여 구매합니다.",
        )
    )

    fun getNotSetHome(i: Int) = getItem(
        Material.LIGHT_GRAY_BED,
        "&7&l${i + 1}&f&l번 홈",
        listOf("", "&7&l현재 지정된 홈이 없습니다.", "&7&l클릭 시 현 위치에 홈이 지정됩니다.")
    )

    fun getSetHome(i: Int, data: HomeData): ItemStack {
        val x = data.location?.x?.toFormat(2)?: "-"
        val y = data.location?.y?.toFormat(2)?: "-"
        val z = data.location?.z?.toFormat(2)?: "-"

        return getItem(
            Material.RED_BED,
            "&e&l${i + 1}&f&l번 홈",
            listOf(
                "",
                "&f&l지정된 홈 위치: &a&lx$x, y$y, z$z",
                "&7&l버리기 키를 누를 시 지정된 홈을 삭제합니다.",
                "&7&l클릭 시 지정된 홈으로 이동됩니다."
            )
        ).apply { enchantEffect() }
    }
}