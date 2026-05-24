package _RedGold__.main.managers.packetLogManager

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

val packetClientSendQueue = ConcurrentLinkedQueue<String>()
val isSaving = AtomicBoolean(false)
