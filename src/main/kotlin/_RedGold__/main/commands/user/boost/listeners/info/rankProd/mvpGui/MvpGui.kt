package _RedGold__.main.commands.user.boost.listeners.info.rankProd.mvpGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MvpGui {
    fun openGui(player: Player) {
        val gui = MvpHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.GOLD_INGOT,
            "&e&l클릭하여 ${MvpConst.giveRank.name} 랭크 구매하기",
            listOf("",
                "&f&l구매가: &4&l${MvpConst.PRICE.toFormat()} 루비",
                "&7&l요구 조건: VIP 랭크 필요",
                "",
                "&c&o* 계정당 최대 1회 구매입니다."
            )
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&e&l혜택 정보",
            listOf("",
                "&a&l[ 영구 혜택 ]",
                "&7&l  - &a&lVIP 랭크의 모든 혜택 포함",
                "&7&l  - &e&l채팅 창에 [inv] 입력 시 현재 인벤토리 정보가 채팅창에 표시됩니다.", //TODO: 아직 못 만듬
                "&7&l  - &e&l채팅 창에 [ec] 입력 시 현재 엔더상자 정보가 채팅창에 표시됩니다.", //TODO: 아직 못 만듬
                "&7&l  - &e&l퇴장 메시지 변경 및 다양한 기능 사용 가능", //TODO: <- 이거 좀 수정하자ㅇㅇ
                "&7&l  - &e&l/nick(닉변, 닉네임) 명령어로 닉네임 변경 가능(PVP 중 사용 불가)",
                "",
                "&6&l[ +보너스 ]",
                "&7&l  - &6&l${MvpConst.BONUS_GOLD.toFormat()} 골드 지급",
                "&7&l  - &b&l${MvpConst.BONUS_CRYSTAL.toFormat()} 크리스탈 지급",
                "&7&l  - &e&l무작위 중급 강화석 지급",
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}