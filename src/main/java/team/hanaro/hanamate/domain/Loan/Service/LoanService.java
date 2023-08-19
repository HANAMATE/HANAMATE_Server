package team.hanaro.hanamate.domain.Loan.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Loan.Dto.LoanRequestDto;
import team.hanaro.hanamate.domain.Loan.Dto.LoanResponseDto;
import team.hanaro.hanamate.domain.Loan.Repository.LoanRepository;
import team.hanaro.hanamate.domain.MyWallet.Repository.MyWalletRepository;
import team.hanaro.hanamate.domain.User.Repository.ChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.User.Service.CustomUserDetailsService;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.Loans;
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
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final MyWalletRepository myWalletRepository;

    public ResponseEntity<?> apply(LoanRequestDto.Apply apply, Authentication authentication) {

        log.info("대출 서비스 들어옴");
        String userId = authentication.getName(); // Assuming the access token is stored in the "Authorization" header
        log.info("userId={}", userId); //test 회원 아이디를 가져오게 됨.
        Child now_user = childRepository.findByLoginId(userId).get();

        Loans loans=Loans.builder()
                .child(now_user)
                //부모가 아닌 값이 들어가면 500 오류가 뜸 TODO: 오류 처리해줘야함
                .parent(now_user.getMyParentList().get(0).getParentId())
                .walletId(now_user.getMyWallet().getId())
                .loanName(apply.getLoanName())
                .loanAmount(Integer.valueOf(apply.getLoanAmount()))
                .duration(apply.getDuration())
                .loanMessage(apply.getLoanMessage())
                .interestRate(1)
                .paymentMethod("원리금균등상환")
                .completed(false)
//                .parent(to_parent) //TODO : 부모와 아이가 매핑되어있는 DB에서 부모 id 가져오기
//                .startDate(apply.getStartDate()) //TODO: 부모가 승인해줘야 생김.
//                .endDate(apply.getEndDate())
                .build();

        loanRepository.save(loans);
        return response.success("대출 신청이 완료되었습니다.");

    }


    public ResponseEntity<?> initLoanInfo() {
        LoanResponseDto.initInfo initInfo = new LoanResponseDto.initInfo();
        initInfo.setInterestRate(1);
        initInfo.setPaymentMethod("원리금균등상환");

        ResponseEntity<?> responseEntity= response.success(initInfo,"정상적으로 대출 초기 정보를 가져왔습니다.", HttpStatus.OK);
        return responseEntity;
    }
}
