package team.hanaro.hanamate.domain.Loan.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.hanaro.hanamate.domain.Loan.Dto.LoanRequestDto;
import team.hanaro.hanamate.domain.Loan.Service.LoanService;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.global.Response;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor

public class LoanController {
    private final LoanService loanService;
    private final Response response;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@Validated @RequestBody LoanRequestDto.Apply apply, Errors errors, HttpServletRequest request) {
        log.info("대출 컨트롤러 들어옴");
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return loanService.apply(apply, request);
    }


//    @GetMapping("/loan/apply")
//    public ResponseEntity<?> apply(@ResponseBody LoanRequestDto.Apply apply, Errors errors){
//
//    }


}