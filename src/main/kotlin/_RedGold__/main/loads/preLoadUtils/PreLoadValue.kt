package _RedGold__.main.loads.preLoadUtils

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

object PreLoadValue {
    val instanceMap = mutableMapOf<KClass<*>, Any>()
    val creating = mutableSetOf<KClass<*>>()
    val constructorCache = mutableMapOf<KClass<*>, KFunction<*>>()

    lateinit var scannedClasses: List<KClass<*>>
}