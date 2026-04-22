package _RedGold__.main.function

import _RedGold__.main.functions.Color.gc
import org.bukkit.Bukkit
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private val logFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    fun info(msg: String) {
        val now = logFormatter.format(Date())
        val timestamp = System.currentTimeMillis()

        Bukkit.getConsoleSender().sendMessage(gc("&f&l[INFO ${now}] $msg &7&l${timestamp}"))
    }

    fun warn(msg: String) {
        val now = logFormatter.format(Date())
        val timestamp = System.currentTimeMillis()

        Bukkit.getConsoleSender().sendMessage(gc("&e&l[WARN ${now}] &f&l$msg &7&l${timestamp}"))
    }

    fun error(msg: String) {
        val now = logFormatter.format(Date())
        val timestamp = System.currentTimeMillis()

        Bukkit.getConsoleSender().sendMessage(gc("&c&l[ERROR ${now}] &f&l$msg &7&l${timestamp}"))
    }
}