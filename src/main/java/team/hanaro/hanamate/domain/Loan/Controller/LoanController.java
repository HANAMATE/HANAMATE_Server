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
    public ResponseEntity<?> initLoanInfo(@Validated @RequestBody Errors errors){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.initLoanInfo();
    }

    //아이 - 대출 신청할떄 계산해줘서 보여주기 위함
    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@Validated @RequestBody Errors errors,LoanRequestDto.Calculate calculate, Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
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


    //부모, 아이 - 대출 신청 정보 보기 (대출이름, 대출금액, 메세지)
    @GetMapping("/applyInfo")
    public ResponseEntity<?> applyInfo(@Validated @RequestBody Errors errors, Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.applyInfo(authentication);

    }


    //대출 승인 - 부모 (이때, 시작날짜, 마감날짜, 기간이 정해지고, valid는 true로 변경)
    @PostMapping("/approve")
    public ResponseEntity<?> approve(@Validated @RequestBody LoanRequestDto.Approve approve,Errors errors, Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.approve(approve, authentication);
    }

    //대출 거절 => 삭제 - 부모 (대출 loans 테이블에서 삭제)
    @PostMapping("/refuse")
    public ResponseEntity<?> approve(@Validated @RequestBody Errors errors,Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.refuse(authentication);
    }

    //대출 상품 기본 정보 가져오기(진행중일때) (대출이름, 대출금액, 마감날짜)
    @GetMapping("/historyInfo")
    public ResponseEntity<?> historyInfo(@Validated @RequestBody Errors errors,Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.historyInfo(authentication);
    }


    //대출 상품 상세 정보 가져오기 (회차, 거래날짜(=상환날짜), 상환금액, 상환 성공여부)
    @GetMapping("/historydetailInfo")
    public ResponseEntity<?> historydetailInfo(@Validated @RequestBody Errors errors,Authentication authentication){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.historydetailInfo(authentication);
    }

    //TODO : 대출 상세 정보 위의 대출 상세 정보 가져오기 ( 이름, 총금액, 총이자, 총상환금액, 이자율, 상환방식, 기한)
    //대출 상품 기본 정보 가져오기랑 비슷하게 구현하면 됨.

}
