package _RedGold__.main.core.guild.settings.member.donate

import _RedGold__.main.core.guild.expManager.addGuildExp
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class DonateListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val player = event.whoClicked as Player
        val holder = gui.holder as? DonateHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        when(val slot = event.slot) {
            13 -> {
                if (holder.gold < 1000) {
                    player.fail("&c기부 금액은 최소 1000골드 이상이여야 합니다.")
                    return
                }

                if (player.data.gold - holder.gold < 0) {
                    player.fail("&c골드가 부족합니다. 필요 골드: ${(holder.gold - player.data.gold).toFormat()} 골드")
                    return
                }

                if (playerCooldownMsg(player)) return

                player.closeInventory()
                player.good("&a기부가 완료되었습니다.")

                player.data.gold -= holder.gold

                taskAsync {
                    addGuildExp(holder.id, holder.upExp)
                }
            }

            in 27..35 -> {
                val change = when(slot) {
                    in 27..30 -> -(DEFAULT_GOLD * 10.pow(30 - slot)) // 차감은 음수로
                    in 32..35 -> DEFAULT_GOLD * 10.pow(slot - 35) // 추가는 양수로
                    else -> return
                }

                holder.gold = (holder.gold + change).coerceAtLeast(0)

                gui.item[31] = getItem(
                    Material.BLACK_STAINED_GLASS_PANE,
                    "&6&l기부 금액&f: &6&l${holder.gold} 골드"
                )

                gui.item[13] = getItem(
                    Material.GOLD_BLOCK,
                    "&a&l[기부 하기]",
                    "",
                    "&e&l클릭 시 길드에 &6&l${holder.gold} 골드&e&l를 기부하여 &a&l${holder.upExp} EXP&e&l를 증가시킵니다."
                )

                if (change < 0) {
                    player.sendMsg("&c기부 금액에 ${(-change).toFormat()} 골드를 회수하였습니다. 기부 금액: ${holder.gold.toFormat()}")
                    player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 2f)
                } else {
                    player.sendMsg("&a기부 금액에 ${change.toFormat()} 골드를 추가하였습니다. 기부 금액: ${holder.gold.toFormat()}")
                    player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f)
                }
            }
        }
    }
}