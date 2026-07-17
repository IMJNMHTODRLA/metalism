package _RedGold__.main.core.guild.command

fun getHeaderTab() = listOf("설정", "화이트리스트", "챗", "채팅", "c", "가입", "검색", "등록")
fun getPayloadTab(header: String?): List<String> =
    when(header) {
        "설정" -> emptyList()
        "화이트리스트" -> listOf("추가", "add", "삭제", "remove")
        "챗", "채팅", "c" -> listOf("메시지")
        "가입" -> listOf("초대코드", "길드ID")
        "검색" -> listOf("키워드(미포함가능)")
        "등록" -> listOf("길드이름")

        else -> emptyList()
    }

fun getSubTab(header: String?) =
    when(header) {
        "설정" -> emptyList()
        "화이트리스트" -> listOf("UUID")
        "챗", "채팅", "c" -> listOf("메시지")
        "가입" -> listOf("초대코드 또는 길드ID")
        "검색" -> emptyList()
        "등록" -> emptyList()

        else -> emptyList()
    }