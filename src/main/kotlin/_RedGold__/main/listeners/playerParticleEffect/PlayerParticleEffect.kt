package _RedGold__.main.listeners.playerParticleEffect

import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.ATTACK_PARTICLE_EFFECT
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.JUMP_PARTICLE_EFFECT
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.particleEffect.particleEffectList
import com.destroystokyo.paper.event.entity.EntityJumpEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

@RequireListener
class PlayerParticleEffect : Listener {
    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player?: return
        val victim = event.entity

        val id = attacker.data.boostSettingMap[ATTACK_PARTICLE_EFFECT]
            .takeUnless { it == 0 }
            ?: return

        val data = particleEffectList.getOrNull(id)?: return
        val particle = data.particle?: return
        val blockData = data.data?.blockData

        val world = Bukkit.getWorld(OVER_WORLD)?: return

        val width = victim.width / 2.0
        val height = victim.height / 2.0

        val location = victim.location.clone()
        val frontLoc = location.add(0.0, height, 0.0)

        world.spawnParticle(
            particle,
            frontLoc.x, frontLoc.y, frontLoc.z,
            20,
            width, height, width,
            0.0,
            blockData
        )
    }

    @EventHandler
    fun onJump(event: EntityJumpEvent) {
        val player = event.entity as? Player?: return

        val id = player.data.boostSettingMap[JUMP_PARTICLE_EFFECT]
            .takeUnless { it == 0 }
            ?: return

        val data = particleEffectList.getOrNull(id)?: return
        val particle = data.particle?: return
        val blockData = data.data?.blockData

        val world = Bukkit.getWorld(OVER_WORLD)?: return

        val width = player.width / 2.0
        val height = 0.1

        val location = player.location

        world.spawnParticle(
            particle,
            location.x, location.y, location.z,
            10,
            width, height, width,
            0.085,
            blockData
        )
    }
}