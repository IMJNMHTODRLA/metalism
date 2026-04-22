package _RedGold__.main.listeners.playerRespawn

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.SPAWN_WORLD_LOCATION
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class PlayerRespawn(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if (!event.isBedSpawn) event.respawnLocation = SPAWN_WORLD_LOCATION

        val player = event.player
        player.sendMsg("&c&l당신은 플레이어에게 사망하였습니다.")
        player.sendMsg("&7&l죽은 위치로 돌아가기를 원한다면 /back 명령어를 사용해주세요.")

        player.isInvulnerable = true
        plugin.task(100) {
            if (player.isOnline) player.isInvulnerable = false
        }
    }
}