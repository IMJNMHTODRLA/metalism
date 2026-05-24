package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.commands.user.mission.listeners.GlobalConst
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.modify
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum
import _RedGold__.main.managers.playerData.variableManager.missionManager.TOTAL_ACHIEVEMENT_MISSION
import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.achievementMissionInfoList
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class AchievementGui {
    fun openGui(player: Player, sound: Float = 1f) {
        val gui = AchievementHolder().inventory

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        repeat(TOTAL_ACHIEVEMENT_MISSION) { i ->
            val missionInfo = achievementMissionInfoList[i]
            val missionData = player.data.missionMap[MissionEnum.ACHIEVEMENT]?.get(i)?: return@repeat
            val slot = GlobalConst.getSlot(i)

            gui.item[slot] = GlobalConst.setMission(missionData, missionInfo)
        }

        gui.item[48] = getItem(
            Material.EMERALD,
            "&a&l일일 미션"
        )

        gui.item[49] = getItem(
            Material.DIAMOND,
            "&e&l주간 미션"
        )

        gui.item[50] = getItem(
            Material.DRAGON_EGG,
            "&d&l업적 미션"
        ).modify { enchantEffect() }

        player.sendSound(Sound.UI_LOOM_TAKE_RESULT, 1f, sound)
        player.inv + gui
    }
}