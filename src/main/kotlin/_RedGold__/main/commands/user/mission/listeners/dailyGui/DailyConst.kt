package _RedGold__.main.commands.user.mission.listeners.dailyGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum.DAILY
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.dailyMissionInfoList
import org.bukkit.entity.Player

object DailyConst {
    private val totalDaily = dailyMissionInfoList[6]

    fun mission(player: Player, i: Int, action: () -> Unit = {}) =
        GlobalConst.missionClear(player, DAILY, dailyMissionInfoList[i], action) {
            GlobalConst.missionClear(player, DAILY, totalDaily)
        }
}