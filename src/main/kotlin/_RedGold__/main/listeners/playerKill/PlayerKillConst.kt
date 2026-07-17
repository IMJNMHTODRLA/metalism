package _RedGold__.main.listeners.playerKill

object PlayerKillConst {
    const val GIVE_EXP = 100
    const val GIVE_GOLD = 40000L

    val KILL_STREAK_FORMULA = { streak: Int ->
        when (streak) {
            in 0..4 -> false to 0
            in 5..29 -> (streak % 5 == 0) to 2
            else -> (streak % 10 == 0) to 12
        }
    }

    const val KILL_STREAK_MESSAGE = "&a&l%kill_streak%연킬&f&l을 하여 &b&l%give_crystal% 크리스탈&f&l을 획득하였습니다."
    val KILL_MESSAGE = """
        &f&l+&6&l%gold% 골드
        &f&l+&b&l%crystal% 크리스탈
        &f&l+&a&l%exp% 경험치
        &7&l연킬 순위에서 보상을 획득 할 수 있습니다.
    """.trimIndent()

    const val KILL_ACTIONBAR = "&f&l+&6&l%gold% 골드&8, &f&l+&b&l%crystal% 크리스탈&8, &f&l+&a&l%exp% 경험치"
    const val DEATH_MESSAGE = "&f&l당신은 플레이어에게 &4&l사망하여 &6&l%gold% 골드&f&l와 연킬을 &c&l잃었습니다."
}