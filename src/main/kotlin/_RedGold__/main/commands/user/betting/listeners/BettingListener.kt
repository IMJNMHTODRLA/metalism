package _RedGold__.main.commands.user.betting.listeners

import _RedGold__.main.commands.user.betting.listeners.diceGui.DiceConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

abstract class BettingListener<T : BettingHolder> : Listener {
    open fun startBetting(player: Player, holder: T) {
        holder.isStart = true
        player.data.gold -= holder.betGold
    }

    open fun isStartOk(
        player: Player, holder: T
    ): Boolean {
        if (holder.betGold <= 0) {
            player.fail("&c베팅 금액을 설정하지 않았습니다.")
            return false
        }

        if (player.data.gold - holder.betGold < 0) {
            player.fail("&c베팅 금액이 보유 골드를 초과하였습니다.")
            return false
        }

        if (holder.betGold > DiceConst.MAX_BET) {
            player.fail("&c베팅 금액이 너무 높습니다.")
            return false
        }

        return true
    }
}