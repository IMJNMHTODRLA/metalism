package _RedGold__.main.commands.mission.sys.dailyGui

import _RedGold__.main.commands.mission.sys.achievementGui.AchievementUpdate
import _RedGold__.main.commands.mission.sys.dailyGui.DailyUpdate.DailyUpdate.isGet
import _RedGold__.main.commands.mission.sys.weeklyGui.WeeklyUpdate
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireJavaPlugin
@RequireListener
class DailyUpdate(private val plugin: JavaPlugin) : Listener {
    object DailyUpdate {
        val isGet: MutableMap<UUID, MutableList<Int>> = mutableMapOf()
    }

    private fun Player.isClear(t: Int, max: Int, mission: String, sound: Float = 1f) {
        val uuid = this.uniqueId
        if (isGet[uuid]?.contains(t) == true) return

        val data = getData(plugin, this, "mission/daily/progress/$t").toInt()
        val missionAll: Int
        if (data < max) {
            saveData(plugin, this, "mission/daily/progress/$t", data + 1)
            if (data + 1 == max) {
                this.sendMessage(gc("&f&l미션 &a&l\"$mission\"&f&l(을)를 클리어 하였습니다."))
                this.playSound(this.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, sound, 2f)

                missionAll = getData(plugin, this, "mission/daily/progress/6").toInt()
                saveData(plugin, this, "mission/daily/progress/6", missionAll + 1)
                if (missionAll + 1 == 5) this.sendMessage(gc("&f&l미션 &a&l\"일일 미션을 5회 클리어\"&f&l(을)를 클리어 하였습니다."))
            }
            return
        }

        if (isGet[uuid] == null) isGet[uuid] = mutableListOf(t)
        else isGet[uuid]!!.add(t)
    }

    @EventHandler
    fun onMission0(event: PlayerJoinEvent) {
        val player = event.player
        player.isClear(0, 1, "일일 접속", 0f)
        if (isGet[player.uniqueId]?.contains(0) != true) {
            AchievementUpdate(plugin).onMission0A1A2A3(player)
            WeeklyUpdate(plugin).onMission0(player)
            return
        }
    }

    fun onMission1(player: Player) {
        player.isClear(1, 1, "일일 상점 아이템 구매")
        if (isGet[player.uniqueId]?.contains(1) != true) WeeklyUpdate(plugin).onMission1(player)
    }

    @EventHandler
    fun onMission2(event: BlockBreakEvent) {
        val player = event.player
        player.isClear(2, 16, "블록 파괴를 16회")
    }

    @EventHandler
    fun onMission3(event: BlockPlaceEvent) {
        val player = event.player
        player.isClear(3, 16, "블록 설치를 16회")
    }

    @EventHandler
    fun onMission4(event: EntityDeathEvent) {
        val attacker = event.entity.killer?: return
        val victim = event.entity

        if (victim.type == EntityType.WITHER_SKELETON) attacker.isClear(4, 3, "위더 스켈레톤 처치를 3회")
    }

    @EventHandler
    fun onMission5(event: PlayerDeathEvent) {
        val attacker = event.entity.killer
        attacker?.isClear(5, 1, "플레이어 처치를 1회")
    }
}