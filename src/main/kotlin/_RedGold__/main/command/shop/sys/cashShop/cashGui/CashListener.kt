package _RedGold__.main.command.shop.sys.cashShop.cashGui

import _RedGold__.main.command.shop.sys.cashShop.deathGui.DeathGui
import _RedGold__.main.command.shop.sys.cashShop.joinGui.JoinGui
import _RedGold__.main.command.shop.sys.cashShop.killGui.KillGui
import _RedGold__.main.command.shop.sys.cashShop.kitGui.KitGui
import _RedGold__.main.command.shop.sys.cashShop.styleGui.StyleGui
import _RedGold__.main.command.shop.sys.cashShop.ticketGui.TicketGui
import _RedGold__.main.command.shop.sys.cashShop.ticketGui.TicketHolder
import _RedGold__.main.command.shop.sys.cashShop.ticketGui.TicketListener.CosmeticType
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.Scheduler.task
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import _RedGold__.main.sys.JoinQuit.JoinMessage.messageType
import _RedGold__.main.sys.KillRespawn.ChatApply.deathSoundMessage
import _RedGold__.main.sys.KillRespawn.ChatApply.killSoundMessage
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitch
import _RedGold__.main.sys.KillRespawn.ChatApply.soundPitchKill
import _RedGold__.main.sys.KillRespawn.ChatApply.soundType
import _RedGold__.main.sys.KillRespawn.ChatApply.soundTypeKill
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom

@RequireListener
@RequireJavaPlugin
class CashListener(private val plugin: JavaPlugin) : Listener {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2948FC")}§l§oM
        ${rgb("2F4BFC")}§l§oE
        ${rgb("344FFC")}§l§oT
        ${rgb("3A53FD")}§l§oA
        ${rgb("3F57FD")}§l§oL
        ${rgb("455AFD")}§l§oI
        ${rgb("4A5EFD")}§l§oS
        ${rgb("5062FD")}§l§oM 
        ${rgb("5B69FE")}§l§oC
        ${rgb("606DFE")}§l§oA
        ${rgb("6671FE")}§l§oS
        ${rgb("6B75FE")}§l§oH 
        ${rgb("767CFE")}§l§oS
        ${rgb("7C80FF")}§l§oH
        ${rgb("8184FF")}§l§oO
        ${rgb("8787FF")}§l§oP
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    private val random = SecureRandom()

    private val maxDeath = 13 //0~12
    private val maxKill = 13 //13~25
    private val maxJoin = 13 //26~38
    private val maxStyle = 14 //39~52

    private val offset1 = maxDeath
    private val offset2 = maxDeath + maxKill
    private val offset3 = maxDeath + maxKill + maxJoin
    private val maxAll = maxDeath + maxKill + maxJoin + maxStyle //53

    private val maxTool = 5
    private val maxCpvp = 8

    private val background = getItem(
        "magenta_stained_glass_pane",
        prefix
    )

