package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class TicketGui(private val plugin: JavaPlugin) {
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
        val buyTimes = getData(plugin, player, "ticket/buy").toInt()
        val getTicket = getData(plugin, player, "ticket/get").toInt()

        val gui = TicketHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(13, getItem(
            "emerald",
            "&a뽑기권 구매 또는 사용 &8구매 횟수: ($buyTimes/10)",
            listOf(
                "",
                "&a[구매(좌클릭)] &f&l구매가: 100 캐시 &8&l(5연속 뽑기권이 구매 됩니다.)",
                "&b[사용(우클릭)] &f&l5연속 뽑기권이 사용됩니다.&8(사용 가능한 뽑기권 횟수: &a&l${getTicket}회)",
                "",
                "&f&l뽑기에서 각 회차마다 개별적으로 50%의 확률로 치장품이 등장 합니다.",
                "&f&l50% 확률로 실패 할 시 100,000 골드가 지급됩니다.",
                "&f&l만일 50% 확률로 성공 시 치장품에서 사망/킬 사운드는 각각 17.5%,",
                "&f&l접속 메시지는 10%, 칭호(영구 획득)는 5% 확률로 등장합니다.",
                "",
                "&c&l만일 기존에 장착, 또는 구매를 한 치장품이 나올 시 100,000 골드로 변경됩니다.",
                "&c&l오직 치장품만 뽑을 수 있으며 키트 아이템은 안 나옵니다.",
                "&c&l사망/킬 사운드, 접속 메시지 치장품이 뽑기로 나올 시 장착을 안하면 100,000 골드로 변경됩니다.",
                "",
                "&8매 주마다 구매 횟수 초기화 됩니다."
            )
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}