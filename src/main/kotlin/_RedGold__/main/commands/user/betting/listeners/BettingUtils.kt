package _RedGold__.main.commands.user.betting.listeners

import _RedGold__.main.commands.user.betting.listeners.diceGui.DiceConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

fun changeBet(player: Player, slot: Int, maxBet: Int, gui: Inventory, holder: BettingHolder) {
    val change = when (slot) {
        in 45..48 -> -(GlobalConst.DEFAULT_GOLD * 10.pow(48 - slot)) // 차감은 음수로
        in 50..53 -> GlobalConst.DEFAULT_GOLD * 10.pow(slot - 50) // 추가는 양수로
        else -> return
    }
    val newAmount = holder.betGold + change

    if (newAmount < 0) {
        player.fail("&c베팅 금액을 음수로 내릴 수 없습니다.")
        return
    }

    if (newAmount > maxBet) {
        player.fail("&c베팅 금액을 더 이상 높힐 수 없습니다.")
        return
    }

    holder.betGold = newAmount

    gui.item[49] = getItem(
        Material.GRAY_STAINED_GLASS_PANE,
        GlobalConst.BET_GOLD_MESSAGE.fill(
            "gold" to holder.betGold.toFormat()
        ).gc()
    )

    if (change < 0) {
        player.sendMsg("&c베팅 금액에서 ${(-change).toFormat()} 골드를 회수하였습니다. 베팅 금액: ${holder.betGold.toFormat()}")
        player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 2f)
    } else {
        player.sendMsg("&a베팅 금액에 ${change.toFormat()} 골드를 추가하였습니다. 베팅 금액: ${holder.betGold.toFormat()}")
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f)
    }
}

fun isStartOk(
    player: Player, holder: BettingHolder,
    addAction: () -> Pair<Boolean, String> = { true to "" }
): Pair<Boolean, String> {
    if (holder.betGold <= 0) {
        return false to "&c베팅 금액을 설정하지 않았습니다."
    }

    if (player.data.gold - holder.betGold < 0) {
        return false to "&c베팅 금액이 보유 골드를 초과하였습니다."
    }

    if (holder.betGold > DiceConst.MAX_BET) {
        return false to "&c베팅 금액이 너무 높습니다."
    }

    return addAction()
}

fun startBetting(
    player: Player, holder: BettingHolder,
    addAction: () -> Unit = {}
) {
    holder.isStart = true
    player.data.gold -= holder.betGold
}
