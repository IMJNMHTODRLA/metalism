package _RedGold__.main.loads.preLoadUtils

import _RedGold__.main.loads.*
import io.github.classgraph.ClassGraph
import kotlin.reflect.KClass

object ScanClassUtil {
    fun scanClasses(): List<KClass<*>> {
        ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .acceptPackages("_RedGold__.main")
            .scan().use { scan ->
                val classes = mutableSetOf<Class<*>>()

                classes += scan.getClassesWithAnnotation(OnInstance::class.java.name).loadClasses()
                classes += scan.getClassesWithAnnotation(RequireListener::class.java.name).loadClasses()
                classes += scan.getClassesWithAnnotation(RequireCommandExecutor::class.java.name).loadClasses()
                classes += scan.getClassesWithAnnotation(RequirePacketListener::class.java.name).loadClasses()
                classes += scan.getClassesWithAnnotation(RequireLinkPacketListener::class.java.name).loadClasses()

                return classes.map { it.kotlin }
            }
    }
}