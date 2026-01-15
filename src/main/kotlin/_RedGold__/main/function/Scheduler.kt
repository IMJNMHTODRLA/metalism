package _RedGold__.main.function

import _RedGold__.main.function.Scheduler.task
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

object Scheduler {
    fun JavaPlugin.task(delay: Long? = null, loop: Long? = null, work: (BukkitTask) -> Unit): BukkitTask {
        var bukkitTask: BukkitTask? = null

        val runnable = Runnable { bukkitTask?.let { work(it) } }

        val task = if (delay == null) Bukkit.getScheduler().runTask(this, runnable)
            else if (loop == null) Bukkit.getScheduler().runTaskLater(this, runnable, delay)
            else Bukkit.getScheduler().runTaskTimer(this, runnable, delay, loop)

        bukkitTask = task
        return task
    }

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