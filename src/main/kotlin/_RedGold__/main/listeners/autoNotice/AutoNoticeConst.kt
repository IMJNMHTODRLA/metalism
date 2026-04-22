package _RedGold__.main.listeners.autoNotice

import _RedGold__.main.functions.buildSmartMessage
import _RedGold__.main.managers.playerData.PREFIX

internal object AutoNoticeConst {
    val NOTICE_MESSAGE = listOf(
        buildSmartMessage {
            text("$PREFIX &f&l서버 규칙 위반 시 &c&l이 서버에서 정지될 수 있습니다&f&l. ")
            text("&e&l[여기를 클릭하여]") {
                hover("&7클릭하여 규칙 확인하기")
                command("/규칙")
            }
            text(" &f&l규칙을 확인해주세요.")
        },
        buildSmartMessage {
            text("$PREFIX &a&lShift + F &f&l키를 눌러 메뉴를 열 수 있습니다.")
        },
        buildSmartMessage {
            text("$PREFIX &c&l비인가 클라이언트&7&l/&c&l버그 악용&f&l 시 이 서버에서 정지됩니다.")
        },
        buildSmartMessage {
            text("$PREFIX &f&l모든 &a&l후원금&f&l은 이 서버를 &e&l유지&f&l하는데 사용 됩니다!")
        },
        buildSmartMessage {
            text("$PREFIX &f&l모든 소식과 공지를 빠르게 확인을 원할 경우 ")
            text("&b&l[여기를 클릭하여]") {
                hover("&7클릭하여 디스코드에 접속하기")
                command("/디스코드")
            }
            text(" &f&l디스코드 서버에 접속해주세요.")
        },
        buildSmartMessage {
            text("$PREFIX &f&l후원을 하여 &d&l크리스탈&f&l충전을 원할 경우 ")
            text("&a&l[여기를 클릭하여]") {
                hover("&7클릭하여 후원 하기")
                command("/후원 충전")
            }
            text(" &f&l후원을 해주세요.")
        },
        buildSmartMessage {
            text("$PREFIX &f&l비매너 플레이어를 발견 시 ")
            text("&b&l[여기를 클릭하여]") {
                hover("&7클릭하여 디스코드에 접속하기")
                command("/디스코드")
            }
            text(" &f&l디스코드 서버에서 &7#\uD83D\uDCE2-신고 &f&l채널에서 신고해주세요.")
        }
    )
}