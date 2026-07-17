package _RedGold__.main.commands.user.chest.listeners.chestGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.chestManager.DEF_CHEST_SLOT

class ChestHolder(val page: Int) : EasyHolder(DEF_CHEST_SLOT) {
    override fun title() = "창고($page)"
}