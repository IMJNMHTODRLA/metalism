package _RedGold__.main.commands.user.boost.listeners.settings.vip.jumpParticleEffectGui

import _RedGold__.main.commands.user.boost.listeners.settings.SettingsGlobalConst
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum.JUMP_PARTICLE_EFFECT
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.particleEffect.particleEffectList
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class JumpParticleEffectGui {
    fun openGui(player: Player, page: Int) {
        val gui = JumpParticleEffectHolder(page).inventory
        gui.item(BACKGROUND)
        gui.item[27..gui.end] = BACKGROUND_1

        val hasRank = player in JUMP_PARTICLE_EFFECT
        val equipValue = player.data.boostSettingMap[JUMP_PARTICLE_EFFECT]?: JUMP_PARTICLE_EFFECT.default

        repeat(14) { i ->
            val slot = SettingsGlobalConst.getSlot(i)
            val id = SettingsGlobalConst.getIdFromI(page, i)
            val data = particleEffectList.getOrNull(id)?: return@repeat

            gui.item[slot] = SettingsGlobalConst.setDisplayItem(
                data.name,
                equipValue == id,
                hasRank,
                JUMP_PARTICLE_EFFECT.needPermission
            )
        }

        gui.item[27] = getItem(
            Material.RED_STAINED_GLASS_PANE,
            "&c&l이전 페이지로 이동",
        )

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: ($page)",
        )

        gui.item[35] = getItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&a&l다음 페이지로 이동",
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}