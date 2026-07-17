package _RedGold__.main.functions

import _RedGold__.main.Main
import com.github.shynixn.mccoroutine.bukkit.minecraftDispatcher
import com.github.shynixn.mccoroutine.bukkit.scope
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

object Scheduler {
    @Deprecated("plugin 이거 쓰지마 ㅠㅠㅠ", ReplaceWith("task(delay, loop, work)", "import _RedGold__.main.functions.task"), DeprecationLevel.WARNING)
    fun JavaPlugin.task(delay: Long? = null, loop: Long? = null, work: (BukkitTask) -> Unit): BukkitTask {
        var bukkitTask: BukkitTask? = null

        val runnable = Runnable { bukkitTask?.let { work(it) } }

        val task = if (delay == null) Bukkit.getScheduler().runTask(this, runnable)
            else if (loop == null) Bukkit.getScheduler().runTaskLater(this, runnable, delay)
            else Bukkit.getScheduler().runTaskTimer(this, runnable, delay, loop)

        bukkitTask = task
        return task
    }

    @Deprecated("plugin 이거 쓰지마 ㅠㅠㅠ", ReplaceWith("taskAsync(delay, loop, work)", "import _RedGold__.main.functions.taskAsync"), DeprecationLevel.WARNING)
    fun JavaPlugin.taskAsync(delay: Long? = null, loop: Long? = null, work: (BukkitTask) -> Unit): BukkitTask {
        var bukkitTask: BukkitTask? = null

        val runnable = Runnable { bukkitTask?.let { work(it) } }

        val task = if (delay == null) Bukkit.getScheduler().runTaskAsynchronously(this, runnable)
            else if (loop == null) Bukkit.getScheduler().runTaskLaterAsynchronously(this, runnable, delay)
            else Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, loop)

        bukkitTask = task
        return task
    }
}

fun launch(
    context: CoroutineContext = Main.instance.minecraftDispatcher,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    if (!Main.instance.scope.isActive) {
        return Job()
    }

    return Main.instance.scope.launch(context, start, block)
}

fun task(delay: Long? = null, loop: Long? = null, work: BukkitTask.() -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null

    val runnable = Runnable { bukkitTask?.work() }

    val task = if (delay == null) Bukkit.getScheduler().runTask(Main.instance, runnable)
        else if (loop == null) Bukkit.getScheduler().runTaskLater(Main.instance, runnable, delay)
        else Bukkit.getScheduler().runTaskTimer(Main.instance, runnable, delay, loop)

    bukkitTask = task
    return task
}

fun taskAsync(delay: Long? = null, loop: Long? = null, work: BukkitTask.() -> Unit): BukkitTask {
    var bukkitTask: BukkitTask? = null

    val runnable = Runnable { bukkitTask?.work() }

    val task = if (delay == null) Bukkit.getScheduler().runTaskAsynchronously(Main.instance, runnable)
        else if (loop == null) Bukkit.getScheduler().runTaskLaterAsynchronously(Main.instance, runnable, delay)
        else Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, runnable, delay, loop)

    bukkitTask = task
    return task
}