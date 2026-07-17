package _RedGold__.main.listeners.playerKill

import java.util.*
import java.util.concurrent.ConcurrentHashMap

object PlayerKillValue {
    val killedPlayerMap = ConcurrentHashMap<UUID, MutableSet<UUID>>()
}