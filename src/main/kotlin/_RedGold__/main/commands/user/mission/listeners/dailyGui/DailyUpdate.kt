package _RedGold__.main.commands.user.mission.listeners.dailyGui

import _RedGold__.main.commands.user.mission.listeners.achievementGui.AchievementConst
import _RedGold__.main.commands.user.mission.listeners.weeklyGui.WeeklyConst
import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent

@RequireListener
class DailyUpdate : Listener {
    @EventHandler
    fun onMission0(event: PlayerJoinEvent) = DailyConst.mission(event.player, 0) {
        WeeklyConst.mission(event.player, 0)
        AchievementConst.mission(event.player, 0)
        AchievementConst.mission(event.player, 1)
        AchievementConst.mission(event.player, 2)
    }

    @EventHandler
    fun onMission2(event: BlockPlaceEvent) = DailyConst.mission(event.player, 2)

    @EventHandler
    fun onMission3(event: BlockBreakEvent) = DailyConst.mission(event.player, 3)

    @EventHandler
    fun onMission4(event: EntityDeathEvent) {
        DailyConst.mission(event.entity.killer?: return, 4)
    }

    @EventHandler
    fun onMission5(event: PlayerDeathEvent) {
        DailyConst.mission(event.entity.killer?: return, 5)
    }
}