    enum class PickType( //100.0 -> 1000
        val min: Int,
        val max: Int
    ) {
        TOOL(0, 14),
        COSMETIC(15, 29),
        CPVP(30, 499),
        EXP(500, 999);

        companion object {
            fun fromRandom(value: Int): PickType {
                return entries.first { value in it.min..it.max }
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder is CashHolder) {
            val player = event.player as Player
            val gui = event.inventory
            val holder = gui.holder as CashHolder

            if (holder.isRoulette) plugin.task(1) {
                player.openInventory(gui)
            }
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is CashHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as CashHolder
            val clickType = event.click

            val isRoulette = holder.isRoulette
            val getPoint = holder.point
            event.isCancelled = true

            if (!isRoulette) {
                when (slot) {
                    3 -> KillGui(plugin).openGui(player)
                    5 -> DeathGui(plugin).openGui(player)

                    12 -> KitGui(plugin).openGui(player)
                    14 -> {}//도구 상점

                    21 -> StyleGui(plugin).openGui(player)
                    23 -> JoinGui(plugin).openGui(player)

                    13 -> {
                        val cash = getData(plugin, player, "cash").toLong()

                        if (cash < 50) {
                            player.fail("&c캐시가 부족합니다. 필요 캐시: ${(50 - cash).toFormat()}캐시")
                            return
                        }

                        saveData(plugin, player, "cash", cash - 50)
                        saveData(plugin, player, "ticket/point", getPoint + 1)

                        holder.isRoulette = true
                        for (i in 0 until gui.size) gui.setItem(i, background)

                        val resultValue: MutableList<String> = mutableListOf()
                        for (i in 0..4) {
                            val pickType = PickType.fromRandom(random.nextInt(1000))
                            val pickNum = when(pickType) {
                                PickType.TOOL -> random.nextInt(maxTool)
                                PickType.COSMETIC -> random.nextInt(maxAll)
                                PickType.CPVP -> random.nextInt(maxCpvp)
                                else -> 0
                            }
                            resultValue.add("${pickType.name}|$pickNum")

                            gui.setItem(11 + i, getItem(
                                "chest",
                                "&e&l클릭하여 치장품 뽑기"
                            ))
                        }

                        gui.setItem(22, getItem(
                            "barrier",
                            "&c&l뽑기 종료"
                        ))

                        holder.resultValue = resultValue
                    }
                }
                return
            }

            if (slot == 22) {
                if (false in holder.isOpen) {
                    player.fail("&c모든 치장품을 뽑아주세요.")
                    return
                }
                holder.isRoulette = false

                var giveExp = 0
                var getCosmetic = 0
                var giveItem = 0

                for (i in 0..4) {
                    val result = holder.resultValue[i].split("|").toTypedArray()
                    val pickType = PickType.valueOf(result[0])
                    val pickNum = result[1].toInt()

                    val isEquip = holder.isEquip[i]
                    if (!isEquip) return

                    if (pickType == PickType.EXP) {
                        giveExp += 20
                        continue
                    }

                    if (pickType == PickType.CPVP) {
                        player.inventory.addItem(getItem(
                            itemByCpvp(pickNum)[0]
                        ))
                        giveItem++
                        continue
                    }

                    if (pickType == PickType.COSMETIC) {
                        when(pickNum) {
                            in 0..<offset1 -> saveData(plugin, player, "death_sound", pickNum + 1)
                            in offset1..<offset2 -> saveData(plugin, player, "kill_sound", (pickNum - offset1) + 1)
                            in offset2..<offset3 -> saveData(plugin, player, "join_message", (pickNum - offset2) + 1)
                            in offset3..<maxAll -> saveData(plugin, player, "style/${MAX_STYLE + pickNum}", (pickNum - offset3) + 1)
                        }
                        getCosmetic++
                        continue
                    }

                    if (pickType == PickType.TOOL) {
                        //추가예정ㅇㅇ
                        when(pickNum) {
                            in 0..<offset1 -> saveData(plugin, player, "death_sound", pickNum + 1)
                            in offset1..<offset2 -> saveData(plugin, player, "kill_sound", (pickNum - offset1) + 1)
                            in offset2..<offset3 -> saveData(plugin, player, "join_message", (pickNum - offset2) + 1)
                            in offset3..<maxAll -> saveData(plugin, player, "style/${MAX_STYLE + ((pickNum - offset3) + 1)}", 1)
                        }
                        getCosmetic++
                        continue
                    }
                }

                player.good("&a&l얻은 EXP: ${giveExp}exp, 얻은 치장품: ${getCosmetic}개, 얻은 아이템: ${giveItem}개")
                player.giveExp(giveExp)

                TicketGui(plugin).openGui(player)
            }

            if (slot in 11..15) {
                val id = when(slot) {
                    11 -> 0
                    12 -> 1
                    13 -> 2
                    14 -> 3
                    15 -> 4
                    else -> -1
                }

                if (holder.isOpen[id]) {
                    if (clickType == ClickType.LEFT) hasEquip(id, slot, player, gui, holder)
                    else if (clickType == ClickType.RIGHT) playMusic(id, player, holder)
                    return
                }

                openRoulette(id, slot, player, gui, holder)
            }
        }
    }

    private fun openRoulette(
        id: Int,
        slot: Int,
        player: Player,
        gui: Inventory,
        holder: CashHolder,
    ) {
        if (holder.isOpen[id]) return

        holder.isOpen[id] = true
        val resultList = holder.resultValue[id]
        val resultItem = resultList.split("|").toTypedArray()

        val pickType = PickType.valueOf(resultItem[0])
        val pickNum = resultItem[1].toInt()

        fun checkCosmetic(
            item: String, prefix: String, equalMessage: String,
            setDescription: Boolean = false //listOf("", "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.", "&8${resultList}")
        ) {
            val des = if (!setDescription) listOf("", "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.", "&8${resultList}")
            else listOf("", "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.", "&e&l우클릭 시 미리듣기가 가능합니다.", "&8${resultList}")

            gui.setItem(slot, getItem(
                item,
                "&f&l$prefix &a&l$equalMessage 치장품을 획득 하였습니다!",
                des
            ).apply {addUnsafeEnchantment(Enchantment.PROTECTION, 1)})

            holder.isEquip[id] = true
            player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f)
            plugin.task(20) {
                player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f)
            }
        }

