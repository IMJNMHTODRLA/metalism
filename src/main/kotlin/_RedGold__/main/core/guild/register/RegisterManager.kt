package _RedGold__.main.core.guild.register

import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.isGuildJoin
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player

fun registerGuild(player: Player, name: String?) =
    taskAsync {
        if (name.isNullOrBlank()) {
            player.sendMsg("&c길드 이름을 작성해주세요.")
            return@taskAsync
        }

        val uuid = player.uniqueId
        if (isGuildJoin(uuid)) return@taskAsync

        if (player.data.gold < REGISTER_FEE) {
            player.sendMsg("&c골드가 부족합니다. 필요 골드: ${(REGISTER_FEE - player.data.gold).toFormat()} 골드")
            return@taskAsync
        }

        player.data.gold -= REGISTER_FEE

        val id = insertGuild(name, uuid)

        joinedGuildCache[uuid] = id
        guildLevelCache[id] = 1

        player.sendMsg("길드 등록에 성공하였습니다.")
    }