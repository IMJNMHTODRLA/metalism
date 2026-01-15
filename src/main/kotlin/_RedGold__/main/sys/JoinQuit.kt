package _RedGold__.main.sys

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defDataUuid
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.hasDataUuid
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.byteSave
import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import _RedGold__.main.sys.ExpMultiple.ExpMultipleData.normalPlayer
import _RedGold__.main.sys.ExpMultiple.ExpMultipleData.subPlayer
import _RedGold__.main.sys.JoinQuit.JoinMessage.messageType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.nio.ByteBuffer
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireJavaPlugin
@RequireListener
class JoinQuit(private val plugin: JavaPlugin) : Listener {
    private val prefix = """
        ${rgb("2444FC")}§l[
        ${rgb("2A48FC")}§l* 
        ${rgb("3651FD")}§lM
        ${rgb("3C55FD")}§lE
        ${rgb("4359FD")}§lT
        ${rgb("495DFD")}§lA
        ${rgb("4F61FD")}§lL
        ${rgb("5565FD")}§lI
        ${rgb("5B6AFE")}§lS
        ${rgb("616EFE")}§lM 
        ${rgb("6D76FE")}§lB
        ${rgb("747AFE")}§lA
        ${rgb("7A7EFE")}§lN 
        ${rgb("8687FF")}§l*
        ${rgb("8C8BFF")}§l]
    """.trimIndent().replace("\n", "")

    object JoinMessage {
        val messageType = listOf(
            "&8[&a+&8] %style%%rank% %name% &e님이 서버에 접속했습니다.",
            "&8[&a+&8] %style%%rank% %name% &e님이 서버에 나타났습니다.",
            "&8[&a+&8] %style%%rank% %name% &7&lJoined",
            "&8[&b✦&8] %style%%rank% %name% &e님 환영합니다!",
            "&8[&a»&8] %style%%rank% %name% &e님이 &a&l온라인&e으로 전환했습니다.",
            "&8[&7»&8] &fJo&ki&fned with %style%%rank% %name%",
            "&8[&b»&8] %style%%rank% %name% &b&l님이 서버에 등장 하였습니다.",
            "&8[&a+&8] %style%%rank% %name% 님이 서버에 &a&l생성되었습니다.",
            "&8[&7?&8] %style%%rank% %name% 님이 서버에 &8&l접속...했나요?",
            "&8[&a!&8] %style%%rank% %name% 님이 게임에 참여하였습니다!",
            "&8[&a+&8] %style%%rank% %name% 님이 &2&l마인크래프트 세상에 들어왔습니다.",
            "&8[&a+&8] %style%%rank% %name% 님이 서버에 접속하였습니다. &a&l환영해주세요!",
            "&8[&a+&8] %style%%rank% %name%",
            "&8+ %name%"
        )
    }

    private val isGiveSub: MutableList<UUID> = mutableListOf()
    private val isSendSub: MutableList<UUID> = mutableListOf()

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        val playerName = player.name
        val uuid = player.uniqueId

        val now = System.currentTimeMillis() / 1000
        val exp = monthlySubData[uuid]?: 0
        val isSub = exp <= now

        if (!player.hasPlayedBefore()) {
            addMakeGold(plugin, 10000L)

            player.inventory.addItem(getItem("iron_sword").apply {
                addEnchantment(Enchantment.UNBREAKING, 2)
                addEnchantment(Enchantment.SHARPNESS, 2)
            })
            player.inventory.addItem(getItem("iron_axe").apply {
                addEnchantment(Enchantment.UNBREAKING, 2)
                addEnchantment(Enchantment.EFFICIENCY, 3)
            })
            player.inventory.addItem(getItem("iron_pickaxe").apply {
                addEnchantment(Enchantment.UNBREAKING, 2)
                addEnchantment(Enchantment.EFFICIENCY, 3)
            })
            player.inventory.addItem(getItem("iron_shovel").apply {
                addEnchantment(Enchantment.UNBREAKING, 2)
                addEnchantment(Enchantment.EFFICIENCY, 3)
            })

            player.inventory.setItem(EquipmentSlot.HEAD, getItem("iron_helmet"))
            player.inventory.setItem(EquipmentSlot.CHEST, getItem("iron_chestplate"))
            player.inventory.setItem(EquipmentSlot.LEGS, getItem("iron_leggings"))
            player.inventory.setItem(EquipmentSlot.FEET, getItem("iron_boots"))

            player.inventory.addItem(ItemStack(Material.BREAD, 64))
        }

