package _RedGold__.main.event.randomEffect

import _RedGold__.main.Main.Event.END_TIME
import _RedGold__.main.Main.Event.START_TIME
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.advancedToken
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficulty
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficultyEffect
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent1
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent2
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.nextEvent
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.token
import _RedGold__.main.event.randomEffect.selectGui.SelectGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.defDataUuid
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.hasDataUuid
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityPotionEffectEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.lang.System
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireJavaPlugin
@RequireListener
class System(private val plugin: JavaPlugin) : Listener {
    object RandomEffectEvent {
        val difficulty: MutableMap<UUID, Int> = ConcurrentHashMap()
        val point: MutableMap<UUID, Long> = ConcurrentHashMap()
        val max: MutableMap<UUID, Int> = ConcurrentHashMap()
        val token: MutableMap<UUID, Long> = ConcurrentHashMap()
        val advancedToken: MutableMap<UUID, Long> = ConcurrentHashMap()
        val difficultyEffect = listOf(
            PotionEffect(PotionEffectType.HUNGER, Int.MAX_VALUE, 4, true, false),
            PotionEffect(PotionEffectType.SLOWNESS, Int.MAX_VALUE, 1, true, false),
            PotionEffect(PotionEffectType.MINING_FATIGUE, Int.MAX_VALUE, 0, true, false),
            PotionEffect(PotionEffectType.WEAKNESS, Int.MAX_VALUE, 1, true, false),
            PotionEffect(PotionEffectType.WITHER, Int.MAX_VALUE, 3, true, false),
        )
        var nextEvent = 0L

        val killEvent1: MutableMap<UUID, Boolean> = ConcurrentHashMap() //하드코어
        val killEvent2: MutableMap<UUID, Boolean> = ConcurrentHashMap() //얼티밋
    }
    private var min5times = 0

    private val giveDonePoint = listOf(0L, 100_000L, 150_000L, 300_000L, 350_000L, 550_000L)
    private val difficultyMessage = listOf("&7&l선택안함(Nothing)", "&e&l보통(Normal)", "&c&l어려움(Hard)", "&4&l하드코어(HardCore)", "&b&l익스트림(Extreme)", "&d&l얼티밋(Ultimate)")
    private val give5minute = listOf(0L, 10_000L, 15_000L, 30_000L, 35_000L, 60_000L)

    private val giveDoneToken = listOf(0L, 50L, 85L, 100L, 125L, 150L)
    private val giveDoneAdvanced = listOf(0L, 0L, 0L, 10L, 20L, 30L)

    private val eventMonsterHandel = "randomEffectSkeletonMob"
    private val eventMonsterHandelSuper = "randomEffectZombieMob"

    private val activeBossEntity = mutableListOf<Entity>()

    init {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val now = LocalDateTime.now()
            if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                nextEvent = -1L
                return@Runnable
            }

            nextEvent = (System.currentTimeMillis() / 1000) + 1800L

            for (player in Bukkit.getOnlinePlayers()) {
                val uuid = player.uniqueId
                val getDifficulty = difficulty[uuid]?: continue
                difficulty[uuid] = 0

                if (max[uuid]!! <= 2) SelectGui().openGui(player)

                if (getDifficulty != 0) {
                    val givePoint = giveDonePoint[getDifficulty]
                    val giveToken = giveDoneToken[getDifficulty]
                    val giveAdvancedToken = giveDoneAdvanced[getDifficulty]

                    point[uuid] = point[uuid]!! + givePoint
                    token[uuid] = token[uuid]!! + giveToken
                    advancedToken[uuid] = advancedToken[uuid]!! + giveAdvancedToken

                    player.clearActivePotionEffects()

                    player.sendMessage(gc("${difficultyMessage[getDifficulty]} &f&l난이도에서 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                    player.sendMessage(gc("${difficultyMessage[getDifficulty]} &f&l난이도에서 생존을 하여 &2&l${giveToken.toFormat()} 토큰&f&l을 획득하였습니다."))
                    if (giveAdvancedToken != 0L) player.sendMessage(gc("${difficultyMessage[getDifficulty]} &f&l난이도에서 생존을 하여 &a&l${giveAdvancedToken.toFormat()} 고급 토큰&f&l을 획득하였습니다."))
                }
            }
        }, 0L, 36000L)

