//package team.hanaro.hanamate.domain.Member;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//@Slf4j
//@Controller
////@RestController
//@RequiredArgsConstructor
//public class MemberController {
//
//    //생성자 주입
//    private final MemberService memberService;
//
//    @GetMapping("/member/")
//    public String findAll(Model model){
//        List<MemberDTO> memberDTOList = memberService.findAll();
//        //어떠한 html로 가져갈 데이터가 있다면 model 사용
//        model.addAttribute("memberList", memberDTOList);
//        return "list";
//
//    }
//
//    @GetMapping("/member/{id}")
//    public String findById(@PathVariable Long id, Model model){
//        MemberDTO memberDTO = memberService.findById(id);
//        model.addAttribute("member", memberDTO);
//        return "detail";
//    }
//
//    @GetMapping("/member/update")
//    public String updateForm(HttpSession session, Model model){
//        String myEmail = (String)session.getAttribute("loginEmail");
//        MemberDTO memberDTO = memberService.updateForm(myEmail);
//        model.addAttribute("updateMember", memberDTO);
//        return "update";
//
//    }
//
//    @PostMapping("/member/update")
//    public String update(@ModelAttribute MemberDTO memberDTO){
//        memberService.update(memberDTO);
//        return "redirect:/member/"+ memberDTO.getMemberId();
//    }
//
//    @GetMapping("/member/delete/{id}")
//    //id값도 같이 넘겨받기 때문에 PathVariable 사용해야 함.
//    public String delete(@PathVariable Long id){
//        memberService.deleteById(id);
//        return "redirect:/member/";
//    }
//
//    @GetMapping("/member/logout")
//    public String logout(HttpSession session){
//        session.invalidate();
//        return "index";
//    }
//
//
//}
