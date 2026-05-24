package _RedGold__.main.managers.playerData.dataManager

import _RedGold__.main.managers.playerData.OVER_WORLD
import org.bukkit.Bukkit
import org.bukkit.Location

data class HomeData(
    var isUnlocked: Boolean = false,
    var location: LocationData? = null
)

data class LocationData(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
) {
    constructor(loc: Location) : this(
        loc.x, loc.y, loc.z, loc.yaw, loc.pitch
    )

    operator fun invoke() = Location(
        Bukkit.getWorld(OVER_WORLD), x, y, z, yaw, pitch
    )
}