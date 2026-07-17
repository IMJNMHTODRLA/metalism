package _RedGold__.main.core.guild

import _RedGold__.main.core.guild.chat.sendGuildChat
import _RedGold__.main.core.guild.command.getHeaderTab
import _RedGold__.main.core.guild.command.getPayloadTab
import _RedGold__.main.core.guild.command.getSubTab
import _RedGold__.main.core.guild.join.joinGuild
import _RedGold__.main.core.guild.register.registerGuild
import _RedGold__.main.core.guild.search.searchGui.SearchGui
import _RedGold__.main.core.guild.settings.SettingsGui
import _RedGold__.main.core.guild.settings.leader.whitelist.autoDetectWhitelist
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireTabExecutor
@RequireCommandExecutor("guild", PermissionEnum.USER, aliases = ["길드"])
class Guild : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId

        if (args.isEmpty()) SettingsGui.openGui(player)

        val headerArg = args.getOrNull(0) //<command> arg1
        val payloadArg = args.getOrNull(1) //<command> arg1 arg2
        val subArg = args.getOrNull(2) //<command> arg1 arg2 arg3

        when(headerArg) {
            "길드ID" -> {
                val guildId = joinedGuildCache[uuid]

                if (guildId == null) player.sendMsg("&7가입된 길드가 없습니다.")
                else player.sendMsg("&a가입된 길드ID: $guildId")
            }

            "설정" -> SettingsGui.openGui(player)
            "화이트리스트" -> return autoDetectWhitelist(player, payloadArg, subArg)

            "챗", "채팅", "c" -> {
                val guildId = joinedGuildCache[uuid]
                val message = args.drop(1).joinToString(" ")

                sendGuildChat(player, guildId, message)
            }

            "가입" -> return joinGuild(player, payloadArg, subArg)
            "검색" -> SearchGui.openGui(player, payloadArg)

            "등록" -> registerGuild(player, payloadArg)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        val headerArg = args.getOrNull(0)

        return when(args.size) {
            1 -> getHeaderTab()
            2 -> getPayloadTab(headerArg)
            3 -> getSubTab(headerArg)

            else -> emptyList()
        }
    }
}