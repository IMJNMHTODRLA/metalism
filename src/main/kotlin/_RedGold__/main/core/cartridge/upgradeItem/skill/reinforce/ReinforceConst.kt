package _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce

import _RedGold__.main.Main
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.ReinforceImportEnum.*
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.ReinforceTypeEnum.*
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.NamespacedKey

const val CUSTOM_MODEL_PREFIX = 28_00_00

const val IS_REINFORCE_KEY = "is_reinforce_cartridge"
@SetSlowInit val isReinforceNameSpace by lazy { NamespacedKey(Main.instance, IS_REINFORCE_KEY) }

const val REINFORCE_VERSION_KEY = "reinforce_cartridge_version"
const val REINFORCE_VERSION = 0
@SetSlowInit val reinforceVersionNameSpace by lazy { NamespacedKey(Main.instance, REINFORCE_VERSION_KEY) }

const val REINFORCE_TYPE_KEY = "reinforce_cartridge_type"
@SetSlowInit val reinforceTypeNameSpace by lazy { NamespacedKey(Main.instance, REINFORCE_TYPE_KEY) }

const val REINFORCE_IMPORT_KEY = "reinforce_cartridge_import"
@SetSlowInit val reinforceImportNameSpace by lazy { NamespacedKey(Main.instance, REINFORCE_IMPORT_KEY) }

val maxReinforceType = ReinforceTypeEnum.entries.size

val reinforceItemList = listOf(
    ReinforceInfoData(HEAL, GENERAL),
    ReinforceInfoData(HEAL, BASIC),
    ReinforceInfoData(HEAL, BEGINNER),
    ReinforceInfoData(HEAL, INTERMEDIA),
    ReinforceInfoData(HEAL, ADVANCED),
    //회복 타입

    ReinforceInfoData(COMBAT, GENERAL),
    ReinforceInfoData(COMBAT, BASIC),
    ReinforceInfoData(COMBAT, BEGINNER),
    ReinforceInfoData(COMBAT, INTERMEDIA),
    ReinforceInfoData(COMBAT, ADVANCED),
    //전투 타입

    ReinforceInfoData(MOVEMENT, GENERAL),
    ReinforceInfoData(MOVEMENT, BASIC),
    ReinforceInfoData(MOVEMENT, BEGINNER),
    ReinforceInfoData(MOVEMENT, INTERMEDIA),
    ReinforceInfoData(MOVEMENT, ADVANCED),
    //이속 타임

    ReinforceInfoData(UNDERWATER, GENERAL),
    ReinforceInfoData(UNDERWATER, BASIC),
    ReinforceInfoData(UNDERWATER, BEGINNER),
    ReinforceInfoData(UNDERWATER, INTERMEDIA),
    ReinforceInfoData(UNDERWATER, ADVANCED),
    //수중 타입

    ReinforceInfoData(SUPPORT, GENERAL),
    ReinforceInfoData(SUPPORT, BASIC),
    ReinforceInfoData(SUPPORT, BEGINNER),
    ReinforceInfoData(SUPPORT, INTERMEDIA),
    ReinforceInfoData(SUPPORT, ADVANCED)
    //서폿 타입
)