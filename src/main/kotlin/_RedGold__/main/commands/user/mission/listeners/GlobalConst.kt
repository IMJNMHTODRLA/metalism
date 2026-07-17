package _RedGold__.main.commands.user.mission.listeners

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.dataManager.MissionData
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionInfoData
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionRewardData
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GlobalConst {
    fun getSlot(n: Int) = n + 10 + (n / 7 * 2)
    fun getId(n: Int) = ((n - 10) / 9 * 7) + ((n - 10) % 9)

    fun missionClear(player: Player, type: MissionEnum, i: Int, action: () -> Unit = {}, clearAction: () -> Unit = {}) {
        val data = player.data.missionMap[type]?.get(i)?: return
        val info = type.infoLink.getOrNull(i)?: return

        val isClear = data.progress >= info.max
        if (isClear) return

        data.progress += 1
        action()

        if (data.progress >= info.max) {
            player.good("&a미션 &6&l\"${info.title}\"&f&l을(를) 클리어 하였습니다.")
            clearAction()
        }
    }

    fun missionReward(player: Player, type: MissionEnum, i: Int): Boolean {
        val data = player.data.missionMap[type]?.get(i)?: return false
        val info = type.infoLink.getOrNull(i)?: return false
        val reward = type.rewardLink.getOrNull(i)?: return false

        if (data.isClaim) {
            player.fail("&c이미 보상을 획득 하였습니다.")
            return false
        }

        if (data.progress < info.max) {
            player.fail("&c${info.max - data.progress}회 부족합니다.")
            return false
        }

        when {
            reward.gold != 0L -> player.data.gold += reward.gold
            reward.crystal != 0 -> player.data.crystal += reward.crystal
            reward.item.isNotEmpty() -> reward.item.forEach { player.inv += it }
            reward.style != 0 -> player.data.addCosmetic(reward.style, CosmeticEnum.STYLE, false)
        }

        data.isClaim = true

        player.sendMsg("&a보상 획득이 완료 되었습니다.")
        player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f)
        return true
    }

    fun setMission(player: Player, i: Int, type: MissionEnum): ItemStack {
        val data = player.data.missionMap[type]?.get(i)?: return BACKGROUND
        val info = type.infoLink.getOrNull(i)?: return BACKGROUND

        val isClaim = data.isClaim
        val isClear = data.progress >= info.max

        val itemId = when {
            isClaim -> Material.NETHERITE_INGOT
            isClear -> Material.GOLD_INGOT
            else -> Material.GOLD_NUGGET
        }

        val title = when {
            isClaim -> "&f&l${info.title}&a&l 클리어 완료! (&7&l이미 보상을 획득 하였습니다.)"
            isClear -> "&f&l${info.title}&a&l 클리어 완료!&7&l (보상 획득이 가능합니다.)"
            else -> "&f&l${info.title} &e&l(${data.progress}/${info.max})"
        }

        return getItem(itemId, title, listOf("", "&6&l보상: ${info.reward}"))
    }
}