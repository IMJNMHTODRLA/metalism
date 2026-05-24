package _RedGold__.main.core.guild

import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireTabExecutor
@RequireCommandExecutor("guild", PermissionEnum.USER, aliases = ["길드"])
class Guild : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        if (args.isEmpty())

        return true

        /*
            1. 길드 기초 구조 및 DB
            - 소속: 1인 1길드 시스템 (플레이어 UUID를 PK로 관리)
            - 운영: 길드장은 자유롭게 탈퇴 가능하며, 길드장 탈퇴 시 길드는 즉시 해체(삭제) 처리
            - 경제: 1 EXP 증가 = 1,000 골드 기부 (기부 시 길드원 전원 골드 환전 불가 정책)
            - 비용: 길드 등록 시 5,000,000 골드 소모

            2. 성장 및 레벨업 공식
            - 필요 경험치: 기본 1레벨, 이후 레벨당 20 * (레벨 ^ 1.4)씩 증가
            - 레벨업 혜택 (1레벨당):
                * 이동 속도: +0.06%(0.0006) 곱연산 추가
                * 공격력: +0.04%(0.0004) 곱연산 추가
            - 레벨업 혜택 (2레벨당):
                * 공용 창고: +1칸 증가 (최대 108레벨/54칸까지)
            - 최대 길드원 (4레벨당):
                * 1명씩 증가 (최대 200레벨/50명까지)
            - 특징: 창고는 54레벨에서 멈추지만 스탯(속도/공격력)은 제한 없이 계속 누적

            3. PVP 및 경험치 수급
            - 처치 보상: 상대 플레이어 처치 시 7 * (본인 길드 레벨 ^ 1.1) EXP 획득
            - 어뷰징 방지: 동일 플레이어 처치 시 3시간 쿨타임 적용 (쿨타임 중 EXP 지급 불가)
            - 정책: 적대 길드 시스템은 리스크 관리를 위해 제외

            4. 기타 사항
            - 개인 창고: 유저별 개인 가상 창고 별도 존재
            - 길드 창고: 길드원 간 아이템 전달 및 공용 보급 용도
            - 스탯 구현: 기본 스텟에서 곱연산
         */

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("설정", "화이트리스트")
        return emptyList()
    }
}