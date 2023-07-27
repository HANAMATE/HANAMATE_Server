package team.hanaro.hanamate.domain.Login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Member.MemberEntity;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginReq login(LoginReq loginReq) {
        /*
        1. 회원이 입력한 이메일로 DB에서 조회를 함.
        2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */

        Optional<MemberEntity> byLoginId = loginRepository.findByLoginId(loginReq.getLoginId());
        if (byLoginId.isPresent()){
            //조회 결과가 있다.(해당 이메일을 가진 회원정보가 있다)
            MemberEntity memberEntity =byLoginId.get();
            if((memberEntity.getLoginPw().equals(loginReq.getLoginPw()))){
                //비밀번호가 일치
                //entity -> dto 변환 후 리턴
                LoginReq dto = LoginReq.toMemberDTO(memberEntity);
                log.info("dto={}" ,dto);
                return dto;

            }
            else{
                //비밀번호 불일치(로그인실패)
                return null;
            }
        }else{
            //조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }

    }


}
