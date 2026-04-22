package _RedGold__.main.commands.user.ranking.listeners.selectGui

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory
        gui.item(BACKGROUND)

        gui.item[3] = getItem(
            Material.GOLD_INGOT,
            "&6&l골드 순위"
        )

        gui.item[4] = getItem(
            Material.EMERALD_BLOCK,
            "&a&l후원 순위"
        )

        gui.item[5] = getItem(
            Material.EXPERIENCE_BOTTLE,
            "&2&l플레이 타임 순위"
        )

        gui.item[12] = getItem(
            Material.DIAMOND_SWORD,
            "&c&l누적 킬 순위"
        )

        gui.item[14] = getItem(
            Material.END_CRYSTAL,
            "&d&l연킬 순위",
            listOf("", "&8&l연킬 보상 획득이 가능합니다.")
        )

        gui.item[21] = getItem(
            Material.REDSTONE,
            "&4&l누적 데스 순위"
        )

        gui.item[22] = getItem(
            Material.REDSTONE_BLOCK,
            "&4&l연데스 순위",
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}