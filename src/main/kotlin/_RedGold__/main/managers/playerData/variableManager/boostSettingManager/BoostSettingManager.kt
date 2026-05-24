package _RedGold__.main.managers.playerData.variableManager.boostSettingManager

import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.PermissionEnum.VIP
import org.bukkit.entity.Player

enum class BoostSettingEnum(
    val needPermission: PermissionEnum,
    val default: Int,
) {
    CHAT_GG_COLOR(VIP, 0),
    ATTACK_PARTICLE_EFFECT(VIP, 0),
    JUMP_PARTICLE_EFFECT(VIP, 0);

    operator fun contains(player: Player) = player.permission(needPermission)
}
