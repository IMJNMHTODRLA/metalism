package _RedGold__.main.event.randomEffect

import _RedGold__.main.Main.Event.END_TIME
import _RedGold__.main.Main.Event.START_TIME
import _RedGold__.main.command.shop.sys.cashShop.ticketGui.TicketListener.CosmeticType
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficulty
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficultyEffect
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent1
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent2
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.nextEvent
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.event.randomEffect.selectGui.SelectGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.defDataUuid
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.hasDataUuid
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
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

    private val giveDonePoint = listOf(0L, 100_000L, 150_000L, 300_000L, 400_000L, 550_000L)
    private val difficultyMessage = listOf("&7&l선택안함(Nothing)", "&e&l보통(Normal)", "&c&l어려움(Hard)", "&4&l하드코어(HardCore)", "&b&l익스트림(Extreme)", "&d&l얼티밋(Ultimate)")
    private val give5minute = listOf(0L, 10_000L, 15_000L, 30_000L, 50_000L, 60_000L)

    private val giveDoneCash = listOf(0L, 0L, 0L, 3L, 4L, 6L)

    private val eventMonsterHandel = "randomEffectSkeletonMob"
    private val eventMonsterHandelSuper = "randomEffectZombieMob"

    private val activeBossEntity = mutableListOf<Entity>()
    private val giveBonusPoint: MutableMap<UUID, Long> = ConcurrentHashMap()
    private val giveBonusPoint2nd: MutableMap<UUID, Long> = ConcurrentHashMap()

    init {
        val delayTick = run{
            val now = LocalDateTime.now()
            val minute = now.minute
            val second = now.second

            val targetMinute = if (minute < 30) 30 else 60
            val remainingMinutes = targetMinute - minute - 1
            val remainingSeconds = 60 - second

            (remainingMinutes * 60 + remainingSeconds).toLong() * 20L
        }
        nextEvent = (System.currentTimeMillis() / 1000) + (delayTick / 20)

        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val now = LocalDateTime.now()
            if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                nextEvent = -1L
                return@Runnable
            }

            nextEvent = (System.currentTimeMillis() / 1000) + 1800L

            val saveCash = mutableListOf<Pair<Player, Long>>()

            for (player in Bukkit.getOnlinePlayers()) {
                val uuid = player.uniqueId
                val getDifficulty = difficulty[uuid]?: continue
                difficulty[uuid] = 0

                if (max[uuid]!! <= 2) SelectGui().openGui(player)

                if (getDifficulty != 0) {
                    val givePoint = giveDonePoint[getDifficulty]
                    val giveCash = giveDoneCash[getDifficulty]

                    point[uuid] = point[uuid]!! + givePoint

                    player.clearActivePotionEffects()

                    player.sendMessage(gc("${difficultyMessage[getDifficulty]} &f&l난이도에서 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                    if (giveCash > 0) {
                        saveCash.add(Pair(player, giveCash))
                        player.sendMessage(gc("${difficultyMessage[getDifficulty]} &f&l난이도에서 생존을 하여 &b&l${giveCash.toFormat()} 캐시&f&l을 획득하였습니다."))
                    }
                }
            }

            if (saveCash.isNotEmpty()) {
                var index = 0

                object : BukkitRunnable() {
                    override fun run() {
                        val end = (index + 3).coerceAtMost(saveCash.size)
                        val batchList = saveCash.subList(index, end)

                        for ((player, cash) in batchList) saveData(plugin, player, "cash", getData(plugin, player, "cash") + cash)

                        index += 3
                        if (index >= saveCash.size) {
                            this.cancel()
                            plugin.logger.info("이벤트 캐시 저장 전체 완료 (${saveCash.size}명)")
                        }
                    }
                }.runTaskTimer(plugin, 3L, 3L)
            }
        }, delayTick, 36000L)

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
            if (min5times == 1) return@Runnable

            for (player in Bukkit.getOnlinePlayers()) {
                val uuid = player.uniqueId
                val difficulty = difficulty[uuid]?: continue
                giveBonusPoint.remove(uuid)
                giveBonusPoint2nd.remove(uuid)

                if (difficulty != 0) {
                    val givePoint = give5minute[difficulty]

                    if (difficulty in 1..2) {
                        player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분간 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                        point[uuid] = (point[uuid] ?: 0L) + givePoint
                    } else if (difficulty in 3..5) {
                        if (killEvent1[uuid] != false) { //!= false는 null과 true 둘다 통과임
                            point[uuid] = (point[uuid] ?: 0L) + givePoint
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분간 생존을 하여 &d&l${givePoint.toFormat()} 점수&f&l를 획득하였습니다."))
                        } else {
                            point[uuid] = (point[uuid] ?: 0L) + (givePoint / 2)
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분 안에 이벤트 몬스터를 못 죽여 &d&l${(givePoint / 2).toFormat()} 점수&f&l를 획득하였습니다."))
                        }

                        if (min5times % 7 != 0) continue

                        val location = player.location
                        val monster = location.world.spawnEntity(location, org.bukkit.entity.EntityType.SKELETON) as org.bukkit.entity.Skeleton

                        monster.setGravity(true)
                        monster.maxHealth = 400.0
                        monster.health = 400.0
                        monster.customName = gc("&b&l${player.name}&f&l의 &c&l난이도 챌린지 &f&l이벤트 몬스터")
                        monster.isCustomNameVisible = true

                        val equipment = monster.equipment

                        equipment.setItemInMainHand(getItem("bow").apply {
                            addEnchantment(Enchantment.POWER, 3)
                            addEnchantment(Enchantment.PUNCH, 2)
                        })
                        equipment.setItemInOffHand(null)
                        equipment.itemInMainHandDropChance = 0.0f
                        equipment.itemInOffHandDropChance = 0.0f

                        equipment.helmet = getItem("diamond_helmet").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.chestplate = getItem("diamond_chestplate").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.leggings = getItem("diamond_leggings").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.boots = getItem("diamond_boots").apply {
                            addUnsafeEnchantment(Enchantment.PROTECTION, 3)
                            addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 1)
                            addUnsafeEnchantment(Enchantment.FROST_WALKER, 1)
                        }
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
                        monster.setMetadata(
                            "spawnTime",
                            FixedMetadataValue(plugin, System.currentTimeMillis() / 1000)
                        )

                        killEvent1[uuid] = false
                        giveBonusPoint[uuid] = 120_000
                        activeBossEntity.add(monster)
                    } else {
                        if (killEvent2[uuid] == false) {
                            player.sendMessage(gc("${difficultyMessage[difficulty]} &f&l난이도에서 5분 안에 이벤트 몬스터를 못 죽여 &c&l채력 9칸&f&l이 깎였습니다."))
                            val newHealth = player.health - 18.0
                            if (newHealth < 0) {
                                player.health = 0.0
                                continue
                            }
                            else player.health -= 18.0
                        }

                        if (min5times % 7 != 0) continue

                        val location = player.location
                        val monster = player.world.spawnEntity(location, org.bukkit.entity.EntityType.ZOMBIE) as org.bukkit.entity.Zombie

                        monster.setGravity(true)
                        monster.isBaby = false
                        monster.maxHealth = 800.0
                        monster.health = 800.0
                        monster.customName = gc("&b&l${player.name}&f&l의 &c&l난이도 챌린지 &f&l이벤트 몬스터")
                        monster.isCustomNameVisible = true
                        val equipment = monster.equipment

                        equipment.setItemInMainHand(getItem("diamond_sword").apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 3)})
                        equipment.setItemInOffHand(null)
                        equipment.itemInMainHandDropChance = 0.0f
                        equipment.itemInOffHandDropChance = 0.0f

                        equipment.helmet = getItem("diamond_helmet").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.chestplate = getItem("diamond_chestplate").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.leggings = getItem("diamond_leggings").apply {addUnsafeEnchantment(Enchantment.PROTECTION, 3)}
                        equipment.boots = getItem("diamond_boots").apply {
                            addUnsafeEnchantment(Enchantment.PROTECTION, 3)
                            addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 1)
                            addUnsafeEnchantment(Enchantment.FROST_WALKER, 1)
                        }
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
                            0,
                            true, false
                        ))
                        monster.target = player

                        monster.setMetadata(
                            eventMonsterHandelSuper,
                            FixedMetadataValue(plugin, uuid.toString())
                        )
                        monster.setMetadata(
                            "spawnTime",
                            FixedMetadataValue(plugin, System.currentTimeMillis() / 1000)
                        )

                        killEvent2[uuid] = false
                        giveBonusPoint2nd[uuid] = 240_000
                        activeBossEntity.add(monster)
                    }
                }
            }
        }, delayTick, 6000L)

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            point.forEach {(uuid, value) -> saveDataUuid(plugin, uuid, "randomEffect/point", value)}
            max.forEach {(uuid, value) -> saveDataUuid(plugin, uuid, "randomEffect/max", value)}
        }, delayTick + 20L, 7000L)
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

            val spawnTime = entity.getMetadata("spawnTime").first().asLong()
            val nowTime = System.currentTimeMillis() / 1000
            val elapsedTime = nowTime - spawnTime
            if (nowTime - spawnTime < 300) {
                val giveBonusPoint = giveBonusPoint[ownerUUID]!! - (400L * elapsedTime)
                ownerPlayer.sendMessage(gc(
                    "&f&l이벤트 몹(스캘레톤)을 240초 안에 처치해 &d&l${giveBonusPoint.toFormat()} 점수를 추가로 획득하였습니다."
                ))
                point[ownerUUID] = (point[ownerUUID] ?: 0L) + giveBonusPoint
            }
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

            val spawnTime = entity.getMetadata("spawnTime").first().asLong()
            val nowTime = System.currentTimeMillis() / 1000
            val elapsedTime = nowTime - spawnTime
            if (nowTime - spawnTime < 300) {
                val giveBonusPoint = giveBonusPoint2nd[ownerUUID]!! - (800L * elapsedTime)
                ownerPlayer.sendMessage(gc(
                    "&f&l이벤트 몹(좀비)을 240초 안에 처치해 &d&l${giveBonusPoint.toFormat()} 점수를 추가로 획득하였습니다."
                ))
                point[ownerUUID] = (point[ownerUUID] ?: 0L) + giveBonusPoint
            }
            return
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageByEntityEvent) {
        val player = event.damager as? Player?: return
        val entity = event.entity as? LivingEntity?: return
        val uuid = player.uniqueId
        val item = player.inventory.itemInMainHand

        if (entity.hasMetadata(eventMonsterHandel) || entity.hasMetadata(eventMonsterHandelSuper)) return
        val monsterHandler = if (entity.hasMetadata(eventMonsterHandel)) eventMonsterHandel else eventMonsterHandelSuper

        if (entity.getMetadata(monsterHandler).first().asString().toUuid() != uuid) {
            event.damage = 0.0
            return
        }
        if (item.type.isAir) return
        val meta = item.itemMeta ?: return

        when {
            meta.hasEnchant(Enchantment.SHARPNESS) -> point[uuid] = (point[uuid] ?: 0L) + 500L
            meta.hasEnchant(Enchantment.SMITE) -> return
            meta.hasEnchant(Enchantment.BANE_OF_ARTHROPODS) -> return
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
            point.remove(uuid)
            max.remove(uuid)
            difficulty.remove(uuid)
            giveBonusPoint.remove(uuid)
            giveBonusPoint2nd.remove(uuid)

            for (effect in player.activePotionEffects) player.removePotionEffect(effect.type)
        }
    }

    @EventHandler
    fun onPreJoin(event: AsyncPlayerPreLoginEvent) {
        val uuid = event.uniqueId
        if (hasDataUuid(plugin, uuid, "randomEffect/join")) {
            point[uuid] = getDataUuid(plugin, uuid, "randomEffect/point").toLong()
            max[uuid] = getDataUuid(plugin, uuid, "randomEffect/max").toInt()
            difficulty[uuid] = 0
        }
        for (i in 0..38) defDataUuid(plugin, uuid, "randomEffect/get/$i", 0)
    }
}