        registerPacketListener()

        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val iterator = activeBossEntity.iterator()
            while (iterator.hasNext()) {
                val boss = iterator.next()
                boss.remove()
                iterator.remove()
            }

            val now = LocalDateTime.now()
            if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                nextEvent = -1L
                return@Runnable
            }

            min5times++
            if (min5times % 6 == 0 || min5times == 1) return@Runnable

            for (player in Bukkit.getOnlinePlayers()) {
                val uuid = player.uniqueId
                val difficulty = difficulty[uuid]?: continue

                if (difficulty != 0) {
                    val givePoint = give5minute[difficulty]

                    if (difficulty in 1..2) {
                        player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분간 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                        point[uuid] = (point[uuid] ?: 0L) + givePoint
                    }

                    if (difficulty in 3..5) {
                        if (killEvent1[uuid] != false) {
                            point[uuid] = (point[uuid] ?: 0L) + givePoint
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분간 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                        } else {
                            point[uuid] = (point[uuid] ?: 0L) + (givePoint / 2)
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분 안에 이벤트 몬스터를 못 죽여 &d&l${(givePoint / 2).toFormat()} 점수&f&l를 획득하였습니다."))
                        }

                        val location = player.location
                        val monster = location.world.spawnEntity(location, org.bukkit.entity.EntityType.SKELETON) as org.bukkit.entity.Skeleton

                        monster.setGravity(true)
                        (monster as org.bukkit.entity.LivingEntity).maxHealth = 400.0
                        monster.health = 400.0
                        monster.customName = gc("&b&l${player.name}&f&l의 &c&l난이도 챌린지 &f&l이벤트 몬스터")
                        monster.isCustomNameVisible = true

                        val equipment = monster.equipment

                        equipment.setItemInMainHand(getItem("bow"))
                        equipment.setItemInOffHand(null)
                        equipment.itemInMainHandDropChance = 0.0f
                        equipment.itemInOffHandDropChance = 0.0f

                        equipment.helmet = null
                        equipment.chestplate = null
                        equipment.leggings = null
                        equipment.boots = null
                        equipment.helmetDropChance = 0.0f
                        equipment.chestplateDropChance = 0.0f
                        equipment.leggingsDropChance = 0.0f
                        equipment.bootsDropChance = 0.0f

                        monster.canPickupItems = false

                        monster.addPotionEffect(PotionEffect(
                            PotionEffectType.FIRE_RESISTANCE,
                            Int.MAX_VALUE,
                            0,
                            true, false
                        ))
                        monster.addPotionEffect(PotionEffect(
                            PotionEffectType.SPEED,
                            Int.MAX_VALUE,
                            0,
                            true, false
                        ))
                        (monster as org.bukkit.entity.Creature).target = player

                        monster.setMetadata(
                            eventMonsterHandel,
                            FixedMetadataValue(plugin, uuid.toString())
                        )

                        killEvent1[uuid] = false
                        activeBossEntity.add(monster)
                    }

                    if (difficulty == 5) {
                        if (killEvent2[uuid] == false) {
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분 안에 이벤트 몬스터를 못 죽여 &c&l채력 9칸&f&l이 깎였습니다."))
                            val newHealth = player.health - 18.0
                            if (newHealth < 0) {
                                player.health = 0.0
                                continue
                            }
                            else player.health -= 18.0
                        }

                        val location = player.location
                        val monster = location.world.spawnEntity(location, org.bukkit.entity.EntityType.ZOMBIE) as org.bukkit.entity.Zombie

                        monster.setGravity(true)
                        monster.isBaby = false
                        (monster as org.bukkit.entity.LivingEntity).maxHealth = 500.0
                        monster.health = 500.0
                        monster.customName = gc("&b&l${player.name}&f&l의 &c&l난이도 챌린지 &f&l이벤트 몬스터")
                        monster.isCustomNameVisible = true
                        val equipment = monster.equipment

                        equipment.setItemInMainHand(null)
                        equipment.setItemInOffHand(null)
                        equipment.itemInMainHandDropChance = 0.0f
                        equipment.itemInOffHandDropChance = 0.0f

                        equipment.helmet = null
                        equipment.chestplate = null
                        equipment.leggings = null
                        equipment.boots = null
                        equipment.helmetDropChance = 0.0f
                        equipment.chestplateDropChance = 0.0f
                        equipment.leggingsDropChance = 0.0f
                        equipment.bootsDropChance = 0.0f

                        monster.canPickupItems = false

                        monster.addPotionEffect(PotionEffect(
                            PotionEffectType.FIRE_RESISTANCE,
                            Int.MAX_VALUE,
                            0,
                            true, false
                        ))
                        monster.addPotionEffect(PotionEffect(
                            PotionEffectType.SPEED,
                            Int.MAX_VALUE,
                            0,
                            true, false
                        ))
                        monster.addPotionEffect(PotionEffect(
                            PotionEffectType.STRENGTH,
                            Int.MAX_VALUE,
                            1,
                            true, false
                        ))
                        (monster as org.bukkit.entity.Creature).target = player

                        monster.setMetadata(
                            eventMonsterHandelSuper,
                            FixedMetadataValue(plugin, uuid.toString())
                        )

                        killEvent2[uuid] = false
                        activeBossEntity.add(monster)
                    }
                }
            }
        }, 0L, 6000L)

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            point.forEach {(uuid, value) -> saveDataUuid(plugin, uuid, "randomEffect/point", value)}
            max.forEach {(uuid, value) -> saveDataUuid(plugin, uuid, "randomEffect/max", value)}
        },0L, 7000L)
    }

    private fun registerPacketListener() {
        val protocolManager = ProtocolLibrary.getProtocolManager()

        protocolManager.addPacketListener(object : PacketAdapter(
            plugin,
            PacketType.Play.Server.SPAWN_ENTITY,
            PacketType.Play.Server.ENTITY_METADATA
        ) {
            override fun onPacketSending(event: PacketEvent) {
                val player = event.player
                val packet = event.packet

                val entity = protocolManager.getEntityFromID(player.world, packet.integers.read(0))?: return
                val isEventMonster = entity.hasMetadata(eventMonsterHandel) || entity.hasMetadata(eventMonsterHandelSuper)

                if (isEventMonster) {
                    val monsterHandler = if (entity.hasMetadata(eventMonsterHandel)) eventMonsterHandel else eventMonsterHandelSuper
                    val ownerUUIDString = entity.getMetadata(monsterHandler).first().asString()
                    val ownerUUID = UUID.fromString(ownerUUIDString)

                    if (player.uniqueId != ownerUUID) event.isCancelled = true
                }
            }
        })
    }

    @EventHandler
    fun onEntityTarget(event: EntityTargetLivingEntityEvent) {
        val monster = event.entity
        if (monster.hasMetadata(eventMonsterHandel) || monster.hasMetadata(eventMonsterHandelSuper)) {
            val target = event.target
            if (target is Player) {
                val monsterHandler = if (monster.hasMetadata(eventMonsterHandel)) eventMonsterHandel else eventMonsterHandelSuper
                val ownerUUIDString = monster.getMetadata(monsterHandler).first().asString()
                val ownerUUID = UUID.fromString(ownerUUIDString)

                if (target.uniqueId != ownerUUID) {
                    event.isCancelled = true
                    return
                }
            } else {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity

        if (entity.hasMetadata(eventMonsterHandel)) {
            val ownerUUID = UUID.fromString(entity.getMetadata(eventMonsterHandel).first().asString())
            val ownerPlayer = Bukkit.getPlayer(ownerUUID)?: return

            ownerPlayer.sendMessage(gc("&a&l이벤트 몹(스캘레톤) 처치에 성공하였습니다!"))
            ownerPlayer.playSound(ownerPlayer.location, Sound.ENTITY_WITHER_DEATH, 1f, 1f)
            killEvent1[ownerUUID] = true

            event.drops.clear()
            event.droppedExp = 200
            activeBossEntity.remove(entity)
            return
        }

        if (entity.hasMetadata(eventMonsterHandelSuper)) {
            val ownerUUID = UUID.fromString(entity.getMetadata(eventMonsterHandelSuper).first().asString())
            val ownerPlayer = Bukkit.getPlayer(ownerUUID)?: return

            ownerPlayer.sendMessage(gc("&a&l이벤트 몹(좀비) 처치에 성공하였습니다!"))
            ownerPlayer.playSound(ownerPlayer.location, Sound.ENTITY_WITHER_DEATH, 1f, 1f)
            killEvent2[ownerUUID] = true

            event.drops.clear()
            event.droppedExp = 300
            activeBossEntity.remove(entity)
            return
        }
    }

    @EventHandler
    fun onPotionEffectRemove(event: EntityPotionEffectEvent) {
        val player = event.entity
        if (player !is Player) return
        val uuid = player.uniqueId
        val getDifficulty = difficulty[uuid]?: 0

        if (getDifficulty == 0) return
        if (min5times % 6 == 0) return

        val effectType = event.action
        if (effectType == EntityPotionEffectEvent.Action.REMOVED || effectType == EntityPotionEffectEvent.Action.CLEARED) {
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                for (i in 0..<getDifficulty) player.addPotionEffect(difficultyEffect[i])
            }, 1L)
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val uuid = event.player.uniqueId
        if (difficulty[uuid] != null) {
            difficulty[uuid] = 0

            for (entity in activeBossEntity) {
                if (entity.hasMetadata(eventMonsterHandel) || entity.hasMetadata(eventMonsterHandelSuper)) {
                    val monsterHandler = if (entity.hasMetadata(eventMonsterHandel)) eventMonsterHandel else eventMonsterHandelSuper
                    if (UUID.fromString(entity.getMetadata(monsterHandler).first().asString()) == uuid) {
                        activeBossEntity.remove(entity)
                        entity.remove()
                    }
                }
            }
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId
        if (hasDataUuid(plugin, uuid, "randomEffect/join")) {
            saveData(plugin, player, "randomEffect/point", point[uuid]!!)
            saveData(plugin, player, "randomEffect/max", max[uuid]!!)
            saveData(plugin, player, "token/normal", token[uuid]!!)
            saveData(plugin, player, "token/advanced", advancedToken[uuid]!!)
            point.remove(uuid)
            max.remove(uuid)
            difficulty.remove(uuid)
            token.remove(uuid)
            advancedToken.remove(uuid)

            for (effect in player.activePotionEffects) player.removePotionEffect(effect.type)
        }
    }

    @EventHandler
    fun onPreJoin(event: AsyncPlayerPreLoginEvent) {
        val uuid = event.uniqueId
        if (hasDataUuid(plugin, uuid, "randomEffect/join")) {
            point[uuid] = getDataUuid(plugin, uuid, "randomEffect/point").toLong()
            max[uuid] = getDataUuid(plugin, uuid, "randomEffect/max").toInt()
            token[uuid] = getDataUuid(plugin, uuid, "token/normal").toLong()
            advancedToken[uuid] = getDataUuid(plugin, uuid, "token/advanced").toLong()
            difficulty[uuid] = 0
        }
        for (i in 0..38) defDataUuid(plugin, uuid, "randomEffect/get/$i", 0)
    }
}