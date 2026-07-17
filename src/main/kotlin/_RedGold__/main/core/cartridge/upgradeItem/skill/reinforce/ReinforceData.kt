package _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce

import _RedGold__.main.core.cartridge.upgradeItem.IsCancelInteract
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.modifyMeta
import _RedGold__.main.functions.setBooleanId
import _RedGold__.main.functions.setIntegerId
import _RedGold__.main.functions.setStringId
import org.bukkit.Material

data class ReinforceInfoData(
    val type: ReinforceTypeEnum,
    val import: ReinforceImportEnum
) {
    fun item(amount: Int = 1) = getItem(
        Material.PAPER,
        "&8&l[${import.colorName}&8&l] ${type.colorName} &f&l강화 아이템",
        listOf("", "&e&l카트리지의 스킬 강화 재료에 사용됩니다."), amount
    ).modifyMeta {
        setCustomModelData(CUSTOM_MODEL_PREFIX + type.intId + import.intId)
        //PREFIX(28) + type(0~99) + import(0~99)

        setBooleanId(IsCancelInteract, true)
        setStringId(reinforceTypeNameSpace, type.name)
        setStringId(reinforceImportNameSpace, import.name)

        setBooleanId(isReinforceNameSpace, true)
        setIntegerId(reinforceVersionNameSpace, REINFORCE_VERSION)
    }
}