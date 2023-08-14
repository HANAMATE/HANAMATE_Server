package team.hanaro.hanamate.domain.Allowance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.Allowance.Dto.RequestDto;

//@Transactional
@Tag(name = "용돈", description = "용돈과 관련된 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/allowance")
public class AllowanceController {
    private final AllowanceService allowanceService;

    /* 1. 아이 : 용돈 조르기 요청 조회*/
    @Operation(summary = "[아이] 용돈 조르기 요청 조회", description = "용돈 조르기 한 내역 가져오기", tags = {"용돈"})
    @GetMapping("/request/list")
    public ResponseEntity<?> GetAllowanceList(@RequestBody RequestDto.ChildRequestList childRequestList) {
        return allowanceService.getAllowanceRequestList(childRequestList);
    }

    /* 2. 아이 : 용돈 조르기 생성 */
    @Operation(summary = "[아이] 용돈 조르기 생성", description = "용돈 조르기", tags = {"용돈"})
    @PostMapping("/request")
    public ResponseEntity<?> MakeAllowanceRequest(@RequestBody RequestDto.ChildRequest childRequest) {
        return allowanceService.makeAllowanceRequest(childRequest);
    }

    /* 3. 부모 : 용돈 조르기 승인 */
    @Operation(summary = "[부모] 용돈 조르기 승인", description = "용돈 조르기 승인", tags = {"용돈"})
    @PostMapping("/request/approve")
    public ResponseEntity<?> ApproveAllowanceRequest(@RequestBody RequestDto.ParentApprove parentApprove) {
        return allowanceService.updateRequestStatus(parentApprove);
    }

    /* 4. 부모 : 용돈 보내기 */
    @Operation(summary = "[부모] 용돈 보내기", description = "용돈 보내기", tags = {"용돈"})
    @PostMapping("")
    public ResponseEntity<?> SendAllowance(@RequestBody RequestDto.SendAllowance sendAllowance) {
        return allowanceService.sendAllowance(sendAllowance);
    }

    /* 5. 부모 : 정기 용돈 조회 */
    @Operation(summary = "[부모] 정기 용돈 조회하기", description = "정기 용돈 조히", tags = {"용돈"})
    @GetMapping("/periodic")
    public ResponseEntity<?> getPeriodicAllowance(@RequestBody RequestDto.PeriodicAllowance periodicAllowance) {
        return allowanceService.getPeriodicAllowance(periodicAllowance);
    }

    /* 6. 부모 : 정기 용돈 생성하기 */
    @Operation(summary = "[부모] 정기 용돈 생성하기", description = "정기 용돈 생성", tags = {"용돈"})
    @PostMapping("/periodic")
    public ResponseEntity<?> makePeriodicAllowance(@RequestBody RequestDto.makePeriodicAllowance periodicAllowance) {
        return allowanceService.makePeriodicAllowance(periodicAllowance);
    }

    /* 7. 부모 : 정기 용돈 수정 */
    @PostMapping("/periodic/update")
    public ResponseEntity<?> updatePeriodicAllowance(@RequestBody RequestDto.updatePeriodicAllowance periodicAllowance) {
        return allowanceService.updatePeriodicAllowance(periodicAllowance);
    }

    /* 8. 부모 : 정기 용돈 삭제 */
    @DeleteMapping("/periodic")
    public ResponseEntity<?> deletePeriodicAllowance(@RequestBody RequestDto.deletePeriodicAllowance periodicAllowance) {
        return allowanceService.deletePeriodicAllowance(periodicAllowance);
    }
}
