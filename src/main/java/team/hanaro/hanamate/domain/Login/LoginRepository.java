//package team.hanaro.hanamate.domain.Login;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import team.hanaro.hanamate.domain.Member.MemberEntity;
//
//import java.util.Optional;
//
//public interface LoginRepository extends JpaRepository<MemberEntity, Long> {
//    //save같은 메서드는 JpaRepository에서 제공하는 것을 썼는데
//    //특정 쿼리문을 수행하기 위해서는 따로 정의해줘야함. 인터페이스이기 떄문에 추상 메서드로 정의해줘야함.
//
//    //이메일로 회원 정보 조회(select * from member_table where member_email=?)
//    //Optional은 자바에서 제공하는 클래스인데 기본적으로 Optional에 감싸서 객체를 넘겨줌
////    Optional<MemberEntity> findByMemberEmail(String memberEmail);
//    Optional<MemberEntity> findByLoginId(String memberId);
//}