        val style = getData(plugin, player, "style/apply").toInt()
        val styleMessage = symmetry.getOrNull(style)?: ""
        val message = messageType[getData(plugin, player, "join_message").toInt()]

        val formatStyle = if (styleMessage.isNotEmpty()) "$styleMessage " else ""

        event.joinMessage = gc(message
            .replace("%style%", formatStyle)
            .replace("%rank%", " ${getPlayerRankPrefix(player)}")
            .replace("%name%", playerName))

        applyStyle[uuid] = style

        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        if (isSub) return

        if (isGiveSub.contains(uuid)) return
        isGiveSub.add(uuid)

        val giveCash = if (LocalDate.now().dayOfWeek == DayOfWeek.MONDAY) 105L else 5L

        saveDataUuid(plugin, uuid, "cash", getDataUuid(plugin, uuid, "cash") + giveCash)
        saveDataUuid(plugin, uuid, "gold", getDataUuid(plugin, uuid, "gold") + 50000L)
        player.level += 1

        var allGiveGold = 0L

        for (online in Bukkit.getOnlinePlayers()) {
            if (online.uniqueId == uuid) continue

            val giveGold = if (online.hasPermission("Main.plus")) 5000L else 4000L
            saveDataUuid(plugin, uuid, "gold", getDataUuid(plugin, uuid, "gold") + giveGold)
            allGiveGold += giveGold

            player.good("&a&l월정액 플레이어가 들어와 ${giveGold.toFormat()} 골드를 받았습니다.")
        }

        addMakeGold(plugin, 50_000 + (giveCash * 10000) + allGiveGold)

        subPlayer += 0.25f
        normalPlayer += 0.1f

