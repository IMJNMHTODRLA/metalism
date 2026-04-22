package _RedGold__.main.sys

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Gui.addPotion
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.sys.KillRespawn.ChatApply.applyDeath
import _RedGold__.main.sys.KillRespawn.ChatApply.applyKill
import _RedGold__.main.sys.KillRespawn.ChatApply.ggTiming
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitch
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitchKill
import _RedGold__.main.sys.KillRespawn.ChatApply.soundType
import _RedGold__.main.sys.KillRespawn.ChatApply.soundTypeKill
import _RedGold__.main.sys.KillRespawn.KillStreakObject.killStreak
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
import org.bukkit.potion.PotionEffectType
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@RequireJavaPlugin
@RequireListener
class KillRespawn(private val plugin: JavaPlugin) : Listener {
    private val whoKill: MutableMap<UUID, Long> = ConcurrentHashMap()
    private val killStreakBonus: MutableMap<UUID, Long> = ConcurrentHashMap()
    private val lastKilled: MutableMap<UUID, MutableMap<UUID, Long>> = ConcurrentHashMap()

    object KillStreakObject {
        val killStreak: MutableMap<UUID, Int> = ConcurrentHashMap()
    }

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

        val soundPitch = listOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f) //13
        val deathSoundMessage = listOf(
            "물에 빠진", "귀신", "비", "흑우", "박쥐", "돼지",
            "모루", "부숴진", "폭팔", "먹다", "타버림", "금고 부숨", "웅장한 브금(김)"
        )

        var applyKill: MutableMap<UUID, Int> = ConcurrentHashMap()
        val soundTypeKill = listOf(
            Sound.ITEM_MACE_SMASH_GROUND_HEAVY, Sound.BLOCK_HONEY_BLOCK_FALL,
            Sound.BLOCK_SLIME_BLOCK_BREAK, Sound.ENTITY_ZOMBIE_DEATH, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,
            Sound.ENTITY_ZOGLIN_DEATH, Sound.BLOCK_ANVIL_USE, Sound.UI_TOAST_CHALLENGE_COMPLETE,
            Sound.ENTITY_SHEEP_AMBIENT, Sound.ITEM_GOAT_HORN_SOUND_1, Sound.ITEM_TRIDENT_RIPTIDE_1,
            Sound.ITEM_TRIDENT_THUNDER, Sound.ENTITY_GENERIC_DRINK
        )

        val soundPitchKill = listOf(1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        val killSoundMessage = listOf(
            "철퇴", "꿀", "슬라임", "좀비", "철문 공격", "조글린",
            "모루", "발전 과제", "셜커", "염소뿔", "삼지창", "웅장한 삼지창", "마심"
        )

        val ggColorMapping: List<String> = listOf(
            "&f", "&0&l", "&1&l", "&2&l", "&3&l", "&4&l", "&5&l", "&6&l", "&7&l", "&8&l", "&9&l",
            "&a&l", "&b&l", "&c&l", "&d&l", "&e&l", "&f&l"
        )
        val ggTiming: MutableMap<UUID, MutableMap<UUID, Long>> = ConcurrentHashMap()
    }

    private val secureRandom = SecureRandom()

    @EventHandler
    fun onKill(event: EntityDeathEvent) {
        val attacker = event.entity.killer?: return
        val victim = event.entity as? Player?: return

        val now = System.currentTimeMillis() / 1000

        val victimUuid = victim.uniqueId
        val attackerUuid = attacker.uniqueId

        val deathSound = (applyDeath[victimUuid]?: 0) - 1
        val killSound = (applyKill[attackerUuid]?: 0) - 1

        val attackerKill = getData(plugin, attacker, "kill").toLong()
        val victimDeath = getData(plugin, victim, "death").toLong()

        val attackerName = attacker.name

        val level = attacker.level

        saveData(plugin, attacker, "kill", attackerKill + 1)
        saveData(plugin, victim, "death", victimDeath + 1)

        val attackerGold = getData(plugin, attacker, "gold").toLong()
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

        var getGold = 12000L
        var removeGold = 15000L

        if (victimGold - 15000L < 0) {
            getGold = victimGold
            removeGold = victimGold
        }

        val victimBonus = killStreakBonus[victimUuid] ?: 0L
        if ((killStreak[victimUuid]?: 0) >= 30) {
            val steelGold = (victimBonus * 0.1).toLong()
            getGold += steelGold
            removeGold += steelGold

            attacker.sendMessage(gc("&f&l${attackerName}님의 &e&l연킬 보너스의 10%&8(${steelGold.toFormat()} 골드)&f&l를 &c&l뺏었습니다!"))
        }

        val lastKillMap = lastKilled.getOrPut(attackerUuid) {ConcurrentHashMap()}
        val streak: Int

        if ((lastKillMap[victimUuid]?: 0L) > now) {
            streak = killStreak[attackerUuid]?: 0
        } else {
            streak = (killStreak[attackerUuid]?: 0) + 1
            lastKillMap[victimUuid] = now + 150
        }

        val giveBonusGold = when {
            streak < 2 -> false
            streak in 2..9 -> streak % 2 == 0
            streak in 10..29 -> streak % 5 == 0
            else -> streak % 10 == 0
        }

        if (giveBonusGold) {
            val streakDouble = streak.toDouble()
            val bonusGold = when(streak) {
                in 2..9 -> 8000L * (streakDouble / 10 + 1)
                in 10..29 -> 9000L * (streakDouble / 8 + 1)
                in 30..59 -> 10000L * (streakDouble / 7 + 1)
                else -> 11000L * (streakDouble / 6 + 1)
            }.toLong()

            getGold += bonusGold
            killStreakBonus[attackerUuid] = (killStreakBonus[attackerUuid]?: 0) + bonusGold

            when {
                streak < 10 -> {
                    attacker.sendMessage(gc("&a&l${streak}연킬&f&l을 하여 &6&l${bonusGold.toFormat()} 골드&f&l를 획득하였습니다.&8&l(연킬은 매일마다 초기화 됩니다.)"))
                    attacker.playSound(attacker.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
                }
                streak in 10..29 -> {
                    attacker.sendMessage(gc("&c&l${streak}연킬&f&l을 하여 &6&l${bonusGold.toFormat()} 골드&f&l를 획득하였습니다.&8&l(연킬은 매일마다 초기화 됩니다.)"))
                    attacker.playSound(attacker.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.5f)
                }
                else -> {
                    attacker.sendMessage(gc("&d&l${streak}연킬&f&l을 하여 &6&l${bonusGold.toFormat()} 골드&f&l를 획득하였습니다.&8&l(연킬은 매일마다 초기화 됩니다.)"))

                    Bukkit.broadcastMessage(gc("&f&l${attackerName}님이 &d&l${streak}연킬을 달성하였습니다!"))
                    Bukkit.broadcastMessage(gc("&f&l${attackerName}님을 처치 시 &c&l획득한 연킬 보너스의 &4&l10%를 &c&l강탈할 수 있습니다."))

                    attacker.playSound(attacker.location, Sound.ENTITY_WITHER_SPAWN, 1.0f, 1.0f)
                    attacker.world.strikeLightningEffect(attacker.location)
                    attacker.addPotion(PotionEffectType.BLINDNESS, 2, 1)
                    Bukkit.getOnlinePlayers().forEach {it.playSound(it.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)}
                }
            }
        }

        saveData(plugin, attacker, "gold", attackerGold + getGold)
        saveData(plugin, victim, "gold", victimGold - removeGold)

        addHoldGold(plugin, removeGold)
        addMakeGold(plugin, getGold)

        attacker.sendMessage(gc("&f&l+&6&l${getGold.toFormat()} 골드"))
        attacker.sendMessage(gc("&f&l+&a&l${giveLevel.toFormat()} 경험치"))
        attacker.sendMessage(gc("&f&l+&c&l1 연킬&7&l($streak)"))
        attacker.sendMessage(gc("&7&l연킬 순위에서 보상을 획득 할 수 있습니다."))

        attacker.sendActionBar(gc(
            "&f&l+&6&l${getGold.toFormat()} 골드&8, &f&l+&a&l${giveLevel.toFormat()} 경험치"
        ))

        victim.sendMessage(gc(
            "&f&l당신은 플레이어에게 &4&l사망하여 &6&l${removeGold.toFormat()} 골드&f&l와 연킬을 &c&l잃었습니다."
        ))

        if (deathSound != -1) victim.playSound(victim.location, soundType[deathSound], 1.0f, soundPitch[deathSound])
        if (killSound != -1) attacker.playSound(attacker.location, soundTypeKill[killSound], 1.0f, soundPitchKill[killSound])

        whoKill[victimUuid] = now + 5
        killStreak[attackerUuid] = streak
        killStreak.remove(victimUuid)
        killStreakBonus.remove(victimUuid)

        val innerMap = ggTiming.getOrPut(attackerUuid) {ConcurrentHashMap()}
        innerMap[victimUuid] = now + 60
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        val victimUuid = (event.entity as? Player?: return).uniqueId
        if (whoKill[victimUuid] != null && whoKill[victimUuid]!! > System.currentTimeMillis() / 1000) event.isCancelled = true
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

        val uuid = event.player.uniqueId

        if (whoKill[uuid] != null && whoKill[uuid]!! > System.currentTimeMillis() / 1000) {
            event.player.sendMessage(gc("&f&l당신은 플레이어에게 &4&l사망하여 &a&l5초간 모든 데미지에 먼역입니다."))
        }
    }
}
