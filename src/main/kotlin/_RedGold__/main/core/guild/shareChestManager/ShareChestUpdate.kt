package _RedGold__.main.core.guild.shareChestManager

import _RedGold__.main.core.guild.expManager.expBenefits.shareChestSizeBenefits
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.shareChestManager.shareChestInv.ShareChestInvHolder
import _RedGold__.main.functions.task
import org.bukkit.inventory.Inventory

fun shareChestUpdate() {
    val updates = mutableListOf<Triple<Int, Int, Inventory>>()

    shareChestData.forEach { (id, gui) ->
        val level = guildLevelCache[id]?: return@forEach
        val size = shareChestSizeBenefits(level)

        if (gui.size != size) {
            updates.add(Triple(id, size, gui))
        }
    }

    task {
        updates.forEach { (id, size, oldGui) ->
            val newGui = ShareChestInvHolder(size).inventory
            newGui.contents = oldGui.contents

            shareChestData[id] = newGui
            oldGui.viewers.forEach {
                it.openInventory(newGui)
            }
        }
    }
}