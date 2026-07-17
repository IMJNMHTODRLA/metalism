package _RedGold__.main.core.guild.homeManager.home

import _RedGold__.main.core.guild.getGuildId2Member
import _RedGold__.main.core.guild.homeManager.getGuildHome
import _RedGold__.main.core.guild.sendNotGuildJoinMsg
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import org.bukkit.Sound
import org.bukkit.entity.Player

fun teleportGuildHome(player: Player) =
    taskAsync {
        val uuid = player.uniqueId

        val id = getGuildId2Member(uuid)?: run {
            sendNotGuildJoinMsg(player)
            return@taskAsync
        }

        val location = getGuildHome(id)?: return@taskAsync

        task {
            player.teleportAsync(location)
            player.sendMessage("&a&l순간이동 완료!")
            player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)
        }
    }