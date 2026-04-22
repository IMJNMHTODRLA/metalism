package _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum

class CosmeticHolder(
    val cosmeticEnum: CosmeticEnum
) : EasyHolder(6 * 9) {
    override fun title() = "치장품 관리"
}