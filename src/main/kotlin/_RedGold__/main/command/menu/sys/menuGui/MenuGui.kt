package _RedGold__.main.command.menu.sys.menuGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.ServerGold.getHoldGold
import _RedGold__.main.function.ServerGold.getMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.RunScoreboard
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MenuGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, sound: Float = 1f) {
        val cashExc = getData(plugin, player, "cash_exc").toInt()
        val gui = MenuHolder(cashExc).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("304CFC")}§l§oM
                ${rgb("3B54FD")}§l§oE
                ${rgb("475CFD")}§l§oT
                ${rgb("5264FD")}§l§oA
                ${rgb("5E6BFE")}§l§oL
                ${rgb("6973FE")}§l§oI
                ${rgb("757BFE")}§l§oS
                ${rgb("8083FF")}§l§oM
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(10, getItem("grass_block", "&f&l랜덤 TP")) //rtp 완
        gui.setItem(11, getItem("gold_ingot", "&f&l상점")) //shop 완
        gui.setItem(12, getItem("gold_block", "&f&l순위")) //ranking 완
        gui.setItem(13, getItem("chest", "&f&l창고")) //chest 완
        gui.setItem(14, getItem("emerald_block", "&f&l후원")) //boost 완
        gui.setItem(15, getItem("tnt", "&f&l도박")) //betting 완
        gui.setItem(16, getItem("red_bed", "&f&l홈 관리")) //home 완

        gui.setItem(19, getItem("redstone", "&f&l죽은 위치로 돌아가기")) //플러그인 사용
        gui.setItem(20, getItem("cake", "&f&l이벤트").apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)}) //event 완
        gui.setItem(21, getItem("book", "&f&l미션").apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)}) //mission 완
        gui.setItem(22, getItem("writable_book", "&f&l칭호 선택")) //style 완
        gui.setItem(23, getItem("ender_chest", "&a&l엔더 상자", listOf("", "&c&lplus 랭크 이상만 사용 가능합니다.")))
        gui.setItem(24, getItem("paper", "&b&l디스코드 / 커뮤니티"))


        val serverHoldGold = getHoldGold(plugin).toLong()
        val serverMakeGold = getMakeGold(plugin).toLong()

        /*val serverEconomy = (
            if (serverHoldGold - serverMakeGold <= -1000000L) "&c&l나쁨"
            else if (serverHoldGold - serverMakeGold >= 1000000L) "&a&l좋음"
            else "&e&l보통"
        )*/

        gui.setItem(45, getItem(
            "gold_ingot",
            "&8&l서버 경제 현황",
            listOf(
                "&f",
                "&f&l서버 보유 골드: &6&l${serverHoldGold.toFormat()} 골드",
                "&f&l서버 발행 골드: &6&l${serverMakeGold.toFormat()} 골드",
                //"&f",
                //"&8서버 경제 상태: $serverEconomy"
                "&7",
                "&7좌클릭 시 100,000 골드를 소비하여 10 캐시를 얻습니다.($cashExc/10)",
                "&7&l최대 100 캐시 까지 획득 가능합니다.",
                "",
                "&7우클릭 시 1 캐시를 소비하여 9,800 골드를 얻습니다.(최대치 없음)",
                "&7",
                "&c&l매 주마다 환전 횟수가 초기화 됩니다."
            )
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(49, getPlayerSkull(
            player.name,
            "&b&l${player.name}&f&l님",
            listOf(
                "&f",
                "&f&l랭크: ${getPlayerRankPrefix(player)}",
                "&f&l보유 골드: &6&l${(RunScoreboard.PlayerDataCache.gold[player.uniqueId]?: 0L).toFormat()} 골드",
                "&f&l보유 캐시: &b&l${(RunScoreboard.PlayerDataCache.cash[player.uniqueId]?: 0L).toFormat()} 캐시",
                "&f",
                "&f&l지연 시간: &a&l${player.ping} ms"
            )
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, sound, 1f)
    }
}