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
                "&7&l  - &e&l/showinv(sinv) 명령어로 현재 인벤토리 정보를 전체 채팅에 전송합니다.",
                "&7&l  - &e&l/showec(sec) 명령어로 현재 엔더 상자 인벤토리 정보를 전체 채팅에 전송합니다.",
                "&7&l  - &e&l/nick(닉변, 닉네임) 명령어로 닉네임 변경 가능(PVP 중 사용 불가)",
                "&7&l  - &e&l/fly 명령어로 스폰에서 플라이 가능",
                "&7&l  - &7&lComing soon...", //TODO: 추후에 출시 해야겠다
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