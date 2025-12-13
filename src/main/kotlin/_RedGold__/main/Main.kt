package _RedGold__.main

import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.load.PreLoad
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime

class Main : JavaPlugin() {
    object Event {
        const val EVENT_NAME = "&c&l난이도 챌린지"
        const val EVENT_CODE = "randomEffect"
        const val EVENT_ITEM = "potion"
        val START_TIME: LocalDateTime = LocalDateTime.of(2025, 12, 7, 12, 0, 0)
        val END_TIME: LocalDateTime = LocalDateTime.of(3025, 12, 13, 10, 0, 0)
    }

    override fun onEnable() {
        if (!isFileExists(this, "server_gold", "hold.data")) WriteSave(this, "server_gold", "hold.data", "0")
        if (!isFileExists(this, "server_gold", "make.data")) WriteSave(this, "server_gold", "make.data", "0")

        PreLoad.loadClass(this)
    }

    override fun onDisable() {

    }
}
