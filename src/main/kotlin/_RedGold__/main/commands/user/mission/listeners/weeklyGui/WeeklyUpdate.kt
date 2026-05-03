package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent

@RequireListener
class WeeklyUpdate : Listener {
    @EventHandler
    fun onMission2(event: BlockPlaceEvent) = WeeklyConst.mission(event.player, 2)

    @EventHandler
    fun onMission3(event: BlockBreakEvent) = WeeklyConst.mission(event.player, 3)

    @EventHandler
    fun onMission4(event: EntityDeathEvent) {
        WeeklyConst.mission(event.entity.killer?: return, 4)
    }

    @EventHandler
    fun onMission5(event: PlayerDeathEvent) {
        WeeklyConst.mission(event.entity.killer?: return, 5)
    }
}