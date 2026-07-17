package _RedGold__.main.core.cartridge.upgradeItem.skill.mystery

import _RedGold__.main.Main
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.NamespacedKey

const val CUSTOM_MODEL_PREFIX = 26_00_00

const val IS_MYSTERY_KEY = "is_mystery_cartridge"
@SetSlowInit val isMysteryNameSpace by lazy { NamespacedKey(Main.instance, IS_MYSTERY_KEY) }

const val MYSTERY_VERSION_KEY = "mystery_cartridge_version"
const val MYSTERY_VERSION = 0
@SetSlowInit val mysteryVersionNameSpace by lazy { NamespacedKey(Main.instance, MYSTERY_VERSION_KEY) }

const val MYSTERY_TYPE_KEY = "mystery_cartridge_type"
@SetSlowInit
val mysteryTypeNameSpace by lazy { NamespacedKey(Main.instance, MYSTERY_TYPE_KEY) }

const val MYSTERY_ENERGY_KEY = "mystery_cartridge_energy"
@SetSlowInit val mysteryEnergyNameSpace by lazy { NamespacedKey(Main.instance, MYSTERY_ENERGY_KEY) }

val maxMysteryType = MysteryTypeEnum.entries.size

val mysteryItemList =
    MysteryTypeEnum.entries.flatMap { type ->
        MysteryStatusEnum.entries.map { status ->
            MysteryInfoData(type, status)
        }
    }