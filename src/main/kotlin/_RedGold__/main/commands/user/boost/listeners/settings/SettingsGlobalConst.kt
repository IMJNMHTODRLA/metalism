package _RedGold__.main.commands.user.boost.listeners.settings

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastNumber.seconds
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.task
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KFunction

object SettingsGlobalConst {
    fun getSlot(i: Int) = i + 10 + (i / 7 * 2)
    fun getIdFromI(page: Int, i: Int) = i + (page * 14)
    fun getIdFromSlot(page: Int, slot: Int) =
        slot.takeIf { it in 10..25 && it % 9 in 1..7 }
            ?.let { (it - 10) - ((it - 10) / 9 * 2) + (page * 14) }

    fun setEquip(player: Player, title: String, enum: BoostSettingEnum, setId: Int, doneAction: () -> Unit) {
        if (player !in enum && enum.default != setId) {
            val permissionName = enum.needPermission.name

            player.fail("&c$permissionName 전용 설정입니다.")
            return
        }
        player.data.boostSettingMap[enum] = setId

        player.sendMsg("$title&a&l (으)로 설정하였습니다.")
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)

        doneAction()
    }

    fun preview(player: Player, action: Player.() -> Unit, doneAction: () -> Unit) {
        player.closeInventory()

        player.action()
        task(1.seconds) {
            if (!player.isOnline) return@task
            doneAction()
        }
    }

    fun setDisplayItem(
        rawTitle: String,
        isEquip: Boolean,
        hasRank: Boolean,
        permission: PermissionEnum
    ): ItemStack {
        val material = if (isEquip) Material.WRITABLE_BOOK else Material.BOOK
        val title = "$rawTitle${if (isEquip) "&8&l [&a&l장착됨&8&l]" else ""}"

        return getItem(
            material,
            title,
            listOf(
                "",
                "&a&l[좌클릭]: &f&l장착",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                if (hasRank) "" else "&c${permission.name} 전용 설정입니다."
            )
        )
    }
}