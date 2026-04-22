package _RedGold__.main.listeners.playerKill

import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal object PlayerKillValue {
    val killedPlayerMap = ConcurrentHashMap<UUID, MutableSet<UUID>>()
}