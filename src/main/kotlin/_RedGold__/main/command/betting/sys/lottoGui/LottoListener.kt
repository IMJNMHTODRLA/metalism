package _RedGold__.main.command.betting.sys.lottoGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom
import kotlin.math.floor

@RequireListener
@RequireJavaPlugin
class LottoListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder!!
        if (holder is LottoHolder && holder.isStart) Bukkit.getScheduler().runTask(plugin, Runnable {
            event.player.openInventory(event.inventory)
        })
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is LottoHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as LottoHolder
            event.isCancelled = true

            if (!holder.isStart) {
                when (slot) {
                    22 -> {
                        val gold = getData(plugin, player, "gold").toLong()

                        if (gold - 1000 < 0) {
                            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(1000 - gold).toFormat()}"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        holder.isStart = true

                        saveData(plugin, player, "gold", gold - 1000)
                        addHoldGold(plugin, 1000)

                        var delayTime = -1L
                        val result = listOf(
                            SecureRandom().nextInt(10) + 1,
                            SecureRandom().nextInt(10) + 1,
                            SecureRandom().nextInt(10) + 1,
                            SecureRandom().nextInt(10) + 1,
                            SecureRandom().nextInt(10) + 1,
                            SecureRandom().nextInt(10) + 1
                        ) //1~10사이(6자리)

                        val itemNumList = listOf(19, 20, 21, 23, 24, 25)

                        for (i in 0..5) {
                            for (ii in 0..40) {
                                val randomNum = SecureRandom().nextInt(10) + 1
                                delayTime += 1

                                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                    gui.setItem(itemNumList[i], getItem(
                                        "gold_nugget",
                                        "&8&l[ &7&l${randomNum} &8&l]",
                                        listOf("", "&7&l추첨 중...: $randomNum")
                                    ).apply {amount = randomNum})
                                    player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                                }, delayTime)
                            }

                            delayTime += 1
                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                if (result[i] == holder.select[i]) {
                                    gui.setItem(itemNumList[i], getItem(
                                        "gold_ingot",
                                        "&f&l[ &6&l${result[i]} &f&l]",
                                        listOf("", "&7&l${i + 1}번째 숫자 추첨 완료!")
                                    ).apply {
                                        addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 5)
                                        amount = result[i]
                                    })

                                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
                                } else {
                                    gui.setItem(itemNumList[i], getItem(
                                        "black_concrete",
                                        "&f&l[ &a&l${result[i]} &f&l]",
                                        listOf("", "&7&l${i + 1}번째 숫자 추첨 완료!")
                                    ).apply {amount = result[i]})

                                    player.playSound(player.location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, 2f)
                                }

                            }, delayTime)

                            delayTime += 20
                        }

                        delayTime += 10
                        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                            var rightNumTimes = 0

                            for (i in 0..5) {
                                if (result[i] == holder.select[i]) rightNumTimes++
                            }

                            val winMessage = listOf(
                                "&8&l추첨에서 낙첨되었습니다...(0자리 일치)", // 7등
                                "&7&l추첨에서 1자리를 맞춰 6등이 되었습니다.(1자리 일치)", // 6등
                                "&7&l추첨에서 2자리를 맞춰 5등이 되었습니다(2자리 일치)", // 5등
                                "&f&l축하합니다! 추첨에서 3자리를 맞춰 4등이 되었습니다!", // 4등
                                "&e&l축하합니다!! 추첨에서 4자리를 맞춰 3등이 되었습니다!!", // 3등
                                "${rgb("FFCC00")}&l추첨에서 무려 5자리를 맞춰 2등을 차지하였습니다!!!", // 2등
                                "&6&l축하합니다!!!! 추첨에서 무려 6자리 모두 맞춰 1등에 당첨되었습니다!!!!!" // 1등
                            )

                            val winSound = listOf(
                                Sound.ENTITY_LIGHTNING_BOLT_IMPACT, // 7등
                                Sound.ENTITY_PLAYER_ATTACK_STRONG, // 6등
                                Sound.ENTITY_PLAYER_ATTACK_CRIT, // 5등
                                Sound.ENTITY_EXPERIENCE_ORB_PICKUP, // 4등
                                Sound.ENTITY_PLAYER_LEVELUP, // 3등
                                Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, // 2등
                                Sound.ENTITY_FIREWORK_ROCKET_TWINKLE // 1등
                            )

                            val winPrize = listOf(
                                0L, // 7등
                                1_000L, // 6등
                                10_000L, // 5등
                                1_000_000L, // 4등
                                10_000_000L, // 3등
                                50_000_000L, // 2등
                                100_000_000L // 1등
                            )

                            holder.isStart = false
                            player.closeInventory()

                            player.sendMessage(gc(winMessage[rightNumTimes]))
                            player.sendMessage(gc("&6&l획득 골드: ${winPrize[rightNumTimes].toFormat()} 골드"))

                            saveData(plugin, player, "gold", gold - 1000 + winPrize[rightNumTimes])
                            addMakeGold(plugin, winPrize[rightNumTimes])

                            player.playSound(player.location, winSound[rightNumTimes], 1f, 1f)
                        }, delayTime)
                    }




                    19, 20, 21, 23, 24, 25 -> {
                        val getListNum = when(slot) {
                            19 -> 0
                            20 -> 1
                            21 -> 2
                            23 -> 3
                            24 -> 4
                            25 -> 5
                            else -> 0
                        }

                        holder.select[getListNum] = ((holder.select[getListNum]) % 10) + 1

                        val itemNumList = listOf(19, 20, 21, 23, 24, 25)
                        for (i in 0..5) {
                            gui.setItem(itemNumList[i], getItem(
                                "gold_nugget",
                                "&f&l[ &e&l${holder.select[i]} &f&l]",
                                listOf("", "&7클릭 시 ${i + 1}번째 로또 번호가 1 증가됩니다.", "&7현재 고른 로또 번호: ${holder.select}")
                            ).apply {amount = holder.select[i]})
                        }

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                    }
                }
            }
        }
    }
}