package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum.WEEKLY
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.weeklyMissionInfoList
import org.bukkit.entity.Player

object WeeklyConst {
    private val totalWeekly = weeklyMissionInfoList[6]

    fun mission(player: Player, i: Int, action: () -> Unit = {}) =
        GlobalConst.missionClear(player, WEEKLY, weeklyMissionInfoList[i], action) {
            GlobalConst.missionClear(player, WEEKLY, totalWeekly)
        }
}