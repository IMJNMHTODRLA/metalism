package _RedGold__.main.managers.playerData

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.functions.Gui.getItem
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.Node
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

const val DISCORD_INVITE = "https://discord.gg/[초대코드]"
const val YOUTUBE_CHANNEL = "https://유튜브 URL 그거 만들기/" //TODO: URL 그거 만들기
const val SERVER_ADDRESS = "metalism.kro.kr"

const val OVER_WORLD = "world"
const val NETHER_WORLD = "world_nether"
const val END_WORLD = "world_the_end"

const val SPAWN_WORLD = "spawn_a"
val SPAWN_WORLD_LOCATION = Location(
    Bukkit.getWorld(SPAWN_WORLD), 0.5, 100.0, 0.5
)
const val TUTORIAL_WORLD = "tutorial_a"

val PREFIX = "${rgb("2444FC")}§l[${rgb("304CFC")}§lM${rgb("3B54FD")}§lE${rgb("475CFD")}§lT${rgb("5264FD")}§lA${rgb("5E6BFE")}§lL${rgb("6973FE")}§lI${rgb("757BFE")}§lS${rgb("8083FF")}§lM${rgb("8C8BFF")}§l]"
val BACKGROUND = getItem(
    Material.MAGENTA_STAINED_GLASS_PANE,
    PREFIX
)
val BACKGROUND_1 = getItem(
    Material.BLACK_STAINED_GLASS_PANE,
    PREFIX
)

val api = LuckPermsProvider.get()

enum class PermissionEnum(
    val node: String,
    val prefix: String,
    val priority: Int,
    vararg val parent: PermissionEnum = emptyArray()
) {
    USER("main.user", "&7&l[USER]&7", 99),
    VIP("main.vip", "&a&l[VIP]&a", 6, USER),
    MVP("main.mvp", "&6&l[MVP]&6", 5, VIP), //월마다 3만 루비 내거나 매년 30만 루비에 보너스로 360 크리스탈 얻거나

    GUIDE("main.guide", "&b&l[GUIDE]&b", 4, MVP), // 1. 헬퍼 (채팅 관리, 유저 응대만 가능)
    ADMIN("main.admin", "&c&l[ADMIN]&c", 3, GUIDE), // 2. 어드민 (경제 조절)

    YOUTUBER("main.youtuber", "&c&l[YOUTUBER]&c", 2, ADMIN),
    OWNER("main.owner", "&4&l[OWNER]&4", 1, YOUTUBER);

    fun register(plugin: JavaPlugin) {
        if (plugin.server.pluginManager.getPermission(node) != null) return
        val defaultValue = if (this == USER) PermissionDefault.TRUE else PermissionDefault.FALSE

        val perm = Permission(node, defaultValue)
        parent.forEach { parent ->
            perm.addParent(parent.node, true)
        }
        plugin.server.pluginManager.addPermission(perm)
    }

    companion object {
        private val map = entries.associateBy { it.name }

        operator fun get(name: String) = map[name.uppercase()]
        operator fun get(player: Player) = entries.reversed().find { player.permission(it) }?: USER

        fun modify(uuid: UUID, permission: PermissionEnum): Boolean {
            return api.userManager.modifyUser(uuid) { user ->
                entries.forEach { user.data().remove(Node.builder(it.node).build()) }
                user.data().add(Node.builder(permission.node).build())
            }.thenApply { true }.exceptionally { false }.join()
        }
    }
}
