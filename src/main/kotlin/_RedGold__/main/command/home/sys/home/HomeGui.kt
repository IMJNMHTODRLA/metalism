package _RedGold__.main.command.home.sys.home

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class HomeGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, ) {
        val buyHome = mutableListOf<Int>()
        val saveHome = mutableListOf<String>()
        val homePrice = mutableListOf(20_000, 50_000, 100_000)

        for (i in 0..2) {
            buyHome.add(getData(plugin, player, "home/buy/$i").toInt())
            saveHome.add(getData(plugin, player, "home/save/$i"))
        }

        val gui = HomeHolder(buyHome, saveHome, homePrice).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("334EFC")}§l§oE
                ${rgb("3A53FD")}§l§oT
                ${rgb("4258FD")}§l§oA
                ${rgb("495DFD")}§l§oL
                ${rgb("5162FD")}§l§oI
                ${rgb("5868FE")}§l§oS
                ${rgb("5F6DFE")}§l§oM 
                ${rgb("6E77FE")}§l§oH
                ${rgb("767CFE")}§l§oO
                ${rgb("7D81FF")}§l§oM
                ${rgb("8586FF")}§l§oE
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        val itemNumList = listOf(3, 4, 5)
        val homePart = listOf(saveHome[0].split(";"), saveHome[1].split(";"), saveHome[2].split(";"))

        for (i in 0..2) {
            val homeValue =
                if (buyHome[i] == 0) 0
                else if (saveHome[i] == "n;n;n") 1
                else 2

            val bed = listOf("black_bed", "light_gray_bed", "red_bed")
            val title = listOf("&c&l${i + 1}&f&l번 홈", "&7&l${i + 1}&f&l번 홈", "&e&l${i + 1}&f&l번 홈")
            val description = listOf(
                listOf("", "&c&l현재 홈을 구매하지 않아, 홈을 지정할 수 없습니다.", "", "&f&l구매가: &a&l${homePrice[i]}원", "&7&l클릭 시 구매가 됩니다."),
                listOf("", "&f&l지정된 홈 위치: &7&l현재 홈이 지정되지 않았습니다.", "&7&l클릭 시 현 위치에 홈이 지정됩니다."),
                listOf("", "&f&l지정된 홈 위치: &a&lx${String.format("%.2f", homePart[i][0].toDoubleOrNull()?: 0.0)}, y${String.format("%.2f", homePart[i][1].toDoubleOrNull()?: 0.0)}, z${String.format("%.2f", homePart[i][2].toDoubleOrNull()?: 0.0)}", "&7&l버리기 키를 누를 시 현 위치에 홈이 지정됩니다.", "&7&l좌클릭(또는 우클릭) 시 지정된 홈으로 이동됩니다.")
            )

            gui.setItem(itemNumList[i], getItem(
                bed[homeValue],
                title[homeValue],
                description[homeValue]
            ).apply {if (homeValue == 2) addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})
        }

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
    }
}