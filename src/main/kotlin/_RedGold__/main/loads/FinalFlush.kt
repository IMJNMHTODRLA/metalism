package _RedGold__.main.loads

import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.catch
import io.github.classgraph.ClassGraph
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SetFinalFlush

class FinalFlush {
    fun init() {
        val scannedClasses: Set<KClass<*>> = ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .acceptPackages("_RedGold__.main")
            .scan().use { scan ->
                val classes = mutableSetOf<Class<*>>()

                classes += scan.getClassesWithAnnotation(SetFinalFlush::class.java.name).loadClasses()
                classes += scan.getClassesWithMethodAnnotation(SetFinalFlush::class.java.name).loadClasses()
                classes += scan.getClassesWithFieldAnnotation(SetFinalFlush::class.java.name).loadClasses()

                classes.map { it.kotlin }.toSet()
            }

        scannedClasses.forEach { clazz ->
            val instance = getInstance(clazz)?: return@forEach

            clazz.declaredFunctions
                .filter { it.annotations.any { ann -> ann is SetFinalFlush } }
                .forEach { func ->
                    catch("FinalFlush Function Error: ${clazz.simpleName}.${func.name}", ExceptionSeverity.NONE) {
                        func.isAccessible = true
                        func.call(instance)
                        println("[FinalFlush] Function Executed: ${clazz.simpleName}.${func.name}")
                    }
                }

            clazz.declaredMemberProperties
                .filter { it.annotations.any { ann -> ann is SetFinalFlush } }
                .forEach { prop ->
                    triggerProperty(prop, instance)
                }
        }
    }

    private fun triggerProperty(prop: KProperty1<*, *>, instance: Any) =
        catch("FinalFlush Property Error: ${instance::class.simpleName}.${prop.name}", ExceptionSeverity.NONE) {
            prop.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val readableProp = prop as KProperty1<Any, *>
            readableProp.get(instance)
            println("[FinalFlush] Property Flushed: ${instance::class.simpleName}.${prop.name}")
        }

    private fun getInstance(clazz: KClass<*>): Any? {
        return clazz.objectInstance?: try {
            clazz.java.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            null
        }
    }
}