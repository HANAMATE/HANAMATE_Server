package team.hanaro.hanamate.domain.Allowance;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.hanaro.hanamate.entities.Allowances;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AllowanceScheduler {

    private final AllowancesRepository allowancesRepository;

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        System.out.println("Scheduler Check - {" + System.currentTimeMillis() / 1000 + "}");
        Thread.sleep(5000);
    }

    //@Scheduled(cron = "0 0 6 * * *") //매일 오전 6시에 실행하는 cron
    @Scheduled(fixedDelay = 1000) //테스트를 위해서 추가
    public void scheduleTaskUsingCronExpression() {
        System.out.println("Scheduler tasks using cron jobs - {" + System.currentTimeMillis() / 1000 + "}");

        // 해야할 일
        // 1. 오늘 날짜 가져오기
        LocalDateTime localDateTime = LocalDateTime.now();
        int currentDayOfWeek = localDateTime.getDayOfWeek().getValue(); /* 월요일(1) ~ 일요일(7) */
        int currentDate = localDateTime.getDayOfMonth();
        //System.out.println("currentDate: "+currentDate+", currentDayOfWeek: "+currentDayOfWeek);
        // 2. 오늘 날짜와 연관된 정기용돈 목록 불러오기
        List<Allowances> allowances = allowancesRepository.findAllByEverydayOrDayOfWeekOrTransferDateAndValidIsTrue(true, currentDayOfWeek, currentDate);

        if(!allowances.isEmpty()){
            // 3. 용돈 이체 일으키기
            for(Allowances allowance: allowances){
                System.out.println(allowance.toString());

                //TODO: allowance의 parentIdx와 childrenIdx로 User 불러오기
                //TODO: allowanceAmount>parent지갑 잔액이 작으면 실패 --> 거래 실패 이력 남기기
                //TODO: allowanceAmount<=parent지갑 잔액이면 성공 --> 용돈 이체하고, 거래 내역 남기기
            }
        }


    }
}
