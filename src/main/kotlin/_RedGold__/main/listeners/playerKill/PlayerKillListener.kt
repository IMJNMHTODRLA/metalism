package _RedGold__.main.listeners.playerKill

import _RedGold__.main.function.api.toFormat
import _RedGold__.main.functions.Color.sendAction
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.listeners.GlobalValue
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

@RequireListener
class PlayerKillListener : Listener {
    @EventHandler
    fun onKill(event: EntityDeathEvent) {
        val attacker = event.entity.killer?: return
        val victim = event.entity as? Player ?: return

        val attackerUUID = attacker.uniqueId
        val victimUUID = victim.uniqueId

        val giveGold = minOf(victim.data.gold, PlayerKillConst.GIVE_GOLD)
        var giveCrystal = 0

        val victimDeath = victim.data.equipDeath.second
        val attackerKill = attacker.data.equipKill.second

        attacker.data.combatData.kill++
        victim.data.combatData.death++

        val killedSet = PlayerKillValue.killedPlayerMap.computeIfAbsent(attackerUUID) { mutableSetOf() }

        if (!killedSet.contains(victimUUID)) {
            attacker.data.combatData.killStreak++
            killedSet.add(victimUUID)
        }
        attacker.data.combatData.deathStreak = 0

        victim.data.combatData.deathStreak++
        victim.data.combatData.killStreak = 0

        attacker.giveExp(PlayerKillConst.GIVE_EXP)

        val killStreak = PlayerKillConst.KILL_STREAK_FORMULA(attacker.data.combatData.killStreak)
        if (killStreak.first) {
            giveCrystal += killStreak.second

            attacker.sendMsg(
                PlayerKillConst.KILL_STREAK_MESSAGE.fill(
                    "kill_streak" to attacker.data.combatData.killStreak,
                    "give_crystal" to killStreak.second.toFormat()
                )
            )

            attacker.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
        }

        attacker.data.gold += giveGold
        attacker.data.crystal += giveCrystal

        attacker.sendMsg(PlayerKillConst.KILL_MESSAGE.fill(
            "gold" to giveGold.toFormat(),
            "crystal" to giveCrystal.toFormat(),
            "exp" to PlayerKillConst.GIVE_EXP.toFormat()
        ))

        attacker.sendAction(PlayerKillConst.KILL_ACTIONBAR.fill(
            "gold" to giveGold.toFormat(),
            "crystal" to giveCrystal.toFormat(),
            "exp" to PlayerKillConst.GIVE_EXP.toFormat()
        ))

        victim.sendMsg(PlayerKillConst.DEATH_MESSAGE.fill(
            "gold" to giveGold.toFormat()
        ))

        attackerKill.first?.let { attacker.sendSound(it) }
        victimDeath.first?.let { victim.sendSound(it) }

        GlobalValue.chatGGTiming[attackerUUID] = victimUUID to now + 120

        //TODO: 뽑기 아이템 드롭 X
    }
}