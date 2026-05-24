package _RedGold__.main.core.guild

import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.SetSlowInit

@SetSlowInit
object GuildUpdate {
    @Volatile private var shareGuildMembers = allGetGuildMembers()

    init {
        taskAsync(loop = updateTick) {
            shareGuildMembers = allGetGuildMembers()

            refresh()
        }
    }

    private fun refresh() {
        joinedGuildCache.clear()
        joinedGuildCache.putAll(shareGuildMembers)
    }
}