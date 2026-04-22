package _RedGold__.main.commands.user.mailbox.listeners.infoGui

import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.mailBoxManager.MailBoxData
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class InfoGui {
    fun openGui(player: Player, returnPage: Long, mailData: MailBoxData) {
        val gui = InfoHolder(returnPage, mailData).inventory

        gui.item(BACKGROUND)
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[13] = getItem(
            Material.BOOK,
            "&e&l우편함 보상",
            listOf("",
                "&f&l골드: &6&l${mailData.giveGold} 골드",
                "&f&l크리스탈: &b&l${mailData.giveCrystal} 크리스탈",
                "&7&l그 외 아이템 ${mailData.item?.amount?: "-"} 개",
                "",
                "&a&l좌클릭 시 우편함 보상 획득이 가능합니다.",
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)
    }
}