package _RedGold__.main.commands.owner.giveBook

import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import kotlin.random.Random

@RequireCommandExecutor("givebook", PermissionEnum.OWNER)
class GiveBook : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

        val allPages = List(100) {
            (1..196).joinToString("") {
                val codePoint = Random.nextInt(0x1F600, 0x1F650)
                String(Character.toChars(codePoint))
            }
        }

        val book = ItemStack(Material.WRITTEN_BOOK)
        val meta = book.itemMeta as BookMeta
        meta.title = "테스트용 책"
        meta.author = "Debug"
        meta.pages = allPages // 이모티콘 200글자 삽입
        book.itemMeta = meta

        sender.inventory.addItem(book)
        sender.sendMessage("§a이모티콘 200개가 담긴 책이 생성되었습니다!")
        return true
    }
}