package team.hanaro.hanamate.domain.Loan.Service;


import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Loan.Dto.LoanRequestDto;
import team.hanaro.hanamate.domain.Loan.Repository.LoanRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.User.Service.CustomUserDetailsService;
import team.hanaro.hanamate.entities.Loans;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;
import team.hanaro.hanamate.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final Response response;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final UsersRepository usersRepository;

    public ResponseEntity<?> apply(LoanRequestDto.Apply apply, HttpServletRequest request) {

        // 1. Access Token 가져오기
        String accessToken = request.getHeader("Authorization"); // Assuming the access token is stored in the "Authorization" header

        log.info("accessToken={}", accessToken);
        // 2. Access Token 검증
        if (!jwtTokenProvider.validateToken(accessToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 3. Access Token 에서 User Id를 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String userId = authentication.getName(); // User Id를 가져옵니다.

        log.info("userId={}", userId); //test 회원 아이디를 가져오게 됨.

        // 3. UserDetailsService를 통해 UserDetails 객체 가져오기
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

        // 4. UserDetails 객체에서 필요한 정보 가져와서 대출 신청 로직 수행
        // 예시로 UserDetails의 getUsername 메소드를 호출하여 사용자 이름을 가져오는 예시를 포함합니다.
        String username = userDetails.getUsername();
        // ... 대출 신청과 관련된 로직 수행 ...


        User now_user = usersRepository.findByLoginId(userId).get();
        Loans loans=Loans.builder()
                .children(now_user)
                .loanName(apply.getLoanName())
                .loanAmount(Integer.valueOf(apply.getLoanAmount()))
                .interestRate(1)
                .completed(false)
//                .parent(to_parent) //TODO : 부모와 아이가 매핑되어있는 DB에서 부모 id 가져오기
//                .startDate(apply.getStartDate()) //TODO: 부모가 승인해줘야 생김.
//                .endDate(apply.getEndDate())
                .duration(apply.getDuration())
                .loanMessage(apply.getLoanMessage())
                .build();

        loanRepository.save(loans);
        return response.success("대출 신청이 완료되었습니다.");

    }
}
