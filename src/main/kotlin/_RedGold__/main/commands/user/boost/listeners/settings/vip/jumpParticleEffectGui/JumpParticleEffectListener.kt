package _RedGold__.main.commands.user.boost.listeners.settings.vip.jumpParticleEffectGui

import _RedGold__.main.commands.user.boost.listeners.settings.SettingsGlobalConst
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.JUMP_PARTICLE_EFFECT
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.particleEffect.particleEffectList
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class JumpParticleEffectListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? JumpParticleEffectHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        val slot = event.slot
        val clickType = event.click
        val page = holder.page

        when(slot) {
            27 -> JumpParticleEffectGui().openGui(player, (page - 1).coerceAtLeast(0))
            35 -> JumpParticleEffectGui().openGui(player, page + 1)

            else -> {
                val id = SettingsGlobalConst.getIdFromSlot(page, slot)?: return
                val data = particleEffectList.getOrNull(id)?: return

                if (clickType.isLeftClick)
                    SettingsGlobalConst.setEquip(player, data.name, JUMP_PARTICLE_EFFECT, id) {
                        JumpParticleEffectGui().openGui(player, page)
                    }
                else if (clickType.isRightClick)
                    SettingsGlobalConst.preview(plugin, player, {
                        val world = Bukkit.getWorld(OVER_WORLD)?: return@preview

                        val width = width / 2.0
                        val height = 0.1

                        val particle = data.particle?: return@preview
                        val blockData = data.data?.blockData

                        world.spawnParticle(
                            particle,
                            location.x, location.y + 0.1, location.z,
                            10,
                            width, height, width,
                            0.085,
                            blockData
                        )
                    }) { JumpParticleEffectGui().openGui(player, page) }
            }
        }
    }
}