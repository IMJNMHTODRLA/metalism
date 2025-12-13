package _RedGold__.main.command.mission.sys.achievementGui

import _RedGold__.main.command.mission.sys.dailyGui.DailyGui
import _RedGold__.main.command.mission.sys.weeklyGui.WeeklyGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


@RequireJavaPlugin
@RequireListener
class AchievementListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is AchievementHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as AchievementHolder
            val slot = event.slot

            val progressList = holder.progressList
            val getList = holder.getList
            val maxList = listOf(200, 200, 300, 12, 24, 50, 300, 10, 100, 10_000, 10_000, 400, 200, 300, 300, 20, 30)

            event.isCancelled = true

            fun c(t: Int, name: String, giveGold: Long? = null, giveItem: List<ItemStack>? = null, giveCash: Long? = null, giveStyle: Int? = null) {
                if (getList[t]) {
                    player.sendMessage(gc("&c이미 보상을 획득 하였습니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                if (progressList[t] < maxList[t]) {
                    player.sendMessage(gc("&c${maxList[t] - progressList[t]}회가 부족합니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                if (giveItem != null) {
                    var emptySlots = 0
                    for (item in player.inventory.storageContents) {
                        if (item == null) emptySlots++
                    }

                    if (giveItem.size > emptySlots) {
                        player.sendMessage(gc("&c인벤토리에 ${giveItem.size}칸 이상의 빈칸이 필요합니다."))
                        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        return
                    }

                    for (gave in giveItem) player.inventory.addItem(gave)
                }

                if (giveGold != null && giveGold != 0L) {
                    saveData(plugin, player, "gold", getData(plugin, player, "gold").toLong() + giveGold)
                    addMakeGold(plugin, giveGold)
                }

                if (giveCash != null && giveCash != 0L) {
                    saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + giveCash)
                    addMakeGold(plugin, giveCash * 10_000)
                }

                if (giveStyle != null) saveData(plugin, player, "style/$giveStyle", 1)

                saveData(plugin, player, "mission/achievement/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))
                Bukkit.broadcastMessage(gc("&6&l${player.name}&f&l님이 &b&l\"$name\" &f&l미션을 클리어하여 보상을 획득 하였습니다!"))

                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                AchievementGui(plugin).openGui(player, 0f)
                return
            }

            when (slot) {
                10 -> c(0, "일일 접속을 200회", 0, listOf(
                    getItem("netherite_pickaxe", "&d&l네더라이트 곡괭이(효율 VII)", listOf("", "&8&l\"일일 접속을 200회\" 미션 보상에서 획득 가능"))
                        .apply {addUnsafeEnchantment(Enchantment.EFFICIENCY, 7)}
                ), 0, 0)
                11 -> c(1, "플레이어 처치를 200회", 0, listOf(
                    getItem("netherite_sword", "&d&l네더라이트 검(날카로움 VI)", listOf("", "&8&l\"플레이어 처치를 80회\" 미션 보상에서 획득 가능"))
                        .apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 6)}
                ), 0, 1)
                12 -> c(2, "흑요석 설치를 300회", 0, listOf(getItem("end_crystal").apply {amount = 64}), 10)
                13 -> c(3, "월간 상점 아이템 구매를 12회", 0, listOf(getItem("enchanted_golden_apple").apply {amount = 3}), 200)
                14 -> c(4, "월간 상점 아이템 구매를 24회", 0, null, 2000, 2)
                15 -> c(5, "모루 손상을 50회", 0, listOf(
                    getItem("experience_bottle").apply {amount = 64},
                    getItem("experience_bottle").apply {amount = 64}
                ), 10)
                16 -> c(6, "TNT 점화를 300회", 0, listOf(getItem("creeper_spawn_egg").apply {amount = 16}), 20)


                19 -> c(7, "마법이 부여된 황금 사과 섭취를 10회", 0, listOf(
                    getItem("netherite_pickaxe", "&d&l네더라이트 곡괭이(행운 V)", listOf("", "&8&l\"마법이 부여된 황금 사과 섭취를 10회\" 미션 보상에서 획득 가능"))
                        .apply {addUnsafeEnchantment(Enchantment.FORTUNE, 5)}
                ))
                20 -> c(8, "엔더진주 사용을 100회", 200_000, listOf(getItem("elytra"), getItem("elytra")))
                21 -> c(9, "블록 설치를 10,000회", 2_000_000, null, 500, 3)
                22 -> c(10, "블록 파괴를 10,000회", 2_500_000, null, 550, 4)
                23 -> c(11, "엔드 수정 폭팔을 400회", 0, listOf(getItem("creeper_spawn_egg").apply {amount = 18}), 20)
                24 -> c(12, "불사의 토템 발동을 200회", 0, listOf(
                    getItem("netherite_leggings", "&d&l네더라이트 레깅스(폭팔로부터 보호 V)", listOf("", "&8&l\"불사의 토템 발동을 200회\" 미션 보상에서 획득 가능"))
                        .apply {addUnsafeEnchantment(Enchantment.BLAST_PROTECTION, 5)}
                ))
                25 -> c(13, "황금 사과 섭취를 300회", 0, listOf(getItem("enchanted_golden_apple")), 30)


                28 -> c(14, "위더 처치를 20회", 300_000, listOf(
                    getItem("netherite_sword", "&d&l네더라이트 검(강타 VI)", listOf("", "&8&l\"위더 처치를 20회\" 미션 보상에서 획득 가능"))
                        .apply {addUnsafeEnchantment(Enchantment.SMITE, 6)}
                ))
                29 -> c(15, "위더 처치를 30회", 2_000_000)

                48 -> DailyGui(plugin).openGui(player)
                49 -> WeeklyGui(plugin).openGui(player)
                50 -> AchievementGui(plugin).openGui(player)
            }
        }
    }
}