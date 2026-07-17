package _RedGold__.main.core.cartridge.upgradeItem.skill.mystery

import _RedGold__.main.core.cartridge.upgradeItem.IsCancelInteract
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.modifyMeta
import _RedGold__.main.functions.setBooleanId
import _RedGold__.main.functions.setIntegerId
import _RedGold__.main.functions.setStringId
import org.bukkit.Material

data class MysteryInfoData(
    val type: MysteryTypeEnum,
    val status: MysteryStatusEnum
) {
    fun item(amount: Int = 1) = getItem(
        Material.PAPER,
        "&8&l[${status.colorName}&8&l] ${type.colorName} &f&l플라즈마 코어",
        listOf("", "&e&l카트리지의 스킬 강화 재료에 사용됩니다."), amount
    ).modifyMeta {
        setCustomModelData(CUSTOM_MODEL_PREFIX + type.intId + status.intId)
        //PREFIX(26) + type(0~99) + status(0~99)

        setBooleanId(IsCancelInteract, true)
        setStringId(mysteryTypeNameSpace, type.name)
        setStringId(mysteryEnergyNameSpace, status.name)

        setBooleanId(isMysteryNameSpace, true)
        setIntegerId(mysteryVersionNameSpace, MYSTERY_VERSION)
    }
}