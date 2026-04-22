package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.limitGui

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class LimitGui {
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
        val gui = LimitHolder().inventory

        fun Int.fi(id: String, title: String, pur: Int) {
            gui.setItem(this, getItem(
                id,
                "&f&l$title",
                listOf(
                    "&f",
                    prefix,
                    "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()} 뽑기 포인트",
                    "&a&l[미리보기(우클릭)] &7&l우클릭 시 아이템을 미리 볼 수 있습니다.",
                )
            ).apply {
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
                addUnsafeEnchantment(Enchantment.PROTECTION, 1)
            })
        }

        fun Int.none() {
            gui.setItem(this, getItem(
                "barrier",
                "&c&l판매하고 있는 아이템이 아닙니다.",
                listOf(
                    "&f",
                    prefix,
                    "&c&l[구매 불가]",
                    "&c&l[미리보기 불가]",
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

        //80 캐시 = 1 뽑기 포인트
        10.fi("dragon_breath", "&d&l[ 무한의 해방자 ]", 80)
        11.fi("echo_shard", "&4&l[ 제약의 인장 ]", 70)
        12.fi("amethyst_shard", "&c&l[ 임계점의 파편 ]", 60)
        for (i in 13..16) i.none()

        for (i in 19..25) i.none()

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}