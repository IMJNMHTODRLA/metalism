package _RedGold__.main.managers.playerData

import _RedGold__.main.managers.logManager.logObserver
import _RedGold__.main.managers.playerData.dataManager.*
import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import _RedGold__.main.managers.playerData.variableManager.BoostSettingEnum
import _RedGold__.main.managers.playerData.variableManager.RANGE_HOME
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionEnum
import org.bukkit.Sound
import java.util.*

data class PlayerData(
    val uuid: UUID,

    var combatData: CombatData = CombatData(uuid),
    var shopData: ShopData = ShopData(),
    val homeMap: MutableMap<Int, HomeData> = (RANGE_HOME).associateWith { HomeData() }.toMutableMap(),

    val missionMap: MutableMap<MissionEnum, MutableMap<Int, MissionData>> =
        MissionEnum.entries.associateWith { type ->
            (0..type.total).associateWith { MissionData() }.toMutableMap()
        }.toMutableMap(),

    val cosmeticMap: MutableMap<CosmeticEnum, MutableMap<Int, CosmeticData>> =
        CosmeticEnum.entries.associateWith {
            mutableMapOf(0 to CosmeticData(true))
        }.toMutableMap(),

    val boostMap: MutableMap<BoostEnum, BoostData> = mutableMapOf(),
    val boostSettingMap: MutableMap<BoostSettingEnum, Int> =
        BoostSettingEnum.entries.associateWith { boostSettingEnum ->
            boostSettingEnum.default
        }.toMutableMap()
) {
    var gold by logObserver(0L, logger(uuid))   //석탄이 한개에 500골드 정도함 &6
    var crystal by logObserver(0, logger(uuid)) //10연뽑에 1200 크리스탈, 1 크리스탈 = 25 루비 &b
    var ruby by logObserver(0, logger(uuid))    //1원에 1루비 &2
    var boost by logObserver(0, logger(uuid))   //후원 금액

    private fun getEquipRaw(type: CosmeticEnum): Pair<Boolean, Any?> {
        val equipEntry = cosmeticMap[type]?.entries?.firstOrNull { it.value.isEquip }

        val isEquipped = equipEntry != null
        val item = type.link[equipEntry?.key?: 0]

        return isEquipped to item
    }

    @Deprecated(".let 계속 입력해야 되서 사용 안하는 걸 추천ㅇㅇ", ReplaceWith("equipStyle"), DeprecationLevel.WARNING)
    val rawEquipStyle get() = getEquipRaw(CosmeticEnum.STYLE) as Pair<Boolean, String>

    val equipStyle get() =
        (getEquipRaw(CosmeticEnum.STYLE) as Pair<Boolean, String>).let {
            if (it.first) true to "${it.second} "
            else false to ""
        }

    val equipJoin get() = getEquipRaw(CosmeticEnum.JOIN) as Pair<Boolean, String>
    val equipDeath get() = getEquipRaw(CosmeticEnum.DEATH) as Pair<Boolean, Pair<Sound?, String>>
    val equipKill get() = getEquipRaw(CosmeticEnum.KILL) as Pair<Boolean, Pair<Sound?, String>>

    fun hasEquip(i: Int, type: CosmeticEnum) = cosmeticMap[type]?.get(i)?.isEquip?: false
    fun hasCosmetic(i: Int, type: CosmeticEnum) = cosmeticMap[type]?.get(i) != null

    fun setCosmetic(i: Int, type: CosmeticEnum, value: Boolean) = cosmeticMap[type]?.get(i)?.run { isEquip = value; true } ?: false
    fun addCosmetic(i: Int, type: CosmeticEnum, value: Boolean) = cosmeticMap.getOrPut(type) { mutableMapOf() }.put(i, CosmeticData(value)) == null
    fun removeCosmetic(i: Int, type: CosmeticEnum) = cosmeticMap[type]?.remove(i) != null
}
