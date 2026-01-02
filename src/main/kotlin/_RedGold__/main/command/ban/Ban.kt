package _RedGold__.main.command.ban

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("ban", "owner", "", ["unban"])
@RequireTabExecutor
@RequireJavaPlugin
class Ban(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private val prefix = """
        ${rgb("2444FC")}§l[
        ${rgb("2A48FC")}§l* 
        ${rgb("3651FD")}§lM
        ${rgb("3C55FD")}§lE
        ${rgb("4359FD")}§lT
        ${rgb("495DFD")}§lA
        ${rgb("4F61FD")}§lL
        ${rgb("5565FD")}§lI
        ${rgb("5B6AFE")}§lS
        ${rgb("616EFE")}§lM 
        ${rgb("6D76FE")}§lB
        ${rgb("747AFE")}§lA
        ${rgb("7A7EFE")}§lN 
        ${rgb("8687FF")}§l*
        ${rgb("8C8BFF")}§l]
    """.trimIndent().replace("\n", "")

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (label.contains("unban")) {
            if (args.isEmpty()) return false

            val uuid = try {
                UUID.fromString(args[0])
            } catch (e: IllegalArgumentException) {
                sender.sendMessage(gc("&cuuid 반환 실패"))
                return false
            }

            saveDataUuid(plugin, uuid, "ban", "temp;0;a")
            return true
        }

        if (label.contains("ban")) {
            if (args.size < 3) return false

            val uuid = args[0].toUuid()

            val now = System.currentTimeMillis() / 1000
            val tomorrow = Instant.now().atZone(ZoneId.systemDefault()).plusMonths(1).toInstant().epochSecond
            val reason = args[2] //예시: 게임 플레이 규칙 2항 위반

            val type: List<Any> = when(args[1]) {
                "temp" -> listOf("temp", tomorrow)
                "perm" -> listOf("perm", 0)
                else -> listOf("temp", 0)
            }

            saveDataUuid(plugin, uuid, "ban", "${type[0]};${type[1]};$reason")

            val heBan = Bukkit.getPlayer(uuid)
            if (heBan != null) {
                var timeRemain = tomorrow - now

                val days: Long = timeRemain / 86400L
                timeRemain %= 86400L
                val hours: Long = timeRemain / 3600L
                timeRemain %= 3600L
                val minutes: Long = timeRemain / 60L
                val seconds: Long = timeRemain % 60L

                val message = when(type[0]) {
                    "temp" -> "&f&l당신은 이 서버에서 &c&l${days}일 ${hours}시간 ${minutes}분 ${seconds}초 동안 &4&l일시적&c&l으로 서버 이용이 &4&l제한 되었습니다."
                    "perm" -> "&f&l당신은 이 서버에서 &4&l영구적&c&l으로 서버 이용이 &4&l제한 되었습니다."
                    else -> ""
                }

                heBan.kickPlayer(gc("""
                    
                    $prefix
                    
                    $message
                    &7&l사유: $reason
                    
                    &7&l항소를 하실려면 사용자 명 &b&l_al_1s__&7&l로 개인 DM을 보내주세요.
                    &7&l디스코드: &b&lhttps://discord.gg/[초대 코드]
                    
                    &8UUID: $uuid
                """.trimIndent()))
            }
            return true
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return Bukkit.getOnlinePlayers().stream()
                    .filter { player -> player.name.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault())) }
                    .map { player -> player.uniqueId.toString() }
                    .collect(Collectors.toList())

        if (args.size == 2 && label.contains("ban")) return listOf("temp", "perm")
        if (args.size == 3 && label.contains("ban")) return listOf("예시: 게임 플레이 규칙 2항 위반")

        return emptyList()
    }
}