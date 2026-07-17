package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.cashGui

import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.deathGui.DeathGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.joinGui.JoinGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.killGui.KillGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.kitGui.KitGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.limitGui.LimitListener
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.styleGui.StyleGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.toolGui.ToolGui
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.toolGui.ToolListener.ToolListenerObject.itemStacks
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
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
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
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

    private val maxTool = 6
    private val maxCpvp = 8

    private val background = getItem(
        "magenta_stained_glass_pane",
        prefix
    )
    private val backgroundGood = getItem(
        "pink_stained_glass_pane",
        prefix
    )

    //enum class PickType( //100.00 -> 10000
    //    val min: Int,
    //    val max: Int
    //) {
    //    ItemLimitBreakA(0, 74),
    //    ItemLimitBreakB(75, 174),
    //    ItemLimitBreakC(175, 299),
    //    TOOL(300, 499),
    //    COSMETIC(500, 699),
    //    CPVP(700, 5299),
    //    EXP(5300, 9999);

    //    companion object {
    //        fun fromRandom(value: Int): PickType {
    //            return entries.first { value in it.min..it.max }
    //        }
    //    }
    //} //패스 때

    enum class PickType( //100.00 -> 10000
        val min: Int,
        val max: Int
    ) {
        ItemLimitBreakA(0, 49),
        ItemLimitBreakB(50, 124),
        ItemLimitBreakC(125, 224),
        TOOL(225, 374),
        COSMETIC(375, 524),
        CPVP(525, 5124),
        EXP(5125, 9999);

        companion object {
            fun fromRandom(value: Int): PickType {
                return entries.first { value in it.min..it.max }
            }
        }
    } //일반

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
                    14 -> ToolGui().openGui(player)

                    21 -> StyleGui(plugin).openGui(player)
                    23 -> JoinGui(plugin).openGui(player)

                    13 -> {
                        val cash = getData(plugin, player, "cash").toLong()

                        if (cash < 80) {
                            player.fail("&c캐시가 부족합니다. 필요 캐시: ${(80 - cash).toFormat()}캐시")
                            return
                        }

                        saveData(plugin, player, "cash", cash - 80)
                        saveData(plugin, player, "ticket/point", getPoint + 1)

                        holder.isRoulette = true
                        for (i in 0 until gui.size) gui.setItem(i, background)

                        val resultValue: MutableList<String> = mutableListOf()
                        var numberOfGoodItem = 0
                        for (i in 0..4) {
                            val pickType = PickType.fromRandom(random.nextInt(10000))
                            val pickNum = when(pickType) {
                                PickType.TOOL -> random.nextInt(maxTool)
                                PickType.COSMETIC -> random.nextInt(maxAll)
                                PickType.CPVP -> random.nextInt(maxCpvp)
                                else -> 0
                            }
                            resultValue.add("${pickType.name}|$pickNum")

                            var isVerySoGood = when {
                                pickType.name.contains("ItemLimitBreak") -> {
                                    numberOfGoodItem++
                                    Triple("ender_chest", "&d&l클릭하여 뽑기 결과 보기(한계 돌파 재화 확정!!!)", true)
                                }
                                pickType == PickType.TOOL || pickType == PickType.COSMETIC -> {
                                    numberOfGoodItem++
                                    Triple("ender_chest", "&d&l클릭하여 뽑기 결과 보기(도구 또는 치장품 확정!!!)", false)
                                }

                                else -> Triple("chest", "&e&l클릭하여 뽑기 결과 보기", false)
                            }

                            if (random.nextInt(10) >= 2) isVerySoGood = Triple("chest", "&e&l클릭하여 뽑기 결과 보기", false)

                            gui.setItem(11 + i, getItem(
                                isVerySoGood.first,
                                isVerySoGood.second
                            ).apply {
                                addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                (isVerySoGood.third) then addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)
                            })
                        }

                        if (numberOfGoodItem >= 3) {
                            plugin.task(1) {
                                for (i in (0..10) + (16..26)) gui.setItem(i, backgroundGood.apply {
                                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                                    addUnsafeEnchantment(Enchantment.PROTECTION, 1)
                                })
                            }
                            plugin.task(11) {
                                for (i in (0..10) + (16..26)) gui.setItem(i, background)
                            }
                            player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f)
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
                    if (!isEquip) continue

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
                        val giveTool = player.inventory.addItem(itemStacks[pickNum])
                        giveTool.values.forEach {player.world.dropItemNaturally(player.location, it)}
                        giveItem++
                        continue
                    }

                    if (pickType.name.contains("ItemLimitBreak")) {
                        val gaveItemId = when(pickType) {
                            PickType.ItemLimitBreakA -> 0
                            PickType.ItemLimitBreakB -> 1
                            PickType.ItemLimitBreakC -> 2

                            else -> 0
                        }

                        val giveLimitBreak = player.inventory.addItem(
                            LimitListener.LimitedBreakItems.itemStacks[gaveItemId]
                        )
                        giveLimitBreak.values.forEach {player.world.dropItemNaturally(player.location, it)}

                        giveItem++
                        continue
                    }
                }

                player.good("&a&l얻은 EXP: ${giveExp}exp, 얻은 치장품: ${getCosmetic}개, 얻은 아이템: ${giveItem}개")
                player.giveExp(giveExp)

                CashGui(plugin).openGui(player)
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

            player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f, 1f)
            plugin.task(20) {
                player.playSound(player.location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f)
            }
        }

        if (pickType.name.contains("ItemLimitBreak")) {
            val itemMetaData = when(pickType) {
                PickType.ItemLimitBreakA -> Pair("dragon_breath", "&d&l[ 무한의 해방자 ]")
                PickType.ItemLimitBreakB -> Pair("echo_shard", "&4&l[ 제약의 인장 ]")
                PickType.ItemLimitBreakC -> Pair("amethyst_shard", "&c&l[ 임계점의 파편 ]")

                else -> Pair("brown_dye", "만일 이 아이템이 떳다면 버그니깐 운영자한테 말해주세요(말 하면 보상 지급 됨)")
            }

            gui.setItem(slot, getItem(
                itemMetaData.first,
                "${itemMetaData.second} &e&l아이템을 획득 하였습니다!",
                listOf("", "&8${resultList}")
            ))

            player.playSound(player.location, Sound.BLOCK_END_PORTAL_SPAWN, 1f, 1f)
            return
        }

        if (pickType == PickType.TOOL) {
            val toolItem = itemByTool(pickNum)

            gui.setItem(slot, getItem(
                toolItem.id,
                "&f&l${toolItem.name} &d&l아이템을 획득 하였습니다!",
                listOf(
                    "",
                    "&f&l스킬: ${toolItem.skill}",
                    "&f&l${toolItem.description}",
                    "&8&l내구도: ${toolItem.durability}",
                    "",
                    "&8${resultList}"
                )
            ))
            player.playSound(player.location, Sound.ENTITY_WITHER_SPAWN, 1f, 1f)
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
            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
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

    private fun itemByTool(n: Int): ToolInfo {
        return when(n) {
            0 -> ToolInfo(
                "netherite_pickaxe", "&c&l불타는 곡괭이", "&a&l[자동 제련]",
                "광석 채굴 시 원석 대신 주괴가 떨궈집니다.", 1800
            )
            1 -> ToolInfo(
                "netherite_pickaxe", "&0&l공간 왜곡기", "&0&l[공간 지연]",
                "블록을 0.1초 만에 채굴을 하지만, 아이템은 2초 후에 떨궈집니다.", 128
            )
            2 -> ToolInfo(
                "iron_boots", "&b&l배속의 신발", "&b&l[배속 이동]",
                "착용 시 15,000골드 사용과 동시에 내구도 5를 소비하여 신속 IV(10초)가 지급됩니다.", 250
            )
            3 -> ToolInfo(
                "totem_of_undying", "&2&l자본의 토템", "&b&l[자본 회복]",
                "발동 시 100,000 골드를 사용하여 체력 5칸 회복하고 힘 I(15초), 신속 II(25초)가 지급됩니다.(1회용)", 1
            )
            4 -> ToolInfo(
                "totem_of_undying", "&4&l마지막 기회", "&c&l[아드레날린]",
                "발동 시 체력 10칸 회복되고 힘 VI(10초), 재생 V(10초)가 지급되며 10초 후 체력 9칸이 깎이고 속도 감속 IV(10초), 위더 II(30초)가 지급됩니다.(1회용)", 1
            )
            5 -> ToolInfo(
                "fire_charge", "&c&l마그마의 심장", "&c&l[열기 전달]",
                "F키를 이용하여 왼손에 아이템을 들 시 사용하여 5분 동안 불 계열 대미지를 안 받고 엔티티를 공격 시 엔티티가 10초간 불에 붙습니다.(1회용)", 1
            )

            else -> ToolInfo("", "", "", "", 0)
        }
    } data class ToolInfo(val id: String, val name: String, val skill: String, val description: String, val durability: Int)

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
        val resultItem = holder.resultValue[id].split("|").toTypedArray()
        val pickType = PickType.valueOf(resultItem[0])
        if (pickType != PickType.COSMETIC) return

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