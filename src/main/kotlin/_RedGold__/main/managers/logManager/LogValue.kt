package _RedGold__.main.managers.logManager

import java.util.concurrent.ConcurrentLinkedQueue

val logData = ConcurrentLinkedQueue<String>()
var isInit = false
lateinit var data: String
lateinit var time: String
