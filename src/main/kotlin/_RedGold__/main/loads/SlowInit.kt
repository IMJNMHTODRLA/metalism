package _RedGold__.main.loads

import io.github.classgraph.ClassGraph
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class SetSlowInit

class SlowInit {
    fun init() {
        // 💡 ClassGraph가 긁어온 정보를 스트림을 완전히 닫은 '이후' 안전하게 처리하도록 리스트화
        val classNames = mutableListOf<String>()

        ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .enableMethodInfo()
            .enableFieldInfo()
            .acceptPackages("_RedGold__.main")
            .scan().use { scan ->
                // 메모리 누수 및 zip file closed 예방을 위해 이름만 먼저 추출
                classNames += scan.getClassesWithAnnotation(SetSlowInit::class.java.name).names
                classNames += scan.getClassesWithMethodAnnotation(SetSlowInit::class.java.name).names
                classNames += scan.getClassesWithFieldAnnotation(SetSlowInit::class.java.name).names
            }

        // 중복 이름 제거 후 안전하게 로드
        val scannedJavaClasses = classNames.distinct().mapNotNull { name ->
            try {
                Class.forName(name)
            } catch (e: Exception) {
                null
            }
        }

        scannedJavaClasses.forEach { javaClazz ->
            val hasClassAnnotation = javaClazz.isAnnotationPresent(SetSlowInit::class.java)
            val isKtFile = javaClazz.name.endsWith("Kt")

            var cachedInstance: Any? = null
            var isInstanceResolved = false

            fun getOrCreateInfo(): Any? {
                if (isInstanceResolved) return cachedInstance
                isInstanceResolved = true
                if (isKtFile) return null

                try {
                    cachedInstance = try {
                        val field = javaClazz.getDeclaredField("INSTANCE")
                        field.isAccessible = true
                        field.get(null)
                    } catch (e: Exception) {
                        try {
                            val constructor = javaClazz.getDeclaredConstructor()
                            constructor.isAccessible = true
                            constructor.newInstance()
                        } catch (ex: Exception) {
                            null
                        }
                    }
                } catch (e: Throwable) {
                    println("[SlowInit] ❌ 인스턴스 생성 중 크래시 방어: ${javaClazz.simpleName}")
                }
                return cachedInstance
            }

            // 1. 프로퍼티 지연 초기화 트리거
            if (!isKtFile) {
                val fields = if (hasClassAnnotation) {
                    javaClazz.declaredFields.toList()
                } else {
                    javaClazz.declaredFields.filter { it.isAnnotationPresent(SetSlowInit::class.java) }
                }

                fields.forEach { field ->
                    try {
                        val inst = getOrCreateInfo()
                        if (inst != null || Modifier.isStatic(field.modifiers)) {
                            field.isAccessible = true
                            field.get(inst)
                            println("[SlowInit] Field Initialized: ${javaClazz.simpleName}.${field.name}")
                        }
                    } catch (e: Throwable) {
                        // 침묵
                    }
                }
            }

            // 2. 함수 초기화 및 실행
            javaClazz.declaredMethods
                .filter { method -> hasClassAnnotation || method.isAnnotationPresent(SetSlowInit::class.java) }
                .filter { method -> !method.isSynthetic && !Modifier.isVolatile(method.modifiers) }
                .forEach { method ->
                    try {
                        method.isAccessible = true

                        if (method.parameterCount == 0) {
                            if (Modifier.isStatic(method.modifiers) || isKtFile) {
                                try {
                                    method.invoke(null)
                                    println("[SlowInit] Static Method Executed: ${javaClazz.simpleName}.${method.name}")
                                } catch (e: InvocationTargetException) {
                                    println("[SlowInit] 🚨 내부 로직 실행 중 에러 발견 (${javaClazz.simpleName}.${method.name}):")
                                    e.targetException.printStackTrace()
                                }
                            } else {
                                val inst = getOrCreateInfo()
                                if (inst != null) {
                                    try {
                                        method.invoke(inst)
                                        println("[SlowInit] Instance Method Executed: ${javaClazz.simpleName}.${method.name}")
                                    } catch (e: InvocationTargetException) {
                                        println("[SlowInit] 🚨 내부 로직 실행 중 에러 발견 (${javaClazz.simpleName}.${method.name}):")
                                        e.targetException.printStackTrace()
                                    }
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        println(e)
                    }
                }
        }
    }
}