package _RedGold__.main.command.verify

import com.google.gson.Gson
import _RedGold__.main.function.Color.gc
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import com.google.gson.JsonObject
import okhttp3.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException
import java.util.concurrent.TimeUnit

@RequireCommandExecutor("연동", "user", "", ["verify"])
@RequireTabExecutor
@RequireJavaPlugin
class Verify(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private var waitTime: MutableMap<String, Long> = mutableMapOf()
    private val client = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .build()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false
        val now = System.currentTimeMillis() / 1000
        val name = player.name

        if ((waitTime[name]?: 0L) > now) {
            player.sendMessage(gc(
                "&c&l${waitTime[name]!! - now}초 후에 다시 시도할 수 있습니다."
            ))
            return true
        }

        val url = HttpUrl.Builder()
            .scheme("http")
            .host("localhost")
            .port(7201)
            .addPathSegment("auth")
            .addPathSegment("create")
            .build()

        val body = FormBody.Builder()
            .add("name", player.name)
            .add("uuid", player.uniqueId.toString())
            .add("secret", "MTQzMTg2ODgyNDEwMzTMyNjg1MAG64EbcxDwcyNjg1MAMAG6cYi64EbTMyNjg1YTUtYThkNC03ZjKlMAG64EbcxDwc")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                player.sendMessage(gc("&c&lAPI 서버 연결에 실패했습니다. 관리자에게 문의해주세요."))
            }

            override fun onResponse(call: Call, response: Response) {
                val returnJson = Gson().fromJson(response.body!!.charStream(), JsonObject::class.java)

                Bukkit.getScheduler().runTask(plugin, Runnable {
                    if (!returnJson["result"].asBoolean) {
                        player.sendMessage(gc("&c&l연동 코드를 생성하는데 실패하였습니다. 관리자에게 문의 해주세요."))
                        return@Runnable
                    }

                    player.sendMessage(gc("&a&l연동 코드 생성이 완료되었습니다."))
                    player.sendMessage(gc("&f&l당신의 연동 코드는 &e&l${returnJson["code"].asString}&f&l입니다."))
                    player.sendMessage(gc("&f&l만료까지 &c&l1분 &f&l남았습니다."))

                    waitTime[name] = (System.currentTimeMillis() / 1000) + 60
                })
            }
        })

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return emptyList()
    }
}
