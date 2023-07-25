package team.hanaro.hanamate.domain.Login;//package team.hanaro.hanamate.domain.Home;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class LoginController {
//    //기본페이지 요청 메서드
//    @GetMapping("/")
//    public String index(){
//        return "login"; //templates 폴더의 index.html을 찾아감.
////    }
////}
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import team.hanaro.hanamate.domain.Login.DefaultRes;
//import team.hanaro.hanamate.domain.Login.LoginReq;
//import team.hanaro.hanamate.global.ResponseMessage;
//import team.hanaro.hanamate.global.StatusCode;

//@RestController
//@RequestMapping
//public class LoginController {
//
//    @PostMapping("/")
//    public ResponseEntity login(@RequestBody LoginReq loginReq) {
//        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginReq), HttpStatus.OK);
//    }
//}