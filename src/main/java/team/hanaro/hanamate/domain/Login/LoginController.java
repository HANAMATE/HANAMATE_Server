package team.hanaro.hanamate.domain.Login;//package team.hanaro.hanamate.domain.Home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.hanaro.hanamate.domain.Jwt.JwtTokenUtil;
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
    public String login(@RequestBody LoginReq loginReq){
        LoginReq loginResult = loginService.login(loginReq);
        if (loginResult != null){
            //login 성공
            log.info("로그인 성공함");

//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginResult), HttpStatus.OK);
            String secretKey = "vlweyvbsyt9v7zq57tejmnvuyzblycfpqye08f7mgva9xkha";
            long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

            log.info("loginResult.getLoginId()={}", loginResult.getLoginId());
            log.info("secretKey={}", secretKey);
            log.info("expireTimeMs={}", expireTimeMs);
            String jwtToken = JwtTokenUtil.createToken(loginResult.getLoginId(), secretKey, expireTimeMs);
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, jwtToken), HttpStatus.OK);
//            return"성공";
            return jwtToken;
        }else{
            //login 실패
            return"실패";
//            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.LOGIN_FAIL), HttpStatus.OK);

        }

    }
}