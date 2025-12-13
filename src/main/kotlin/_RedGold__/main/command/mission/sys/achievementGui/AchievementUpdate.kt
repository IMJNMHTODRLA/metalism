package _RedGold__.main.command.mission.sys.achievementGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import com.destroystokyo.paper.event.block.AnvilDamagedEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemType
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireJavaPlugin
@RequireListener
class AchievementUpdate(private val plugin: JavaPlugin) : Listener {
    private val isGet: MutableMap<UUID, MutableList<Int>> = mutableMapOf()

    private fun Player.isClear(t: Int, max: Int, mission: String) {
        val uuid = this.uniqueId
        if (isGet[uuid]?.contains(t) == true) return

        val data = getData(plugin, this, "mission/achievement/progress/$t").toInt()
        if (data < max) {
            saveData(plugin, this, "mission/achievement/progress/$t", data + 1)
            if (data + 1 == max) {
                this.sendMessage(gc("&f&l미션 &6&l\"$mission\"&f&l(을)를 클리어 하였습니다."))
                this.playSound(this.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
            }
            return
        }

        if (isGet[uuid] == null) isGet[uuid] = mutableListOf(t)
        else isGet[uuid]!!.add(t)
    }

    fun onMission0(player: Player) {
        player.isClear(0, 200, "일일 접속을 200회")
    }

    @EventHandler
    fun onMission1(event: EntityDeathEvent) {
        if (event.entity !is Player) return
        val attacker = event.entity.killer?: return

        attacker.isClear(1, 200, "플레이어 처치를 200회")
    }

    @EventHandler
    fun onMission2(event: BlockPlaceEvent) {
        val player = event.player
        if (event.block.type == Material.OBSIDIAN) player.isClear(2, 300, "흑요석 설치를 300회")
    }

    fun onMission3(player: Player) {
        player.isClear(3, 12, "월간 상점 아이템 구매를 12회")
    }

    fun onMission4(player: Player) {
        player.isClear(4, 24, "월간 상점 아이템 구매를 24회")
    }

    @EventHandler
    fun onMission5(event: AnvilDamagedEvent) {
        val player = event.view.player as Player
        player.isClear(5, 50, "모루 손상을 50회")
    }

    @EventHandler
    fun onMission6(event: EntitySpawnEvent) {
        val tnt = event.entity as? TNTPrimed?: return
        val player = tnt.source as? Player?: return

        player.isClear(6, 300, "TNT 점화를 300회")
    }

    @EventHandler
    fun onMission7(event: PlayerItemConsumeEvent) {
        val player = event.player
        if(event.item.type == Material.ENCHANTED_GOLDEN_APPLE) player.isClear(7, 10, "마법이 부여된 황금 사과 섭취를 10회")
    }

    @EventHandler
    fun onMission8(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.ENDER_PEARL) return

        val player = event.player
        player.isClear(8, 100, "엔더진주 사용을 100회")
    }

    @EventHandler
    fun onMission9(event: BlockPlaceEvent) {
        val player = event.player
        player.isClear(9, 10_000, "블록 설치를 10,000회")
    }

    @EventHandler
    fun onMission10(event: BlockBreakEvent) {
        val player = event.player
        player.isClear(10, 10_000, "블록 파괴를 10,000회")
    }

    @EventHandler
    fun onMission11(event: EntityDamageByEntityEvent) {
        if (event.entity.type != EntityType.END_CRYSTAL) return
        val player = event.damager as? Player ?: return

        player.isClear(11, 400, "엔드 수정 폭팔을 400회")
    }

    @EventHandler
    fun onMission12(event: EntityResurrectEvent) {
        val player = event.entity as? Player ?: return
        if (!event.isCancelled) player.isClear(12, 200, "불사의 토템 발동을 200회")
    }

    @EventHandler
    fun onMission13(event: PlayerItemConsumeEvent) {
        val player = event.player
        if(event.item.type == Material.GOLDEN_APPLE) player.isClear(13, 300, "황금 사과 섭취를 300회")
    }

    @EventHandler
    fun onMission14(event: EntityDeathEvent) {
        if (event.entity.type != EntityType.WITHER) return

        val player = event.entity.killer?: return
        player.isClear(14, 20, "위더 처치를 20회")
    }

    @EventHandler
    fun onMission15(event: EntityDeathEvent) {
        if (event.entity.type != EntityType.WITHER) return

        val player = event.entity.killer?: return
        player.isClear(15, 30, "위더 처치를 30회")
    }
}