package _RedGold__.main.sys

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Scheduler.task
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
class AutoNotice(private val plugin: JavaPlugin) {
    private var times = 0
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("304CFC")}§l§oM
        ${rgb("3B54FD")}§l§oE
        ${rgb("475CFD")}§l§oT
        ${rgb("5264FD")}§l§oA
        ${rgb("5E6BFE")}§l§oL
        ${rgb("6973FE")}§l§oI
        ${rgb("757BFE")}§l§oS
        ${rgb("8083FF")}§l§oM
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    init {
        loop()
    }

    private fun loop() {
        plugin.task(0, 20 * 60 * 10) {
            if (times >= 7) times = 0
            val msgs = when (times) {
                0 -> gc("$prefix &f&l서버 규칙 위반 시 &c&l경고 없이 즉시 이 서버에서 차단 될 수 있습니다&f&l. &e&l/규칙 &f&l명령어로 규칙을 확인하세요.")
                1 -> gc("$prefix &a&lShift + F &f&l키를 눌러 메뉴를 열 수 있습니다.")
                2 -> gc("$prefix &f&l&c&l비인가 클라이언트(프로그램)&7&l/&c&l버그 악용&f&l시 즉시 이 서버에서 차단됩니다.")
                3 -> gc("$prefix &f&l모든 &a&l후원금&f&l은 이 서버를 &e&l유지&f&l하는데 사용 됩니다.")
                4 -> gc("$prefix &f&l모든 소식과 공지를 빠르게 확인 하실려면 &b&l/디스코드 &f&l명령어를 입력 해 디스코드 서버에 접속해주세요.")
                5 -> gc("$prefix &f&l후원 신청을 하실려면 &b&l/디스코드 &f&l명령어로 디스코드 서버에 접속 해 &7&l후원신청 &f&l티켓을 열어 진행해주세요.")
                6 -> gc("$prefix &f&l비매너 플레이어를 발견 시 &b&l/디스코드 &f&l명령어로 디스코드 서버에 접속 해 &7&l# &f&l티켓을 열어 진행해주세요.")
                else -> ""
            }

            Bukkit.getServer().broadcastMessage(msgs)
            times++
        }
    }
}