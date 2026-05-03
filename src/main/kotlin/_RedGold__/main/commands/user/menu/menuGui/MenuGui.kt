package _RedGold__.main.commands.user.menu.menuGui

import _RedGold__.main.function.api.toFormat
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.modify
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MenuGui {
    fun openGui(player: Player) {
        val gui = MenuHolder().inventory
        gui.item(BACKGROUND)

        gui.item[10] = getItem(Material.GRASS_BLOCK, "&f&l랜덤 TP") //rtp 완
        gui.item[11] = getItem(Material.GOLD_INGOT, "&f&l상점") //shop 완
        gui.item[12] = getItem(Material.GOLD_BLOCK, "&f&l순위") //ranking 완
        gui.item[13] = getItem(Material.CHEST, "&f&l창고") //chest 완
        gui.item[14] = getItem(Material.EMERALD_BLOCK, "&f&l후원") //boost 완
        gui.item[15] = getItem(Material.TNT, "&f&l도박") //betting 완
        gui.item[16] = getItem(Material.RED_BED, "&f&l홈 관리") //home 완

        gui.item[19] = getItem(Material.REDSTONE, "&f&l죽은 위치로 돌아가기") //플러그인 사용
        gui.item[20] = getItem(Material.CAKE, "&f&l이벤트") modify { enchantEffect() } //event 완
        gui.item[21] = getItem(Material.BOOK, "&f&l미션") modify { enchantEffect() } //mission 완
        gui.item[22] = getItem(Material.WRITABLE_BOOK, "&f&l치장품 관리") //cosmetic 완
        gui.item[23] = getItem(Material.ENDER_CHEST, "&a&l엔더 상자", listOf("", "&c&lplus 랭크 이상만 사용 가능합니다.")) //완
        gui.item[24] = getItem(Material.PAPER, "&b&l디스코드 / 커뮤니티")

        gui.item[49] = getPlayerSkull(
            player.uniqueId,
            "&b&l${player.name}&f&l님",
            listOf(
                "",
                "&f&l랭크: ${PermissionEnum[player].prefix}",
                "&f&l보유 골드: &6&l${player.data.gold.toFormat()} 골드",
                "&f&l보유 크리스탈: &b&l${player.data.crystal.toFormat()} 크리스탈",
                "",
                "&f&l지연 시간: &a&l${player.ping} ms"
            )
        )

        player.inv + gui
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}