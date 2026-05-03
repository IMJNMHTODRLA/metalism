package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum.DAILY
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.achievementMissionInfoList
import org.bukkit.entity.Player

object AchievementConst {
    fun mission(player: Player, i: Int, action: () -> Unit = {}) =
        GlobalConst.missionClear(player, DAILY, achievementMissionInfoList[i], action)
}