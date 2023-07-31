//package team.hanaro.hanamate.domain.Signup;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class SignupService {
//
//    private final SignupRepository signupRepository;
//
////    // Spring Security를 사용한 로그인 구현 시 사용
////     private final BCryptPasswordEncoder encoder;
//
//    public void signup(SignupReq signupReq) {
//        //1. dto -> entity 변환
//        //2. repository의 save 메서드 호출
//        signupRepository.save(signupReq.toMemberEntity()); //Repo 함수 이름은 반드시 save로 해야함!
////        signupRepository.save(signupReq.toMemberEntity(encoder.encode(signupReq.getLoginPw())));
//        //repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
//        //repository(interface)는 Jpa를 사용하는 형태
//
//
//    }
//
//
//
//}
