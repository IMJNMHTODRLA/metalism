package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.joinGui

import _RedGold__.main.Main.Gacha.GACHA_POINT_TO_GOLD_TIMES
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.good
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class JoinListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is JoinHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            val uuid = player.uniqueId
            val name = player.name
            event.isCancelled = true

            fun buy(id: Int, removePoint: Long) {
                val ticketPoint = getData(plugin, player, "ticket/point").toInt()

                if (ticketPoint < removePoint) {
                    player.fail("&c뽑기 포인트가 부족합니다. 필요 뽑기 포인트: ${(removePoint - ticketPoint).toFormat()} 뽑기 포인트")
                    return
                }

                saveData(plugin, player, "ticket/point", ticketPoint - removePoint)
                saveData(plugin, player, "join_message", id)

                addHoldGold(plugin, ticketPoint * GACHA_POINT_TO_GOLD_TIMES)

                player.good("&a접속 메시지 구매가 완료되었습니다.")
                JoinGui(plugin).openGui(player, 0f)
            }

            fun preview(message: String) {
                val style = symmetry.getOrNull(applyStyle[uuid]?: -1)?: ""
                val formatStyle = if (style.isNotEmpty()) "$style " else ""

                player.sendMessage(gc(message
                    .replace("%name%", name)
                    .replace("%rank%", getPlayerRankPrefix(player))
                    .replace("%style%", formatStyle)
                ))
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 0)
                    11 -> buy(1, 14)
                    12 -> buy(2, 14)
                    13 -> buy(3, 14)
                    14 -> buy(4, 15)
                    15 -> buy(5, 14)
                    16 -> buy(6, 15)

                    19 -> buy(7, 14)
                    20 -> buy(8, 16)
                    21 -> buy(9, 18)
                    22 -> buy(10, 17)
                    23 -> buy(11, 18)
                    24 -> buy(12, 14)
                    25 -> buy(13, 14)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview("&8[&a+&8] %style%%rank% %name% &e님이 서버에 접속했습니다.")
                    11 -> preview("&8[&a+&8] %style%%rank% %name% &e님이 서버에 나타났습니다.")
                    12 -> preview("&8[&a+&8] %style%%rank% %name% &7&lJoined")
                    13 -> preview("&8[&b✦&8] %style%%rank% %name% &e님 환영합니다!")
                    14 -> preview("&8[&a»&8] %style%%rank% %name% &e님이 &a&l온라인&e으로 전환했습니다.")
                    15 -> preview("&8[&7»&8] &fJo&ki&fned with %style%%rank% %name%")
                    16 -> preview("&8[&b»&8] %style%%rank% %name% &b&l님이 서버에 등장 하였습니다.")

                    19 -> preview("&8[&a+&8] %style%%rank% %name% 님이 서버에 &a&l생성되었습니다.")
                    20 -> preview("&8[&7?&8] %style%%rank% %name% 님이 서버에 &8&l접속...했나요?")
                    21 -> preview("&8[&a!&8] %style%%rank% %name% 님이 게임에 참여하였습니다!")
                    22 -> preview("&8[&a+&8] %style%%rank% %name% 님이 &2&l마인크래프트 세상에 들어왔습니다.")
                    23 -> preview("&8[&a+&8] %style%%rank% %name% 님이 서버에 접속하였습니다. &a&l환영해주세요!")
                    24 -> preview("&8[&a+&8] %style%%rank% %name%")
                    25 -> preview("&8+ %name%")
                }
                return
            }
        }
    }
}