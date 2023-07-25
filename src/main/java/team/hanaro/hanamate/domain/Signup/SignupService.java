package team.hanaro.hanamate.domain.Signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Member.MemberEntity;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final SignupRepository signupRepository;

    public void signup(SignupReq signupReq) {
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(signupReq);
        signupRepository.save(memberEntity); //Repo 함수 이름은 반드시 save로 해야함!
        //repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
        //repository(interface)는 Jpa를 사용하는 형태


    }



}
