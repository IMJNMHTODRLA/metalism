package _RedGold__.main.functions

import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.entity.Player

object EasyPermission {
    fun Player.permission(permission: PermissionEnum): Boolean = this.hasPermission(permission.node)
}