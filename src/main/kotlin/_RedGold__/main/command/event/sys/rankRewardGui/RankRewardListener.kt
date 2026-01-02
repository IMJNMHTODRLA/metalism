package _RedGold__.main.command.event.sys.rankRewardGui

import _RedGold__.main.Main.Event.END_TIME
import _RedGold__.main.Main.Event.EVENT_CODE
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime

@RequireListener
@RequireJavaPlugin
class RankRewardListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is RankRewardHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val slot = event.slot
            event.isCancelled = true

            if (slot == 48) {
                if (END_TIME.plusDays(2).isBefore(LocalDateTime.now())) {
                    player.fail("&c이벤트 종료 후 2일이 안 지났습니다.")
                    return
                }

                val rank = RankRewardGui().getTier2Player(uuid)

                if (rank == "&8&l랭크 없음") {
                    player.fail("&c당신은 ${rank}&c이여서 보상 획득이 불가능 합니다.")
                    return
                }

                val hasRankReward = getData(plugin, player, "$EVENT_CODE/rank_reward")

                if (hasRankReward != "0") {
                    player.fail("&c당신은 이미 보상을 획득 하였습니다.")
                    return
                }

                saveData(plugin, player, "$EVENT_CODE/rank_reward", 1)
                var giveGold: Long? = null
                var giveCash: Long? = null
                var givePotion: Byte? = null
                var giveCrystal: Byte? = null
                var giveRespawn: Byte? = null
                var giveDontDie: Byte? = null
                var giveBlack: Byte? = null

                player.good("&a보상 획득이 완료 되었습니다.")

                when(rank) {
                    "${rgb("D593FF")}&l챌린저(1위)" -> {
                        giveGold = 3_000_000
                        giveCash = 200
                        givePotion = 64
                        giveCrystal = 64
                        giveRespawn = 64
                        giveDontDie = 24
                    }
                    "${rgb("4FD0FF")}&l마스터(2위~5위)" -> {
                        giveGold = 2_250_000
                        giveCash = 150
                        givePotion = 48
                        giveCrystal = 48
                        giveRespawn = 48
                        giveDontDie = 16
                    }
                    "${rgb("FFBFF4")}&l플래티넘(6위~상위 10%)" -> {
                        giveGold = 2_000_000
                        giveCash = 100
                        givePotion = 32
                        giveCrystal = 32
                        giveRespawn = 32
                        giveDontDie = 8
                    }
                    "${rgb("FFBF00")}&l골드(상위 11%~30%)" -> {
                        giveGold = 1_500_000
                        giveCash = 75
                        givePotion = 24
                        giveCrystal = 24
                        giveRespawn = 24
                    }
                    "${rgb("999999")}&l실버(상위 31%~50%)" -> {
                        giveGold = 2_000_000
                        giveCash = 50
                        givePotion = 16
                        giveCrystal = 16
                        giveBlack = 24
                    }
                    "${rgb("895422")}&l브론즈(상위 51%~100%)" -> {
                        giveGold = 1_000_000
                        giveCash = 25
                        givePotion = 8
                        giveCrystal = 8
                        giveBlack = 16
                    }
                }

                if (giveGold != null) {
                    saveData(plugin, player, "gold", getData(plugin, player, "gold") + giveGold)
                    addMakeGold(plugin, giveGold)
                }

                if (giveCash != null) {
                    saveData(plugin, player, "cash", getData(plugin, player, "cash") + giveCash)
                    addMakeGold(plugin, giveCash * 10000)
                }

                if (givePotion != null) player.inventory.addItem(getItem("experience_bottle").apply{amount = givePotion.toInt()})
                if (giveCrystal != null) player.inventory.addItem(getItem("end_crystal").apply{amount = giveCrystal.toInt()})
                if (giveRespawn != null) player.inventory.addItem(getItem("respawn_anchor").apply{amount = giveRespawn.toInt()})
                if (giveDontDie != null) player.inventory.addItem(getItem("totem_of_undying").apply{amount = giveDontDie.toInt()})
                if (giveBlack != null) player.inventory.addItem(getItem("obsidian").apply{amount = giveBlack.toInt()})
            }
        }
    }
}