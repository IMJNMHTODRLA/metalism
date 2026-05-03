package _RedGold__.main.commands.user.boost.listeners.info.infoGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class InfoGui {
    fun openGui(player: Player) {
        val gui = InfoHolder().inventory
        gui.item(BACKGROUND)

        gui.item[11] = getItem(
            Material.DIAMOND,
            "&b&l크리스탈 상품"
        )

        gui.item[13] = getItem(
            Material.NAME_TAG,
            "&6&l랭크 상품"
        )

        gui.item[15] = getItem(
            Material.CHEST,
            "&e&l패키지 상품"
        )

        gui.setItem(12, getItem(
            "written_book",
            "&b&lQ. &f&l후원을 하면 어떤 혜택들을 지급하나요?",
            listOf(
                "",
                "&a&lA. &f&l저희 서버는 누적 후원 금액 30,000원 이상 후원 시 영구 혜택을 지급합니다.",
                "",
                "       &f&l혜택은 다음과 같습니다.",
                "&7&l==================================",
                "   &a&l[ 영구 혜택 ]",
                "       &7&l- &b&lPLUS 랭크 지급", //완
                "       &7&l- &a&l탭 리스트 우선 표시", //완
                "       &7&l- &e&lGG 메시지 색깔 변경 및 각종 메시지 변경 가능", //GG함
                "       &7&l- &b&l200 캐시 지급", //완
                "       &7&l- &e&l화살 경로에 파티클 표시(비활성화 가능)",
                "       &7&l- &e&l접속 시 강조된 접속 메시지 전달(비활성화 가능)",
                "",
                "   &b&l[ 월정액 혜택 ] &7&l(매달 15,000원 후원 시 유지)",
                "       &8&l* 영구 혜택 획득 이후 이용 가능",
                "       &7&l- &e&l매주 월요일에 접속 시 100 캐시 지급", //완
                "       &7&l- &e&l일일 접속 시 본인에게 5캐시와 50,000 골드, 1레벨 지급", //완
                "       &7&l- &e&l일일 접속 시 서버 전체 유저에게 4,000 골드 지급(PLUS랭크는 5,000 골드)", //완
                "       &7&l- &e&l메뉴에서 엔더 상자 열기 가능(/ec, /enderchest로도 가능)", //완
                "       &7&l- &e&l접속 시 경험치 배수가 (현재 배수 + 0.25)배로 변화", //완
                "       &7&l- &e&l접속 시 (현재 배수 + 0.1)배로 변화(월정액 외 플레이어만)", //완
                "       &7&l- &e&l/skin <설정할 스킨의 닉네임> 명령어 사용 가능(스킨만 변경)", //완?
                "       &7&l- &e&l/nick <설정할 닉네임> 명령어 사용 가능(닉네임만 변경)",
                "       &7&l- &e&l월정액 혜택이 2일 이내일 시 접속 시 경고 메시지 보내기(비활성화 가능)",
                "&7&l======================================================="
            )
        ))

        gui.setItem(13, getItem(
            "written_book",
            "&b&lQ. &f&l후원은 한 번 하면 영구적인가요?",
            listOf(
                "",
                "&a&lA. &f&l반은 맞습니다. 한 번 후원하면 대부분의 해당 혜택은 영구적으로 유지되지만,",
                "       &f&l월정액이 붙은 혜택은 매달 특정 금액 이상을 후원 해야 합니다."
            )
        ))

        gui.setItem(14, getItem(
            "written_book",
            "&b&lQ. &f&l이미 후원했는데 또 하면 어떻게 되나요?",
            listOf(
                "",
                "&a&lA. &f&l영구 혜택을 보유하신 상태에서 추가 후원을 하실 경우,",
                "       &b&l월정액 혜택이 활성화됩니다.",
                "",
                "       &7&l- 이미 월정액을 이용 중이라면 기간이 30일 연장됩니다.",
                "       &7&l- 모든 후원 금액은 누적으로 계산되어 혜택이 적용됩니다."
            )
        ))

        gui.setItem(20, getItem(
            "written_book",
            "&b&lQ. &f&l후원 결제 방법은 무엇인가요?",
            listOf(
                "",
                "&a&lA. &f&l아래 상품권 및 교환권으로 후원이 가능합니다.",
                "",
                "       &8&l[ &e&l문화상품권 계열 &8&l]",
                "       &f- 컬쳐랜드(16핀) / 온라인문화상품권(18핀)",
                "       &f- 컬쳐랜드 교환권 (기프티콘/카톡)",
                "",
                "       &8&l[ &e&l기타 상품권 &8&l]",
                "       &f- 틴캐시 / 틴캐시 교환권",
                "       &f- 북앤라이프 도서상품권 / 교환권",
                "",
                "       &7&l* 그 외 상품권은 디스코드 문의 바랍니다."
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
                "&a&lA. &f&l네, 누적 후원 금액이 30,000원 이상일 때 혜택이 적용됩니다."
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

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}