package team.hanaro.hanamate.domain.Allowance;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/allowance")
public class AllowanceController {
    private final AllowanceService allowanceService;

    public AllowanceController(AllowanceService allowanceService) {
        this.allowanceService = allowanceService;
    }

    /* 1. 아이 : 용돈 조르기 요청 조회하기 */
    @GetMapping("/request/list")
    public AllowanceResponseDto.ChildResponseList GetAllowanceList(@RequestBody AllowanceRequestDto.ChildRequestList childRequestList) {
        return allowanceService.getChildRequestList(childRequestList);
    }

    /* 2. 아이 : 용돈 조르기 요청 */
    @PostMapping("/request")
    public String MakeAllowanceRequest(@RequestBody AllowanceRequestDto.ChildRequest childRequest) {
        return allowanceService.makeChildRequest(childRequest);
    }
    /* 3. 부모 : 용돈 조르기 승인 */
    /* 4. 부모 : 용돈 보내기 */
    /* 5. 부모 : 정기 용돈 */
}
