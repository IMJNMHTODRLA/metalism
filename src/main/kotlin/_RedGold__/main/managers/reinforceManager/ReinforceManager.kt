package _RedGold__.main.managers.reinforceManager

//TODO: 나중에 이 아이템이 강화 아이템이 맞는지 확인하는 fun 만들기

fun randomReinforceItem(amount: Int, vararg second: ReinforceImportEnum) = ReinforceItemList
    .filter { it.import in second }
    .random()
    .item(amount)