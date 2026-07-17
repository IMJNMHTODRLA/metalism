package _RedGold__.main.commands.user.boost.listeners.settings.vip.chatGGColorGui

import _RedGold__.main.commands.user.boost.listeners.settings.SettingsGlobalConst
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.CHAT_GG_COLOR
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.chatGGColor.chatGGColorList
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class ChatGGColorListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? ChatGGColorHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        val slot = event.slot
        val clickType = event.click

        when(slot) {
            27 -> ChatGGColorGui().openGui(player, (holder.page - 1).coerceAtLeast(0))
            35 -> ChatGGColorGui().openGui(player, holder.page + 1)

            else -> {
                val id = SettingsGlobalConst.getIdFromSlot(holder.page, slot)?: return
                val (title, color) = chatGGColorList.getOrNull(id)?: return

                if (clickType.isLeftClick)
                    SettingsGlobalConst.setEquip(player, title, CHAT_GG_COLOR, id) {
                        ChatGGColorGui().openGui(player, holder.page)
                    }
                else if (clickType.isRightClick)
                    SettingsGlobalConst.preview(player, {
                        val prefix = PermissionEnum[this].prefix
                        val (_, style) = data.equipStyle

                        sendMsg("$prefix $style $name&f: ${color}GG")
                    }) { ChatGGColorGui().openGui(player, holder.page) }
            }
        }
    }
}