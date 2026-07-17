package _RedGold__.main.listeners.playerTabList

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.task
import _RedGold__.main.listeners.GlobalConst
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.Bukkit

@SetSlowInit
class PlayerTabListListener {
    @SetSlowInit
    fun startUpdateTabList() {
        task(0, 5) {
            val times = PlayerTabListValue.times
            val colorList = GlobalConst.COLOR_LIST
            val max = colorList.size

            val prefix = """
                ${colorList[(times + 0) % max].rgb()}§l§oM
                ${colorList[(times + 1) % max].rgb()}§l§oE
                ${colorList[(times + 2) % max].rgb()}§l§oT
                ${colorList[(times + 3) % max].rgb()}§l§oA
                ${colorList[(times + 4) % max].rgb()}§l§oL
                ${colorList[(times + 5) % max].rgb()}§l§oI
                ${colorList[(times + 6) % max].rgb()}§l§oS
                ${colorList[(times + 7) % max].rgb()}§l§oM
            """.trimIndent().replace("\n", "")

            val header = PlayerTabListConst.HEADER.fill("prefix" to prefix).gc()
            val footer = PlayerTabListConst.FOOTER.fill(
                "tps" to Bukkit.getTPS()[0].toFormat(1),
                "total_player" to Bukkit.getOnlinePlayers().size
            ).gc()

            Bukkit.getOnlinePlayers().forEach { player ->
                player.setPlayerListHeaderFooter(header, footer)
            }

            if (times == 0) PlayerTabListValue.times = 7 else PlayerTabListValue.times--
        }
    }
}