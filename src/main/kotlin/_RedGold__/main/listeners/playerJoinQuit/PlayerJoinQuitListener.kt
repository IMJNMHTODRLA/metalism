package _RedGold__.main.listeners.playerJoinQuit

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.EasyEnchant.enchant
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

@RequireListener
class PlayerJoinQuitListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val uuid = player.uniqueId

        if (!player.hasPlayedBefore()) {
            player.inv += getItem(Material.IRON_SWORD).apply {
                enchant[Enchantment.UNBREAKING, Enchantment.SHARPNESS] = 2
            }

            listOf(Material.IRON_AXE, Material.IRON_PICKAXE, Material.IRON_SHOVEL).forEach {
                player.inv += getItem(it).apply {
                    enchant[Enchantment.UNBREAKING, Enchantment.EFFICIENCY] = 2
                }
            }

            player.inv[EquipmentSlot.HEAD] = getItem(Material.IRON_HELMET)
            player.inv[EquipmentSlot.CHEST] = getItem(Material.IRON_CHESTPLATE)
            player.inv[EquipmentSlot.LEGS] = getItem(Material.IRON_LEGGINGS)
            player.inv[EquipmentSlot.FEET] = getItem(Material.IRON_BOOTS)

            player.inv += ItemStack(Material.BREAD, 64)
        }

        val (_, message) = player.data.equipJoin
        val style = player.data.equipStyle.let {
            if (it.first) "${it.second} "
            else ""
        }

        event.joinMessage = message.fill(
            "style" to style,
            "rank" to PermissionEnum[player].prefix,
            "name" to player.name,
        ).gc()

        player.sendSound(Sound.ENTITY_PLAYER_LEVELUP)

        val playerMonthlyPackage = player.data.boostMap[BoostEnum.MONTHLY_PACKAGE]
        val expirationAt = playerMonthlyPackage?.expirationAt?: 0L
        val remainingPeriod = expirationAt - now

        if (
            !PlayerJoinQuitValue.monthlyClaimed.contains(uuid) &&
            remainingPeriod > 0
        ) {
            player.sendMsg(PlayerJoinQuitConst.dailyGiveMessage.fill(
                "crystal" to PlayerJoinQuitConst.monthly.dailyCrystal.toFormat(),
                "day_exp" to (remainingPeriod / 86400.0).toFormat(1)
            ))

            player.data.crystal += PlayerJoinQuitConst.monthly.dailyCrystal
            PlayerJoinQuitValue.monthlyClaimed.add(uuid)
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        player.isInvulnerable = false //TODO: 언젠가 얘도 해야함
        event.quitMessage = "&8[&c-&8] ${PermissionEnum[player].node} ${player.name} &e님이 서버에서 퇴장했습니다.".gc()
    }
}