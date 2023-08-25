package team.hanaro.hanamate.domain.Loan.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    //아이 - 대출 신청할떄 계산해줘서 보여주기 위함
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody LoanRequestDto.Calculate calculate, @AuthenticationPrincipal UserDetails userDetails){
        return loanService.calculate(calculate, userDetails.getUsername());
    }
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@Validated @RequestBody LoanRequestDto.Apply apply, Errors errors, @AuthenticationPrincipal UserDetails userDetails) {
//        log.info("대출 컨트롤러 들어옴");
//        log.info("authentication={}", authentication);
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.apply(apply, userDetails.getUsername());
    }


    //부모, 아이 - 대출 신청 정보 보기 (대출이름, 대출금액, 메세지)
    @GetMapping("/applyInfo")
    public ResponseEntity<?> applyInfo( @AuthenticationPrincipal UserDetails userDetails){
        return loanService.applyInfo(userDetails.getUsername());

    }


    //대출 승인 - 부모 (이때, 시작날짜, 마감날짜, 기간이 정해지고, valid는 true로 변경)
    @PostMapping("/approve")
    public ResponseEntity<?> approve(@Validated @RequestBody LoanRequestDto.Approve approve, @AuthenticationPrincipal UserDetails userDetails){
        return loanService.approve(approve, userDetails.getUsername());
    }

    //대출 거절 => 삭제 - 부모 (대출 loans 테이블에서 삭제)
    @PostMapping("/refuse")
    public ResponseEntity<?> approve(@AuthenticationPrincipal UserDetails userDetails){
        return loanService.refuse(userDetails.getUsername());
    }

    //대출 상품 기본 정보 가져오기(진행중일때) (대출이름, 대출금액, 마감날짜)
    @GetMapping("/historyInfo")
    public ResponseEntity<?> historyInfo(@AuthenticationPrincipal UserDetails userDetails){
        return loanService.historyInfo(userDetails.getUsername());
    }


    //대출 상품 상세 정보 가져오기 (회차, 거래날짜(=상환날짜), 상환금액, 상환 성공여부)
    @GetMapping("/historydetailInfo")
    public ResponseEntity<?> historydetailInfo(@AuthenticationPrincipal UserDetails userDetails){
        return loanService.historydetailInfo(userDetails.getUsername());
    }

    //TODO : 대출 상세 정보 위의 대출 상세 정보 가져오기 ( 이름, 총금액, 총이자, 총상환금액, 이자율, 상환방식, 기한)
    //대출 상품 기본 정보 가져오기랑 비슷하게 구현하면 됨.

}
