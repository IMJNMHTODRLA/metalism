package _RedGold__.main.function

import _RedGold__.main.functions.Color.gc
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.entity.Player

enum class MsgType {
    HOVER,
    //HOVER_ITEM,
    //HOVER_ENTITY,
    CLICK_COMMAND,
    CLICK_URL,
    CLICK_SUGGEST,
    COPY_TEXT,
    CHANGE_PAGE
}

object EasyComponent {
    private val pattern = "(?<!\\\\)%([^%]+)(?<!\\\\)%".toRegex()

    fun Player.newSendMsg(format: String, vararg actions: Pair<MsgType, String>) {
        val matches = pattern.findAll(format).toList()

        var component = Component.text("")
        var lastIndex = 0

        fun toComp(text: String): Component {
            return LegacyComponentSerializer.legacySection().deserialize(gc(text.replace("\\%", "%")))
        }

        matches.forEachIndexed { index, matchResult ->
            val beforeText = format.substring(lastIndex, matchResult.range.first)
            if (beforeText.isNotEmpty()) component = component.append(toComp(beforeText))

            val innerText = matchResult.groupValues[1]
            var part = toComp(innerText)

            if (index < actions.size) {
                val (type, value) = actions[index]
                part = when (type) {
                    MsgType.HOVER -> part.hoverEvent(toComp(value))
                    MsgType.CLICK_COMMAND -> part.clickEvent(ClickEvent.runCommand(value))
                    MsgType.CLICK_URL -> part.clickEvent(ClickEvent.openUrl(value))
                    MsgType.CLICK_SUGGEST -> part.clickEvent(ClickEvent.suggestCommand(value))
                    MsgType.COPY_TEXT -> part.clickEvent(ClickEvent.copyToClipboard(value))
                    MsgType.CHANGE_PAGE -> part.clickEvent(ClickEvent.changePage(value))
                }
            }

            component = component.append(part)
            lastIndex = matchResult.range.last + 1
        }

        if (lastIndex < format.length) component = component.append(toComp(format.substring(lastIndex)))
        this.sendMessage(component)
    }
}