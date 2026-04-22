package _RedGold__.main.commands.boost.sys.goDiscord

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Sound
import org.bukkit.entity.Player

class GoDiscord {
    //discord://-/invite/minecraft

    fun sendMsg(player: Player) {
        player.sendMessage(Component.text("클릭하여 디스코드 서버에 접속해 후원하기")
            .color(NamedTextColor.BLUE)
            .clickEvent(ClickEvent.openUrl("https://discord.gg/invite/minecraft"))
        )

        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
    }
}