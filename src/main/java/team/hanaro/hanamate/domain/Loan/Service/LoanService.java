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
import team.hanaro.hanamate.domain.Loan.Repository.LoanHistoryRepository;
import team.hanaro.hanamate.domain.Loan.Repository.LoanRepository;
import team.hanaro.hanamate.domain.MyWallet.Repository.MyWalletRepository;
import team.hanaro.hanamate.domain.User.Repository.ChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.User.Service.CustomUserDetailsService;
import team.hanaro.hanamate.domain.User.Service.UsersService;
import team.hanaro.hanamate.entities.*;
import team.hanaro.hanamate.global.Response;
import team.hanaro.hanamate.jwt.JwtTokenProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final UsersService usersService;
    private final LoanHistoryRepository loanHistoryRepository;

    public ResponseEntity<?> initLoanInfo() {
        LoanResponseDto.initInfo initInfo = new LoanResponseDto.initInfo();
        initInfo.setInterestRate(1);
        initInfo.setPaymentMethod("원금균등상환");
        // TODO: 08.19 얼만큼 빌릴 수 있는지 제공해줘야 함.
        //개인의 정기 용돈의 금액을 id를 통해 가져와서 빌릴 수 있는 값을 보냄
//        initInfo.setCanAmount_3month();

        return response.success(initInfo, "정상적으로 대출 초기 정보를 가져왔습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> apply(LoanRequestDto.Apply apply, Authentication authentication) {

        log.info("대출 서비스 들어옴");
        String userId = authentication.getName();
        Child now_user = childRepository.findByLoginId(userId).get();

        Loans loans = Loans.builder()
                .child(now_user)
                //부모가 아닌 값이 들어가면 500 오류가 뜸 TODO: 오류 처리해줘야함
                .parent(now_user.getMyParentList().get(0).getParent())
                .walletId(now_user.getMyWallet().getId())
                .loanName(apply.getLoanName())
                .loanAmount(Integer.valueOf(apply.getLoanAmount()))
                .loanMessage(apply.getLoanMessage())
                .interestRate(1)
                .paymentMethod("원금균등상환")
                .completed(false)
                .total_interestRate(apply.getTotal_interestRate())
                .total_repaymentAmount(apply.getTotal_repaymentAmount())
                .sequence(apply.getSequence())
                .build();


        loanRepository.save(loans);


        return response.success("대출 신청이 완료되었습니다.");
    }


    public ResponseEntity<?> calculate(LoanRequestDto.Calculate calculate, Authentication authentication) {

        String userId = authentication.getName();
        Child now_user = childRepository.findByLoginId(userId).get();
        Integer allowance = allowanceService.getPeriodicAllowanceByChildId(now_user);// ByChildID라는 함수를 가져왔다는 가정으로

        if (allowance == null) {
            return response.fail("정기용돈이 존재하지 않아 대출 신청을 할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        ArrayList<Integer> loanAmountList = new ArrayList<>();
        ArrayList<Integer> repaymentList = new ArrayList<>();
        Integer interestRate = calculate.getInterestRate();
        Integer sequence = calculate.getSequence();
        Integer loanAmount = calculate.getLoanAmount();
        Integer balance = loanAmount;

        Integer total_interestRate = 0;
        Integer interestRates = 0;
        Integer month_amount = loanAmount / sequence;
        for (int i = 0; i < sequence; i++) {
            interestRates = (int) Math.round((interestRate * 0.01) / 12 * balance);
            loanAmountList.add(interestRates); //(이자값이 회차마다 달라지기 때문에 각 배열에 넣음)
            total_interestRate += interestRates; //총이자 계산
            balance -= month_amount; //원금에서의 잔액 계산
            repaymentList.add(interestRates + month_amount);
        }
        Integer total_loanAmount = loanAmount + total_interestRate; //총납입금액
        //빌릴 수 있는 최대 금액 계산
        Integer maxLoanAmount = Collections.max(repaymentList);
        if (allowance < maxLoanAmount) {
            ResponseEntity<?> responseEntity = response.fail("대출 한도가 넘어 대출 신청을 할 수 없습니다.", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        LoanResponseDto.CalculateResult calculateResult = new LoanResponseDto.CalculateResult();
        calculateResult.setLoanAmountList(loanAmountList);
        calculateResult.setRepaymentList(repaymentList);
        calculateResult.setTotal_interestRate(total_interestRate);
        calculateResult.setTotal_repaymentAmount(total_loanAmount);

        return response.success(calculateResult, "정상적으로 대출 맞춤 정보를 계산하였습니다.", HttpStatus.OK);


    }

    //부모 - 아이 화면에서 대출 신청 정보 가져오기 (대출에 관련된 부모, 아이만 해당 정보를 가져올 수 있음 아니면 에러남)
    public ResponseEntity<?> applyInfo(Authentication authentication) {
        String userId = authentication.getName();
        User now_user = usersRepository.findByLoginId(userId).get();
        LoanResponseDto.applyInfo applyInfo = new LoanResponseDto.applyInfo();
        if (now_user.getUserType().equals("Child")) {
            Child now_child = childRepository.findByLoginId(userId).get();
            Loans now_loan = loanRepository.findByChild(now_child).get();
            applyInfo.setLoanName(now_loan.getLoanName());
            applyInfo.setLoanAmount(now_loan.getLoanAmount());
            applyInfo.setLoanMessage(now_loan.getLoanMessage());
        } else {
            Parent now_parent = parentRepository.findByLoginId(userId).get();
            Loans now_loan = loanRepository.findByParent(now_parent).get();
            applyInfo.setLoanName(now_loan.getLoanName());
            applyInfo.setLoanAmount(now_loan.getLoanAmount());
            applyInfo.setLoanMessage(now_loan.getLoanMessage());
        }

        return response.success(applyInfo, "정상적으로 대출 신청 정보를 가져왔습니다.", HttpStatus.OK);

    }

    //history 정보에 값 넣기


    public ResponseEntity<?> approve(LoanRequestDto.Approve approve, Authentication authentication) {
        String userId = authentication.getName();
        Parent now_parent = parentRepository.findByLoginId(userId).get();
        Optional<Loans> optionalLoans = loanRepository.findByParent(now_parent);


        if (optionalLoans.isPresent()) {
            Loans existingLoan = optionalLoans.get();

            existingLoan.setValid(true);
            existingLoan.setStartDate(approve.getStartDate());
            existingLoan.setEndDate(approve.getEndDate());
            existingLoan.setDuration(approve.getDuration());
            loanRepository.save(existingLoan); // 새로운 객체로 업데이트

            //TODO 0821 고민 -> 전자로 구현함.
            //이때 history값을 다 넣어주고 용돈 안내면 false로 하고, 내면 true로 하고, 프론트에서 보여줄때는 true인것만 보여주는 식으로.. 하면 될거 같은데
            //용돈을 낼때 history값이 들어가게 하는게 로직으로는 맞아서 고민 중
            Integer interestRate = existingLoan.getInterestRate();
            Integer sequence = existingLoan.getSequence();
            Integer loanAmount = existingLoan.getLoanAmount();
            Integer balance = loanAmount;

            Integer total_interestRate = 0;
            Integer interestRates = 0;
            Integer month_amount = loanAmount / sequence;

            for (int i = 0; i < sequence; i++) {
                interestRates = (int) Math.round((interestRate * 0.01) / 12 * balance);
                total_interestRate += interestRates; //총이자 계산
                balance -= month_amount; //원금에서의 잔액 계산

                LoanHistory loanHistory = LoanHistory.builder()
                        .sequence_time(i + 1)
                        .transactionDate(approve.getStartDate()) //TODO: 월 단위로 바뀌도록 설정해야함.일단 임시로 시작날짜로 해놓음
                        .repaymentAmount(interestRates + month_amount)
                        .success(false)
                        .loans(existingLoan)
                        .build();
                loanHistoryRepository.save(loanHistory);
            }


        }


//        calculate(calculate, authentication);

//        LoanHistory loanHistory= LoanHistory.builder()


        return response.success(null, "정상적으로 대출을 승인했습니다.", HttpStatus.OK);

    }


    public ResponseEntity<?> refuse(Authentication authentication) {
        String userId = authentication.getName();
        Parent now_parent = parentRepository.findByLoginId(userId).get();
        Long now_loanId = loanRepository.findByParent(now_parent).get().getLoanId();

        loanRepository.deleteById(now_loanId);

        return response.success(null, "정상적으로 대출이 거절되어 요청이 삭제됐습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> historyInfo(Authentication authentication) {
        String userId = authentication.getName();
        User now_user = usersRepository.findByLoginId(userId).get();

        if (now_user.getUserType().equals("Child")) {
            Child now_child = childRepository.findByLoginId(userId).get();
            Optional<List<Loans>> optionalLoans = loanRepository.findAllByChildAndValidIsTrue(now_child);

            if (optionalLoans.isPresent()) {
                List<Loans> loanHistories = optionalLoans.get();
                List<LoanResponseDto.historyInfo> historyInfoList = new ArrayList<>();
                for (Loans loanHistory : loanHistories) {
                    LoanResponseDto.historyInfo historyInfoDto = new LoanResponseDto.historyInfo(loanHistory);
                    historyInfoList.add(historyInfoDto);
                }
                return response.success(historyInfoList, "나의 대출 내역 조회에 성공했습니다", HttpStatus.OK);
            } else {
                return response.fail("나의 대출 내역 조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
            }
        } else {
            Parent now_parent = parentRepository.findByLoginId(userId).get();
            Optional<List<Loans>> optionalLoans = loanRepository.findAllByParentAndValidIsTrue(now_parent);

            if (optionalLoans.isPresent()) {
                List<Loans> loanHistories = optionalLoans.get();
                List<LoanResponseDto.historyInfo> historyInfoList = new ArrayList<>();
                for (Loans loanHistory : loanHistories) {
                    LoanResponseDto.historyInfo historyInfoDto = new LoanResponseDto.historyInfo(loanHistory);
                    historyInfoList.add(historyInfoDto);
                }
                return response.success(historyInfoList, "아이의 대출 내역 조회에 성공했습니다", HttpStatus.OK);
            } else {
                return response.fail("아이의 대출 내역 조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
//        return response.success(null, "대출 조회 내역이 없습니다", HttpStatus.OK);
    }


    public ResponseEntity<?> historydetailInfo(Authentication authentication) {
        String userId = authentication.getName();
        User now_user = usersRepository.findByLoginId(userId).get();

        if (now_user.getUserType().equals("Child")) {
            Child now_child = childRepository.findByLoginId(userId).get();
            Optional<Loans> loans = loanRepository.findByChild(now_child);

//            Optional<List<Loans>> loans = loanRepository.findAllByChildAndValidIsTrue(now_child);
            Optional<List<LoanHistory>> optionalLoans = loanHistoryRepository.findAllByLoansAndSuccessIsTrue(loans);


            if (optionalLoans.isPresent()) {
                List<LoanHistory> loanHistories = optionalLoans.get();
                List<LoanResponseDto.historydetailInfo> historydetailInfoList = new ArrayList<>();
                for (LoanHistory loanHistory : loanHistories) {
                    LoanResponseDto.historydetailInfo historydetailInfo = new LoanResponseDto.historydetailInfo(loanHistory);
                    historydetailInfoList.add(historydetailInfo);
                }
                return response.success(historydetailInfoList, "나의 대출 상세 내역 조회에 성공했습니다", HttpStatus.OK);
            } else {
                return response.fail("나의 대출 상세 내역 조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
//        else{
//            Parent now_parent = parentRepository.findByLoginId(userId).get();
//            Optional<List<Loans>> loans = loanRepository.findAllByParentAndValidIsTrue(now_parent);
//            Optional<List<LoanHistory>> optionalLoans = loanHistoryRepository.findAllByLoansAndSuccessIsTrue(loans);
//
//            if (optionalLoans.isPresent()) {
//                List<LoanHistory> loanHistories = optionalLoans.get();
//                List<LoanResponseDto.historydetailInfo> historydetailInfoList = new ArrayList<>();
//                for (LoanHistory loanHistory : loanHistories) {
//                    LoanResponseDto.historydetailInfo historydetailInfo = new LoanResponseDto.historydetailInfo(loanHistory);
//                    historydetailInfoList.add(historydetailInfo);
//                }
//                return response.success(historydetailInfoList, "나의 대출 상세 내역 조회에 성공했습니다", HttpStatus.OK);
//            } else {
//                return response.fail("나의 대출 상세 내역 조회에 실패했습니다.", HttpStatus.BAD_REQUEST);
//            }
//        }
//
//
        return response.success(null, "대출 조회 내역이 없습니다", HttpStatus.OK);

    }
}
