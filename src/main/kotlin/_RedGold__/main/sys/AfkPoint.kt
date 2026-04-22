package _RedGold__.main.sys

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
class AfkPoint(private val plugin: JavaPlugin) {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("304CFC")}§l§oM
        ${rgb("3B54FD")}§l§oE
        ${rgb("475CFD")}§l§oT
        ${rgb("5264FD")}§l§oA
        ${rgb("5E6BFE")}§l§oL
        ${rgb("6973FE")}§l§oI
        ${rgb("757BFE")}§l§oS
        ${rgb("8083FF")}§l§oM
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    init {
        plugin.task(0, 20 * 60 * 10) {
            for (player in Bukkit.getOnlinePlayers()) {
                val gold = getData(plugin, player, "gold").toLong()

                player.sendMessage(gc("$prefix &a&l10분간 &f&l접속하여 &6&l30,000 골드&f&l를 획득 하였습니다!"))

                addMakeGold(plugin, 30000)
                saveData(plugin, player, "gold", gold + 30_000L)
            }
        }
    }
}