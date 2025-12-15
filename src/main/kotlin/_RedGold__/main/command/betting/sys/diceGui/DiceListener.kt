package _RedGold__.main.command.betting.sys.diceGui

import _RedGold__.main.function.Color.gc
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
class DiceListener(private val plugin: JavaPlugin) : Listener {
    private val random = SecureRandom()

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder!!
        if (holder is DiceHolder && holder.isStart) Bukkit.getScheduler().runTask(plugin, Runnable {
            event.player.openInventory(event.inventory)
        })
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is DiceHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as DiceHolder
            event.isCancelled = true

            if (!holder.isStart) {
                when (slot) {
                    22 -> {
                        if (holder.betGold <= 0) {
                            player.sendMessage(gc("&c베팅 금액을 설정하지 않았습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        if (holder.select < 0) {
                            player.sendMessage(gc("&c숫자 1~6중 한개를 골라주세요."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        if (gold - holder.betGold < 0) {
                            player.sendMessage(gc("&c베팅 금액이 보유 골드를 초과하였습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        if (holder.betGold > 500_000) {
                            player.sendMessage(gc("&c베팅 금액이 너무 높습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        holder.isStart = true

                        saveData(plugin, player, "gold", gold - holder.betGold)
                        addHoldGold(plugin, holder.betGold)

                        var delayTime = 0L
                        val result = random.nextInt(6) //0~5사이

                        for (i in 0..50) {
                            val randomNum = random.nextInt(6) + 1

                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                gui.setItem(22, getItem(
                                    "gold_nugget",
                                    "&7&l주사위 굴리는 중${".".repeat((i % 3) + 1)}"
                                ).apply {amount = randomNum})
                                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                            }, delayTime)

                            delayTime += 1
                        }

                        for (i in 0..30) {
                            delayTime += 2
                            val randomNum = random.nextInt(6) + 1

                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                gui.setItem(22, getItem(
                                    "gold_nugget",
                                    "&7&l주사위 굴리는 중${".".repeat((i % 3) + 1)}"
                                ).apply {amount = randomNum})
                                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                            }, delayTime)
                        }

                        for (i in 0..10) {
                            delayTime += 3
                            val randomNum = random.nextInt(6) + 1

                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                gui.setItem(22, getItem(
                                    "gold_nugget",
                                    "&7&l주사위 굴리는 중${".".repeat((i % 3) + 1)}"
                                ).apply {amount = randomNum})
                                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                            }, delayTime)
                        }

                        for (i in 0..8) {
                            delayTime += 6
                            val randomNum = random.nextInt(6) + 1

                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                gui.setItem(22, getItem(
                                    "gold_nugget",
                                    "&7&l주사위 굴리는 중${".".repeat((i % 3) + 1)}"
                                ).apply {amount = randomNum})
                                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                            }, delayTime)
                        }

                        delayTime += 30
                        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                            gui.setItem(22, getItem(
                                "gold_nugget",
                                "&f&l결과: &7&l${result + 1}"
                            ).apply {
                                amount = result + 1
                                addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)
                            })

                            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1.5f)

                            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                                holder.isStart = false
                                player.closeInventory()

                                if (result.toByte() == holder.select) {
                                    val giveGold = floor(holder.betGold * 4.5).toLong()

                                    player.sendMessage(gc("&a주사위 굴리기 도박에 성공하였습니다. 획득 골드: ${giveGold.toFormat()} 골드"))

                                    saveData(plugin, player, "gold", gold - holder.betGold + giveGold)
                                    addMakeGold(plugin, holder.betGold)

                                    player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f)
                                }
                                else {
                                    player.sendMessage(gc("&c주사위 굴리기 도박에 실패하였습니다. 잃은 골드: ${holder.betGold.toFormat()} 골드"))
                                    player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_HURT, 1f, 1.5f)
                                }
                            }, 20)
                        }, delayTime)
                    }




                    19, 20, 21, 23, 24, 25 -> {
                        val getNum = when(slot) {
                            19 -> 0
                            20 -> 1
                            21 -> 2
                            23 -> 3
                            24 -> 4
                            25 -> 5

                            else -> 0
                        }

                        val itemNumList = listOf(19, 20, 21, 23, 24, 25)

                        for (i in 1..6) {
                            gui.setItem(itemNumList[i - 1], getItem(
                                "gold_nugget",
                                "&e${i}번",
                                listOf("", "&7클릭 시 ${i}번으로 선택 됩니다.")
                            ).apply {amount = i})
                        }

                        gui.setItem(slot, getItem(
                            "gold_nugget",
                            "&e${getNum + 1}번",
                            listOf("", "&7클릭 시 ${getNum + 1}번으로 선택 됩니다.")
                        ).apply {
                            amount = getNum + 1
                            addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)
                        })

                        holder.select = getNum.toByte()
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                    }
                    45, 46, 47, 48 -> {
                        val removeGold = when(slot) {
                            45 -> 100_000
                            46 -> 10_000
                            47 -> 1_000
                            48 -> 100
                            else -> 0
                        }

                        if (holder.betGold - removeGold < 0) {
                            player.sendMessage(gc("&c베팅 금액을 음수로 내릴 수 없습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        holder.betGold -= removeGold

                        gui.setItem(49, getItem(
                            "gray_stained_glass_pane",
                            "&6&l배팅 금액&f: ${holder.betGold.toFormat()} &6&l골드",
                        ))

                        player.sendMessage(gc("&c베팅 금액에서 ${removeGold.toLong().toFormat()} 골드를 회수하였습니다. 베팅 금액: ${holder.betGold.toFormat()}"))
                        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 2f)
                    }
                    50, 51, 52, 53 -> {
                        val addGold = when(slot) {
                            53 -> 100_000
                            52 -> 10_000
                            51 -> 1_000
                            50 -> 100
                            else -> 0
                        }

                        if (holder.betGold + addGold > 500_000) {
                            player.sendMessage(gc("&c베팅 금액을 더 이상 높힐 수 없습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        holder.betGold += addGold

                        gui.setItem(49, getItem(
                            "gray_stained_glass_pane",
                            "&6&l배팅 금액&f: ${holder.betGold.toFormat()} &6&l골드",
                        ))

                        player.sendMessage(gc("&a베팅 금액에 ${addGold.toLong().toFormat()} 골드를 추가하였습니다. 베팅 금액: ${holder.betGold.toFormat()}"))
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
                    }
                }
            }
        }
    }
}