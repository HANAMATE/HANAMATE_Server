package team.hanaro.hanamate.domain.Loan.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Loan.Dto.LoanRequestDto;
import team.hanaro.hanamate.domain.Loan.Repository.LoanRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.User.Service.CustomUserDetailsService;
import team.hanaro.hanamate.entities.Loans;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;
import team.hanaro.hanamate.jwt.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final Response response;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final UsersRepository usersRepository;

    public ResponseEntity<?> apply(LoanRequestDto.Apply apply, Authentication authentication) {

        log.info("대출 서비스 들어옴");
        String userId = authentication.getName(); // Assuming the access token is stored in the "Authorization" header
        log.info("userId={}", userId); //test 회원 아이디를 가져오게 됨.
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
//                .duration(apply.getDuration())
                .loanMessage(apply.getLoanMessage())
                .build();

        loanRepository.save(loans);
        return response.success("대출 신청이 완료되었습니다.");

    }
}
