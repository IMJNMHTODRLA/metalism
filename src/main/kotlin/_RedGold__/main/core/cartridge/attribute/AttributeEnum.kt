package _RedGold__.main.core.cartridge.attribute

import _RedGold__.main.core.cartridge.attribute.AttackAttribute.*
import _RedGold__.main.core.cartridge.attribute.DefenseAttribute.*

enum class AttackAttribute(val index: Int, val displayName: String) {
    NORMAL(0, "일반"),
    EXPLOSION(1, "폭팔"),
    FIRE(2, "화염"),
    MAGIC(3, "마법");
}

enum class DefenseAttribute(
    val index: Int, val displayName: String,
    val matchUp: AttackAttribute?, val reverseMatchUp: AttackAttribute?,
) {
    GENERAL(0, "일반", null, null),
    HEAVY_ARMOR(1, "중갑", EXPLOSION, MAGIC),
    FLAMMABLE(2, "가연", FIRE, EXPLOSION),
    HARD(3, "경질", MAGIC, FIRE);

    fun getMultiplier(attack: AttackAttribute) =
        when(attack) {
            matchUp -> 2.0
            reverseMatchUp -> 0.5

            else -> 1.0
        }
}
