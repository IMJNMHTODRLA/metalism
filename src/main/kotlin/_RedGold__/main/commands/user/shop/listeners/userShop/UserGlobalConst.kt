package _RedGold__.main.commands.user.shop.listeners.userShop

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.afterWith
import _RedGold__.main.functions.remainingWith
import _RedGold__.main.loads.SetFinalFlush
import _RedGold__.main.loads.SetSlowInit
import _RedGold__.main.managers.userShopManager.*
import org.bukkit.entity.Player
import java.util.*

object UserGlobalConst {
    @SetSlowInit
    val allUserShopData by lazy {
        getAllUserShopData()
    }

    val newUserItemData = mutableMapOf<UUID, MutableList<UserShopEntry>>()

    @SetFinalFlush
    fun final() {
        updateAllUserShopData(allUserShopData, newUserItemData)
    }

    private val dbCooldown = mutableMapOf<UUID, Long>()
    private const val COOLDOWN_TIME = 2

    fun isUnderCooldown(player: Player): Boolean {
        val uuid = player.uniqueId

        val cooldown = dbCooldown[uuid]?: 0
        if (!cooldown.afterWith(COOLDOWN_TIME)) {
            player.sendMsg("&c${cooldown.remainingWith(COOLDOWN_TIME)}초 후에 다시 시도해주세요.")
            return true
        }
        dbCooldown[uuid] = now
        return false
    }
}