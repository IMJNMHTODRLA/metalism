package _RedGold__.main.managers.playerData.variableManager.missionManager

import org.bukkit.inventory.ItemStack

data class MissionInfoData(
    val title: String,
    val max: Int,
    val reward: String
)

data class MissionRewardData(
    val gold: Long = 0,
    val crystal: Int = 0,
    val item: List<ItemStack> = emptyList(),
    val style: Int = 0
)