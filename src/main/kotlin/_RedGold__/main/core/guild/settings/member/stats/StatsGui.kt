package _RedGold__.main.core.guild.settings.member.stats

import _RedGold__.main.core.guild.expManager.*
import _RedGold__.main.core.guild.expManager.expBenefits.*
import _RedGold__.main.core.guild.getGuildId2Member
import _RedGold__.main.core.guild.getGuildStats
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotGuildJoinMsg
import _RedGold__.main.functions.*
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object StatsGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Member(uuid)?: run {
                sendNotGuildJoinMsg(player)
                return@taskAsync
            }

            val guildStats = getGuildStats(id)?: run {
                player.fail("&c길드를 찾는데 실패하였습니다.")
                return@taskAsync
            }

            val leaderName = Bukkit.getOfflinePlayer(guildStats.leader).name
            val currentLevel = currentLevel(guildStats.exp)

            val maxExpOfLevel = maxExpOfLevel(currentLevel)
            val requireExpOfLevel = requireExpOfLevel(guildStats.exp)
            val reachNextLevel = nextLevelPercent(maxExpOfLevel, requireExpOfLevel)
            val progressBar = makeProgressBar(reachNextLevel)

            val benefits = getAllLevelBenefits(currentLevel)

            task {
                val gui = StatsHolder().inventory
                gui.item(BACKGROUND)

                gui.item[12] = getItem(
                    Material.EXPERIENCE_BOTTLE,
                    "&a&l[ 길드 EXP ]",
                    "",
                    "&f&l현재 레벨: &e&${currentLevel}레벨",
                    "&f&l목표 EXP: &e&l$requireExpOfLevel/$maxExpOfLevel&8&l(${reachNextLevel.toPercent})",
                    "&a&l$progressBar",
                )

                gui.item[13] = getItem(
                    Material.BOOK,
                    "&e&l[ 길드 기본 정보 ]",
                    "",
                    "&f&l길드 ID: &e&l${guildStats.id}",
                    "&f&l길드 명: &e&l${guildStats.name}",
                    "&f&l리더: &e&l${leaderName}",
                    "&8&l생성 일: ${guildStats.createdAt.formatTimestamp()}"
                ).modify { enchantEffect() }

                gui.item[14] = getItem(
                    Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                    "&b&l[ 길드 EXP 혜택 ]",
                    "",
                    "&f&l낙하 데미지: &b&l-${benefits.fallDamageDec.toPercent}/${MAX_DAMAGE_PERCENT.toPercent}",
                    "&f&l공격 데미지: &c&l+${benefits.attackDamage.toPercent}/${MAX_ATTACK_PERCENT.toPercent}",
                    "&f&l공용 창고 크기: &e&l${benefits.shareChestSize}/${MAX_CHEST_SIZE}칸",
                    "&f&l최대 길드원: &e&l${benefits.maxMembers}/${MAX_MEMBERS}명",
                    "&f&l플레이어 처치 보상: &a&l+${benefits.killReward.toFormat()} EXP",
                    "",
                    "&8&l혜택은 매 10분마다 새로고침됩니다."
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}