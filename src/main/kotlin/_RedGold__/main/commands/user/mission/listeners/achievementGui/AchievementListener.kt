package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.commands.user.mission.listeners.dailyGui.DailyGui
import _RedGold__.main.commands.user.mission.listeners.weeklyGui.WeeklyGui
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum
import _RedGold__.main.managers.playerData.variableManager.missionManager.TOTAL_ACHIEVEMENT_MISSION
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.achievementMissionInfoList
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.achievementMissionRewardList
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class AchievementListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is AchievementHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        when(
            val slot = event.slot
        ) {
            48 -> DailyGui().openGui(player)
            49 -> WeeklyGui().openGui(player)
            50 -> AchievementGui().openGui(player)

            else -> {
                val id = GlobalConst.getId(slot).takeIf {
                    it in 0..<TOTAL_ACHIEVEMENT_MISSION
                }?: return

                GlobalConst.missionReward(
                    player,
                    MissionEnum.ACHIEVEMENT,
                    id,
                ).trueRun { AchievementGui().openGui(player, 0f) }
            }
        }
    }
}