package _RedGold__.main.core.cartridge

import _RedGold__.main.core.cartridge.attribute.AttackAttribute
import _RedGold__.main.core.cartridge.attribute.DefenseAttribute

abstract class Cartridge<ExTarget, BasicTarget, UpgradeTarget, SubTarget> {
    abstract val id: String

    abstract val attackAttribute: AttackAttribute
    abstract val defenseAttribute: DefenseAttribute

    abstract fun useExSkill(target: ExTarget?)
    abstract fun useBasicSkill(target: BasicTarget?)
    abstract fun useUpgradeSkill(target: UpgradeTarget?)
    abstract fun useSubSkill(target: SubTarget?)
}