package _RedGold__.main.function

import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

//@RequireJavaPlugin
class DatabaseManager(val plugin: JavaPlugin) {
    private var connection: Connection? = null

    init {
        connection = DriverManager.getConnection("jdbc:sqlite:${plugin.dataFolder}/database.db")
    }

    fun save(uuid: UUID, rootName: String, value: Any) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, Runnable {
            connection?.prepareStatement(
                "INSERT OR REPLACE INTO player_data (uuid, rootName, value) VALUES (?, ?, ?)"
            )?.use { pstmt ->
                pstmt.setString(1, uuid.toString())
                pstmt.setString(2, rootName)
                pstmt.setString(3, value.toString())
                pstmt.executeUpdate()
            }
        })
    }

    fun get(uuid: UUID, rootName: String): String {
        var result = ""

        connection?.prepareStatement(
            "SELECT value FROM player_data WHERE uuid = ? AND rootName = ?"
        )?.use { pstmt ->
            pstmt.setString(1, uuid.toString())
            pstmt.setString(2, rootName)

            pstmt.executeQuery().use { rs ->
                if (rs.next()) result = rs.getString("value")
            }
        }
        return result
    }
}