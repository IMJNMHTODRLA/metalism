package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile

import _RedGold__.main.managers.playerData.PermissionEnum

object ProfileGlobalConst {
    val maxRegList = { permission: PermissionEnum ->
        when(permission) {
            PermissionEnum.USER -> 3
            PermissionEnum.PLUS -> 4
            PermissionEnum.VIP -> 5
            PermissionEnum.MVP -> 6

            else -> 3
        }
    }

    const val TOTAL_MAX_REG = 6
}