        if (exp - now <= 172800 && !isSendSub.contains(uuid)) {
            val remainingDays = "%.1f".format((exp - now).toDouble() / 86400.0)

            player.sendMessage(gc("&c&l월정액 혜택이 종료되기 까지 &4&l${remainingDays}일&c&l남았습니다!"))
            player.sendMessage(gc("&c&l월정액 혜택 유지를 원할 시 ${remainingDays}일 이내 후원을 해주시면 됩니다!"))
            isSendSub.add(uuid)
        }
    }

    @EventHandler
    fun onPreJoin(event: AsyncPlayerPreLoginEvent) {
        val uuid = event.uniqueId

        if (hasDataUuid(plugin, uuid, "ban")) {
            val banData = getDataUuid(plugin, uuid, "ban").split(";")
            if (banData[0] == "perm") {
                event.kickMessage = (gc("""
                    
                    $prefix
                    
                    &f&l당신은 이 서버에서 &4&l영구적&c&l으로 서버 이용이 &4&l제한 되었습니다.
                    &7&l사유: ${banData[2]}
                    
                    &7&l항소를 하실려면 사용자 명 &b&l_al_1s__&7&l로 개인 DM을 보내주세요.
                    &7&l디스코드: &b&lhttps://discord.gg/[초대 코드]
                    
                    &8UUID: $uuid
                """.trimIndent()))

                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                return
            }

            val now = System.currentTimeMillis() / 1000
            var timeRemain = banData[1].toLong() - now

            if (timeRemain > 0) {
                val days: Long = timeRemain / 86400L
                timeRemain %= 86400L
                val hours: Long = timeRemain / 3600L
                timeRemain %= 3600L
                val minutes: Long = timeRemain / 60L
                val seconds: Long = timeRemain % 60L

                event.kickMessage = (gc("""
                    
                    $prefix
                    
                    &f&l당신은 이 서버에서 &c&l${days}일 ${hours}시간 ${minutes}분 ${seconds}초 동안 &4&l일시적&c&l으로 서버 이용이 &4&l제한 되었습니다.
                    &7&l사유: ${banData[2]}
                    
                    &7&l항소를 하실려면 사용자 명 &b&l_al_1s__&7&l로 개인 DM을 보내주세요.
                    &7&l디스코드: &b&lhttps://discord.gg/[초대 코드]
                    
                    &8UUID: $uuid
                """.trimIndent()))

                event.loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
                return
            }
        }

        defDataUuid(plugin, uuid, "gold", 10000)
        defDataUuid(plugin, uuid, "cash", 100)
        defDataUuid(plugin, uuid, "cash_exc", 0)
        defDataUuid(plugin, uuid, "kill", 0)
        defDataUuid(plugin, uuid, "death", 0)
        defDataUuid(plugin, uuid, "boost", 0)

        defDataUuid(plugin, uuid, "gg_color", 0)
        defDataUuid(plugin, uuid, "kill_message", 0)

        for (i in 0..2) {
            defDataUuid(plugin, uuid, "home/save/$i", "n;n;n")
            defDataUuid(plugin, uuid, "home/buy/$i", 0)
        }

        for (i in 0..6) defDataUuid(plugin, uuid, "daily_shop/$i", 0)
        defDataUuid(plugin, uuid, "monthly_shop", 0)

        defDataUuid(plugin, uuid, "style/apply", -1)
        for (i in 0..18) defDataUuid(plugin, uuid, "style/$i", 0)
        for (i in 0..13) defDataUuid(plugin, uuid, "plant_shop/$i", 0)
        for (i in 0..3) defDataUuid(plugin, uuid, "kit_shop/$i", 0)

        defDataUuid(plugin, uuid, "ticket/point", 0)

        defDataUuid(plugin, uuid, "death_sound", 0)
        defDataUuid(plugin, uuid, "kill_sound", 0)
        defDataUuid(plugin, uuid, "join_message", 0)

        //총 치장품 갯수 29개

        //0: &a&l[시간의 연속]
        //1: &4&l[킬러]
        //2: &d&l[컬렉션]
        //3: &8&l[무게 변화]
        //4: &8&l[지각 변동]

        //5: &4&l[미제 사건]
        //6: &d&l[크리스탈]
        //7: &c&l[하드코어]
        //8: &f&l[백업 없음]
        //9: &8&l[솔플]
        //10: &8&l[팀플]
        //11: &c&l[99/0/0]

        for (i in 0..6) {
            defDataUuid(plugin, uuid, "mission/daily/progress/$i", 0)
            defDataUuid(plugin, uuid, "mission/daily/get/$i", 0)
        }
        /*
        일일 접속/0
        일일 상점에서 아이템 구매하기/1
        블록 50번 이상 파괴하기/2
        다이아몬드 20개 캐기/3
        위더 스캘레톤 3마리 처치하기/4
        플레이어에게 하트 5칸 이상의 피해 주기/5
        일일 미션 모두 클리어 하기/6
        */

        for (i in 0..6) {
            defDataUuid(plugin, uuid, "mission/weekly/progress/$i", 0)
            defDataUuid(plugin, uuid, "mission/weekly/get/$i", 0)
        }
        /*
        일주일에 5번 접속하기/0
        플레이어 5명 처치하기/1
        일일 상점에서 아이템 5번 구매하기/2
        위더 스캘레톤 9마리 처치하기/3
        블록 250번 이상 파괴하기/4
        모루 5번 사용하기/5
        주간 미션 모두 클리어/6
        */

        for (i in 0..15) {
            defDataUuid(plugin, uuid, "mission/achievement/progress/$i", 0)
            defDataUuid(plugin, uuid, "mission/achievement/get/$i", 0)
        }
        /*
        누적 접속 150번 이상/0
        플레이어 80명 이상 처치하기/1
        흑요석 300개 이상 설치하기/2
        월간 상점에서 12번 이상 아이템 구매하기/3
        월간 상점에서 24번 이상 아이템 구매하기/4
        모루 200번 이상 사용하기/5
        TNT를 300번 이상 터트리기/6
        인첸트된 황금사과 10번 이상 먹기/7
        엔더진주 300번 이상 사용하기/8
        블록 20,000번 이상 설치하기/9
        블록 50,000번 이상 파괴하기/10
        엔드 수정 400번 이상 터트리기/11
        불사의 토템 200번 이상 터트리기/12
        리스폰 정박기 300번 이상 터트리기/13
        황금 사과 300번 이상 먹기/14
        위더 누적 20마리 처치/15
        위더 누적 30마리 처치/16
        */
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val playerName = player.name

        event.quitMessage = gc("&8[&c-&8] ${getPlayerRankPrefix(player)} $playerName &e님이 서버에서 퇴장했습니다.")
    }
}