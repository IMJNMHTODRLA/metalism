package _RedGold__.main.sys

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.KillRespawn.ChatApply.applyDeath
import _RedGold__.main.sys.KillRespawn.ChatApply.applyKill
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitch
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitchKill
import _RedGold__.main.sys.KillRespawn.ChatApply.soundType
import _RedGold__.main.sys.KillRespawn.ChatApply.soundTypeKill
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@RequireJavaPlugin
@RequireListener
class KillRespawn(private val plugin: JavaPlugin) : Listener {
    private val whoKill: MutableMap<Player, Long> = mutableMapOf()
    object ChatApply {
        var applyDeath: MutableMap<UUID, Int> = ConcurrentHashMap()

        /** 얘가 death 임 */
        val soundType = listOf(
            Sound.AMBIENT_UNDERWATER_ENTER, Sound.AMBIENT_CAVE,
            Sound.WEATHER_RAIN, Sound.ENTITY_COW_DEATH, Sound.ENTITY_BAT_DEATH,
            Sound.ENTITY_PIG_DEATH, Sound.BLOCK_ANVIL_LAND, Sound.ITEM_TOTEM_USE,
            Sound.ENTITY_GENERIC_EXPLODE, Sound.ENTITY_GENERIC_EAT, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE,
            Sound.BLOCK_VAULT_BREAK, Sound.MUSIC_CREDITS
        )

        val soundPitch = listOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        val deathSoundMessage = listOf(
            "물에 빠진", "귀신", "비", "흑우", "박쥐", "돼지",
            "모루", "부숴진", "폭팔", "먹다", "타버림", "금고 부숨"
        )

        var applyKill: MutableMap<UUID, Int> = ConcurrentHashMap()
        val soundTypeKill = listOf(
            Sound.ITEM_MACE_SMASH_GROUND_HEAVY, Sound.BLOCK_HONEY_BLOCK_FALL,
            Sound.BLOCK_SLIME_BLOCK_BREAK, Sound.ENTITY_ZOMBIE_DEATH, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,
            Sound.ENTITY_ZOGLIN_DEATH, Sound.BLOCK_ANVIL_USE, Sound.UI_TOAST_CHALLENGE_COMPLETE
        )

        val soundPitchKill = listOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        val killSoundMessage = listOf("철퇴", "꿀", "슬라임", "좀비", "철문 공격", "조글린", "모루", "발전 과제")
    }

    private val secureRandom = SecureRandom()

    @EventHandler
    fun onKill(event: EntityDeathEvent) {
        val attacker = event.entity.killer?: return
        val victim = event.entity as? Player?: return

        val victimUuid = victim.uniqueId
        val attackerUuid = attacker.uniqueId

        val deathSound = (applyDeath[victimUuid]?: 0) - 1
        val killSound = (applyKill[attackerUuid]?: 0) - 1

        val attackerKill = getData(plugin, attacker, "kill").toLong()
        val victimDeath = getData(plugin, victim, "death").toLong()

        val level = attacker.level

        saveData(plugin, attacker, "kill", attackerKill + 1)
        saveData(plugin, victim, "death", victimDeath + 1)

        val attackerGold = getData(plugin, attacker, "gold").toLong()
        val attackerCash = getData(plugin, attacker, "cash").toLong()

        val victimGold = getData(plugin, victim, "gold").toLong()

        val giveLevel = when(level) {
            0 -> 7
            in 1..16 -> (2 * level) + 21
            in 17..31 -> (5 * level) - 32
            in 32..59 -> (9 * level) - 184
            in 60..79 -> (5 * level) - 100 // 기존: (7 * level) - 229
            in 80..99 -> (4 * level) - 135 // 기존: (7 * level) - 335
            in 100..149 -> (3 * level) - 77 // 기존: (5 * level) - 277
            in 150..200 -> (2 * level) - 42 // 기존: (3 * level) - 192
            else -> (1 * level) - 56 // 기존: (2 * level) - 156
        }

        attacker.giveExp(giveLevel)

        var getGold = 30000L
        var getCash = 2L
        var removeGold = 60000L

        if (victimGold - 50000L < 0) {
            getGold = victimGold
            getCash = 0
            removeGold = victimGold
        }

        saveData(plugin, attacker, "gold", attackerGold + getGold)
        saveData(plugin, attacker, "cash", attackerCash + getCash)

        saveData(plugin, victim, "gold", victimGold - removeGold)

        attacker.sendMessage(gc("&f&l+&6&l${getGold.toFormat()} 골드"))
        attacker.sendMessage(gc("&f&l+&6&l${getCash.toFormat()} 캐시"))
        attacker.sendMessage(gc("&f&l+&b&l${giveLevel.toFormat()} 경험치"))

        attacker.sendActionBar(gc(
            "&f&l+&6&l${getGold.toFormat()} 골드&8, &f&l+&b&l${getCash.toFormat()} 캐시&8, &f&l+&b&l${giveLevel.toFormat()} 경험치"
        ))

        victim.sendMessage(gc(
            "&f&l당신은 플레이어에게 &4&l사망하여 &6&l${removeGold.toFormat()} 골드&f&l를 &c&l잃었습니다."
        ))

        if (deathSound != -1) victim.playSound(victim.location, soundType[deathSound], 1.0f, soundPitch[deathSound])
        if (killSound != -1) attacker.playSound(attacker.location, soundTypeKill[killSound], 1.0f, soundPitchKill[killSound])

        whoKill[victim] = (System.currentTimeMillis() / 1000) + 5
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val victim = event.entity as? Player?: return

        if (whoKill[victim] != null && whoKill[victim]!! > System.currentTimeMillis() / 1000) event.isCancelled = true
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        if (!event.isBedSpawn) {
            val x = secureRandom.nextInt(-10000, 10000 + 1)
            val z = secureRandom.nextInt(-10000, 10000 + 1)

            val world = Bukkit.getWorld("world")!!
            val highestY = world.getHighestBlockYAt(x, z) + 1.0

            event.respawnLocation = Location(world, x + 0.5, highestY, z + 0.5)
        }

        if (whoKill[event.player] != null && whoKill[event.player]!! > System.currentTimeMillis() / 1000) {
            event.player.sendMessage(gc("&f&l당신은 플레이어에게 &4&l사망하여 &a&l5초간 모든 데미지에 먼역입니다."))
        }
    }
}
