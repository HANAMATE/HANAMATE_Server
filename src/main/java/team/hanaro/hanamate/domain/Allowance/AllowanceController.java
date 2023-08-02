package team.hanaro.hanamate.domain.Allowance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /* 3. 부모 : 용돈 조르기 승인 */
    /* 4. 부모 : 정기 용돈 */
    /* 5. 부모 : 용돈 보내기 */
}
