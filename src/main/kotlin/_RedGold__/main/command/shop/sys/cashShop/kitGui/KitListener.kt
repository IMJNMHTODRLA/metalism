package _RedGold__.main.command.shop.sys.cashShop.kitGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.itemDamage
import _RedGold__.main.function.Gui.itemPotion
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.ShulkerBox
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType

@RequireJavaPlugin
@RequireListener
class KitListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is KitHolder) {
            val player = event.whoClicked as Player
            val gui = event.inventory
            val holder = gui.holder as KitHolder
            val clickType = event.click
            val slot = event.slot
            val buyTimes = holder.buyTimes
            val isPreviewing = holder.isPreviewing
            event.isCancelled = true

            val itemList: List<List<ItemStack>> = listOf(
                listOf(
                    getItem("netherite_sword").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
                        addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_axe").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_pickaxe").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.EFFICIENCY, 5)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_helmet").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_chestplate").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_leggings").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_boots").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 4)
                        addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    },
                    getItem("shield").apply {
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    },
                    getItem("splash_potion", "&f&l투척용 물약").apply {itemMeta = itemPotion(itemMeta, PotionEffectType.SPEED, 180, 0)},
                    getItem("potion", "&f&l물약").apply {itemMeta = itemPotion(itemMeta, PotionEffectType.STRENGTH, 180, 0)},
                    getItem("golden_apple").apply {amount = 64},
                    getItem("experience_bottle").apply {amount = 64},
                    getItem("golden_carrot").apply {amount = 64},
                    getItem("tnt").apply {amount = 64},
                    getItem("cobweb").apply {amount = 64},
                    getItem("water_bucket"),
                    getItem("fishing_rod").apply {addUnsafeEnchantment(Enchantment.KNOCKBACK, 1)},
                ), //PVP 키트
                listOf(
                    getItem("netherite_sword").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
                        addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3)
                        addUnsafeEnchantment(Enchantment.KNOCKBACK, 1)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_axe").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.SHARPNESS, 5)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_pickaxe").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.EFFICIENCY, 5)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_helmet").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_chestplate").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_leggings").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.BLAST_PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    }, getItem("netherite_boots").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.PROTECTION, 4)
                        addUnsafeEnchantment(Enchantment.FEATHER_FALLING, 4)
                        addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    },
                    getItem("obsidian").apply {amount = 64},
                    getItem("respawn_anchor").apply {amount = 64},
                    getItem("ender_pearl").apply {amount = 16},
                    getItem("golden_apple").apply {amount = 64},
                    getItem("end_crystal").apply {amount = 64},
                    getItem("glowstone").apply {amount = 64},
                    getItem("experience_bottle").apply {amount = 64},
                    getItem("golden_carrot").apply {amount = 64},
                    getItem("bow").apply {
                        itemMeta = itemDamage(itemMeta, (type.maxDurability * 0.3).toInt())
                        addUnsafeEnchantment(Enchantment.POWER, 5)
                        addUnsafeEnchantment(Enchantment.FLAME, 1)
                        addUnsafeEnchantment(Enchantment.PUNCH, 2)
                        addUnsafeEnchantment(Enchantment.UNBREAKING, 3)
                        addUnsafeEnchantment(Enchantment.MENDING, 1)
                    },
                    getItem("water_bucket"),
                    getItem("splash_potion", "&f&l투척용 물약").apply {itemMeta = itemPotion(itemMeta, PotionEffectType.SPEED, 180, 0)}
                ), //CPVP 키트
                listOf(
                    getItem("glass_bottle").apply {amount = 64},
                    getItem("nether_wart").apply {amount = 32},
                    getItem("blaze_powder").apply {amount = 32},
                    getItem("rabbit_foot").apply {amount = 64},
                    getItem("glistering_melon_slice").apply {amount = 64},
                    getItem("pufferfish").apply {amount = 64},
                    getItem("magma_cream").apply {amount = 64},
                    getItem("gunpowder").apply {amount = 64},
                    getItem("glowstone_dust").apply {amount = 64},
                    getItem("sugar").apply {amount = 64},
                    getItem("golden_carrot").apply {amount = 64},
                    getItem("dragon_breath").apply {amount = 32}
                ), //물약 키트
                listOf(
                    getItem("fire_charge").apply {amount = 64},
                    getItem("egg").apply {amount = 64},
                    getItem("snowball").apply {amount = 16},
                    getItem("snowball").apply {amount = 16},
                    getItem("snowball").apply {amount = 16},
                    getItem("snowball").apply {amount = 16},
                    getItem("wind_charge").apply {amount = 32},
                    getItem("cobweb").apply {amount = 64},
                    getItem("cobweb").apply {amount = 64},
                    getItem("splash_potion", "&f&l투척용 물약").apply {
                        itemMeta = itemPotion(itemMeta, PotionEffectType.SLOWNESS, 2, 4, true)
                        itemMeta = itemPotion(itemMeta, PotionEffectType.BLINDNESS, 2, 4, true)
                    },
                    getItem("splash_potion", "&f&l투척용 물약").apply {
                        itemMeta = itemPotion(itemMeta, PotionEffectType.SLOWNESS, 2, 4, true)
                        itemMeta = itemPotion(itemMeta, PotionEffectType.BLINDNESS, 2, 4, true)
                    },
                    getItem("fishing_rod").apply {addUnsafeEnchantment(Enchantment.KNOCKBACK, 2)},
                    getItem("water_bucket"),
                    getItem("water_bucket"),
                    getItem("lava_bucket")
                ), //방해 키트
            )

            if (isPreviewing) return

            fun buy(id: Int, removeCash: Long) {
                if (buyTimes[id] >= 2) {
                    player.sendMessage(gc("&c더 이상 구매를 할 수 없습니다. 다음 주에 구매해주세요."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                val cash = getData(plugin, player, "cash").toLong()

                if (cash < removeCash) {
                    player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: ${(removeCash - cash).toFormat()}캐시"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "cash", cash - removeCash)
                saveData(plugin, player, "kit_shop/$id", buyTimes[id] + 1)
                addHoldGold(plugin, removeCash * 10_000)

                val typeItem = when (id) {
                    0 -> "red_shulker_box" //PVP 키트
                    1 -> "purple_shulker_box" //CPVP 키트
                    2 -> "orange_shulker_box" //레드스톤 키트
                    else -> "green_shulker_box" //건축 키트
                }

                val title = when (id) {
                    0 -> "PVP 키트" //PVP 키트
                    1 -> "CPVP 키트" //CPVP 키트
                    2 -> "물약 키트" //물약 키트
                    else -> "방해 키트" //함정 키트
                }

                val giveItem = getItem(typeItem, "&f&l$title").apply{
                    val meta = itemMeta as BlockStateMeta
                    val shulker = meta.blockState as ShulkerBox

                    val shulkerInventory = shulker.inventory

                    for (item in itemList[id]) shulkerInventory.addItem(item)

                    shulker.update()
                    meta.blockState = shulker

                    itemMeta = meta
                }

                player.sendMessage(gc("&a키트 구매가 완료되었습니다."))
                player.inventory.addItem(giveItem)

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                KitGui(plugin).openGui(player, 0f)
            }

            fun preview(id: Int) {
                if (isPreviewing) return
                holder.isPreviewing = true
                for (i in 0..26) gui.setItem(i, ItemStack.of(Material.AIR))
                for (i in itemList[id]) gui.addItem(i)
                player.playSound(player.location, Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0, 250)
                    12 -> buy(1, 250)
                    14 -> buy(2, 150)
                    16 -> buy(3, 200)
                }
                return
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    10 -> preview(0)
                    12 -> preview(1)
                    14 -> preview(2)
                    16 -> preview(3)
                }
                return
            }
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder is KitHolder) {
            val player = event.player as Player
            val holder = event.inventory.holder as KitHolder
            val isPreviewing = holder.isPreviewing

            if (isPreviewing) {
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    player.playSound(player.location, Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f)
                    KitGui(plugin).openGui(player, 0f)
                }, 1)
            }
        }
    }
}