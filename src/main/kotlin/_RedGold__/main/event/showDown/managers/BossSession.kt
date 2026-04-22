package _RedGold__.main.event.showDown.managers

import _RedGold__.main.event.showDown.DataManager
import _RedGold__.main.event.showDown.EventMetaDatas
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.api.toFormat
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.ticks
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.plugin.java.JavaPlugin

class BossSession(
    val plugin: JavaPlugin,
    val player: Player,
    val difficulty: Int,
) {
    //val scope = plugin.minecraftDispatcher + SupervisorJob()
    //while (session.isRunning) {
    //    session.player.setPlayerTime(setTime % 24000, false)
    //    setTime += 100
    //    delay(2.ticks)
    //}
    val uuid = player.uniqueId
    val bossEntity = BossEntity(plugin)

    val bossMetadata = EventMetaDatas.BossMetaData.entries[difficulty]
    val minionMetadata = EventMetaDatas.MinionMetaData.entries[difficulty]
    val pointMetaData = EventMetaDatas.PointMetaData.entries[difficulty]
    val coinMetaData = EventMetaDatas.CoinMetaData.entries[difficulty]
    val time = System.currentTimeMillis() / 1000

    val minionManager = MinionManager(plugin, this)
    val bossDisplay = BossDisplay(plugin, this)

    val bossBar = Bukkit.createBossBar(
        gc("&e&l${DataManager.BOSS_NAME_ASCII}"),
        BarColor.BLUE,
        BarStyle.SOLID
    )
    lateinit var boss: Zombie
    val minions = mutableSetOf<Zombie>()

    var isRunning = true

    fun init() {
        DataManager.battleSession[uuid] = this
        bossDisplay.loopTime()
        bossDisplay.showTitle()

        boss = bossEntity.bossSpawn(player, bossMetadata)
        bossBar.addPlayer(player)

        bossDisplay.bossBarTask()
        minionManager.spawnMinion()

        plugin.task(36000) {
            defeat()
        }
    }

    fun victory() {
        if (!isRunning && DataManager.battleSession[uuid] != null) return
        isRunning = false
        DataManager.battleSession.remove(uuid)

        val now = System.currentTimeMillis() / 1000
        val taken = now - time

        val clearPoint = pointMetaData.clearPoint
        val healthPoint = pointMetaData.healthPoint
        val timePoint = pointMetaData.timePoint - (pointMetaData.secTimePoint * taken)
        val totalPoint = (clearPoint + healthPoint + timePoint).coerceAtLeast(0)

        val point = DataManager.point[uuid]?: 0L
        val bestPoint = DataManager.bestPoint[uuid]?: 0L
        val isBestRecord = totalPoint > bestPoint

        DataManager.point[uuid] = point + totalPoint
        DataManager.cachingPoint[uuid] = totalPoint
        DataManager.cachingDifficulty[uuid] = difficulty
        if (isBestRecord) DataManager.bestPoint[uuid] = totalPoint

        val totalCommonCoin = DataManager.commonCoin[uuid]?: 0
        val totalAdvancedCoin = DataManager.advancedCoin[uuid]?: 0
        val commonCoin = coinMetaData.commonCoin
        val advancedCoin = coinMetaData.advancedCoin

        DataManager.commonCoin[uuid] = totalCommonCoin + commonCoin
        DataManager.advancedCoin[uuid] = totalAdvancedCoin + advancedCoin

        minions.removeIf { entity ->
            if (!entity.isValid) return@removeIf true
            entity.remove()
            true
        }

        if (boss.isValid) boss.remove()
        player.sendSound(Sound.ENTITY_WITHER_DEATH)

        plugin.launch {
            delay(40.ticks)
            player.sendMsg("&d&l클리어 점수: ${clearPoint.toFormat()}점")
            player.sendSound(Sound.UI_BUTTON_CLICK)
            delay(10.ticks)
            player.sendMsg("&d&l체력 점수: ${healthPoint.toFormat()}점")
            player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
            delay(10.ticks)
            player.sendMsg("&d&l시간 점수: ${timePoint.toFormat()}점")
            player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f)
            delay(20.ticks)
            player.sendTitle("&e&l총 점수", "", 0, 20, 0)
            player.sendSound(Sound.UI_BUTTON_CLICK)
            delay(20.ticks)
            player.sendTitle("&e&l총 점수", "&d&l${totalPoint}점!${
                isBestRecord then "&8/&6&l최고 기록!"
            }", 0, 20, 0)
            player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE)
            delay(30.ticks)
            player.sendMsg("&8&l&o획득 한 대결전 코인: $commonCoin 코인")
            player.sendMsg("&8&l&o획득 한 고급 대결전 코인: $advancedCoin 고급 코인")
            player.sendSound(Sound.UI_TOAST_OUT)
        }
    }

    fun defeat() {
        if (!isRunning && DataManager.battleSession[uuid] != null) return
        isRunning = false
        DataManager.battleSession.remove(uuid)

        val healthPoint = pointMetaData.healthPoint - (boss.health * EventMetaDatas.DAMAGE_HEALTH_POINT).toLong()
        val totalPoint = healthPoint.coerceAtLeast(0)

        val point = DataManager.point[uuid]?: 0L
        val bestPoint = DataManager.bestPoint[uuid]?: 0L
        val isBestRecord = totalPoint > bestPoint

        DataManager.point[uuid] = point + totalPoint
        DataManager.cachingPoint[uuid] = totalPoint
        DataManager.cachingDifficulty[uuid] = difficulty
        if (isBestRecord) DataManager.bestPoint[uuid] = totalPoint

        minions.removeIf {entity ->
            if (!entity.isValid) return@removeIf true
            entity.remove()
            true
        }

        if (boss.isValid) boss.remove()
        player.sendSound(Sound.ENTITY_ZOMBIE_VILLAGER_CURE)

        plugin.launch {
            delay(40.ticks)
            player.sendMsg("&d&l클리어 점수: 0점")
            player.sendSound(Sound.ENTITY_PLAYER_ATTACK_CRIT)
            delay(10.ticks)
            player.sendMsg("&d&l체력 점수: ${healthPoint.toFormat()}점")
            player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
            delay(10.ticks)
            player.sendMsg("&d&l시간 점수: 0점")
            player.sendSound(Sound.ENTITY_PLAYER_ATTACK_CRIT, 2f)
            delay(20.ticks)
            player.sendTitle("&e&l총 점수", "", 0, 20, 0)
            player.sendSound(Sound.UI_BUTTON_CLICK)
            delay(20.ticks)
            player.sendTitle("&e&l총 점수", "&d&l${totalPoint}점!${
                isBestRecord then "&8/&6&l최고 기록!"
            }", 0, 20, 0)
            player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE)
            delay(30.ticks)
            player.sendMsg("&8&l&o획득 한 대결전 코인: 0 코인")
            player.sendMsg("&8&l&o획득 한 고급 대결전 코인: 0 고급 코인")
            player.sendSound(Sound.UI_TOAST_OUT)
        }
    }

    fun defeatQuit() {
        if (!isRunning && DataManager.battleSession[uuid] != null) return
        isRunning = false
        DataManager.battleSession.remove(uuid)

        val healthPoint = pointMetaData.healthPoint - (boss.health * EventMetaDatas.DAMAGE_HEALTH_POINT).toLong()
        val totalPoint = healthPoint.coerceAtLeast(0)

        val point = DataManager.point[uuid]?: 0L
        val bestPoint = DataManager.bestPoint[uuid]?: 0L
        val isBestRecord = totalPoint > bestPoint

        DataManager.point[uuid] = point + totalPoint
        DataManager.cachingPoint[uuid] = totalPoint
        DataManager.cachingDifficulty[uuid] = difficulty
        if (isBestRecord) DataManager.bestPoint[uuid] = totalPoint

        minions.removeIf {entity ->
            if (!entity.isValid) return@removeIf true
            entity.remove()
            true
        }

        if (boss.isValid) boss.remove()
    }
}