package _RedGold__.main.core.guild.expManager

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SqlExpressionBuilder.minus
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.longParam
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

private fun commonGuildExp(
    id: Int,
    amount: Long,
    action: UpdateStatement.(Column<Long>, Long) -> Expression<Long>
) = transaction(guildDB) {
    GuildStats.update({ GuildStats.id eq id }) {
        it[exp] = it.action(exp, amount)
    }
}

fun addGuildExp(id: Int, amount: Long) = commonGuildExp(id, amount) { old, new -> old + new }
fun subGuildExp(id: Int, amount: Long) = commonGuildExp(id, amount) { old, new -> old - new }
fun setGuildExp(id: Int, amount: Long) = commonGuildExp(id, amount) { _, new -> longParam(new) }
