package _RedGold__.main.commands.user.boost.listeners.info.rankProd.vipGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class VipGui {
    fun openGui(player: Player) {
        val gui = VipHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.GOLD_INGOT,
            "&e&l클릭하여 ${VipConst.giveRank.name} 랭크 구매하기",
            listOf("",
                "&f&l구매가: &4&l${VipConst.PRICE.toFormat()} 루비",
                "&7&l요구 조건: USER 랭크 필요",
                "",
                "&c&o* 계정당 최대 1회 구매입니다."
            )
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&e&l혜택 정보",
            listOf("",
                "&a&l[ 영구 혜택 ]",
                "&7&l  - &a&l탭 리스트 우선 표시",
                "&7&l  - &e&l/showitem(si) 명령어 입력 시 전체 채팅에 ", //TODO: 아직 못 만듬
                "&7&l  - &e&lGG 메시지 색깔/전용 파티클 변경 가능", //TODO: 아직 못 만듬
                "&7&l  - &e&l/skin(스킨) 명령어로 스킨 변경 가능(PVP 중 사용 불가)",
                "&7&l  - &e&l/enderchest(ec, 엔더 상자, 엔상) 명령어로 엔더상자 열기 가능(PVP 중 사용 가능)", //TODO: 아직 못 만듬
                "",
                "&6&l[ +보너스 ]",
                "&7&l  - &6&l${VipConst.BONUS_GOLD.toFormat()} 골드 지급",
                "&7&l  - &b&l${VipConst.BONUS_CRYSTAL.toFormat()} 크리스탈 지급"
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}