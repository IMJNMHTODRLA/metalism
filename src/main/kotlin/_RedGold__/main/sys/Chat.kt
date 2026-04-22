package _RedGold__.main.sys

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import _RedGold__.main.sys.KillRespawn.ChatApply.ggColorMapping
import _RedGold__.main.sys.KillRespawn.ChatApply.ggTiming
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireListener
@RequireJavaPlugin
class Chat(private val plugin: JavaPlugin) : Listener {
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
        var message = event.message
        val now = System.currentTimeMillis() / 1000
        var addGgColor = ""
        var isGgMessage = false

        if (
            player.hasPermission("Main.plus") &&
            message.lowercase() == "gg" &&
            (ggTiming[uuid]?.values?.first()?: 0) > now
        ) {
            addGgColor = ggColorMapping[getData(plugin, player, "gg_color").toInt()]
            isGgMessage = true
            player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f)

            val ggSendUuid = ggTiming[uuid]?.keys?.first()
            if (ggSendUuid != null) {
                val victimOfflinePlayer = Bukkit.getOfflinePlayer(ggSendUuid)
                if (victimOfflinePlayer.isOnline) {
                    val victimPlayer = victimOfflinePlayer.player!!
                    victimPlayer.playSound(victimPlayer.location, Sound.ITEM_GOAT_HORN_SOUND_1, 1f, 1f)
                }
            }

            ggTiming.remove(uuid)
        }

        val messageFormat = "${getPlayerRankPrefix(player)} ${player.name}&f: $addGgColor"
        if (isGgMessage) message = message.uppercase()

        if ((applyStyle[uuid]?: -1) == -1) event.format = gc(messageFormat) + message
        else event.format = gc("${symmetry[applyStyle[uuid]!!]} $messageFormat") + message
    }
}