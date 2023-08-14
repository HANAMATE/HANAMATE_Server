package team.hanaro.hanamate.domain.Allowance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.Allowance.Dto.RequestDto;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.global.Response;

//@Transactional
@Tag(name = "용돈", description = "용돈 조르기, 용돈 보내기, 정기 용돈")
@RequiredArgsConstructor
@RestController
@RequestMapping("/allowance")
public class AllowanceController {
    private final AllowanceService allowanceService;
    private final Response response;

    /* 1. 아이 : 용돈 조르기(대기중) 요청 조회 */
    @Operation(summary = "[아이] 용돈 조르기(대기중) 요청 조회", description = "대기중인 용돈 조르기 내역을 requestDate를 기준으로 최근 20개까지 가져온다.", tags = {"용돈"})
    @GetMapping("/child/pending")
    public ResponseEntity<?> getMyAllowancePendingRequestList(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.getMyAllowancePendingRequestList(user);
    }

    /* 2. 아이 : 용돈 조르기(승인/거절) 요청 조회 */
    @Operation(summary = "[아이] 용돈 조르기(승인/거절) 요청 조회", description = "승인/거절된 용돈 조르기 내역을 changedDate를 기준으로 최근 20개까지 가져온다.", tags = {"용돈"})
    @GetMapping("/child")
    public ResponseEntity<?> getMyAllowanceApprovedRequestList(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.getMyAllowanceApprovedRequestList(user);
    }

    /* 3. 아이 : 용돈 조르기 생성 */
    @Operation(summary = "[아이] 용돈 조르기 생성", description = "용돈 조르기 요청을 생성한다. pending중인 상태인 용돈 조르기 요청은 20개까지 생성 가능하다. ", tags = {"용돈"})
    @PostMapping("/child")
    public ResponseEntity<?> makeAllowanceRequest(@Validated @RequestBody RequestDto.Request request, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.makeAllowanceRequest(request);
    }

    /* 4. 부모 : 용돈 조르기(대기중) 요청 조회 */
    @Operation(summary = "[부모] 용돈 조르기(대기중) 요청 조회", description = "대기중인 용돈 조르기 내역을 requestDate를 기준으로 최근 20개까지 가져온다.", tags = {"용돈"})
    @GetMapping("/parent/pending")
    public ResponseEntity<?> getMyChildAllowancePendingRequestList(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.getMyChildAllowancePendingRequestList(user);
    }

    /* 5. 부모 : 용돈 조르기(승인,거절) 요청 조회 */
    @Operation(summary = "[부모] 용돈 조르기(승인,거절) 요청 조회", description = "승인/거절 된 용돈 조르기 내역을 changedDate를 기준으로 최근 20개까지 가져온다.", tags = {"용돈"})
    @GetMapping("/parent")
    public ResponseEntity<?> getMyChildAllowanceApprovedRequestList(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.getMyChildAllowanceApprovedRequestList(user);
    }

    /* 6. 부모 : 용돈 조르기 상태 변경(대기중 -> 승인/거절) */
    @Operation(summary = "[부모] 용돈 조르기 상태 변경", description = "대기 중인 용돈 조르기를 승인/거절", tags = {"용돈"})
    @PutMapping("/parent")
    public ResponseEntity<?> approveAllowanceRequest(@Validated @RequestBody RequestDto.Approve approve, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.updateRequestStatus(approve);
    }

    /* 7. 부모 : 용돈 보내기 */
    @Operation(summary = "[부모] 용돈 보내기", description = "용돈 보내기", tags = {"용돈"})
    @PostMapping("/send")
    public ResponseEntity<?> sendAllowance(@Validated @RequestBody RequestDto.Request request, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.sendAllowance(request);
    }

    /* 8. 부모 : 정기 용돈 조회 */
    @Operation(summary = "[부모] 정기 용돈 조회하기", description = "정기 용돈 조회", tags = {"용돈"})
    @GetMapping("")
    public ResponseEntity<?> getPeriodicAllowance(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.getPeriodicAllowance(user);
    }

    /* 9. 부모 : 정기 용돈 생성하기 */
    @Operation(summary = "[부모] 정기 용돈 생성하기", description = "정기 용돈 생성", tags = {"용돈"})
    @PostMapping("")
    public ResponseEntity<?> makePeriodicAllowance(@Validated @RequestBody RequestDto.Periodic periodic, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.makePeriodicAllowance(periodic);
    }

    /* 10. 부모 : 정기 용돈 수정 */
    @Operation(summary = "[부모] 정기 용돈 수정하기", description = "정기 용돈 수정", tags = {"용돈"})
    @PutMapping("")
    public ResponseEntity<?> updatePeriodicAllowance(@Validated @RequestBody RequestDto.UpdatePeriodic periodic, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.updatePeriodicAllowance(periodic);
    }

    /* 11. 부모 : 정기 용돈 삭제 */
    @Operation(summary = "[부모] 정기 용돈 삭제하기", description = "정기 용돈 삭제", tags = {"용돈"})
    @DeleteMapping("")
    public ResponseEntity<?> deletePeriodicAllowance(@Validated @RequestBody RequestDto.Allowance periodic, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return allowanceService.deletePeriodicAllowance(periodic);
    }
}
