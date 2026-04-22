package _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui

import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.ifRun
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class CosmeticGui {
    fun openGui(player: Player, cosmeticEnum: CosmeticEnum) {
        val gui = CosmeticHolder(cosmeticEnum).inventory

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        gui.item[49] = getItem(Material.BOOK, "&8&l현재 페이지: (1)")

        repeat(45) { i ->
            if (i >= cosmeticEnum.total) return@repeat

            val hasCosmetic = !player.data.hasCosmetic(i, cosmeticEnum)
            val isEquip = player.data.hasEquip(i, cosmeticEnum)

            gui.item[i] = hasCosmetic
                .ifRun { CosmeticConst.setNoHave(i, cosmeticEnum) }
                .elseRun { CosmeticConst.setHave(i, cosmeticEnum, isEquip) }
        }

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}