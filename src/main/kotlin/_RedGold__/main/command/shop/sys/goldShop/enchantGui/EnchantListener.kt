package _RedGold__.main.command.shop.sys.goldShop.enchantGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Material.ENCHANTED_BOOK
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class EnchantListener(private val plugin: JavaPlugin) : Listener {
    private fun buy(player: Player, enchantment: Enchantment, level: Int, name: String, removeGold: Long, itemNumber: Long) {
        val target = ItemStack(ENCHANTED_BOOK)

        val meta = target.itemMeta as EnchantmentStorageMeta
        meta.addStoredEnchant(enchantment, level, true)
        target.setItemMeta(meta)

        val gold = getData(plugin, player, "gold").toLong()

        if (gold >= removeGold * itemNumber) {
            for (i in 0 until itemNumber) player.inventory.addItem(target)

            player.sendMessage(gc("&a${name}을(를) ${itemNumber}개 구매했습니다."))
            saveData(plugin, player, "gold", gold - (removeGold * itemNumber))
            addHoldGold(plugin, removeGold * itemNumber)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        } else {
            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(removeGold * itemNumber - gold).toFormat()}골드"))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is EnchantHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as EnchantHolder
            val clickType = event.click
            val slot = event.slot
            val page = holder.page
            event.isCancelled = true

            val buyData: List<Long> = listOf(
                450000, 450000, 550000, 450000, 550000, 450000, 550000, 500000, 550000, 600000,
                500000, 550000, 550000, 500000, 550000, 550000, 450000, 550000, 500000, 450000,
                450000, 700000, 450000, 550000, 550000, 500000, 650000, 450000, 500000, 550000,
                500000, 650000, 550000, 500000, 500000, 500000, 550000, 500000, 550000, 500000
            )

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                when (page) {
                    1 -> {
                        when (slot) {
                            10 -> buy(player, Enchantment.AQUA_AFFINITY, 1, "친수성", buyData[0], itemNumber)
                            11 -> buy(player, Enchantment.BANE_OF_ARTHROPODS, 5, "살충 V", buyData[1], itemNumber)
                            12 -> buy(player, Enchantment.BLAST_PROTECTION, 4, "폭발로부터 보호 IV", buyData[2], itemNumber)
                            13 -> buy(player, Enchantment.BREACH, 4, "격파 IV", buyData[3], itemNumber)
                            14 -> buy(player, Enchantment.CHANNELING, 1, "집전", buyData[4], itemNumber)
                            15 -> buy(player, Enchantment.DENSITY, 5, "육중 V", buyData[5], itemNumber)
                            16 -> buy(player, Enchantment.DEPTH_STRIDER, 3, "물갈퀴 III", buyData[6], itemNumber)

                            19 -> buy(player, Enchantment.EFFICIENCY, 5, "효율 V", buyData[7], itemNumber)
                            20 -> buy(player, Enchantment.FEATHER_FALLING, 4, "가벼운 착지 IV", buyData[8], itemNumber)
                            21 -> buy(player, Enchantment.FIRE_ASPECT, 2, "발화 II", buyData[9], itemNumber)
                            22 -> buy(player, Enchantment.FIRE_PROTECTION, 4, "화염으로부터 보호 IV", buyData[10], itemNumber)
                            23 -> buy(player, Enchantment.FLAME, 1, "화염", buyData[11], itemNumber)
                            24 -> buy(player, Enchantment.FORTUNE, 3, "행운 III", buyData[12], itemNumber)
                            25 -> buy(player, Enchantment.FROST_WALKER, 2, "차가운 걸음 II", buyData[13], itemNumber)

                            35 -> EnchantGui().openGui(player, page + 1)
                        }
                        return
                    }
                    2 -> {
                        when (slot) {
                            10 -> buy(player, Enchantment.IMPALING, 5, "찌르기 V", buyData[14], itemNumber)
                            11 -> buy(player, Enchantment.INFINITY, 1, "무한", buyData[15], itemNumber)
                            12 -> buy(player, Enchantment.KNOCKBACK, 2, "밀치기 II", buyData[16], itemNumber)
                            13 -> buy(player, Enchantment.LOOTING, 3, "약탈 III", buyData[17], itemNumber)
                            14 -> buy(player, Enchantment.LOYALTY, 3, "충성 III", buyData[18], itemNumber)
                            15 -> buy(player, Enchantment.LUCK_OF_THE_SEA, 3, "바다의 행운 III", buyData[19], itemNumber)
                            16 -> buy(player, Enchantment.LURE, 3, "미끼 III", buyData[20], itemNumber)

                            19 -> buy(player, Enchantment.MENDING, 1, "수선", buyData[21], itemNumber)
                            20 -> buy(player, Enchantment.MULTISHOT, 1, "다중 발사", buyData[22], itemNumber)
                            21 -> buy(player, Enchantment.PIERCING, 4, "관통 IV", buyData[23], itemNumber)
                            22 -> buy(player, Enchantment.POWER, 5, "힘 V", buyData[24], itemNumber)
                            23 -> buy(player, Enchantment.PROJECTILE_PROTECTION, 4, "발사체로부터 보호 IV", buyData[25], itemNumber)
                            24 -> buy(player, Enchantment.PROTECTION, 4, "보호 IV", buyData[26], itemNumber)
                            25 -> buy(player, Enchantment.PUNCH, 2, "밀어내기 II", buyData[27], itemNumber)

                            27 -> EnchantGui().openGui(player, page - 1)
                            35 -> EnchantGui().openGui(player, page + 1)
                        }
                        return
                    }
                    else -> {
                        when (slot) {
                            10 -> buy(player, Enchantment.QUICK_CHARGE, 1, "빠른 장전 III", buyData[28], itemNumber)
                            11 -> buy(player, Enchantment.RESPIRATION, 5, "호흡 III", buyData[29], itemNumber)
                            12 -> buy(player, Enchantment.RIPTIDE, 4, "급류 III", buyData[30], itemNumber)
                            13 -> buy(player, Enchantment.SHARPNESS, 4, "날카로움 V", buyData[31], itemNumber)
                            14 -> buy(player, Enchantment.SILK_TOUCH, 1, "섬세한 손길", buyData[32], itemNumber)
                            15 -> buy(player, Enchantment.SMITE, 5, "강타 V", buyData[33], itemNumber)
                            16 -> buy(player, Enchantment.SOUL_SPEED, 3, "영혼 가속 III", buyData[34], itemNumber)

                            19 -> buy(player, Enchantment.SWEEPING_EDGE, 3, "휩쓸기 III", buyData[35], itemNumber)
                            20 -> buy(player, Enchantment.SWIFT_SNEAK, 3, "신속한 잠행 III", buyData[36], itemNumber)
                            21 -> buy(player, Enchantment.THORNS, 3, "가시 III", buyData[37], itemNumber)
                            22 -> buy(player, Enchantment.UNBREAKING, 3, "내구성 III", buyData[38], itemNumber)
                            23 -> buy(player, Enchantment.WIND_BURST, 3, "돌풍 III", buyData[39], itemNumber)

                            27 -> EnchantGui().openGui(player, page - 1)
                        }
                        return
                    }
                }
            }
        }
    }
}