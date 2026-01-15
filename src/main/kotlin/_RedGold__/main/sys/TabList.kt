package _RedGold__.main.sys

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class TabList {
    private val colorList: Map<Int, String> = java.util.Map.of(
        0, "2444FC",
        1, "334EFC",
        2, "4258FD",
        3, "5162FD",
        4, "5F6DFE",
        5, "6E77FE",
        6, "7D81FF",
        7, "8C8BFF"
    )

    fun tabList(player: Player, count: Int) {
        val header = """
            ${rgb(colorList[(count + 0) % 8]!!)}§l§oM
            ${rgb(colorList[(count + 1) % 8]!!)}§l§oE
            ${rgb(colorList[(count + 2) % 8]!!)}§l§oT
            ${rgb(colorList[(count + 3) % 8]!!)}§l§oA
            ${rgb(colorList[(count + 4) % 8]!!)}§l§oL
            ${rgb(colorList[(count + 5) % 8]!!)}§l§oI
            ${rgb(colorList[(count + 6) % 8]!!)}§l§oS
            ${rgb(colorList[(count + 7) % 8]!!)}§l§oM
        """.trimIndent().replace("\n", "")

        val footer = gc("&f&lTPS: &a&l${Bukkit.getTPS()[0].toFormat(2)} &8&l| &f&l플레이어: &e&l${Bukkit.getOnlinePlayers().size}/100")
        player.setPlayerListHeaderFooter(gc("$header &8&l| &7&l시즌 1\n"), "\n$footer")
    }
}