package _RedGold__.main.command.shop.sys.cashShop.cashGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.getHoldGold
import _RedGold__.main.function.ServerGold.getMakeGold
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class CashGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player) {
        val gui = CashHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
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
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(4, getItem("magenta_shulker_box", "&5&l키트 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})

        gui.setItem(12, getItem("writable_book", "&a&l칭호 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})
        gui.setItem(13, getItem("netherite_sword", "&c&l킬 사운드 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})
        gui.setItem(14, getItem("redstone", "&4&l사망 사운드 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})

        gui.setItem(22, getItem("writable_book", "&e&l접속 메시지 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})

        gui.setItem(26, getItem("emerald", "&a&l치장품 뽑기권 구매 상점").apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1)})

        val serverHoldGold = getHoldGold(plugin).toLong()
        val serverMakeGold = getMakeGold(plugin).toLong()

        //val serverEconomy: String = (
        //    if (serverHoldGold - serverMakeGold <= -1000000L) "&c&l나쁨"
        //    else if (serverHoldGold - serverMakeGold >= 1000000L) "&a&l좋음"
        //    else "&e&l보통"
        //)

        gui.setItem(18, getItem(
            "gold_ingot",
            "&8&l서버 경제 현황",
            listOf(
                "&f",
                "&f&l서버 보유 골드: &6&l${serverHoldGold.toFormat()} 골드",
                "&f&l서버 발행 골드: &6&l${serverMakeGold.toFormat()} 골드",
                //"&f",
                //"&8서버 경제 상태: $serverEconomy"
            )
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
    }
}