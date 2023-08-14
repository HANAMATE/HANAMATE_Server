package team.hanaro.hanamate.domain.Allowance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.Allowance.Dto.RequestDto;

//@Transactional
@Tag(name = "용돈", description = "용돈 조르기, 용돈 보내기, 정기 용돈")
@RequiredArgsConstructor
@RestController
@RequestMapping("/allowance")
public class AllowanceController {
    private final AllowanceService allowanceService;

    /* 1. 아이 : 용돈 조르기(대기중) 요청 조회 */
    @Operation(summary = "[아이] 용돈 조르기(대기중) 요청 조회", description = "용돈 조르기(대기중) 내역 가져오기", tags = {"용돈"})
    @GetMapping("/child/request/pending")
    public ResponseEntity<?> getMyAllowancePendingRequestList(@RequestBody RequestDto.User user) {
        return allowanceService.getMyAllowancePendingRequestList(user);
    }

    /* 2. 아이 : 용돈 조르기(승인/거절) 요청 조회 */
    @Operation(summary = "[아이] 용돈 조르기(승인/거절) 요청 조회", description = "용돈 조르기(승인/거절) 내역 가져오기", tags = {"용돈"})
    @GetMapping("/child/request")
    public ResponseEntity<?> getMyAllowanceApprovedRequestList(@RequestBody RequestDto.User user) {
        return allowanceService.getMyAllowanceApprovedRequestList(user);
    }

    /* 3. 아이 : 용돈 조르기 생성 */
    @Operation(summary = "[아이] 용돈 조르기 생성", description = "용돈 조르기", tags = {"용돈"})
    @PostMapping("/child/request")
    public ResponseEntity<?> makeAllowanceRequest(@RequestBody RequestDto.Request request) {
        return allowanceService.makeAllowanceRequest(request);
    }

    /* 4. 부모 : 용돈 조르기(대기중) 요청 조회 */
    @Operation(summary = "[부모] 용돈 조르기(대기중) 요청 조회", description = "대기중인 용돈 조르기 요청 내역 가져오기", tags = {"용돈"})
    @GetMapping("/parent/request/pending")
    public ResponseEntity<?> getMyChildAllowancePendingRequestList(@RequestBody RequestDto.User user) {
        return allowanceService.getMyChildAllowancePendingRequestList(user);
    }

    /* 5. 부모 : 용돈 조르기(승인,거절) 요청 조회 */
    @Operation(summary = "[부모] 용돈 조르기(승인,거절) 요청 조회", description = "승인/거절 된 용돈 조르기 요청 내역 가져오기", tags = {"용돈"})
    @GetMapping("/parent/request")
    public ResponseEntity<?> getMyChildAllowanceApprovedRequestList(@RequestBody RequestDto.User user) {
        return allowanceService.getMyChildAllowanceApprovedRequestList(user);
    }

    /* 6. 부모 : 용돈 조르기 상태 변경(대기중 -> 승인/거절) */
    @Operation(summary = "[부모] 용돈 조르기 상태 변경", description = "대기 중인 용돈 조르기를 승인or거절", tags = {"용돈"})
    @PostMapping("/parent/request")
    public ResponseEntity<?> approveAllowanceRequest(@RequestBody RequestDto.Approve approve) {
        return allowanceService.updateRequestStatus(approve);
    }

    /* 7. 부모 : 용돈 보내기 */
    @Operation(summary = "[부모] 용돈 보내기", description = "용돈 보내기", tags = {"용돈"})
    @PostMapping("/send")
    public ResponseEntity<?> sendAllowance(@RequestBody RequestDto.Request request) {
        return allowanceService.sendAllowance(request);
    }

    /* 8. 부모 : 정기 용돈 조회 */
    @Operation(summary = "[부모] 정기 용돈 조회하기", description = "정기 용돈 조회", tags = {"용돈"})
    @GetMapping("/periodic")
    public ResponseEntity<?> getPeriodicAllowance(@RequestBody RequestDto.User user) {
        return allowanceService.getPeriodicAllowance(user);
    }

    /* 9. 부모 : 정기 용돈 생성하기 */
    @Operation(summary = "[부모] 정기 용돈 생성하기", description = "정기 용돈 생성", tags = {"용돈"})
    @PostMapping("/periodic")
    public ResponseEntity<?> makePeriodicAllowance(@RequestBody RequestDto.Periodic periodic) {
        return allowanceService.makePeriodicAllowance(periodic);
    }

    /* 10. 부모 : 정기 용돈 수정 */
    @Operation(summary = "[부모] 정기 용돈 수정하기", description = "정기 용돈 수정", tags = {"용돈"})
    @PutMapping("/periodic")
    public ResponseEntity<?> updatePeriodicAllowance(@RequestBody RequestDto.UpdatePeriodic periodic) {
        return allowanceService.updatePeriodicAllowance(periodic);
    }

    /* 11. 부모 : 정기 용돈 삭제 */
    @Operation(summary = "[부모] 정기 용돈 삭제하기", description = "정기 용돈 삭제", tags = {"용돈"})
    @DeleteMapping("/periodic")
    public ResponseEntity<?> deletePeriodicAllowance(@RequestBody RequestDto.Allowance periodic) {
        return allowanceService.deletePeriodicAllowance(periodic);
    }
}
