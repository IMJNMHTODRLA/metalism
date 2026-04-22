package _RedGold__.main.managers.userShopManager

import _RedGold__.main.managers.playerData.PlayerData

enum class UserShopGoodsEnum(
    val color: String,
    val displayName: String
) {
    GOLD("&6&l", "골드") {
        override fun get(data: PlayerData) = data.gold
        override fun set(data: PlayerData, value: Long) { data.gold = value }
    },
    CRYSTAL("&b&l","크리스탈") {
        override fun get(data: PlayerData) = data.crystal.toLong()
        override fun set(data: PlayerData, value: Long) { data.crystal = value.toInt() }
    };

    abstract operator fun get(data: PlayerData): Long
    abstract operator fun set(data: PlayerData, value: Long)

    fun next() = entries[(ordinal + 1) % entries.size]
}
