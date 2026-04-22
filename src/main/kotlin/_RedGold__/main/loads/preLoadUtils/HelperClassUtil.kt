package _RedGold__.main.loads.preLoadUtils

import _RedGold__.main.loads.*
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

object HelperClassUtil {
    fun isManagedClass(clazz: KClass<*>) = clazz in PreLoadValue.scannedClasses
    fun isLoadTarget(clazz: KClass<*>): Boolean {
        return clazz.hasAnnotation<OnInstance>() ||
                clazz.hasAnnotation<RequireListener>() ||
                clazz.hasAnnotation<RequireCommandExecutor>() ||
                clazz.hasAnnotation<RequirePacketListener>() ||
                clazz.hasAnnotation<RequireLinkPacketListener>()
    }
}