package _RedGold__.main.managers.mailBoxManager

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.NumberFormat.toUuid
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.SetFinalFlush
import _RedGold__.main.managers.database.tableManager.mailboxDB.MailBox
import _RedGold__.main.managers.mailboxDB
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun sendMail(data: MailBoxData) {
    transaction(mailboxDB) {
        val maxId = MailBox
            .select(MailBox.id)
            .orderBy(MailBox.id to SortOrder.DESC)
            .limit(1)
            .map { it[MailBox.id] }
            .singleOrNull() ?: 0

        val nextId = maxId + 1

        MailBox.insert {
            it[id] = nextId

            it[uuid] = data.uuid.toString()

            it[sender] = data.sender
            it[title] = data.title
            it[content] = data.content

            it[item] = data.item?.serializeAsBytes()?.let { bytes ->
                ExposedBlob(bytes)
            }

            it[giveGold] = data.giveGold
            it[giveCrystal] = data.giveCrystal

            it[sendAt] = data.sendAt
            it[duration] = data.duration
        }
    }

    Bukkit.getPlayer(data.uuid)?.sendMsg("&a메일함에 새로운 메일이 도착했습니다!")
}

fun getMail(uuid: UUID, start: Long): List<MailBoxData> {
    return transaction(mailboxDB) {
        MailBox.selectAll()
            .orderBy(MailBox.id to SortOrder.ASC)
            .where {
                (MailBox.uuid eq uuid.toString()) and
                ((MailBox.sendAt plus MailBox.duration) greaterEq now) and
                (MailBox.id notInList readMailId)
            }
            .limit(GET_TIMES).offset(start)
            .map { row ->
                val rawItem = row[MailBox.item]?.bytes

                MailBoxData(
                    row[MailBox.id],
                    row[MailBox.uuid].toUuid(),

                    row[MailBox.sender],
                    row[MailBox.title],
                    row[MailBox.content],

                    rawItem?.let { ItemStack.deserializeBytes(it) },

                    row[MailBox.giveGold],
                    row[MailBox.giveCrystal],

                    row[MailBox.sendAt],
                    row[MailBox.duration],
                )
            }
    }
}

@SetFinalFlush
fun updateMail() {
    transaction(mailboxDB) {
        MailBox.deleteWhere {
            (id inList readMailId) or
            ((sendAt plus duration) less now)
        }
    }
}