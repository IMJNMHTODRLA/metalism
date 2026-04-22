package _RedGold__.main.managers.playerData.variableManager.cosmeticManager

import org.bukkit.Sound

val STYLE_COSMETIC = listOf(
    "",
    "&a&l[시간의 연속]", "&4&l[킬러]", "&d&l[컬렉션]", "&8&l[무게 변화]", "&8&l[지각 변동]", //LIMITED

    "&4&l[미제 사건]", "&d&l[크리스탈]", "&c&l[하드코어]", "&f&l[백업 없음]", "&8&l[솔플]",
    "&8&l[팀플]", "&8&l[99/0/0]", "&a&l[EZ]", "&8&l[GG]", "&c&l[한방컷]", "&b&l[쉴드 전문가]",
    "&a&l[탱커]", "&c&l[딜러]", "&e&l[버퍼러]"
)

val JOIN_COSMETIC = listOf(
    "&8[&a+&8] %style%%rank% %name% &e님이 서버에 접속했습니다.",
    "&8[&a+&8] %style%%rank% %name% &e님이 서버에 나타났습니다.",
    "&8[&a+&8] %style%%rank% %name% &7&lJoined",
    "&8[&b✦&8] %style%%rank% %name% &e님 환영합니다!",
    "&8[&a»&8] %style%%rank% %name% &e님이 &a&l온라인&e으로 전환했습니다.",
    "&8[&7»&8] &fJo&ki&fned with %style%%rank% %name%",
    "&8[&b»&8] %style%%rank% %name% &b&l님이 서버에 등장 하였습니다.",
    "&8[&a+&8] %style%%rank% %name% 님이 서버에 &a&l생성되었습니다.",
    "&8[&7?&8] %style%%rank% %name% 님이 서버에 &8&l접속...했나요?",
    "&8[&a!&8] %style%%rank% %name% 님이 게임에 참여하였습니다!",
    "&8[&a+&8] %style%%rank% %name% 님이 &2&l마인크래프트 세상에 들어왔습니다.",
    "&8[&a+&8] %style%%rank% %name% 님이 서버에 접속하였습니다. &a&l환영해주세요!",
    "&8[&a+&8] %style%%rank% %name%",
    "&8+ %name%"
)

val DEATH_COSMETIC = listOf(
    null to "없음",
    Sound.AMBIENT_UNDERWATER_ENTER to "물에 빠진",
    Sound.AMBIENT_CAVE to "귀신",
    Sound.WEATHER_RAIN to "비",
    Sound.ENTITY_COW_DEATH to "흑우",
    Sound.ENTITY_BAT_DEATH to "박쥐",
    Sound.ENTITY_PIG_DEATH to "돼지",
    Sound.BLOCK_ANVIL_LAND to "모루",
    Sound.BLOCK_FIRE_EXTINGUISH to "식음",
    Sound.ENTITY_GENERIC_EXPLODE to "폭팔",
    Sound.ENTITY_GENERIC_EAT to "먹다",
    Sound.ENTITY_GENERIC_EXTINGUISH_FIRE to "타버림",
    Sound.BLOCK_VAULT_BREAK to "금고 부숨",
    Sound.ENTITY_ENDER_EYE_DEATH to "깨짐"
)

val KILL_COSMETIC = listOf(
    null to "없음",
    Sound.ITEM_MACE_SMASH_GROUND_HEAVY to "철퇴",
    Sound.BLOCK_HONEY_BLOCK_FALL to "꿀",
    Sound.BLOCK_SLIME_BLOCK_BREAK to "슬라임",
    Sound.ENTITY_ZOMBIE_DEATH to "좀비",
    Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR to "철문 공격",
    Sound.ENTITY_ZOGLIN_DEATH to "조글린",
    Sound.BLOCK_ANVIL_USE to "모루",
    Sound.UI_TOAST_CHALLENGE_COMPLETE to "발전 과제",
    Sound.ENTITY_SHEEP_AMBIENT to "셜커",
    Sound.ITEM_GOAT_HORN_SOUND_1 to "염소뿔",
    Sound.ITEM_TRIDENT_RIPTIDE_1 to "삼지창",
    Sound.ITEM_TRIDENT_THUNDER to "웅장한 삼지창",
    Sound.ENTITY_GENERIC_DRINK to "마심"
)