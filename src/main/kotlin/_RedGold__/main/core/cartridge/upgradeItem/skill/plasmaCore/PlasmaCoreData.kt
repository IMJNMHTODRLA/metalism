package _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore

import _RedGold__.main.core.cartridge.upgradeItem.IsCancelInteract
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.modifyMeta
import _RedGold__.main.functions.setBooleanId
import _RedGold__.main.functions.setIntegerId
import _RedGold__.main.functions.setStringId
import org.bukkit.Material

data class PlasmaCoreInfoData(
    val type: PlasmaCoreTypeEnum,
    val energy: PlasmaCoreEnergyEnum
) {
    fun item(amount: Int = 1) = getItem(
        Material.PAPER,
        "&8&l[${energy.colorName}&8&l] ${type.colorName} &f&l플라즈마 코어",
        listOf("", "&e&l카트리지의 스킬 강화 재료에 사용됩니다."), amount
    ).modifyMeta {
        setCustomModelData(CUSTOM_MODEL_PREFIX + type.intId + energy.intId)
        //PREFIX(27) + type(0~99) + energy(0~99)

        setBooleanId(IsCancelInteract, true)
        setStringId(plasmaCoreTypeNameSpace, type.name)
        setStringId(plasmaCoreEnergyNameSpace, energy.name)

        setBooleanId(isPlasmaCoreNameSpace, true)
        setIntegerId(plasmaCoreVersionNameSpace, PLASMA_CORE_VERSION)
    }
}