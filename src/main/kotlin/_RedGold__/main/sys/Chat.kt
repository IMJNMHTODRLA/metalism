package _RedGold__.main.sys

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireListener
class Chat : Listener {
    object ChatApply {
        var applyStyle: MutableMap<UUID, Int> = ConcurrentHashMap()
        const val MAX_STYLE = 5
        val symmetry = listOf(
            "&a&l[시간의 연속]", "&4&l[킬러]", "&d&l[컬렉션]",
            "&8&l[무게 변화]", "&8&l[지각 변동]",

            "&4&l[미제 사건]",
            "&d&l[크리스탈]",
            "&c&l[하드코어]",
            "&f&l[백업 없음]",
            "&8&l[솔플]",
            "&8&l[팀플]",
            "&8&l[99/0/0]",
            "&a&l[EZ]",
            "&8&l[GG]",
            "&c&l[한방컷]",
            "&b&l[쉴드 전문가]",
            "&a&l[탱커]",
            "&c&l[딜러]",
            "&e&l[버퍼러]"
        )
    }

    @EventHandler
    fun onChat(event: PlayerChatEvent) {
        val player = event.player
        val uuid = player.uniqueId

        if ((applyStyle[uuid]?: -1) == -1) event.format = gc("${getPlayerRankPrefix(player)} ${player.name}&f: ${event.message}")
        else event.format = gc("${symmetry[applyStyle[uuid]!!]} ${getPlayerRankPrefix(player)} ${player.name}&f: ${event.message}")
    }
}