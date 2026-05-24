package _RedGold__.main.core.guild.settings.leader.policy

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.modify
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player

object PolicyGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Leader(uuid) ?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            val policyData = getPolicyData(id)?: return@taskAsync

            task {
                val gui = PolicyHolder(policyData).inventory
                gui.item(BACKGROUND)

                policyDisplayItemList.forEachIndexed { i, (comp, item) ->
                    gui.item[11 + i] = item.modify {
                        if (comp?.get(policyData) == true) enchantEffect()
                    }
                }

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}