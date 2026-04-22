package _RedGold__.main.managers

import org.jetbrains.exposed.sql.Database
import java.security.SecureRandom

val secureRandom = SecureRandom()

lateinit var banDB: Database
lateinit var chestDB: Database
lateinit var mailboxDB: Database
lateinit var playersDB: Database
lateinit var userShopDB: Database
