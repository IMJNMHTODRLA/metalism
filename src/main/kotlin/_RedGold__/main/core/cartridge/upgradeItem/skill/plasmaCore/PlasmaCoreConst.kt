package _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore

import _RedGold__.main.Main
import _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore.PlasmaCoreEnergyEnum.*
import _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore.PlasmaCoreTypeEnum.*
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.NamespacedKey

const val CUSTOM_MODEL_PREFIX = 27_00_00

const val IS_PLASMA_CORE_KEY = "is_plasma_core_cartridge"
@SetSlowInit val isPlasmaCoreNameSpace by lazy { NamespacedKey(Main.instance, IS_PLASMA_CORE_KEY) }

const val PLASMA_CORE_VERSION_KEY = "plasma_core_cartridge_version"
const val PLASMA_CORE_VERSION = 0
@SetSlowInit val plasmaCoreVersionNameSpace by lazy { NamespacedKey(Main.instance, PLASMA_CORE_VERSION_KEY) }

const val PLASMA_CORE_TYPE_KEY = "plasma_core_cartridge_type"
@SetSlowInit val plasmaCoreTypeNameSpace by lazy { NamespacedKey(Main.instance, PLASMA_CORE_TYPE_KEY) }

const val PLASMA_CORE_ENERGY_KEY = "plasma_core_cartridge_energy"
@SetSlowInit val plasmaCoreEnergyNameSpace by lazy { NamespacedKey(Main.instance, PLASMA_CORE_ENERGY_KEY) }

val maxPlasmaCoreType = PlasmaCoreTypeEnum.entries.size

val plasmaCoreItemList = listOf(
    PlasmaCoreInfoData(EXPLOSION, LOW),
    PlasmaCoreInfoData(EXPLOSION, MIDDLE),
    PlasmaCoreInfoData(EXPLOSION, HIGH),

    PlasmaCoreInfoData(FIRE, LOW),
    PlasmaCoreInfoData(FIRE, MIDDLE),
    PlasmaCoreInfoData(FIRE, HIGH),

    PlasmaCoreInfoData(MAGIC, LOW),
    PlasmaCoreInfoData(MAGIC, MIDDLE),
    PlasmaCoreInfoData(MAGIC, HIGH),
)