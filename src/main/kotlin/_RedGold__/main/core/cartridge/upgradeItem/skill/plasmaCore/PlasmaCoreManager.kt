package _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore

import _RedGold__.main.core.cartridge.upgradeItem.IsCancelInteract
import _RedGold__.main.functions.*
import org.bukkit.inventory.ItemStack

fun ItemStack.updatePlasmaCore() {
    modifyMeta {
        if (getBooleanId(isPlasmaCoreNameSpace) != true) return

        if (getIntegerId(plasmaCoreVersionNameSpace) != PLASMA_CORE_VERSION)
            setIntegerId(plasmaCoreVersionNameSpace, PLASMA_CORE_VERSION)

        if (getBooleanId(IsCancelInteract) == null)
            setBooleanId(IsCancelInteract, true)

        if (getStringId(plasmaCoreTypeNameSpace) == null)
            setStringId(plasmaCoreTypeNameSpace, PlasmaCoreTypeEnum.getRandom().name)

        if (getStringId(plasmaCoreEnergyNameSpace) == null)
            setStringId(plasmaCoreEnergyNameSpace, PlasmaCoreEnergyEnum.getRandom().name)
    }
}

fun ItemStack.isPlasmaCore(disableUpdate: Boolean = false): Boolean {
    if (!disableUpdate) updatePlasmaCore()

    val isReinforce = itemMeta.getBooleanId(isPlasmaCoreNameSpace)?: false
    return isReinforce
}

fun randomPlasmaCoreItem(amount: Int, vararg second: PlasmaCoreEnergyEnum) = plasmaCoreItemList
    .filter { it.energy in second }
    .random()
    .item(amount)
