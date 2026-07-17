package _RedGold__.main.commands.user.mission.listeners.dailyGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum.DAILY
import org.bukkit.entity.Player

object DailyConst {
    private const val TOTAL_DAILY = 6

    fun mission(player: Player, id: Int, action: () -> Unit = {}) =
        GlobalConst.missionClear(player, DAILY, id, action) {
            if (id == TOTAL_DAILY) return@missionClear

            GlobalConst.missionClear(player, DAILY, TOTAL_DAILY)
        }
}