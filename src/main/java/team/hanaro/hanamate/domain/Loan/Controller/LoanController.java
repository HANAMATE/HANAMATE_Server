package team.hanaro.hanamate.domain.Loan.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.Loan.Dto.LoanRequestDto;
import team.hanaro.hanamate.domain.Loan.Service.LoanService;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.global.Response;

@Slf4j
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor

public class LoanController {
    private final LoanService loanService;
    private final Response response;

    //고정이자, 균등상환방식 사용자에게 정보 미리 전달
    @GetMapping("/applyForm")
    public ResponseEntity<?> initLoanInfo(){
        return loanService.initLoanInfo();
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate( @RequestBody LoanRequestDto.Calculate calculate, Authentication authentication){
        return loanService.calculate(calculate, authentication);
    }
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@Validated @RequestBody LoanRequestDto.Apply apply, Errors errors, Authentication authentication) {
//        log.info("대출 컨트롤러 들어옴");
//        log.info("authentication={}", authentication);
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.apply(apply, authentication);
    }



    //부모, 아이 - 대출 신청 정보 보기
    @GetMapping("/applyInfo")
    public ResponseEntity<?> applyInfo( Authentication authentication){
        return loanService.applyInfo(authentication);

    }


    //대출 승인 - 부모
    @PostMapping("/approve")
    public ResponseEntity<?> approve(@Validated @RequestBody LoanRequestDto.Approve approve, Authentication authentication){
        return loanService.approve(approve, authentication);
    }

    //대출 거절 => 삭제 - 부모
    @PostMapping("/refuse")
    public ResponseEntity<?> approve(Authentication authentication){
        return loanService.refuse(authentication);
    }

    @GetMapping("/historyInfo")
    public ResponseEntity<?> historyInfo(Authentication authentication){
        return loanService.historyInfo(authentication);
    }


}
