package _RedGold__.main.commands.user.mission.listeners

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.playerData.dataManager.MissionData
import _RedGold__.main.managers.playerData.variableManager.MissionEnum
import _RedGold__.main.managers.playerData.variableManager.MissionInfo
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object GlobalConst {
    fun getSlot(n: Int) = n + 10 + (n / 7 * 2)

    fun missionClear(type: MissionEnum, info: MissionInfo, action: () -> Unit) {
        //TODO: 미션 클리어
    }

    fun setMission(data: MissionData, info: MissionInfo): ItemStack {
        val isClaim = data.isClaim
        val isClear = data.progress >= info.max

        val itemId = when {
            isClaim -> Material.NETHERITE_INGOT
            isClear -> Material.GOLD_INGOT
            else -> Material.GOLD_NUGGET
        }

        val title = when {
            isClaim -> "&f&l${info.title} &a&l클리어 완료! (&7&l이미 보상을 획득 하였습니다.)"
            isClear -> "&f&l${info.title} &a&l클리어 완료!&7&l (보상 획득이 가능합니다.)"
            else -> "&f&l${info.title} &e&l(${data.progress}/${info.max})"
        }

        return getItem(itemId, title, listOf("", "&6&l보상: ${info.reward}"))
    }
}