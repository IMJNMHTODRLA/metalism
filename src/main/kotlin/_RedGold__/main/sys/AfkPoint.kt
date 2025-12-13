package _RedGold__.main.sys

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

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
        loop()
    }

    private fun loop() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            for (player in Bukkit.getOnlinePlayers()) {
                val gold = getData(plugin, player, "gold").toLong()
                val cash = getData(plugin, player, "cash").toLong()

                player.sendMessage(gc("$prefix &a&l10분간 &f&l접속하여 &b&l3 캐시&f&l와 &6&l30,000 골드&f&l를 지급 하였습니다."))

                addMakeGold(plugin, 60000)
                saveData(plugin, player, "gold", gold + 30_000L)
                saveData(plugin, player, "cash", cash + 3L)
            }
        }, 0L, 20L * 60L * 10L)
    }
}