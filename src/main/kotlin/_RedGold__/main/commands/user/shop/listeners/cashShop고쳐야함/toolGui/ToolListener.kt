package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui

import _RedGold__.main.Main.Gacha.GACHA_POINT_TO_GOLD_TIMES
import _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui.ToolListener.ToolListenerObject.itemStacks
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class ToolListener(private val plugin: JavaPlugin) : Listener {
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

    object ToolListenerObject {
        val itemId = listOf("fire_pickaxe", "space_warper", "speed_shoes", "capital_totem", "last_chance", "magma_heart")
        const val MAX_NUMBER_ITEM = 5
        lateinit var toolKeyId: NamespacedKey
        lateinit var itemStacks: List<ItemStack>

        fun init(plugin: JavaPlugin) {
            toolKeyId = NamespacedKey(plugin, "custom_item_id")
            itemStacks = listOf(
                getItem("netherite_pickaxe", "&c&l불타는 곡괭이", listOf("",
                    "&f&l스킬: &a&l[자동 제련]",
                    "&f&l광석 채굴 시 원석 대신 주괴가 떨궈집니다.",
                )).apply {
                    val meta = itemMeta as? org.bukkit.inventory.meta.Damageable
                    meta?.setMaxDamage(1800)
                    meta?.persistentDataContainer?.set(toolKeyId, PersistentDataType.STRING, itemId[0])
                    itemMeta = meta
                },

                getItem("netherite_pickaxe", "&0&l공간 왜곡기", listOf("",
                    "&f&l스킬: &0&l[공간 지연]",
                    "&f&l블록을 0.1초 만에 채굴을 하지만, 아이템은 2초 후에 떨궈집니다.",
                )).apply {
                    val meta = itemMeta as? org.bukkit.inventory.meta.Damageable
                    meta?.setMaxDamage(128)
                    meta?.persistentDataContainer?.set(toolKeyId, PersistentDataType.STRING, itemId[1])
                    itemMeta = meta
                },

                getItem("iron_boots", "&b&l배속의 신발", listOf("",
                    "&f&l스킬: &b&l[배속 이동]",
                    "&f&l착용 시 15,000골드 사용과 동시에 내구도 5를 소비하여 신속 IV(10초)가 지급됩니다.",
                )).apply {
                    val meta = itemMeta as? org.bukkit.inventory.meta.Damageable
                    meta?.setMaxDamage(250)
                    meta?.persistentDataContainer?.set(toolKeyId, PersistentDataType.STRING, itemId[2])
                    itemMeta = meta
                },

                getItem("totem_of_undying", "&2&l자본의 토템", listOf("",
                    "&f&l스킬: &b&l[자본 회복]",
                    "&f&l발동 시 100,000 골드를 사용하여 체력 5칸 회복하고 힘 I(15초), 신속 II(25초)가 지급됩니다.(1회용)",
                )).apply {itemMeta = itemMeta?.apply {
                    persistentDataContainer.set(toolKeyId, PersistentDataType.STRING, itemId[3])
                }},

                getItem("totem_of_undying", "&4&l마지막 기회", listOf("",
                    "&f&l스킬: &c&l[아드레날린]",
                    "&f&l발동 시 체력 10칸 회복되고 힘 VI(10초), 재생 V(10초)가 지급되며 10초 후 체력 9칸이 깎이고 속도 감속 IV(10초), 위더 II(30초)가 지급됩니다.(1회용)",
                )).apply {itemMeta = itemMeta?.apply {
                    persistentDataContainer.set(toolKeyId, PersistentDataType.STRING, itemId[4])
                }},

                getItem("fire_charge", "&c&l마그마의 심장", listOf("",
                    "&f&l스킬: &c&l[열기 전달]",
                    "&f&lF키를 이용하여 왼손에 아이템을 들 시 사용하여 5분 동안 불 계열 대미지를 안 받고 엔티티를 공격 시 엔티티가 10초간 불에 붙습니다.(1회용)",
                )).apply {itemMeta = itemMeta?.apply {
                    persistentDataContainer.set(toolKeyId, PersistentDataType.STRING, itemId[5])
                }}
            )
        }
    }

    init {
        ToolListenerObject.init(plugin)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder as? ToolHolder?: return
        if (holder.isPreview) {
            plugin.task(1) {ToolGui().openGui(event.player as Player, 0f)}
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is ToolHolder) {
            val player = event.whoClicked as Player
            val gui = event.inventory
            val holder = gui.holder as ToolHolder
            val clickType = event.click
            val slot = event.slot
            event.isCancelled = true

            fun buy(id: Int, removePoint: Long) {
                val ticketPoint = getData(plugin, player, "ticket/point").toInt()

                if (ticketPoint < removePoint) {
                    player.fail("&c뽑기 포인트가 부족합니다. 필요 뽑기 포인트: ${(removePoint - ticketPoint).toFormat()} 뽑기 포인트")
                    return
                }

                saveData(plugin, player, "ticket/point", ticketPoint - removePoint)
                addHoldGold(plugin, ticketPoint * GACHA_POINT_TO_GOLD_TIMES)
                player.inventory.addItem(itemStacks[id])

                player.good("&a도구 구매가 완료되었습니다.")
                ToolGui().openGui(player, 0f)
            }

            fun preview(id: Int) {
                for (i in 0..35) getItem("magenta_stained_glass_pane", prefix)
                gui.setItem(13, itemStacks[id])
                holder.isPreview = true
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 35)
                    11 -> buy(1, 40)
                    12 -> buy(2, 35)
                    13 -> buy(3, 30)
                    14 -> buy(4, 30)
                    15 -> buy(5, 35)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview(0)
                    11 -> preview(1)
                    12 -> preview(2)
                    13 -> preview(3)
                    14 -> preview(4)
                    15 -> preview(5)
                }
                return
            }
        }
    }
}