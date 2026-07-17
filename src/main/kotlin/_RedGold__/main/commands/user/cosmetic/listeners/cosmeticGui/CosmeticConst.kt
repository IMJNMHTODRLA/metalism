package _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CosmeticConst {
    val messageList = listOf(
        "테스트 메시지 입니다.",
        "1 + 1 = 2",
        "Unknown command. Type \"/help\" for help.",
        "&7/give_admin 명령어를 입력해봐...",
        "서버가 정상적으로 작동 중입니다. 아마도요.",
        "안녕!",
        "[귓속말] Server -> 나: 야 너한테만 말하는 건데 이거 대박임",
        "/give @p diamond_block 64",
        "&e[Server] &f5초 뒤 서버가 &c폭발합니다!!! &8사실 구라에요ㅎ",
        "거울 봐봐요. 재밌을 거에요.",
        "왕이 넘어지면? ... 킹콩",
        "왕이 넘어지면? ......뭐해, 안 일으켜 세우고",
        "세상에서 가장 가난한 왕은? ... 최저임금",
        "소나무가 삐지면? ... 칫솔",
        "자동차가 놀라면? ... 카놀라유",
        "U+C774 AC78 0020 D574 C11D D558 C2DC B2E4 B2C8 002C 0020 B300 B2E8 D558 B124 C694 002E"
    )

    fun getDisplayName(i: Int, enum: CosmeticEnum) = when (val rawName = enum.link[i]) {
        is Pair<*, *> -> rawName.second.toString()
        else -> rawName.toString()
    }

    fun setNoHave(i: Int, enum: CosmeticEnum): ItemStack {
        val displayName = when (val rawName = enum.link[i]) {
            is Pair<*, *> -> rawName.second.toString()
            else -> rawName.toString()
        }

        return getItem(
            Material.BOOK,
            displayName,
            listOf(
                "",
                PREFIX,
                "",
                "&c&l[장착 불가]",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                "$displayName &f&l${enum.typeName}(은)는 ${enum.where[i]}에서 획득이 가능합니다.",
            )
        )
    }

    fun setHave(i: Int, enum: CosmeticEnum, isEquip: Boolean): ItemStack {
        val equipTitle = if (isEquip) "&a&l[선택됨]" else ""
        val displayName = when (val rawName = enum.link[i]) {
            is Pair<*, *> -> rawName.second.toString()
            else -> rawName.toString()
        }

        return getItem(
            Material.BOOK,
            "$displayName $equipTitle",
            listOf(
                "",
                PREFIX,
                "",
                "&a&l[좌클릭]: &f&l장착",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                "$displayName &f&l${enum.typeName}(은)는 ${enum.where[i]}에서 획득이 가능합니다.",
            )
        ).apply { if (isEquip) enchantEffect() }
    }
}