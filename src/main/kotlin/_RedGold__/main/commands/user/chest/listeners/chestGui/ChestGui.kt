package _RedGold__.main.commands.user.chest.listeners.chestGui

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.afterWith
import _RedGold__.main.functions.remainingWith
import _RedGold__.main.managers.chestManager.DEF_CHEST_SLOT
import _RedGold__.main.managers.chestManager.getChest
import _RedGold__.main.managers.chestManager.isChestSaving
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ChestGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, page: Int) {
        val uuid = player.uniqueId

        if (isChestSaving.contains(uuid)) {
            player.fail("&c창고를 데이터베이스에 저장 중입니다.")
            return
        }

        val cooldown = ChestValue.dbCooldown[uuid]?: 0
        if (!cooldown.afterWith(ChestConst.COOLDOWN_TIME)) {
            player.sendMsg("&c${cooldown.remainingWith(ChestConst.COOLDOWN_TIME)}초 후에 다시 시도해주세요.")
            return
        }
        ChestValue.dbCooldown[uuid] = now

        val gui = ChestHolder(page).inventory
        plugin.taskAsync {
            val itemArray = getChest(uuid, page * (DEF_CHEST_SLOT + 1L))

            plugin.task {
                if (!player.isOnline) return@task

                gui.contents = itemArray

                player.inv + gui
                player.sendSound(Sound.BLOCK_CHEST_OPEN)
            }
        }
    }
}