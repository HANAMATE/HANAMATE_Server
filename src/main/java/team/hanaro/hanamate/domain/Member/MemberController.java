package team.hanaro.hanamate.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.Login.DefaultRes;
import team.hanaro.hanamate.global.ResponseMessage;
import team.hanaro.hanamate.global.StatusCode;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
@Controller
//@RestController
@RequiredArgsConstructor
public class MemberController {

    //생성자 주입
    private final MemberService memberService;

    //로그인
    @GetMapping("/")
    public String loginForm(){
        return "login"; //templates 폴더의 login.html을 찾아감.
    }
//    public Map<String, Object> firstController(){
//        return memberService.getFirstData();
//    }


    @PostMapping("/")
//    @ResponseBody
    public ResponseEntity login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
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

//    @PostMapping("/")
////    @RequestBody
//    public ResponseEntity login(@RequestParam("memberEmail") String memberEmail,
//                                @RequestParam("memberPassword") String memberPassword) {
//        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS), HttpStatus.OK);
//    }


    // 회원가입 페이지 출력 요청
    @GetMapping("/signup")
    public String saveForm() {
        return "signup";
    }


    // 회원가입에서 save() 후 post 받아줌
    @PostMapping("/signup")
//    public String save(@RequestParam("memberEmail") String memberEmail,
//                       @RequestParam("memberPassword") String memberPassword,
//                       @RequestParam("memberName") String memberName) {
    public String signup(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
//        System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
        System.out.println("memberDTO = " + memberDTO);
        memberService.signup(memberDTO);
        return "login";
    }






    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        //어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";

    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";

    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+ memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    //id값도 같이 넘겨받기 때문에 PathVariable 사용해야 함.
    public String delete(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }


}
