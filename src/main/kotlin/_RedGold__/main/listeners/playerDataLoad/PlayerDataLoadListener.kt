package _RedGold__.main.listeners.playerDataLoad

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.TimeTool.unixToDays
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PlayerManager
import _RedGold__.main.managers.database.loadPlayerData
import _RedGold__.main.managers.banManager.BanEnum
import _RedGold__.main.managers.banManager.getBan
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

@RequireListener
class PlayerDataLoadListener : Listener {
    @EventHandler
    fun onLoadPlayerDataPreLogin(event: AsyncPlayerPreLoginEvent) { //플레이어의 데이터를 db에서 가져오기
        val uuid = event.uniqueId

        getBan(uuid)?.let {
            if (it.type == BanEnum.PERM_BAN) {
                event.kickMessage = PlayerDataLoadConst.PERM_BAN_MESSAGE.fill(
                    "reason" to it.reason,
                    "uuid" to it.uuid,
                    "bannedAt" to it.bannedAt
                ).gc()

                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                return
            }

            val timeRemain = it.expiresAt!! - now
            if (timeRemain > 0) {
                val timeFormat = unixToDays(timeRemain)

                event.kickMessage = PlayerDataLoadConst.TEMP_BAN_MESSAGE.fill(
                    "days" to timeFormat.days.toFormat(),
                    "hours" to timeFormat.hours,
                    "minutes" to timeFormat.minutes,
                    "seconds" to timeFormat.seconds,

                    "reason" to it.reason,
                    "uuid" to it.uuid,
                    "bannedAt" to it.bannedAt
                ).gc()

                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                return
            }
        }

        PlayerManager.getOrLoad(uuid) { loadPlayerData(uuid) }
    }
}