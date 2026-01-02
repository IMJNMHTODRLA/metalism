package _RedGold__.main.command.event.sys.eventGui

import _RedGold__.main.Main.Event.EVENT_ITEM
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.command.betting.sys.coinGui.CoinHolder
import _RedGold__.main.command.event.sys.rankRewardGui.RankRewardGui
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.RunScoreboard
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class EventGui {
    fun openGui(player: Player) {
        val gui = EventHolder().inventory

        val background = getItem(
            "light_blue_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("324DFC")}§l§oE
                ${rgb("3952FD")}§l§oT
                ${rgb("4057FD")}§l§oA
                ${rgb("475CFD")}§l§oL
                ${rgb("4E60FD")}§l§oI
                ${rgb("5565FD")}§l§oS
                ${rgb("5B6AFE")}§l§oM 
                ${rgb("6973FE")}§l§oE
                ${rgb("7078FE")}§l§oV
                ${rgb("777DFE")}§l§oE
                ${rgb("7E82FF")}§l§oN
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(21, getItem(
            EVENT_ITEM,
            "&f&l진행되는 이벤트: $EVENT_NAME",
            listOf(
                "",
                "&7&l좌클릭 시 이벤트에 참가됩니다.",
                "&7&l우클릭 시 이벤트 순위 확인이 가능합니다.",
                "&c&l주의: 이벤트에 한번 참여시 취소는 불가능 합니다.",
                "",
                "&f&l이벤트 설명은 &b&l디스코드 &7&l#이벤트 채널&f&l에서 &e&l이벤트 설명을 볼 수 있습니다.",
                "&8&l기간: 00월 01일 12:00 - 00월 00일 10:00"
            )
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 8)})

        gui.setItem(22, getItem(
            "gold_ingot",
            "$EVENT_NAME &6&l랭크 순위 및 보상"
        ))

        gui.setItem(23, getItem(
            "experience_bottle",
            "$EVENT_NAME &f&l이벤트 점수 보상 받기"
        ))

        val uuid = player.uniqueId

        gui.setItem(40, getPlayerSkull(
            player.name,
            "&b&l${player.name}&f&l님",
            listOf(
                "",
                "&f&l이벤트 점수: &d&l${point[uuid]?.toFormat()?: "-"} 점수",
                "&f&l랭크: ${RankRewardGui().getTier2Player(uuid)}",
            )
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 8)})
        //랭크별 상위 그런거
        //
        //챌린저: 1위
        //마스터: 2~5위
        //플레티넘: 6위~상위 10%
        //골드: 상위 11%~30%
        //실버: 상위 31%~50%
        //브론즈: 상위 51%~100%

        player.openInventory(gui)
        player.playSound(player.location, Sound.AMBIENT_CAVE, 1f, 1f)
    }
}