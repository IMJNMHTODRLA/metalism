package _RedGold__.main.functions

import _RedGold__.main.functions.Color.gc
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private val SERIALIZER = LegacyComponentSerializer.legacySection()

inline fun Player.smartMessage(crossinline builderAction: SmartMessageBuilder.() -> Unit) {
    this.sendMessage(SmartMessageBuilder().apply(builderAction).build())
}

inline fun Server.smartBroadcast(crossinline builderAction: SmartMessageBuilder.() -> Unit) {
    this.broadcast(SmartMessageBuilder().apply(builderAction).build())
}

inline fun buildSmartMessage(crossinline builderAction: SmartMessageBuilder.() -> Unit): TextComponent {
    return SmartMessageBuilder().apply(builderAction).build()
}

class SmartMessageBuilder {
    private val rootBuilder = Component.text()
    private var currentNode: Component? = null

    fun text(content: String, style: (SmartMessageBuilder.() -> Unit)? = null) {
        flush()
        currentNode = SERIALIZER.deserialize(gc(content))
        style?.invoke(this)
    }

    private fun upNod(modifier: (Component) -> Component) {
        currentNode = currentNode?.let(modifier)
    }

    fun hover(text: String) = apply { upNod { it.hoverEvent(SERIALIZER.deserialize(gc(text)).asHoverEvent()) } }
    fun item(itemStack: ItemStack) = apply { upNod { it.hoverEvent(itemStack.asHoverEvent()) } }
    fun command(cmd: String) = apply { upNod { it.clickEvent(ClickEvent.runCommand(cmd)) } }
    fun url(link: String) = apply { upNod { it.clickEvent(ClickEvent.openUrl(link)) } }
    fun suggest(cmd: String) = apply { upNod { it.clickEvent(ClickEvent.suggestCommand(cmd)) } }
    fun copy(text: String) = apply { upNod { it.clickEvent(ClickEvent.copyToClipboard(text)) } }
    fun insertion(text: String) = apply { upNod { it.insertion(text) } }

    fun keybind(key: String) = apply {
        flush()
        currentNode = Component.keybind(key)
    }

    fun selector(selector: String) = apply {
        flush()
        currentNode = Component.selector(selector)
    }

    private fun flush() {
        currentNode?.let {
            rootBuilder.append(it)
            currentNode = null
        }
    }

    fun build(): TextComponent {
        flush()
        return rootBuilder.build()
    }
}
