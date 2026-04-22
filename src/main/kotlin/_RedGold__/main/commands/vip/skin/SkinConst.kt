package _RedGold__.main.commands.vip.skin

import java.net.URI

internal object SkinConst {
    val SET_NICK_URL = { name: String ->
        URI.create("https://api.mojang.com/users/profiles/minecraft/$name").toURL()
    }
    val SET_UUID_URL = { uuid: String ->
        URI.create("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false").toURL()
    }
}