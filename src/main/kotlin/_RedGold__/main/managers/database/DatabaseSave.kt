package _RedGold__.main.managers.database

import _RedGold__.main.managers.database.tableManager.playersDB.*
import _RedGold__.main.managers.database.tableManager.playersDB.boost.BoostSettingStats
import _RedGold__.main.managers.database.tableManager.playersDB.boost.BoostStats
import _RedGold__.main.managers.database.tableManager.playersDB.cosmetic.CosmeticStats
import _RedGold__.main.managers.database.tableManager.playersDB.cosmetic.EquipCosmeticStats
import _RedGold__.main.managers.playerData.PlayerData
import _RedGold__.main.managers.playerData.PlayerManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert

fun saveAllPlayerData() =
    transaction {
        PlayerManager.getAllData().forEach {
            savePlayerData(it)
            PlayerManager.unload(it.uuid)
        }
    }

fun savePlayerData(playerData: PlayerData) {
    DefaultStats.upsert {
        it[uuid] = playerData.uuid.toString()

        it[gold] = playerData.gold
        it[crystal] = playerData.crystal
        it[ruby] = playerData.ruby
        it[boost] = playerData.boost
    }

    CombatStats.upsert {
        it[uuid] = playerData.uuid.toString()

        it[kill] = playerData.combatData.kill
        it[killStreak] = playerData.combatData.killStreak
        it[death] = playerData.combatData.death
        it[deathStreak] = playerData.combatData.deathStreak
    }

    ShopStats.upsert {
        it[uuid] = playerData.uuid.toString()

        it[isPurchaseMonthly] = playerData.shopData.isPurchaseMonthly
    }

    playerData.homeMap.forEach { (key, value) ->
        HomeStats.upsert {
            it[uuid] = playerData.uuid.toString()

            it[index] = key

            it[isUnlocked] = value.isUnlocked

            it[x] = value.location?.x
            it[y] = value.location?.y
            it[z] = value.location?.z

            it[yaw] = value.location?.yaw
            it[pitch] = value.location?.pitch
        }
    }


    playerData.missionMap.forEach { (enum, dataMap) ->
        dataMap.forEach { (i, data) ->
            MissionStats.upsert {
                it[uuid] = playerData.uuid.toString()

                it[type] = enum
                it[index] = i

                it[progress] = data.progress
                it[isClaim] = data.isClaim
            }
        }
    }

    playerData.cosmeticMap.forEach { (enum, dataMap) ->
        dataMap.forEach { (i, _) ->
            CosmeticStats.upsert {
                it[uuid] = playerData.uuid.toString()

                it[type] = enum
                it[item] = i
            }
        }
    }

    playerData.cosmeticMap.forEach { (enum, dataMap) ->
        dataMap.forEach second@{ (i, data) ->
            if (!data.isEquip) return@second

            EquipCosmeticStats.upsert {
                it[uuid] = playerData.uuid.toString()

                it[equipType] = enum
                it[equipItem] = i
            }
        }
    }

    playerData.boostMap.forEach { (enum, data) ->
        BoostStats.upsert {
            it[uuid] = playerData.uuid.toString()

            it[type] = enum

            it[amount] = data.amount
            it[expirationAt] = data.expirationAt
        }
    }

    playerData.boostSettingMap.forEach { (enum, v) ->
        BoostSettingStats.upsert {
            it[uuid] = playerData.uuid.toString()

            it[type] = enum
            it[value] = v
        }
    }
}
