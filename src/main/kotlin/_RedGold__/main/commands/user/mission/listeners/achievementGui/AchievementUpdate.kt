package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.data.type.RespawnAnchor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityResurrectEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent

@RequireListener
class AchievementUpdate : Listener {
    @EventHandler
    fun onMission3A4(event: PlayerDeathEvent) {
        val player = event.entity.killer?: return

        AchievementConst.mission(player, 3)
        AchievementConst.mission(player, 4)
    }

    @EventHandler
    fun onMission7A8A9(event: BlockPlaceEvent) {
        val player = event.player

        AchievementConst.mission(player, 7)
        AchievementConst.mission(player, 8)
        AchievementConst.mission(player, 9)
    }

    @EventHandler
    fun onMission10A11A12(event: BlockBreakEvent) {
        val player = event.player

        AchievementConst.mission(player, 10)
        AchievementConst.mission(player, 11)
        AchievementConst.mission(player, 12)
    }


    @EventHandler
    fun onMission13(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.ENDER_PEARL) return

        AchievementConst.mission(event.player, 13)
    }

    @EventHandler
    fun onMission14(event: EntityDamageByEntityEvent) {
       if (event.entity.type != EntityType.END_CRYSTAL) return

        val player = when (
            val damager = event.damager
        ) {
            is Player -> damager
            is Projectile -> damager.shooter as? Player
            else -> null
        }?: return

        AchievementConst.mission(player, 14)
    }

    @EventHandler
    fun onMission15(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK) return

        val block = event.clickedBlock?: return
        if (block.type != Material.RESPAWN_ANCHOR) return

        val player = event.player

        if (player.world.environment != World.Environment.NETHER) {
            val blockData = block.blockData as? RespawnAnchor?: return

            if (blockData.charges <= 0) return
            if (event.item?.type == Material.GLOWSTONE && blockData.charges < blockData.maximumCharges) return

            AchievementConst.mission(player, 15)
        }
    }

    @EventHandler
    fun onMission16(event: EntityResurrectEvent) {
        val player = event.entity as? Player?: return
        if (!event.isCancelled) AchievementConst.mission(player, 16)
    }

    @EventHandler
    fun onMission17(event: PlayerItemConsumeEvent) {
        val player = event.player
        if (event.item.type == Material.GOLDEN_APPLE) AchievementConst.mission(player, 17)
    }

    @EventHandler
    fun onMission18(event: EntityDeathEvent) {
        if (event.entity.type != EntityType.WITHER) return
        val player = event.entity.killer?: return

        AchievementConst.mission(player, 18)
    }
}