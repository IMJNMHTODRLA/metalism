package _RedGold__.main.managers.database.tableManager.mailboxDB

import org.jetbrains.exposed.sql.Table

object MailBox : Table("mail_box") {
    val id = integer("id").autoIncrement()
    val uuid = varchar("uuid", 36)

    val sender = text("sender")
    val title = text("title")
    val content = text("content")

    val item = blob("item").nullable()

    val giveGold = long("giveGold").nullable()
    val giveCrystal = integer("giveCrystal").nullable()

    val sendAt = long("send_at")
    val duration = long("duration")
}