package team.hanaro.hanamate.domain.Signup;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.hanaro.hanamate.global.DefaultRes;
import team.hanaro.hanamate.global.ResponseMessage;
import team.hanaro.hanamate.global.StatusCode;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignupController {

    //생성자 주입
    private final SignupService signupService;

    // 회원가입 페이지 출력 요청
    @GetMapping("signup")
    public String saveForm(@RequestBody SignupReq signupReq){
        log.info("SignupReq = {}", signupReq);
        return "signup";
    }

    // 회원가입에서 save() 후 post 받아줌
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupReq signupReq) {
        System.out.println("MemberController.save");
        System.out.println("signupReq = " + signupReq);
        signupService.signup(signupReq);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.CREATED_USER), HttpStatus.OK);
    }

}
