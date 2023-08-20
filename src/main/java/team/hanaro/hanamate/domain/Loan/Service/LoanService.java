package team.hanaro.hanamate.domain.Loan.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.Allowance.AllowanceService;
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

import java.util.ArrayList;
import java.util.Collections;

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

    private final AllowanceService allowanceService;

    public ResponseEntity<?> apply(LoanRequestDto.Apply apply, Authentication authentication) {

        log.info("대출 서비스 들어옴");
        String userId = authentication.getName(); // Assuming the access token is stored in the "Authorization" header
        log.info("userId={}", userId); //test 회원 아이디를 가져오게 됨.
        Child now_user = childRepository.findByLoginId(userId).get();

        Loans loans=Loans.builder()
                .child(now_user)
                //부모가 아닌 값이 들어가면 500 오류가 뜸 TODO: 오류 처리해줘야함
                .parent(now_user.getMyParentList().get(0).getParent())
                .walletId(now_user.getMyWallet().getId())
                .loanName(apply.getLoanName())
                .loanAmount(Integer.valueOf(apply.getLoanAmount()))
                .duration(apply.getDuration()) //기한은 프론트에서 일단위로 빼서 나올 수 있게
                .loanMessage(apply.getLoanMessage())
                .interestRate(1)
                .paymentMethod("원금균등상환")
                .completed(false)
//TODO : 총이자, 총 상환금액, 월별 상환금액(납입원금+이자)  dto에 없으면 추가하기
//                .startDate(apply.getStartDate()) //TODO: 부모가 승인해줘야 생김.
//                .endDate(apply.getEndDate())
//                .duration(apply.getDuration())
                .loanMessage(apply.getLoanMessage())
                .build();

        loanRepository.save(loans);
        return response.success("대출 신청이 완료되었습니다.");

    }


    public ResponseEntity<?> initLoanInfo() {
        LoanResponseDto.initInfo initInfo = new LoanResponseDto.initInfo();
        initInfo.setInterestRate(1);
        initInfo.setPaymentMethod("원금균등상환");
        // TODO: 08.19 얼만큼 빌릴 수 있는지 제공해줘야 함.
        //개인의 정기 용돈의 금액을 id를 통해 가져와서 빌릴 수 있는 값을 보냄
//        initInfo.setCanAmount_3month();

        ResponseEntity<?> responseEntity= response.success(initInfo,"정상적으로 대출 초기 정보를 가져왔습니다.", HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<?> calculate(LoanRequestDto.Calculate calculate, Authentication authentication) {

        String userId = authentication.getName();
        Child now_user = childRepository.findByLoginId(userId).get();
        Integer allowance=  allowanceService.getPeriodicAllowanceByChildId(now_user);// ByChildID라는 함수를 가져왔다는 가정으로

            if (allowance==null){
                  ResponseEntity<?> responseEntity = response.fail("정기용돈이 존재하지 않아 대출 신청을 할 수 없습니다.", HttpStatus.BAD_REQUEST);
                  return responseEntity;
              }
            ArrayList<Integer> loanAmountList = new ArrayList<>();
            ArrayList<Integer> repaymentList = new ArrayList<>();
            Integer interestRate = calculate.getInterestRate();
            Integer sequence=calculate.getSequence();
            Integer loanAmount=calculate.getLoanAmount();
            Integer balance = loanAmount;

            Integer total_interestRate = 0;
            Integer interestRates = 0;
            Integer month_amount = loanAmount/sequence;
            for(int i=0;i<sequence;i++){
                interestRates = (int) Math.round((interestRate*0.01)/12 * balance);
                loanAmountList.add( interestRates); //(이자값이 회차마다 달라지기 때문에 각 배열에 넣음)
                total_interestRate+=interestRates; //총이자 계산
                balance-=month_amount; //원금에서의 잔액 계산
                repaymentList.add(interestRates+month_amount);
        }
        Integer total_loanAmount =loanAmount+total_interestRate; //총납입금액
        //빌릴 수 있는 최대 금액 계산
        Integer maxLoanAmount= Collections.max(repaymentList);
        if (allowance < maxLoanAmount) {
            ResponseEntity<?> responseEntity = response.fail("대출 한도가 넘어 대출 신청을 할 수 없습니다.", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        LoanResponseDto.CalculateResult calculateResult = new LoanResponseDto.CalculateResult();
        calculateResult.setLoanAmountList(loanAmountList);
        calculateResult.setRepaymentList(repaymentList);
        calculateResult.setTotal_interestRate(total_interestRate);
        calculateResult.setTotal_loanAmount(total_loanAmount);

        ResponseEntity<?> responseEntity= response.success(calculateResult,"정상적으로 대출 맞춤 정보를 계산하였습니다.", HttpStatus.OK);
        return responseEntity;


    }
}
