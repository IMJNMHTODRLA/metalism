package _RedGold__.main.managers.database.tableManager.playersDB

import org.jetbrains.exposed.sql.Table

object CombatStats : Table("combat_stats") {
    val uuid = varchar("uuid", 36)
    val kill = integer("kill").index()
    val killStreak = integer("kill_streak").index()
    val death = integer("death").index()
    val deathStreak = integer("death_streak").index()

    override val primaryKey = PrimaryKey(uuid)
}