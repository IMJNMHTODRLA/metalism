package _RedGold__.main.managers.playerData.variableManager.boostSettingManager.particleEffect

import org.bukkit.Material
import org.bukkit.Particle

abstract class BaseBlockParticle(material: Material) { val blockData = material.createBlockData() }
data class ParticleEffect(
    val name: String,
    val particle: Particle? = null,
    val data: BaseBlockParticle? = null
)

val particleEffectList: List<ParticleEffect> = listOf(
    ParticleEffect("&f&l기본"),
    ParticleEffect("&4&l&mBLOOD", Particle.BLOCK, object : BaseBlockParticle(Material.REDSTONE_BLOCK) {}),
    ParticleEffect("&f&l&oROBOT", Particle.BLOCK, object : BaseBlockParticle(Material.IRON_BLOCK) {}),
    ParticleEffect("&f&lDIAMOND", Particle.BLOCK, object : BaseBlockParticle(Material.DIAMOND_BLOCK) {}),
    ParticleEffect("&c&l&oFLAME", Particle.FLAME),
    ParticleEffect("&b&l&oSOUL", Particle.SOUL),
    ParticleEffect("&a&l&oHAPPY", Particle.HAPPY_VILLAGER),
    ParticleEffect("&d&lCHERRY", Particle.CHERRY_LEAVES),
    ParticleEffect("&5&l&nEND", Particle.DRAGON_BREATH),
    ParticleEffect("&e&l&nGLOW", Particle.GLOW),
    ParticleEffect("&c&l&mLAVA", Particle.FALLING_LAVA),
    ParticleEffect("&b&l&mWATER", Particle.FALLING_WATER),
    ParticleEffect("&c&lHEART", Particle.HEART),
)
