package _RedGold__.main.loads

import io.github.classgraph.ClassGraph
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SetFinalFlush

class FinalFlush {
    private val instanceCache = mutableMapOf<KClass<*>, Any?>()

    fun init() {
        val allClasses = mutableListOf<String>()

        // 1. 프로젝트 전체에서 SetFinalFlush가 붙은 모든 클래스 스캔
        ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .enableMethodInfo()
            .enableFieldInfo()
            .acceptPackages("_RedGold__.main") // 실제 패키지 경로로 수정하세요
            .scan().use { scan ->
                // 클래스, 메서드, 필드 중 하나라도 어노테이션이 붙어있으면 리스트에 추가
                allClasses += scan.getClassesWithAnnotation(SetFinalFlush::class.java.name).names
                allClasses += scan.getClassesWithMethodAnnotation(SetFinalFlush::class.java.name).names
                allClasses += scan.getClassesWithFieldAnnotation(SetFinalFlush::class.java.name).names
            }

        allClasses.distinct().forEach { name ->
            val clazz = Class.forName(name)
            val isClassAnnotated = clazz.isAnnotationPresent(SetFinalFlush::class.java)
            val instance = getOrResolveInstance(clazz.kotlin)

            // 2. 함수 실행
            clazz.declaredMethods.filter { method ->
                isClassAnnotated || method.isAnnotationPresent(SetFinalFlush::class.java)
            }.forEach { method ->
                if (method.parameterCount == 0 && !method.isSynthetic) {
                    runCatching {
                        method.isAccessible = true

                        if (Modifier.isStatic(method.modifiers)) method.invoke(null)
                        else if (instance != null) method.invoke(instance)
                        else throw IllegalStateException("인스턴스 초기화 실패로 메서드 호출 불가")
                    }.onFailure { e ->
                        println("🚨 [FinalFlush] 실행 실패: ${method.name} - ${e.cause?.message?: e.message}")
                    }
                }
            }

            // 3. 필드 실행 (get 호출하여 초기화 유도)
            clazz.declaredFields.filter { field ->
                isClassAnnotated || field.isAnnotationPresent(SetFinalFlush::class.java)
            }.forEach { field ->
                if (!field.isSynthetic) {
                    runCatching {
                        field.isAccessible = true

                        if (Modifier.isStatic(field.modifiers)) field.get(null)
                        else if (instance != null) field.get(instance)
                        else throw IllegalStateException("Instance is null")
                    }.onFailure { e ->
                        println("🚨 [FinalFlush] 필드 초기화 실패: ${clazz.simpleName}.${field.name} - ${e.message}")
                    }
                }
            }
        }
    }

    private fun getOrResolveInstance(clazz: KClass<*>): Any? {
        if (instanceCache.containsKey(clazz)) return instanceCache[clazz]

        val inst = clazz.objectInstance ?: try {
            // Kotlin Object 또는 일반 클래스의 인스턴스 탐색
            val field = clazz.java.getDeclaredField("INSTANCE")
            field.isAccessible = true
            field.get(null)
        } catch (e: Exception) {
            try {
                val constructor = clazz.java.getDeclaredConstructor()
                constructor.isAccessible = true
                constructor.newInstance()
            } catch (ex: Exception) { null }
        }

        instanceCache[clazz] = inst
        return inst
    }
}
