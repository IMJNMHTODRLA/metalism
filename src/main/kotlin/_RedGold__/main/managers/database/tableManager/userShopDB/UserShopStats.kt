package _RedGold__.main.managers.database.tableManager.userShopDB

import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import org.bukkit.Material
import org.jetbrains.exposed.sql.Table

object UserShopStats : Table("user_shop_stats") {
    val id = integer("id").autoIncrement()
    val uuid = varchar("uuid", 36)

    val priceType = enumerationByName("price_type", 24, UserShopGoodsEnum::class)
    val priceAmount = long("price_amount")

    val createdAt = long("created_at")

    val item = blob("item")

    val displayMaterial = enumerationByName("display_material", 64, Material::class)
    val displayName = text("display_name")
    val displayIsEnchant = bool("display_is_enchant")
    val displayAmount = integer("display_amount")

    override val primaryKey = PrimaryKey(id)

    //아 머리아파
    //DB 저장 구조:
    /*

    UserShopStats:
    id(PK): (고유 번호, 16진수 8자리, str)
    user_id: (판매자 USER_ID, str)

    price_type: (Enum 클래스 연동, Crystal인지, Gold인지 타입)
    price_amount: (가격)

    created_at: (올린 시간{만료 시간 아님!!}, unix으로 하고 업로드 후 서버 재시작 전 까지는 회수 가능 및 판매 목록에 업로드 X
        14일간 판매 기간, 그 후 5일간 만료(회수 가능)기간,
    )

    item: (아이템 NBT 그런거 데이터, blob)
    amount: (아이템 갯수)

    display_material: (표시용 material, material EnumClass 그거 연동)
    display_name: (표시용 이름, str)
    display_is_enchant: (표시용 인첸트 되어 있는지, boolean)

    메모리 불러오는거:
    id(PK)
    user_id

    price_type/amount
    created_at

    amount

    display_~

     */
}