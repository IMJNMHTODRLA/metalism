package _RedGold__.main.command.shop.sys.cashShop.cashGui

import _RedGold__.main.Main.Gacha.GACHA_MESSAGE
import _RedGold__.main.Main.Gacha.gachaPercent
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.getHoldGold
import _RedGold__.main.function.ServerGold.getMakeGold
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

class CashGui(private val plugin: JavaPlugin) {
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

    private fun Inventory.si(n: Int, id: String, title: String) {
        this.setItem(n, getItem(id, title, listOf("", "&7&l뽑기 포인트로만 구매가 가능한 상점입니다.")))
    }

    fun openGui(player: Player, sound: Float = 1f) {
        val getPoint = getData(plugin, player, "ticket/point").toInt()
        val gui = CashHolder(getPoint).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.si(3, "netherite_sword", "&c&l킬 사운드 상점")
        gui.si(5, "redstone", "&4&l사망 사운드 상점")

        gui.si(12, "magenta_shulker_box", "&5&l키트 상점")
        gui.si(14, "netherite_pickaxe", "&5&l도구 상점")

        gui.si(21, "writable_book", "&e&l접속 메시지 상점")
        gui.si(23, "writable_book", "&a&l칭호 상점")

        gui.setItem(13, getItem(
            "emerald",
            "&a뽑기권 구매 또는 사용",
            listOf(
                "",
                "&a[구매 및 사용(좌클릭)] &f&l구매가: 50 캐시 &8&l(5연속 뽑기권 구매와 동시에 사용이 됩니다.)",
                "",
                "&a&l[확률표&7&l(현재 기간: &f&l${GACHA_MESSAGE}&7&l)]",
                "&5&l${gachaPercent[0]}%&8/&f&l랜덤 도구 지급",
                "&5&l${gachaPercent[1]}%&8/&f&l치장품 지급",
                "&6&l${gachaPercent[2]}%&8/&f&lCPVP 아이템 지급",
                "&e&l${gachaPercent[3]}%&8/&f&l20 경험치 지급",
                "",
                "&8&l뽑기권을 사용 시 1 뽑기 포인트가 적립됩니다.(보유 뽑기 포인트: $getPoint 포인트)",
                "&8&l5%확률로 2포인트가 추가 적립됩니다.",
            )
        ).apply{addUnsafeEnchantment(Enchantment.EFFICIENCY, 1); addItemFlags(ItemFlag.HIDE_ENCHANTS)})

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, sound, 1f)
    }
}