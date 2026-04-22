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
annotation class SetSlowInit

class SlowInit {
    fun init() {
        val scannedClasses = ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .acceptPackages("_RedGold__.main")
            .scan().use { scan ->
                val classes = mutableSetOf<Class<*>>()

                classes += scan.getClassesWithAnnotation(SetSlowInit::class.java.name).loadClasses()

                classes += scan.getClassesWithMethodAnnotation(SetSlowInit::class.java.name).loadClasses()
                classes += scan.getClassesWithFieldAnnotation(SetSlowInit::class.java.name).loadClasses()

                classes.map { it.kotlin }.toSet()
            }

        scannedClasses.forEach { clazz ->
            if (clazz.annotations.any { it is SetSlowInit }) {
                loadAllLazy(clazz)
            } else {
                loadSpecificLazy(clazz)
            }

            loadAllFunctions(clazz)
        }
    }

    private fun loadAllLazy(clazz: KClass<*>) {
        val instance = getInstance(clazz)?: return
        clazz.declaredMemberProperties.forEach { prop ->
            triggerIfLazy(prop, instance)
        }
    }

    private fun loadAllFunctions(clazz: KClass<*>) {
        val instance = getInstance(clazz) ?: return
        clazz.declaredFunctions
            .filter { it.annotations.any { ann -> ann is SetSlowInit } || clazz.annotations.any { ann -> ann is SetSlowInit } }
            .forEach { func ->
                catch("Function init fail", ExceptionSeverity.SHUTDOWN) {
                    func.isAccessible = true
                    if (func.parameters.size == 1) {
                        func.call(instance)
                        println("[SlowInit] Function Executed: ${clazz.simpleName}.${func.name}")
                    }
                }
            }
    }

    private fun loadSpecificLazy(clazz: KClass<*>) {
        val instance = getInstance(clazz)?: return
        clazz.declaredMemberProperties
            .filter { it.annotations.any { ann -> ann is SetSlowInit } }
            .forEach { prop ->
                triggerIfLazy(prop, instance)
            }
    }

    private fun triggerIfLazy(prop: KProperty1<*, *>, instance: Any) = catch("Lazy init fall", ExceptionSeverity.SHUTDOWN) {
        prop.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        val readableProp = prop as KProperty1<Any, *>
        readableProp.get(instance)

        println("[SlowInit] Initialized: ${instance::class.simpleName}.${prop.name}")
    }

    private fun getInstance(clazz: KClass<*>): Any? {
        return clazz.objectInstance?: try {
            clazz.java.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            null
        }
    }
}