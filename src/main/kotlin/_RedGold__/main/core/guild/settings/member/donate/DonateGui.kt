package _RedGold__.main.core.guild.settings.member.donate

import _RedGold__.main.core.guild.getGuildId2Member
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotGuildJoinMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object DonateGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Member(uuid)?: run {
                sendNotGuildJoinMsg(player)
                return@taskAsync
            }

            task {
                val gui = DonateHolder(id).inventory
                gui.item(BACKGROUND)

                gui.item[13] = getItem(
                    Material.GOLD_BLOCK,
                    "&a&l[기부 하기]",
                    "",
                    "&e&l클릭 시 길드에 &6&l0 골드&e&l를 기부하여 &a&l0 EXP&e&l를 증가시킵니다.",
                    "&c&l기부한 골드는 다시 받을 수 없습니다."
                )

                gui.item[31] = getItem(
                    Material.BLACK_STAINED_GLASS_PANE,
                    "&6&l기부 금액&f: &6&l0 골드"
                )

                repeat(4) {
                    val powGold = (DEFAULT_GOLD * 10.pow(it)).toFormat()

                    gui.item[30 - it] = getItem(
                        Material.RED_STAINED_GLASS_PANE,
                        "&c-$powGold 골드",
                        "", "&7클릭 시 기부 금액에서 $powGold 골드를 회수됩니다."
                    )

                    gui.item[32 + it] = getItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        "&a+$powGold 골드",
                        "", "&7클릭 시 기부 금액에서 $powGold 골드를 추가합니다."
                    )
                }

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}