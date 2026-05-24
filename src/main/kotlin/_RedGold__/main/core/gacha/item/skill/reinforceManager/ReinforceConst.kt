package _RedGold__.main.core.gacha.item.skill.reinforceManager

import _RedGold__.main.Main
import _RedGold__.main.loads.SetSlowInit
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.GENERAL
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.BASIC
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.BEGINNER
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.INTERMEDIA
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.ADVANCED

import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceTypeEnum.HEAL
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceTypeEnum.COMBAT
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceTypeEnum.MOVEMENT
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceTypeEnum.UNDERWATER
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceTypeEnum.SUPPORT

import org.bukkit.Material.APPLE
import org.bukkit.Material.FIRE_CHARGE
import org.bukkit.Material.RABBIT_FOOT
import org.bukkit.Material.PRISMARINE_SHARD
import org.bukkit.Material.HONEY_BOTTLE

import org.bukkit.NamespacedKey

const val REINFORCE_TYPE_KEY = "reinforce_item_type"
const val REINFORCE_IMPORT_KEY = "reinforce_item_import"
@SetSlowInit val reinforceTypeNameSpace by lazy { NamespacedKey(Main.instance, REINFORCE_TYPE_KEY) }
@SetSlowInit val reinforceImportNameSpace by lazy { NamespacedKey(Main.instance, REINFORCE_IMPORT_KEY) }

const val MAX_REINFORCE_TYPE = 5

val ReinforceItemList = listOf(
    ReinforceInfoData(APPLE, HEAL, GENERAL), //2레벨부터 +2개씩(EX는 5개)
    ReinforceInfoData(APPLE, HEAL, BASIC), //3레벨부터 스킬 레벨마다 +1개씩(EX는 4개)
    ReinforceInfoData(APPLE, HEAL, BEGINNER), //5레벨부터 스킬 레벨마다 +1개씩(EX는 2개)
    ReinforceInfoData(APPLE, HEAL, INTERMEDIA), //6레벨부터 스킬 레벨마다 +1개씩(EX는 2개)
    ReinforceInfoData(APPLE, HEAL, ADVANCED), //8레벨부터 스킬 레벨마다 +1개씩(EX는 2개)
    //회복 타입

    ReinforceInfoData(FIRE_CHARGE, COMBAT, GENERAL),
    ReinforceInfoData(FIRE_CHARGE, COMBAT, BASIC),
    ReinforceInfoData(FIRE_CHARGE, COMBAT, BEGINNER),
    ReinforceInfoData(FIRE_CHARGE, COMBAT, INTERMEDIA),
    ReinforceInfoData(FIRE_CHARGE, COMBAT, ADVANCED),
    //전투 타입

    ReinforceInfoData(RABBIT_FOOT, MOVEMENT, GENERAL),
    ReinforceInfoData(RABBIT_FOOT, MOVEMENT, BASIC),
    ReinforceInfoData(RABBIT_FOOT, MOVEMENT, BEGINNER),
    ReinforceInfoData(RABBIT_FOOT, MOVEMENT, INTERMEDIA),
    ReinforceInfoData(RABBIT_FOOT, MOVEMENT, ADVANCED),
    //이속 타임

    ReinforceInfoData(PRISMARINE_SHARD, UNDERWATER, GENERAL),
    ReinforceInfoData(PRISMARINE_SHARD, UNDERWATER, BASIC),
    ReinforceInfoData(PRISMARINE_SHARD, UNDERWATER, BEGINNER),
    ReinforceInfoData(PRISMARINE_SHARD, UNDERWATER, INTERMEDIA),
    ReinforceInfoData(PRISMARINE_SHARD, UNDERWATER, ADVANCED),
    //수중 타입

    ReinforceInfoData(HONEY_BOTTLE, SUPPORT, GENERAL),
    ReinforceInfoData(HONEY_BOTTLE, SUPPORT, BASIC),
    ReinforceInfoData(HONEY_BOTTLE, SUPPORT, BEGINNER),
    ReinforceInfoData(HONEY_BOTTLE, SUPPORT, INTERMEDIA),
    ReinforceInfoData(HONEY_BOTTLE, SUPPORT, ADVANCED)
    //서폿 타입
)