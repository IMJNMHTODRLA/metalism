package _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce

import _RedGold__.main.core.cartridge.upgradeItem.IsCancelInteract
import _RedGold__.main.functions.*
import org.bukkit.inventory.ItemStack

fun ItemStack.updateReinforce() {
    modifyMeta {
        if (getBooleanId(isReinforceNameSpace) != true) return

        if (getIntegerId(reinforceVersionNameSpace) != REINFORCE_VERSION)
            setIntegerId(reinforceVersionNameSpace, REINFORCE_VERSION)

        if (getBooleanId(IsCancelInteract) == null)
            setBooleanId(IsCancelInteract, true)

        if (getStringId(reinforceTypeNameSpace) == null)
            setStringId(reinforceTypeNameSpace, ReinforceTypeEnum.getRandom().name)

        if (getStringId(reinforceImportNameSpace) == null)
            setStringId(reinforceImportNameSpace, ReinforceImportEnum.getRandom().name)
    }
}

fun ItemStack.isReinforce(disableUpdate: Boolean = false): Boolean {
    if (!disableUpdate) updateReinforce()

    val isReinforce = itemMeta.getBooleanId(isReinforceNameSpace)?: false
    return isReinforce
}

fun randomReinforceItem(amount: Int, vararg second: ReinforceImportEnum) = reinforceItemList
    .filter { it.import in second }
    .random()
    .item(amount)
