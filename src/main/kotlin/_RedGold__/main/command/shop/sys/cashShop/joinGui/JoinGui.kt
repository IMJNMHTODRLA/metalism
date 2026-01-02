package _RedGold__.main.command.shop.sys.cashShop.joinGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class JoinGui(private val plugin: JavaPlugin) {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2948FC")}§l§oM
        ${rgb("2F4BFC")}§l§oE
        ${rgb("344FFC")}§l§oT
        ${rgb("3A53FD")}§l§oA
        ${rgb("3F57FD")}§l§oL
        ${rgb("455AFD")}§l§oI
        ${rgb("4A5EFD")}§l§oS
        ${rgb("5062FD")}§l§oM 
        ${rgb("5B69FE")}§l§oC
        ${rgb("606DFE")}§l§oA
        ${rgb("6671FE")}§l§oS
        ${rgb("6B75FE")}§l§oH 
        ${rgb("767CFE")}§l§oS
        ${rgb("7C80FF")}§l§oH
        ${rgb("8184FF")}§l§oO
        ${rgb("8787FF")}§l§oP
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player, sound: Float = 1f) {
        val gui = JoinHolder().inventory
        val joinMsg = getData(plugin, player, "join_message").toInt()

        fun Int.fi(title: String, pur: Int, type: Int) {
            val typeMsg = if (joinMsg == type) " &a&l[선택됨]" else ""

            gui.setItem(this, getItem(
                "writable_book",
                "$title$typeMsg",
                listOf(
                    "&f",
                    prefix,
                    "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}캐시",
                    "&a&l[미리보기(우클릭)] &7&l우클릭 시 접속 메시지를 미리 볼 수 있습니다.",
                    "&f",
                    "&c&l새 접속 메시지를 구매하면 기존 접속 메시지를 재구매 해야 합니다."
                )
            ))
        }

        fun Int.none() {
            gui.setItem(this, getItem(
                "barrier",
                "&c&l판매하고 있는 아이템이 아닙니다.",
                listOf(
                    "&f",
                    prefix,
                    "&c&l[구매 불가]",
                    "&c&l[미리듣기 불가]",
                    "&f",
                    "&c&l새 접속 메시지를 구매하면 기존 접속 메시지를 재구매 해야 합니다."
                )
            ))
        }


        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 27 until gui.size) gui.setItem(i, background2)

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        10.fi("기본", 0, 0) //
        11.fi("&8[&a+&8] &ePlayer 님이 서버에 나타났습니다.", 600, 1)
        12.fi("&8[&a+&8] &f&lPlayer &7&lJoined", 600, 2)
        13.fi("&8[&b✦&8] &ePlayer &e님 환영합니다!", 600, 3)
        14.fi("&8[&a»&8] &ePlayer 님이 &a&l온라인&e으로 전환했습니다.", 650, 4)
        15.fi("&8[&7»&8] &fJo&ki&fned with Player", 600, 5)
        16.fi("&8[&b»&8] &f&lPlayer &b&l님이 서버에 등장하였습니다.", 650, 6)

        19.fi("&8[&a+&8] &ePlayer 님이 서버에 &a&l생성되었습니다.", 600, 7)
        20.fi("&8[&7?&8] &ePlayer 님이 서버에 &8&l접속...했나요?", 700, 8)
        21.fi("&8[&a!&8] &ePlayer 님이 게임에 참여하였습니다!", 800, 9)
        22.fi("&8[&a+&8] &ePlayer 님이 &2&l마인크래프트 세상에 들어왔습니다.", 750, 10)
        23.fi("&8[&a+&8] &ePlayer 님이 서버에 접속하였습니다. &a&l환영해주세요!", 800, 11)
        24.fi("&8[&a+&8] &ePlayer", 600, 12)
        25.fi("&8+ &ePlayer", 600, 13)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}