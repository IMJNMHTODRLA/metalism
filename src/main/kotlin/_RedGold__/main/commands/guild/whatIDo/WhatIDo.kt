package _RedGold__.main.commands.guild.whatIDo

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("what_i_do", PermissionEnum.GUIDE, aliases = ["뭐해야함"])
class WhatIDo : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a말 그대로 입니다. 가이드를 하면됩니다.")
        return true
    }
}