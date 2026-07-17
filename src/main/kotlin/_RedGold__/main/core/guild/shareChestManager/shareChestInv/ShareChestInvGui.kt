package _RedGold__.main.core.guild.shareChestManager.shareChestInv

import _RedGold__.main.core.guild.expManager.currentLevel
import _RedGold__.main.core.guild.expManager.expBenefits.shareChestSizeBenefits
import _RedGold__.main.core.guild.getGuildId2Member
import _RedGold__.main.core.guild.getGuildStats
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotGuildJoinMsg
import _RedGold__.main.core.guild.shareChestManager.getShareChest
import _RedGold__.main.core.guild.shareChestManager.shareChestData
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import org.bukkit.entity.Player

object ShareChestInvGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Member(uuid)?: run {
                sendNotGuildJoinMsg(player)
                return@taskAsync
            }

            val stats = getGuildStats(id)?: return@taskAsync
            val level = currentLevel(stats.exp)
            val size = shareChestSizeBenefits(level)

            if (size <= 0) {
                player.fail("&c&l공용 창고 크기가 0 이하입니다.")
                return@taskAsync
            }

            val contents = shareChestData[id]
                ?.let { emptyArray() }
                ?: getShareChest(id, size)

            task {
                val gui = shareChestData.getOrPut(id) {
                    val gui = ShareChestInvHolder(size).inventory
                    gui.contents = contents

                    gui
                }

                player.inv + gui
            }
        }
    }
}