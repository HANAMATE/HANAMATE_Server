package team.hanaro.hanamate.domain.Allowance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Transactional
@Tag(name = "용돈", description = "용돈과 관련된 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/allowance")
public class AllowanceController {
    private final AllowanceService allowanceService;

    /* 1. 아이 : 용돈 조르기 요청 조회하기 */
    @Operation(summary = "[아이] 용돈 조르기 요청 조회", description = "용돈 조르기 한 내역 가져오기", tags = {"용돈"})
    @GetMapping("/request/list")
    public List<AllowanceResponseDto.ChildResponseList> GetAllowanceList(@RequestBody AllowanceRequestDto.ChildRequestList childRequestList) {
        return allowanceService.getChildRequestList(childRequestList);
    }

    /* 2. 아이 : 용돈 조르기 요청 */
    @Operation(summary = "[아이] 용돈 조르기 요청", description = "용돈 조르기", tags = {"용돈"})
    @PostMapping("/request")
    public String MakeAllowanceRequest(@RequestBody AllowanceRequestDto.ChildRequest childRequest) {
        return allowanceService.makeChildRequest(childRequest);
    }

    /* 3. 부모 : 용돈 조르기 승인 */
    @Operation(summary = "[부모] 용돈 조르기 승인", description = "용돈 조르기 승인", tags = {"용돈"})
    @PostMapping("/request/approve")
    public String ApproveAllowanceRequest(@RequestBody AllowanceRequestDto.ParentApprove parentApprove) {
        return allowanceService.approveRequest(parentApprove);
    }

    /* 4. 부모 : 용돈 보내기 */
    /* 5. 부모 : 정기 용돈 */
}
