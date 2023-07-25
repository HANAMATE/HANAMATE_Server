package team.hanaro.hanamate.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public void signup(MemberDTO memberDTO) {
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); //Repo 함수 이름은 반드시 save로 해야함!
        //repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
        //repository(interface)는 Jpa를 사용하는 형태


    }

//    public MemberDTO login(MemberDTO memberDTO) {
//        /*
//        1. 회원이 입력한 이메일로 DB에서 조회를 함.
//        2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
//         */
//
//        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
//        if (byMemberEmail.isPresent()){
//            //조회 결과가 있다.(해당 이메일을 가진 회원정보가 있다)
//            MemberEntity memberEntity =byMemberEmail.get();
//            if((memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword()))){
//                //비밀번호가 일치
//                //entity -> dto 변환 후 리턴
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//
//
//                return dto;
//
//            }
//            else{
//                //비밀번호 불일치(로그인실패)
//                return null;
//            }
//        }else{
//            //조회 결과가 없다(해당 이메일을 가진 회원이 없다)
//            return null;
//        }
//
//    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList= new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList){
            //방법1
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            //방법2
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
//            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}

