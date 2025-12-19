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
import _RedGold__.main.sys.KillRespawn.ChatApply.soundType
import _RedGold__.main.sys.KillRespawn.ChatApply.soundTypeKill
import org.bukkit.Bukkit
import org.bukkit.Sound
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
        STYLE(0, 49),
        JOIN(50, 149),
        KILL(150, 324),
        DEATH(325, 499);

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
                    if (buyTimes >= 10) {
                        player.fail("&c더 이상 구매를 할 수 없습니다. 다음 주에 구매해주세요.")
                        return
                    }

                    val cash = getData(plugin, player, "cash").toLong()

                    if (cash < 100) {
                        player.fail("&c캐시가 부족합니다. 필요 캐시: ${(100 - cash).toFormat()}캐시")
                        return
                    }

                    saveData(plugin, player, "cash", cash - 100)
                    addHoldGold(plugin, 1_000_000)

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
                        val successType = random.nextInt(2)
                        //0 = fail, 1 = success
                        val cosmeticType = CosmeticType.fromRandom(random.nextInt(500))
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

                    holder.resultValue = resultValue
                }
                return
            }

            if (slot in 11..15 && isRoulette) {
                when(slot) {
                    11 -> openRoulette(plugin, 0, slot, player, gui, holder)
                    12 -> openRoulette(plugin, 1, slot, player, gui, holder)
                    13 -> openRoulette(plugin, 2, slot, player, gui, holder)
                    14 -> openRoulette(plugin, 3, slot, player, gui, holder)
                    15 -> openRoulette(plugin, 4, slot, player, gui, holder)
                }
            }
        }
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
            item: String, prefix: String, equalMessage: String, path: String, equalVal: Int, setVal: Int,
            isAddChange: String? = null, setDescription: List<String> = listOf("", "&8${resultList}")
        ) {
            gui.setItem(slot, getItem(
                item,
                "$prefix &a&l$equalMessage 치장품을 획득 하였습니다!",
                setDescription
            ))

            if (getData(plugin, player, path).toInt() == equalVal) {
                player.sendMessage(gc("&e&l위 $equalMessage 치장품을 보유를 하고 있어 100,000 골드로 변경됩니다."))
                player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1f, 1f)
                return
            }
            if (setVal != -99) saveData(plugin, player, path, setVal)
            if (isAddChange != null) holder.isChange[id] = isAddChange
            player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 2f)
        }

        if (successType == 0) {
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
                    checkCosmetic("name_tag", symmetry[getStyle], "칭호", "style/$getStyle", 1, 1)
                }
                CosmeticType.JOIN -> {
                    checkCosmetic(
                        "clock",
                        messageType[cosmeticNum]
                            .replace("%style%", "")
                            .replace("%rank%", "")
                            .replace("%name%", "Player"),
                        "접속 메시지", "join_message", cosmeticNum, -99, "join|$cosmeticNum",
                        listOf(
                            "&c&l장착 안함",
                            "&e&l좌클릭 시 장착을 할 지 말지 선택이 가능합니다.",
                            "",
                            "&8${resultList}"
                        )
                    )
                }
                CosmeticType.KILL -> {
                    checkCosmetic(
                        "netherite_sword", killSoundMessage[cosmeticNum],
                        "킬 사운드", "kill_sound", cosmeticNum, -99, "kill|$cosmeticNum",
                        listOf(
                            "&c&l장착 안함",
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
                        "사망 사운드", "death_sound", cosmeticNum, -99, "death|$cosmeticNum",
                        listOf(
                            "&c&l장착 안함",
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