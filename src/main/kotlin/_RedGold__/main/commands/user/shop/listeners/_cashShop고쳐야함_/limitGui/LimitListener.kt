package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.limitGui

import _RedGold__.main.Main.Gacha.GACHA_POINT_TO_GOLD_TIMES
import _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.limitGui.LimitListener.LimitedBreakItems.itemStacks
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
class LimitListener(private val plugin: JavaPlugin) : Listener {
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

    object LimitedBreakItems {
        val itemId = listOf("infinity_liberator", "seal_restriction", "fragments_critical")
        const val MAX_NUMBER_ITEM = 3
        lateinit var toolKeyId: NamespacedKey
        lateinit var itemStacks: List<ItemStack>

        fun init(plugin: JavaPlugin) {
            toolKeyId = NamespacedKey(plugin, "limited_break_items")
            fun getItemButSetting(item: String, name: String, id: Int): ItemStack {
                return getItem(item, name, listOf("",
                    "&7스킬 한계 해방에 사용되는 &c&l*매우 중요하고 희귀한* &7재화입니다.",
                )).apply {itemMeta = itemMeta?.apply {
                    persistentDataContainer.set(toolKeyId, PersistentDataType.STRING, itemId[id])
                }}
            }

            itemStacks = listOf(
                getItemButSetting("dragon_breath", "&d&l[ 무한의 해방자 ]", 0),
                getItemButSetting("echo_shard", "&4&l[ 제약의 인장 ]", 1),
                getItemButSetting("amethyst_shard", "&c&l[ 임계점의 파편 ]", 2)
            )
        }
    }

    init {
        LimitedBreakItems.init(plugin)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder as? LimitHolder?: return
        if (holder.isPreview) {
            plugin.task(1) {LimitGui().openGui(event.player as Player, 0f)}
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is LimitHolder) {
            val player = event.whoClicked as Player
            val gui = event.inventory
            val holder = gui.holder as LimitHolder
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
                LimitGui().openGui(player, 0f)
            }

            fun preview(id: Int) {
                for (i in 0..35) getItem("magenta_stained_glass_pane", prefix)
                gui.setItem(13, itemStacks[id])
                holder.isPreview = true
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 80)
                    11 -> buy(1, 70)
                    12 -> buy(2, 60)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview(0)
                    11 -> preview(1)
                    12 -> preview(2)
                }
                return
            }
        }
    }
}