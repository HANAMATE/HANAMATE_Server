package team.hanaro.hanamate.domain.Login;//package team.hanaro.hanamate.domain.Home;

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

import javax.servlet.http.HttpSession;
@Slf4j
@RequiredArgsConstructor
//@Controller
@RestController
public class LoginController {

    //생성자 주입
    private final LoginService loginService;

    @GetMapping("/")
//    @ResponseBody
    public String loginForm(@RequestBody LoginReq loginReq){
        log.info("LoginReq = {}", loginReq);
        return "login";
    }


    @PostMapping("/")
//    @ResponseBody
    public ResponseEntity login(@RequestBody LoginReq loginReq, HttpSession session){
        LoginReq loginResult = loginService.login(loginReq);
        if (loginResult != null){
            //login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginResult), HttpStatus.OK);
        }else{
            //login 실패
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.LOGIN_FAIL), HttpStatus.OK);

        }

    }
}