package _RedGold__.main.commands.user.boost.listeners.info

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.logManager.LogTypeEnum
import _RedGold__.main.managers.logManager.writeLog
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player

object InfoGlobalConst {
    fun buy(player: Player, prodName: String, price: Int, alreadyOwned: () -> Boolean, action: Player.() -> Unit) {
        if (alreadyOwned()) {
            player.fail("&c이미 구매한 상품입니다.")
            return
        }

        if (player.data.ruby < price) {
            player.fail("&c루비가 부족합니다. 필요 루비: ${(price - player.data.ruby).toFormat()} 루비")
            return
        }

        player.data.ruby -= price
        player.good("&a${prodName}을(를) 구매했습니다.")

        try {
            player.action()
            writeLog("${player.uniqueId} 님의 $prodName 상품 구매 | 가격: $price | 성공", LogTypeEnum.SUCCESS)
        } catch (e: Exception) {
            writeLog("${player.uniqueId} 님의 $prodName 상품 구매 | 가격: $price | 에러: ${e.message}", LogTypeEnum.FAILURE)
            e.printStackTrace()
        }
    }
}