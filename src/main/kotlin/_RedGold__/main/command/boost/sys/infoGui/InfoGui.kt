package _RedGold__.main.command.boost.sys.infoGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.deathRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class InfoGui {
    fun openGui(player: Player) {
        val gui = InfoHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("324DFC")}§l§oE
                ${rgb("3952FD")}§l§oT
                ${rgb("4057FD")}§l§oA
                ${rgb("475CFD")}§l§oL
                ${rgb("4E60FD")}§l§oI
                ${rgb("5565FD")}§l§oS
                ${rgb("5B6AFE")}§l§oM 
                ${rgb("6973FE")}§l§oB
                ${rgb("7078FE")}§l§oO
                ${rgb("777DFE")}§l§oO
                ${rgb("7E82FF")}§l§oS
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(12, getItem(
            "written_book",
            "&b&lQ. &f&l후원을 하면 어떤 혜택들을 지급하나요?",
            listOf(
                "",
                "&a&lA. &f&l저희 서버는 게임 플레이에 거의 영향을 주지 않는 혜택들 이 제공됩니다.",
                "&8(누적 후원금액 20,000원 이상 후원 시 지급되는 혜택입니다.)",
                "",
                "       &f&l주요 혜택은 다음과 같습니다.",
                "&7&l=======================================================",
                "       &7&l- &b&lPLUS 랭크 지급",
                "       &7&l- &a&l대기열 우회 권한 지급",
                "       &7&l- &e&l텝 리스트 우선 표시",
                "       &7&l- &e&l채팅 입력 시 표시 랭크 변경",
                "       &7&l- &e&lGG 메시지 색깔 지정 가능",
                "       &7&l- &e&l킬/사망 메시지 변경 가능",
                "       &7&l- &6&l200 캐시 지급",
                "&7&l======================================================="
            )
        ))

        gui.setItem(13, getItem(
            "written_book",
            "&b&lQ. &f&l후원은 한 번 하면 영구적인가요?",
            listOf(
                "",
                "&a&lA. &f&l네. 한 번 후원하면 해당 혜택은 영구적으로 유지됩니다."
            )
        ))

        gui.setItem(14, getItem(
            "written_book",
            "&b&lQ. &f&l이미 후원했는데 또 하면 어떻게 되나요?",
            listOf(
                "",
                "&a&lA. &f&l추가적으로 후원을 할 경우 3,000원에 치장품 5연뽑 뽑기권과",
                "       &f&l119,000 골드를 지급해 드립니다.",
                "       &c&l단, 무과금 유저와의 게임 밸런스를 위하여 일주일에 최대 30,000원까지 후원이 가능합니다."
            )
        ))

        gui.setItem(20, getItem(
            "written_book",
            "&b&lQ. &f&l후원을 할 때 어떤 결제 방법으로 해야 하나요?",
            listOf(
                "",
                "&a&lA. &f&l저희 서버는 오직 컬쳐랜드 문화상품권만 가능합니다."
            )
        ))

        gui.setItem(21, getItem(
            "written_book",
            "&b&lQ. &f&l후원을 하면 즉시 적용되나요?",
            listOf(
                "",
                "&a&lA. &f&l아니요. 운영자가 일일이 수동으로 처리하는 방식이며,",
                "       &f&l후원 신청 시간에 따라 최대 48시간까지 소요될 수 있습니다."
            )
        ))

        gui.setItem(22, getItem(
            "lapis_lazuli",
            "&9&l후원하러 가기",
        ))

        gui.setItem(23, getItem(
            "written_book",
            "&b&lQ. &f&l혜택 적용을 위한 최소 후원 금액이 있나요?",
            listOf(
                "",
                "&a&lA. &f&l네, 누적 후원 금액이 20,000원 이상일 때 혜택이 적용됩니다."
            )
        ))

        gui.setItem(24, getItem(
            "written_book",
            "&b&lQ. &f&l디스코드 서버에 마인크래프트 계정을 연동해야 하나요?",
            listOf(
                "",
                "&a&lA. &f&l네, 더 원활한 후원 신청과 빠른 적용을 위해 연동이 필수입니다."
            )
        ))

        gui.setItem(30, getItem(
            "written_book",
            "&b&lQ. &f&l후원금은 어디에 사용되나요?",
            listOf(
                "",
                "&a&lA. &f&l후원금 전액은 서버 유지 비용과 서버 부품을 구입할 때 사용됩니다."
            )
        ))

        gui.setItem(31, getItem(
            "written_book",
            "&b&lQ. &f&l서버에서 정지되면 혜택은 어떻게 되나요?",
            listOf(
                "",
                "&a&lA. &f&l서버 규칙 위반으로 &c&l영구 정지&f&l을 당할 경우,",
                "       &f&l후원 혜택은 &c&l자동으로 박탈&f&l되며 환불은 &c&l불가능&f&l합니다.",
                "       &f&l또한, 후원 신청이 금지되며 후원 순위에서도 비공개 처리가 됩니다.",
                "       &f&l이는 공정한 서버 운영을 위한 필수 조치입니다.",
                "&f",
                "       &f&l오직 영구 정지에만 적용됩니다."
            )
        ))

        gui.setItem(32, getItem(
            "written_book",
            "&b&lQ. &f&l다른 계정으로 랭크나 후원 혜택을 옮길 수 있나요?",
            listOf(
                "",
                "&a&lA. &f&l후원 혜택은 후원한 계정에만 적용됩니다.",
                "       &f&l따라서 &c&l다른 계정으로 혜택 이전은 불가능&f&l합니다."
            )
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}