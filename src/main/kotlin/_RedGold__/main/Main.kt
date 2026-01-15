package _RedGold__.main

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.HttpConn.send
import _RedGold__.main.function.Logger.info
import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.load.PreLoad
import com.google.gson.Gson
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.Node
import okhttp3.*
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class Main : JavaPlugin() {
    object Event {
        const val EVENT_NAME = "&b&l대결전(PVE)"
        const val EVENT_CODE = "randomEffect"
        const val EVENT_ITEM = "diamond_sword"
        val START_TIME: LocalDateTime = LocalDateTime.of(2025, 12, 7, 12, 0, 0)
        val END_TIME: LocalDateTime = LocalDateTime.of(3025, 12, 13, 10, 0, 1)
    }

    object Gacha {
        const val IS_LIMITED = false
        const val GACHA_MESSAGE = "상시 뽑기"
        val gachaPercent = listOf(1.5, 1.5, 47.0, 50.0)
    }

    private val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).build()
    private val api = LuckPermsProvider.get()

    data class DonationStruct(
        val mid: String,
        val amount: Int
    )

    data class MonthlyStruct(
        val mid: String,
        val exp: Long
    )

    object Boost {
        val monthlySubData: MutableMap<UUID, Long> = mutableMapOf()
    }

    override fun onEnable() {
        if (!isFileExists(this, "server_gold", "hold.data")) WriteSave(this, "server_gold", "hold.data", "0")
        if (!isFileExists(this, "server_gold", "make.data")) WriteSave(this, "server_gold", "make.data", "0")

        client.send("7201/boost/getall?table=donations@post", {response ->
            val resultList = Gson().fromJson(response.body!!.charStream(), Array<DonationStruct>::class.java)
            for (result in resultList) {
                val mid = result.mid
                val amount = result.amount

                if (amount < 30_000) continue

                val user = api.userManager.loadUser(mid.toUuid()).join()
                if (user.cachedData.permissionData.checkPermission("Main.plus").asBoolean()) continue

                user.data().add(Node.builder("Main.plus").value(true).build())
                api.userManager.saveUser(user).join()

                saveDataUuid(this, mid.toUuid(), "cash", getDataUuid(this, mid.toUuid(), "cash").toLong() + 250)
                info("plus 랭크 지급/${mid}|${amount}")
            }
        }) //영구 후원 그거

        val now = System.currentTimeMillis() / 1000

        client.send("7201/boost/getall?table=monthly_sub@post", {response ->
            val resultList = Gson().fromJson(response.body!!.charStream(), Array<MonthlyStruct>::class.java)
            for (result in resultList) {
                val mid = result.mid
                val exp = result.exp

                if (exp > now) monthlySubData[mid.toUuid()] = exp
            }
        })

        PreLoad.loadClass(this)
    }

    override fun onDisable() {

    }
}
