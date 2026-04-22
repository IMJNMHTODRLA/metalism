package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui

import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class ProfileGui {
    fun openGui(player: Player, returnPage: Int) {
        val gui = ProfileHolder(returnPage).inventory

        gui.item[0..gui.end] = BACKGROUND

        gui.item[3] = getItem(
            Material.WRITABLE_BOOK,
            "&e&l아이템 등록하기",
            listOf("",
                "&8&l클릭 시 아이템 등록 창으로 이동됩니다."
            )
        )

        gui.item[5] = getItem(
            Material.NAME_TAG,
            "&e&l등록된 아이템 보기",
            listOf("",
                "&8&l클릭 시 등록된 아이템 목록 창으로 이동됩니다."
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_IRON_DOOR_OPEN)
    }
}