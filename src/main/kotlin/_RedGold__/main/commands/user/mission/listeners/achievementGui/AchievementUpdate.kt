package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
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

    fun onMission0A1A2A3(player: Player) {
        player.isClear(0, 50, "일일 접속을 50회")
        player.isClear(1, 100, "일일 접속을 100회")
        player.isClear(2, 150, "일일 접속을 150회")
        player.isClear(3, 200, "일일 접속을 200회")
    }

    @EventHandler
    fun onMission4A5(event: EntityDeathEvent) {
        ((event.entity as Player).killer?: return).isClear(4, 50, "플레이어 처치를 50회")
        ((event.entity as Player).killer?: return).isClear(5, 100, "플레이어 처치를 100회")
    }

    fun onMission6A7(player: Player) {
        player.isClear(6, 6, "월간 상점 아이템 구매를 6회")
        player.isClear(7, 12, "월간 상점 아이템 구매를 12회")
    }

    @EventHandler
    fun onMission8A9(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.ENDER_PEARL) return

        event.player.isClear(8, 100, "엔더진주 사용을 100회")
        event.player.isClear(9, 200, "엔더진주 사용을 200회")
    }

    @EventHandler
    fun onMission10A11A12A13(event: BlockPlaceEvent) {
        event.player.isClear(10, 2500, "블록 설치를 2,500회")
        event.player.isClear(11, 5000, "블록 설치를 5,000회")
        event.player.isClear(12, 7500, "블록 설치를 7,500회")
        event.player.isClear(13, 10000, "블록 설치를 10,000회")
    }


    @EventHandler
    fun onMission14A15A16A17(event: BlockBreakEvent) {
        event.player.isClear(14, 2500, "블록 파괴를 2,500회")
        event.player.isClear(15, 5000, "블록 파괴를 5,000회")
        event.player.isClear(16, 7500, "블록 파괴를 7,500회")
        event.player.isClear(17, 10000, "블록 파괴를 10,000회")
    }

    @EventHandler
    fun onMission18A19A20(event: EntityDamageByEntityEvent) {
        if (event.entity.type != EntityType.END_CRYSTAL) return
        val player = event.damager as? Player?: return

        player.isClear(18, 100, "엔드 수정 폭팔을 100회")
        player.isClear(19, 150, "엔드 수정 폭팔을 150회")
        player.isClear(20, 200, "엔드 수정 폭팔을 200회")
    }

    @EventHandler
    fun onMission21A22(event: EntityResurrectEvent) {
        val player = event.entity as? Player?: return
        if (!event.isCancelled) {
            player.isClear(21, 100, "불사의 토템 발동을 100회")
            player.isClear(22, 200, "불사의 토템 발동을 200회")
        }
    }

    @EventHandler
    fun onMission23A24A25(event: PlayerItemConsumeEvent) {
        val player = event.player
        if (event.item.type == Material.GOLDEN_APPLE) {
            player.isClear(23, 100, "황금 사과 섭취를 100회")
            player.isClear(24, 200, "황금 사과 섭취를 200회")
            player.isClear(25, 300, "황금 사과 섭취를 300회")
        }
    }

    @EventHandler
    fun onMission26A27A28(event: EntityDeathEvent) {
        if (event.entity.type != EntityType.WITHER) return
        val player = event.entity.killer?: return

        player.isClear(26, 10, "위더 처치를 10회")
        player.isClear(27, 20, "위더 처치를 20회")
        player.isClear(28, 30, "위더 처치를 30회")
    }
}