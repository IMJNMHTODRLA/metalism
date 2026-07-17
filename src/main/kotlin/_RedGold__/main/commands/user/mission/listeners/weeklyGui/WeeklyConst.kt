package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum.WEEKLY
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.weeklyMissionInfoList
import org.bukkit.entity.Player

object WeeklyConst {
    private const val TOTAL_WEEKLY = 6

    fun mission(player: Player, id: Int, action: () -> Unit = {}) =
        GlobalConst.missionClear(player, WEEKLY, id, action) {
            if (id == TOTAL_WEEKLY) return@missionClear

            GlobalConst.missionClear(player, WEEKLY, TOTAL_WEEKLY)
        }
}