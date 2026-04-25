package _RedGold__.main.managers.reinforceManager

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.modifyMeta
import _RedGold__.main.functions.setStringId
import org.bukkit.Material

data class ReinforceInfoData(
    val material: Material,
    val type: ReinforceTypeEnum,
    val import: ReinforceImportEnum
) {
    fun item(amount: Int) = getItem(
        material,
        "&8&l[${import.color}${import.displayName}&8&l] ${type.color}${type.displayName} &f&l강화 아이템",
        listOf("", "&e&l뽑기 아이템의 스킬 강화 재료에 사용됩니다."), amount
    ) modifyMeta {
        setStringId(reinforceTypeNameSpace, type.name)
        setStringId(reinforceImportNameSpace, import.name)
    }

    inline val item get() = item(1)
}