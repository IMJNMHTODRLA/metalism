package _RedGold__.main.event._showDown_

object EventMetaDatas {
    const val DAMAGE_HEALTH_POINT = 5L
    const val BATTLE_TIME = 1800L

    enum class GuiMetaData(
        val difficultyItem: String,
        val difficultyName: String,
        val difficultySimple: String,
    ) {
        EASY("stick", "&a&l쉬움(Easy)", "Easy"),
        NORMAL("wooden_sword", "&e&l보통(Normal)", "Normal"),
        HARD("stone_sword", "&c&l어려움(Hard)", "Hard"),
        HARDCORE("iron_sword", "&4&l하드코어(HardCore)", "HardCore"),
        EXTREME("diamond_sword", "&b&l익스트림(Extreme)", "Extreme"),
        //INSANE("netherite_sword", "&b&l익스트림(Extreme)", "Extreme"),
    }

    enum class MinionMetaData(
        val hp: Double,
        val damage: Double,
    ) {
        EASY(100.0, 3.0),
        NORMAL(125.0, 6.0),
        HARD(150.0, 9.0),
        HARDCORE(175.0, 12.0),
        EXTREME(200.0, 15.0),
        //Easy: 100, 1.5
        //Normal: 105, 2.0
        //hard: 115, 2.5
        //HardCore: 130, 3.0
        //Extreme: 300, 5.0
        //Insane: 450, 7.5
        //Torment: 800, 10.0
        //Lunatic: 1200, 13.0
    }

    enum class BossMetaData(
        val hp: Double,
        val defense: Double,
        val damage: Double,
    ) {
        EASY(500.0, 10.0, 10.0), //500
        NORMAL(1000.0, 12.0, 15.0), //750
        HARD(2000.0, 14.0, 20.0), //1250
        HARDCORE(4000.0, 16.0, 25.0), //2000
        EXTREME(8000.0, 18.0, 30.0), //9000
        //Easy: 500
        //Normal: 750
        //hard: 1250
        //HardCore: 1800
        //Extreme: 5000
        //Insane: 8500
        //Torment: 16000
        //Lunatic: 30000
    }

    enum class PointMetaData(
        val clearPoint: Long,
        val healthPoint: Long,
        val timePoint: Long,
    ) {
        EASY(5_000L, 2_500L, 5_400L),
        NORMAL(10_000L, 5_000L, 10_800L),
        HARD(20_000L, 10_000L, 21_600L),
        HARDCORE(40_000L, 20_000L, 43_200L),
        EXTREME(80_000L, 40_000L, 86_400L);

        val secTimePoint get() = timePoint / BATTLE_TIME
        val totalPoint get() = clearPoint + healthPoint + timePoint
    }

    enum class CoinMetaData(
        val commonCoin: Int,
        val advancedCoin: Int,
    ) {
        EASY(15, 0),
        NORMAL(30, 0),
        HARD(45, 0),
        HARDCORE(60, 10),
        EXTREME(75, 20);
    }
}


//Utility("&6[유틸리티]", 800.0, 15.0, 2.0, 48000L),
//LUNATIC("§4[루나틱]", 5000.0, 40.0, 3.0, 192000L),