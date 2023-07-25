package team.hanaro.hanamate.domain.Login;//package team.hanaro.hanamate.domain.Home;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.global.ResponseMessage;
import team.hanaro.hanamate.global.StatusCode;

import javax.servlet.http.HttpSession;
@RequiredArgsConstructor
@Controller
public class LoginController {
    //생성자 주입
    private final LoginService loginService;


    @PostMapping("/")
    @ResponseBody
    public ResponseEntity login(@RequestBody LoginReq loginReq, HttpSession session){
        LoginReq loginResult = loginService.login(loginReq);
        if (loginResult != null){
            //login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());

            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);

        }else{
            return null;
            //login 실패
//            return "login";
        }

    }
}