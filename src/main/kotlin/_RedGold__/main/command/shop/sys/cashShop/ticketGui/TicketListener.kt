package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
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

@RequireJavaPlugin
@RequireListener
class TicketListener(private val plugin: JavaPlugin) : Listener {
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

    private val maxDeath = 13
    private val maxKill = 13
    private val maxJoin = 13
    private val maxStyle = 14

    enum class CosmeticType(
        val min: Int,
        val max: Int
    ) {
        KILL(0, 874),
        DEATH(875, 1749),
        JOIN(1750, 2249),
        STYLE(2250, 2499);
        //0~2499

        companion object {
            fun fromRandom(value: Int): CosmeticType {
                return entries.first { value in it.min..it.max }
            }
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is TicketHolder) {
            val player = event.whoClicked as Player
            val gui = event.inventory
            val holder = gui.holder as TicketHolder
            val clickType = event.click
            val slot = event.slot
            val isRoulette = holder.isRoulette

            val buyTimes = getData(plugin, player, "ticket/buy").toInt()
            val getTicket = getData(plugin, player, "ticket/get").toInt()

            event.isCancelled = true

            if (slot == 13 && !isRoulette) {
                if (clickType == ClickType.LEFT) {
                    if (buyTimes >= 4) {
                        player.fail("&c더 이상 구매를 할 수 없습니다. 다음 주에 구매해주세요.")
                        return
                    }

                    val cash = getData(plugin, player, "cash").toLong()

                    if (cash < 150) {
                        player.fail("&c캐시가 부족합니다. 필요 캐시: ${(150 - cash).toFormat()}캐시")
                        return
                    }

                    saveData(plugin, player, "cash", cash - 150)
                    addHoldGold(plugin, 1_500_000)

                    saveData(plugin, player, "ticket/buy", buyTimes + 1)
                    saveData(plugin, player, "ticket/get", getTicket + 1)
                    player.good("&a뽑기권 구매했습니다.")

                    TicketGui(plugin).openGui(player, 0f)
                } else if (clickType == ClickType.RIGHT) {
                    if (getTicket <= 0) {
                        player.fail("&c뽑기권이 부족합니다.")
                        return
                    }

                    saveData(plugin, player, "ticket/get", getTicket - 1)
                    holder.isRoulette = true

                    val background = getItem(
                        "magenta_stained_glass_pane",
                        prefix
                    )

                    for (i in 0 until gui.size) gui.setItem(i, background)

                    val resultValue: MutableList<String> = mutableListOf()
                    for (i in 0..4) {
                        val successType = random.nextInt(100)
                        //0~74 = fail, 75~99 = success
                        val cosmeticType = CosmeticType.fromRandom(random.nextInt(2500))
                        //00~49 = 칭호, 50~149 = 접속, 150~324, 킬, 325~499
                        val cosmeticNum = when(cosmeticType) {
                            CosmeticType.STYLE -> random.nextInt(maxStyle)
                            CosmeticType.JOIN -> random.nextInt(1, maxJoin)
                            CosmeticType.KILL -> random.nextInt(1, maxKill)
                            CosmeticType.DEATH -> random.nextInt(1, maxDeath)
                        }

                        val all = "$successType|${cosmeticType.name}|$cosmeticNum"
                        resultValue.add(all)

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
                return
            }

            if (slot == 22 && isRoulette) {
                if (false in holder.isOpen) {
                    player.fail("모든 치장품을 뽑아주세요.")
                    return
                }
                holder.isRoulette = false

                player.closeInventory()

                var giveGold = 0L
                var getCosmetic = 0

                for (i in 0..4) {
                    val result = holder.resultValue[i].split("|").toTypedArray()
                    //0 = 성공 또는 실패
                    //1 = 치장품 타입
                    //2 = 치장품 번호
                    //00~49 = 칭호, 50~149 = 접속, 150~324, 킬, 325~499
                    val successType = result[0].toInt()
                    val cosmeticType = CosmeticType.valueOf(result[1])
                    val cosmeticNum = result[2].toInt()

                    val isEquip = holder.isEquip[i]
                    val isHas = holder.isHas[i]

                    if (successType < 75 || isHas || !isEquip) {
                        giveGold += 100_000
                        continue
                    }

                    getCosmetic++

                    when(cosmeticType) {
                        CosmeticType.STYLE -> saveData(plugin, player, "style/$cosmeticNum", 1)
                        CosmeticType.JOIN -> saveData(plugin, player, "join_message", cosmeticNum)
                        CosmeticType.DEATH -> saveData(plugin, player, "death_sound", cosmeticNum)
                        CosmeticType.KILL -> saveData(plugin, player, "kill_sound", cosmeticNum)
                    }
                }

                player.good("&a&l$giveGold 골드, 총 얻은 치장품: ${getCosmetic}개")
            }

            if (slot in 11..15 && isRoulette) {
                val id = when(slot) {
                    11 -> 0
                    12 -> 1
                    13 -> 2
                    14 -> 3
                    15 -> 4
                    else -> -1
                }

                if (holder.isOpen[id]) {
                    val resultItem = holder.resultValue[id].split("|").toTypedArray()
                    val successType = resultItem[0].toInt()

                    if (successType < 75) return

                    if (clickType == ClickType.LEFT) hasEquip(id, slot, player, gui, holder)
                    else if (clickType == ClickType.RIGHT) playMusic(id, player, holder)
                    return
                }

                openRoulette(plugin, id, slot, player, gui, holder)
            }
        }
    }

    private fun playMusic(
        id: Int,
        player: Player,
        holder: TicketHolder
    ) {
        val resultItem = holder.resultValue[id].split("|").toTypedArray()
        //0 = 성공 또는 실패
        //1 = 치장품 타입
        //2 = 치장품 번호
        //00~49 = 칭호, 50~149 = 접속, 150~324, 킬, 325~499
        val cosmeticType = CosmeticType.valueOf(resultItem[1])
        val cosmeticNum = resultItem[2].toInt()

        if (cosmeticType == CosmeticType.DEATH) player.playSound(player.location, soundType[cosmeticNum], 1f, soundPitch[cosmeticNum])
        else if (cosmeticType == CosmeticType.KILL) player.playSound(player.location, soundTypeKill[cosmeticNum], 1f, soundPitchKill[cosmeticNum])
    }

    private fun hasEquip(
        id: Int,
        slot: Int,
        player: Player,
        gui: Inventory,
        holder: TicketHolder
    ) {
        holder.isEquip[id] = !holder.isEquip[id]

        val cosmeticType = CosmeticType.valueOf(holder.resultValue[id].split("|").toTypedArray()[1])
        if (cosmeticType == CosmeticType.STYLE) return

        gui.setItem(slot, gui.getItem(slot)!!.apply {
            if (holder.isEquip[id]) addUnsafeEnchantment(Enchantment.PROTECTION, 0)
            else removeEnchantment(Enchantment.PROTECTION)
        })

        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }

    private fun openRoulette(
        plugin: JavaPlugin,
        id: Int,
        slot: Int,
        player: Player,
        gui: Inventory,
        holder: TicketHolder,
    ) {
        if (holder.isOpen[id]) return

        holder.isOpen[id] = true
        val resultList = holder.resultValue[id]
        val resultItem = holder.resultValue[id].split("|").toTypedArray()
        //0 = 성공 또는 실패
        //1 = 치장품 타입
        //2 = 치장품 번호
        //00~49 = 칭호, 50~149 = 접속, 150~324, 킬, 325~499
        val successType = resultItem[0].toInt()
        val cosmeticType = CosmeticType.valueOf(resultItem[1])
        val cosmeticNum = resultItem[2].toInt()

        fun checkCosmetic(
            item: String, prefix: String, equalMessage: String, path: String, equalVal: Int,
            setDescription: List<String> = listOf("", "&8${resultList}")
        ) {
            gui.setItem(slot, getItem(
                item,
                "$prefix &a&l$equalMessage 치장품을 획득 하였습니다!",
                setDescription
            ))

            if (getData(plugin, player, path).toInt() == equalVal) {
                holder.isHas[id] = true
                player.sendMessage(gc("&e&l위 $equalMessage 치장품을 보유를 하고 있어 100,000 골드로 변경됩니다."))
                player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1f, 1f)
                return
            }
            player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 2f)
        }

        if (successType < 75) {
            gui.setItem(slot, getItem(
                "coal",
                "&c&l뽑기에 실패 하였습니다....",
                listOf("&8${resultList}")
            ))
            player.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f)
        } else {
            when(cosmeticType) {
                CosmeticType.STYLE -> {
                    val getStyle = MAX_STYLE + cosmeticNum
                    checkCosmetic("name_tag", symmetry[getStyle], "칭호", "style/$getStyle", 1)
                }
                CosmeticType.JOIN -> {
                    checkCosmetic(
                        "clock",
                        messageType[cosmeticNum]
                            .replace("%style%", "")
                            .replace("%rank%", "")
                            .replace("%name%", "Player"),
                        "접속 메시지", "join_message", cosmeticNum,
                        listOf(
                            "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.",
                            "",
                            "&8${resultList}"
                        )
                    )
                }
                CosmeticType.KILL -> {
                    checkCosmetic(
                        "netherite_sword", killSoundMessage[cosmeticNum],
                        "킬 사운드", "kill_sound", cosmeticNum,
                        listOf(
                            "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.",
                            "&e&l우클릭 시 소리를 미리 들어 볼 수 있습니다.",
                            "",
                            "&8${resultList}"
                        )
                    )
                }
                CosmeticType.DEATH -> {
                    checkCosmetic(
                        "redstone", deathSoundMessage[cosmeticNum],
                        "사망 사운드", "death_sound", cosmeticNum,
                        listOf(
                            "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.",
                            "&e&l우클릭 시 소리를 미리 들어 볼 수 있습니다.",
                            "",
                            "&8${resultList}"
                        )
                    )
                }
            }
        }
    }
}