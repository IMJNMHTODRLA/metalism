package _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CosmeticConst {
    fun getDisplayName(i: Int, enum: CosmeticEnum) = when (val rawName = enum.link[i]) {
        is Pair<*, *> -> rawName.second.toString()
        else -> rawName.toString()
    }

    fun setNoHave(i: Int, enum: CosmeticEnum): ItemStack {
        val displayName = when (val rawName = enum.link[i]) {
            is Pair<*, *> -> rawName.second.toString()
            else -> rawName.toString()
        }

        return getItem(
            Material.BOOK,
            displayName,
            listOf(
                "",
                PREFIX,
                "&c&l[장착 불가]",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                "$displayName &f&l${enum.typeName}(은)는 ${enum.where[i]}에서 획득이 가능합니다.",
            )
        )
    }

    fun setHave(i: Int, enum: CosmeticEnum, isEquip: Boolean): ItemStack {
        val equipTitle = if (isEquip) "&a&l[선택됨]" else ""
        val displayName = when (val rawName = enum.link[i]) {
            is Pair<*, *> -> rawName.second.toString()
            else -> rawName.toString()
        }

        return getItem(
            Material.BOOK,
            "$displayName $equipTitle",
            listOf(
                "",
                PREFIX,
                "&a&l[좌클릭]: &f&l장착",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                "$displayName &f&l${enum.typeName}(은)는 ${enum.where[i]}에서 획득이 가능합니다.",
            )
        ).apply { if (isEquip) enchantEffect() }
    }
}