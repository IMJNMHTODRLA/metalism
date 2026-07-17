package _RedGold__.main.managers.database

import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.catch
import _RedGold__.main.managers.*
import _RedGold__.main.managers.database.tableManager.banDB.BanStats
import _RedGold__.main.managers.database.tableManager.chestDB.ChestStats
import _RedGold__.main.managers.database.tableManager.guildDB.GuildShareChests
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.database.tableManager.guildDB.GuildWhitelists
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBans
import _RedGold__.main.managers.database.tableManager.mailboxDB.MailBox
import _RedGold__.main.managers.database.tableManager.playersDB.*
import _RedGold__.main.managers.database.tableManager.playersDB.boost.BoostSettingStats
import _RedGold__.main.managers.database.tableManager.playersDB.boost.BoostStats
import _RedGold__.main.managers.database.tableManager.playersDB.cosmetic.CosmeticStats
import _RedGold__.main.managers.database.tableManager.playersDB.cosmetic.EquipCosmeticStats
import _RedGold__.main.managers.database.tableManager.userShopDB.UserShopStats
import _RedGold__.main.managers.playerData.PlayerData
import _RedGold__.main.managers.playerData.dataManager.*
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.collections.forEach
import kotlin.collections.set

fun initDatabase(plugin: JavaPlugin) = catch("DB 초기화 실패", ExceptionSeverity.SHUTDOWN) {
    if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()

    val path = plugin.dataFolder.absolutePath

    banDB = Database.connect(databaseDefaultConfig(path, "ban.db"))
    chestDB = Database.connect(databaseDefaultConfig(path, "chest.db"))
    mailboxDB = Database.connect(databaseDefaultConfig(path, "mailbox.db"))
    playersDB = Database.connect(databaseDefaultConfig(path, "players.db"))
    userShopDB = Database.connect(databaseDefaultConfig(path, "user_shop.db"))
    guildDB = Database.connect(databaseDefaultConfig(path, "guild.db"))

    TransactionManager.defaultDatabase = playersDB

    transaction(banDB) { @Suppress("DEPRECATION") createMissingTablesAndColumns(BanStats) }
    transaction(chestDB) { @Suppress("DEPRECATION") createMissingTablesAndColumns(ChestStats) }
    transaction(mailboxDB) { @Suppress("DEPRECATION") createMissingTablesAndColumns(MailBox) }
    transaction(userShopDB) { @Suppress("DEPRECATION") createMissingTablesAndColumns(UserShopStats) }
    transaction(guildDB) {
        @Suppress("DEPRECATION") createMissingTablesAndColumns(GuildStats, GuildMembers, GuildWhitelists, GuildBans, GuildShareChests)
    }

    transaction {
        @Suppress("DEPRECATION") createMissingTablesAndColumns(
            DefaultStats,
            CombatStats,

            HomeStats,
            MissionStats, ShopStats,

            BoostStats, BoostSettingStats,
            CosmeticStats, EquipCosmeticStats
        )
    }
}

//TODO: 테이블 그거 수정시 무조건 default나 nullable 추가

fun loadPlayerData(playerUuid: UUID): PlayerData = catch("플레이어 데이터 로드 실패", ExceptionSeverity.CRITICAL) {
    val strUUID = playerUuid.toString()

    return transaction {
        val isNotExist = DefaultStats.select(DefaultStats.uuid)
            .where { DefaultStats.uuid eq strUUID }
            .limit(1)
            .empty()

        val playerData = PlayerData(playerUuid)
        if (isNotExist) return@transaction playerData

        DefaultStats.selectAll().where { DefaultStats.uuid eq strUUID }.forEach { defaultRow ->
            playerData.gold = defaultRow[DefaultStats.gold]
            playerData.crystal = defaultRow[DefaultStats.crystal]
            playerData.ruby = defaultRow[DefaultStats.ruby]
            playerData.boost = defaultRow[DefaultStats.boost]
        }

        CombatStats.selectAll().where { CombatStats.uuid eq strUUID }.forEach { combatRow ->
            val combatData = playerData.combatData

            combatData.kill = combatRow[CombatStats.kill]
            combatData.killStreak = combatRow[CombatStats.killStreak]
            combatData.death = combatRow[CombatStats.death]
            combatData.deathStreak = combatRow[CombatStats.deathStreak]
        }

        ShopStats.selectAll().where { ShopStats.uuid eq strUUID }.forEach { shopRow ->
            playerData.shopData = ShopData(
                isPurchaseMonthly = shopRow[ShopStats.isPurchaseMonthly]
            )
        }

        HomeStats.selectAll().where { HomeStats.uuid eq strUUID }.forEach { homeRow ->
            val index = homeRow[HomeStats.index]

            val x = homeRow[HomeStats.x]
            val y = homeRow[HomeStats.y]
            val z = homeRow[HomeStats.z]
            val yaw = homeRow[HomeStats.yaw]
            val pitch = homeRow[HomeStats.pitch]

            val locationData =
                if (
                    x != null && y != null && z != null &&
                    yaw != null && pitch != null
                ) {
                    LocationData(x, y, z, yaw, pitch)
                } else {
                    null
                }

            playerData.homeMap[index] = HomeData(
                homeRow[HomeStats.isUnlocked],
                locationData
            )
        }

        MissionStats.selectAll().where { MissionStats.uuid eq strUUID }.forEach { missionRow ->
            val type = missionRow[MissionStats.type]
            val index = missionRow[MissionStats.index]
            val progress = missionRow[MissionStats.progress]
            val isClaim = missionRow[MissionStats.isClaim]

            playerData.missionMap[type]?.put(
                index, MissionData(progress, isClaim)
            )
        }

        CosmeticStats.selectAll().where { CosmeticStats.uuid eq strUUID }.forEach { cosmeticRow ->
            val type = cosmeticRow[CosmeticStats.type]
            val item = cosmeticRow[CosmeticStats.item]
            playerData.cosmeticMap[type]?.put(item, CosmeticData(false))
        }

        EquipCosmeticStats.selectAll().where { EquipCosmeticStats.uuid eq strUUID }.forEach { equipRow ->
            val equipType = equipRow[EquipCosmeticStats.equipType]
            val equipItem = equipRow[EquipCosmeticStats.equipItem]

            playerData.cosmeticMap[equipType]?.get(equipItem)?.isEquip = true
        }

        BoostStats.selectAll()
            .where { BoostStats.uuid eq strUUID }
            .forEach { boostRow ->
                val type = boostRow[BoostStats.type]
                val amount = boostRow[BoostStats.amount]
                val expirationAt = boostRow[BoostStats.expirationAt]

                playerData.boostMap[type] = BoostData(amount, expirationAt)
            }

        BoostSettingStats.selectAll()
            .where { BoostSettingStats.uuid eq strUUID }
            .forEach { boostSettingRow ->
                val type = boostSettingRow[BoostSettingStats.type]
                val value = boostSettingRow[BoostSettingStats.value]

                playerData.boostSettingMap[type] = value
            }

        playerData
    }
}