        if (pickType == PickType.TOOL) {
            //따로 만들 예정ㅇㅇ
            return
        }

        if (pickType == PickType.COSMETIC) {
            when(pickNum) {
                in 0..<offset1 -> {
                    checkCosmetic("redstone", deathSoundMessage[pickNum], "사망 사운드", true)
                }
                in offset1..<offset2 -> {
                    checkCosmetic("netherite_sword", killSoundMessage[pickNum - offset1], "킬 사운드", true)
                }
                in offset2..<offset3 -> {
                    checkCosmetic("clock", messageType[pickNum - offset2]
                        .replace("%style%", symmetry.getOrNull(applyStyle[player.uniqueId]?: -1)?: "")
                        .replace("%rank%", getPlayerRankPrefix(player))
                        .replace("%name%", player.name)
                    , "접속 메시지")
                }
                in offset3..<maxAll -> {
                    checkCosmetic("name_tag", symmetry[MAX_STYLE + (pickNum - offset3)], "칭호")
                }
            }
            return
        }

        if (pickType == PickType.CPVP) {
            val itemByCpvp = itemByCpvp(pickNum)

            gui.setItem(slot, getItem(
                itemByCpvp[0],
                "&f&l${itemByCpvp[1]} &e&l아이템을 획득 하였습니다!",
                listOf("", "&8${resultList}")
            ))
            player.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 1f, 1f)
            return
        }

        if (pickType == PickType.EXP) {
            gui.setItem(slot, getItem(
                "experience_bottle",
                "&a&l20 경험치를 획득하였습니다!",
                listOf("", "&8${resultList}")
            ))
            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
            return
        }
    }

    private fun itemByCpvp(n: Int): List<String> {
        return when(n) {
            0 -> listOf("end_crystal", "엔드 크리스탈")
            1 -> listOf("obsidian", "흑요석")
            2 -> listOf("totem_of_undying", "불사의 토템")
            3 -> listOf("golden_apple", "황금 사과")
            4 -> listOf("ender_pearl", "엔더 진주")
            5 -> listOf("respawn_anchor", "리스폰 정박기")
            6 -> listOf("glowstone", "발광석")
            7 -> listOf("arrow", "화살")

            else -> listOf("", "")
        }
    }

    private fun hasEquip(
        id: Int,
        slot: Int,
        player: Player,
        gui: Inventory,
        holder: CashHolder
    ) {
        holder.isEquip[id] = !holder.isEquip[id]

        gui.setItem(slot, gui.getItem(slot)!!.apply {
            if (holder.isEquip[id]) addUnsafeEnchantment(Enchantment.PROTECTION, 1)
            else removeEnchantment(Enchantment.PROTECTION)
        })

        player.playSound(player.location, Sound.ITEM_ARMOR_EQUIP_CHAIN, 1f, 1f)
    }

    private fun playMusic(
        id: Int,
        player: Player,
        holder: CashHolder
    ) {
        val location = player.location
        val resultItem = holder.resultValue[id].split("|").toTypedArray()
        val pickType = PickType.valueOf(resultItem[0])
        val pickNum = resultItem[1].toInt()

        if (pickType == PickType.COSMETIC) {
            when(pickNum) {
                in 0..<offset1 -> player.playSound(location, soundType[pickNum], 1f, soundPitch[pickNum])
                in offset1..<offset2 -> player.playSound(location, soundTypeKill[pickNum - offset1], 1f, soundPitchKill[pickNum - offset1])
            }
        }
    }
}