package _RedGold__.main.core.guild.join

import _RedGold__.main.core.guild.expManager.currentLevel
import _RedGold__.main.core.guild.expManager.expBenefits.getMaxMembers
import _RedGold__.main.core.guild.getGuildStats
import _RedGold__.main.core.guild.isGuildJoin
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.taskAsync
import org.bukkit.entity.Player

fun joinGuildWithId(id: Int, player: Player) =
    taskAsync {
        val uuid = player.uniqueId
        if (isGuildJoin(uuid)) return@taskAsync

        val guildStats = getGuildStats(id)
            ?: run {
                player.sendMsg("&c길드가 존재하지 않습니다.")
                return@taskAsync
            }

        if (id in guildJoinLocked) {
            player.sendMsg("&c동시에 가입 중인 플레이어가 있어 가입이 임시로 잠겨 있습니다.")
            return@taskAsync
        }

        val guildLevel = currentLevel(guildStats.exp)
        if (!guildStats.allowSearch) {
            player.sendMsg("&c길드 ID로 가입이 불가능 합니다.")
            return@taskAsync
        }

        val totalMembers = guildStats.totalMembers
        val maxMembers = getMaxMembers(guildLevel)

        if (totalMembers >= maxMembers) {
            player.sendMsg("&c길드 최대 인원에 도달하여 가입을 할 수 없습니다.")
            return@taskAsync
        }

        val banData = playerGuildBanData(id, uuid)
        if (banData != null) {
            player.sendMsg(banData.message)
            return@taskAsync
        }

        val isInWhitelist = playerGuildInWhitelist(id, uuid)
        if (guildStats.enableWhitelist && isInWhitelist) {
            player.sendMsg("&c화이트리스트에 설정이 안 되어 있습니다.")
            return@taskAsync
        }

        setJoinGuildDatabase(id, uuid)
    }
