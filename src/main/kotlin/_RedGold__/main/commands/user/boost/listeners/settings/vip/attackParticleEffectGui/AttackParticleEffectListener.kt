package _RedGold__.main.commands.user.boost.listeners.settings.vip.attackParticleEffectGui

import _RedGold__.main.commands.user.boost.listeners.settings.SettingsGlobalConst
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.ATTACK_PARTICLE_EFFECT
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.particleEffect.particleEffectList
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class AttackParticleEffectListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? AttackParticleEffectHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        val slot = event.slot
        val clickType = event.click
        val page = holder.page

        when(slot) {
            27 -> AttackParticleEffectGui().openGui(player, (page - 1).coerceAtLeast(0))
            35 -> AttackParticleEffectGui().openGui(player, page + 1)

            else -> {
                val id = SettingsGlobalConst.getIdFromSlot(page, slot)?: return
                val data = particleEffectList.getOrNull(id)?: return

                if (clickType.isLeftClick)
                    SettingsGlobalConst.setEquip(player, data.name, ATTACK_PARTICLE_EFFECT, id) {
                        AttackParticleEffectGui().openGui(player, page)
                    }
                else if (clickType.isRightClick)
                    SettingsGlobalConst.preview(plugin, player, {
                        val world = Bukkit.getWorld(OVER_WORLD)?: return@preview

                        val width = player.width / 2.0
                        val height = player.height / 2.0

                        val location = player.location.clone()
                        val frontLoc = location.add(location.direction.multiply(2.0))

                        val particle = data.particle?: return@preview
                        val blockData = data.data?.blockData

                        world.spawnParticle(
                            particle,
                            frontLoc.x, frontLoc.y, frontLoc.z,
                            20,
                            width, height, width,
                            0.0,
                            blockData
                        )
                    }) { AttackParticleEffectGui().openGui(player, page) }
            }
        }
    }
}