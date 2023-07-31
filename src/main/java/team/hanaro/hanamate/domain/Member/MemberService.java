//package team.hanaro.hanamate.domain.Member;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class MemberService {
//    private final MemberRepository memberRepository;
//
//    public List<MemberDTO> findAll() {
//        List<MemberEntity> memberEntityList = memberRepository.findAll();
//        List<MemberDTO> memberDTOList= new ArrayList<>();
//        for (MemberEntity memberEntity : memberEntityList){
//            //방법1
//            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            //방법2
////            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
////            memberDTOList.add(memberDTO);
//        }
//        return memberDTOList;
//
//    }
//
//    public MemberDTO findById(Long id) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if(optionalMemberEntity.isPresent()){
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
////            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
//        }else{
//            return null;
//        }
//    }
//
//
////    //엔티티로 반환함..(JWT에 쓰이는 건데 Entity로 해도 되는건지는 좀 의문이 듬)
////    public MemberEntity getLoginMemberByLoginId(String loginId) {
////        if(loginId == null) return null;
////
////        Optional<MemberEntity> optionalUser = memberRepository.findByLoginId(loginId);
////        if(optionalUser.isEmpty()) return null;
////
////        return optionalUser.get();
////    }
//
//
//    public MemberDTO updateForm(String myLoginId) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByLoginId(myLoginId);
//        if(optionalMemberEntity.isPresent()){
//            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
//        }else{
//            return null;
//        }
//    }
//
//    public void update(MemberDTO memberDTO) {
//        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
//    }
//
//    public void deleteById(Long id) {
//        memberRepository.deleteById(id);
//    }
//}
//
