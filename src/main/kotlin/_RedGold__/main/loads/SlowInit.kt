package _RedGold__.main.loads

import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.catch
import io.github.classgraph.ClassGraph
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
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
            .enableMethodInfo() // 메서드 어노테이션 스캔을 위해 필수 활성화
            .enableFieldInfo()  // 필드 어노테이션 스캔을 위해 필수 활성화
            .acceptPackages("_RedGold__.main")
            .scan().use { scan ->
                val classes = mutableSetOf<Class<*>>()
                classes += scan.getClassesWithAnnotation(SetSlowInit::class.java.name).loadClasses()
                classes += scan.getClassesWithMethodAnnotation(SetSlowInit::class.java.name).loadClasses()
                classes += scan.getClassesWithFieldAnnotation(SetSlowInit::class.java.name).loadClasses()
                classes.map { it.kotlin }.toSet()
            }

        scannedClasses.forEach { clazz ->
            val hasClassAnnotation = clazz.annotations.any { it is SetSlowInit }

            if (hasClassAnnotation) {
                loadAllLazy(clazz)
            } else {
                loadSpecificLazy(clazz)
            }

            loadAllFunctions(clazz, hasClassAnnotation)
        }
    }

    private fun loadAllLazy(clazz: KClass<*>) {
        val instance = getInstance(clazz) ?: return
        clazz.declaredMemberProperties.forEach { prop ->
            triggerIfLazy(prop, instance)
        }
    }

    private fun loadSpecificLazy(clazz: KClass<*>) {
        val instance = getInstance(clazz) ?: return
        clazz.declaredMemberProperties
            .filter { it.annotations.any { ann -> ann is SetSlowInit } }
            .forEach { prop ->
                triggerIfLazy(prop, instance)
            }
    }

    private fun triggerIfLazy(prop: KProperty1<*, *>, instance: Any) = catch("Lazy init fail", ExceptionSeverity.SHUTDOWN) {
        prop.isAccessible = true
        // Getter를 안전하게 호출하여 lazy 트리거 보장
        val getter = prop.getter
        getter.isAccessible = true

        @Suppress("UNCHECKED_CAST")
        val readableProp = prop as KProperty1<Any, *>
        getter.call(instance)

        println("[SlowInit] Initialized: ${instance::class.simpleName}.${prop.name}")
    }

    private fun loadAllFunctions(clazz: KClass<*>, hasClassAnnotation: Boolean) {
        val instance = getInstance(clazz) ?: return
        clazz.declaredFunctions
            .filter { func ->
                // 클래스 자체에 붙어있거나, 해당 함수에 직접 붙어있는 경우만 필터링
                hasClassAnnotation || func.annotations.any { ann -> ann is SetSlowInit }
            }
            .forEach { func ->
                catch("Function init fail", ExceptionSeverity.SHUTDOWN) {
                    func.isAccessible = true

                    // object 함수 및 일반 멤버 함수의 파라미터 개수 예외 안전하게 처리
                    val instanceParams = func.parameters.filter { it.kind == KParameter.Kind.INSTANCE }
                    val regularParams = func.parameters.filter { it.kind == KParameter.Kind.VALUE }

                    if (regularParams.isEmpty()) {
                        if (instanceParams.isNotEmpty()) {
                            func.call(instance) // 일반 멤버 함수 (this 필요)
                        } else {
                            func.call() // static 성격을 띠는 object 함수 (this 불필요)
                        }
                        println("[SlowInit] Function Executed: ${clazz.simpleName}.${func.name}")
                    }
                }
            }
    }

    private fun getInstance(clazz: KClass<*>): Any? {
        return clazz.objectInstance ?: try {
            clazz.java.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            null
        }
    }
}