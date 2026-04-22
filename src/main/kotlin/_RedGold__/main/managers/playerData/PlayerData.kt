package _RedGold__.main.managers.playerData

import _RedGold__.main.functions.isNull
import _RedGold__.main.managers.playerData.dataManager.*
import _RedGold__.main.managers.playerData.variableManager.*
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.Sound
import java.util.*

data class PlayerData(
    val uuid: UUID,

    var gold: Long = 0,         //석탄이 한개에 2000골드 정도함 &6
    var crystal: Int = 0,       //10연뽑에 1200 크리스탈, 1 크리스탈 = 25 루비 &b
    var ruby: Int = 0,          //1원에 1루비 &2
    var boost: Int = 0,         //후원 금액

    var combatData: CombatData = CombatData(),
    var shopData: ShopData = ShopData(),
    val homeMap: MutableMap<Int, HomeData> = (RANGE_HOME).associateWith { HomeData() }.toMutableMap(),

    val missionMap: MutableMap<MissionEnum, MutableMap<Int, MissionData>> =
        MissionEnum.entries.associateWith { type ->
            (0..type.total).associateWith { MissionData() }.toMutableMap()
        }.toMutableMap(),

    val cosmeticMap: MutableMap<CosmeticEnum, MutableMap<Int, CosmeticData>> =
        CosmeticEnum.entries.associateWith {
            mutableMapOf(0 to CosmeticData(true))
        }.toMutableMap(), //“ADRIAN”🗣️🔥🔥

    val boostMap: MutableMap<BoostEnum, BoostData> = mutableMapOf(),
    val boostSettingMap: MutableMap<BoostSettingEnum, Int> =
        BoostSettingEnum.entries.associateWith { boostSettingEnum ->
            boostSettingEnum.default
        }.toMutableMap()
) {
    private fun getEquipRaw(type: CosmeticEnum): Pair<Boolean, Any?> {
        val equipEntry = cosmeticMap[type]?.entries?.firstOrNull { it.value.isEquip }

        val isEquipped = equipEntry != null
        val item = type.link[equipEntry?.key?: 0]

        return isEquipped to item
    }

    val equipStyle get() = getEquipRaw(CosmeticEnum.STYLE) as Pair<Boolean, String>
    val equipJoin get() = getEquipRaw(CosmeticEnum.JOIN) as Pair<Boolean, String>
    val equipDeath get() = getEquipRaw(CosmeticEnum.DEATH) as Pair<Boolean, Pair<Sound?, String>>
    val equipKill get() = getEquipRaw(CosmeticEnum.KILL) as Pair<Boolean, Pair<Sound?, String>>

    fun hasEquip(i: Int, type: CosmeticEnum) = cosmeticMap[type]?.get(i)?.isEquip?: false
    fun hasCosmetic(i: Int, type: CosmeticEnum) = cosmeticMap[type]?.get(i) != null

    fun setCosmetic(i: Int, type: CosmeticEnum, value: Boolean) = cosmeticMap[type]?.get(i)?.run { isEquip = value; true }?: false
}
