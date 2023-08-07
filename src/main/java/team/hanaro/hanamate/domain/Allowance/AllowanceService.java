package team.hanaro.hanamate.domain.Allowance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.MyWallet.MyWalletRepository;
import team.hanaro.hanamate.domain.MyWallet.MyWalletTransactionsRepository;
import team.hanaro.hanamate.domain.User.Dto.Response;
import team.hanaro.hanamate.entities.Member;
import team.hanaro.hanamate.entities.Requests;
import team.hanaro.hanamate.entities.Transactions;
import team.hanaro.hanamate.entities.Wallets;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
    private final MyWalletRepository myWalletRepository;
    private final MyWalletTransactionsRepository myWalletTransactionsRepository;
    private final MemberRepository memberRepository;
    private final Response response;

    public ResponseEntity<?> getChildRequestList(AllowanceRequestDto.ChildRequestList childRequestList) {
        System.out.println(childRequestList.getUserId());
        Optional<List<Requests>> myRequests = allowanceRepository.findAllByRequesterId(childRequestList.getUserId());
        if (myRequests.isPresent()) {
            List<Requests> requestsList = myRequests.get();
            List<AllowanceResponseDto.ChildResponseList> childResponseListArrayList = new ArrayList<>();
            for (Requests request : requestsList) {
                AllowanceResponseDto.ChildResponseList childResponseList = new AllowanceResponseDto.ChildResponseList(request);
                childResponseListArrayList.add(childResponseList);
            }
            return response.success(childResponseListArrayList, "용돈 조르기 요청 리스트 조회에 성공했습니다.", HttpStatus.OK);
        } else {
            return response.fail("대기 상태의 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> makeChildRequest(AllowanceRequestDto.ChildRequest childRequest) {
        Calendar cal = Calendar.getInstance();
        Timestamp requestDate = new Timestamp(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 7);
        Timestamp expiredDate = new Timestamp(cal.getTimeInMillis());

        if (childRequest.getUserId() == null || childRequest.getAllowanceAmount() == null) {
            return response.fail("용돈 조르기 요청에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        Requests requests = Requests.builder()
                .targetId(0L) //TODO: 부모 아이디로 설정
                .requesterId(childRequest.getUserId())
                .allowanceAmount(childRequest.getAllowanceAmount())
                .requestDate(requestDate)
                .expirationDate(expiredDate)
                .requestDescription(childRequest.getRequestDescription())
                .build();
        allowanceRepository.save(requests);
        allowanceRepository.flush();
        return response.success("용돈 조르기에 성공했습니다.");
    }

    public ResponseEntity<?> approveRequest(AllowanceRequestDto.ParentApprove parentApprove) {
        Optional<Requests> request = allowanceRepository.findByRequestId(parentApprove.getRequestId());
        Optional<Wallets> parentWallet = myWalletRepository.findById(parentApprove.getWalletId());

        if (request.isEmpty()) {
            return response.fail("유효하지 않은 요청Id 입니다.", HttpStatus.BAD_REQUEST);
        }

        if (parentWallet.isEmpty()) {
            return response.fail("유효하지 않은 유저Id 입니다.", HttpStatus.BAD_REQUEST);
        }

        // 거부
        if (!parentApprove.getAskAllowance()) {
            int result = allowanceRepository.updateByRequestId(parentApprove.getRequestId(), parentApprove.getAskAllowance());
            if (result != -1) {
                return response.success("해당 용돈 조르기 요청을 거부했습니다.");
            } else {
                return response.fail("용돈 조르기 요청 상태 변경에 실패했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
        // 승인
        else {
            // 1. 잔액 부족
            if (parentWallet.get().getBalance() < parentApprove.getRequestId()) {
                return response.fail("잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
            }
            // 2. 성공
            Optional<Member> child = memberRepository.findByMemberId(request.get().getRequestId());
            Optional<Wallets> childWallet = myWalletRepository.findById(child.get().getWalletId());

            if (child.isEmpty()) {
                response.fail("용돈 조르기 요청의 아이Id가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }
            if (childWallet.isEmpty()) {
                response.fail("용돈 조르기 요청의 아이 지갑Id가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }
            // 2-1. Transaction 작성
            makeTransaction(request.get(), parentWallet.get(), childWallet.get(), parentApprove);
            // 2-2. 아이 지갑 +
            myWalletRepository.updateByWalletId(childWallet.get().getWalletId(), childWallet.get().getBalance() + request.get().getAllowanceAmount());
            // 2-3. 부모 지갑 -
            myWalletRepository.updateByWalletId(parentApprove.getWalletId(), childWallet.get().getBalance() - request.get().getAllowanceAmount());
            return response.success("용돈 조르기 요청을 승인했습니다.");
        }
    }

    public ResponseEntity<?> sendAllowance(AllowanceRequestDto.SendAllowance sendAllowance) {
        //아이 지갑 존재 여부
        Optional<Member> child = memberRepository.findByMemberId(sendAllowance.getChildId());
        Optional<Wallets> childWallet = myWalletRepository.findById(child.get().getWalletId());
        //부모 지갑 존재 여부
        Optional<Member> parent = memberRepository.findByMemberId(sendAllowance.getUserId());
        Optional<Wallets> parentWallet = myWalletRepository.findById(parent.get().getWalletId());

        if (child.isEmpty()) {
            response.fail("아이Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if (childWallet.isEmpty()) {
            response.fail("아이Id에 연결된 지갑Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if (parent.isEmpty()) {
            response.fail("부모Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if (parentWallet.isEmpty()) {
            response.fail("부모Id에 연결된 지갑Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 1. Transaction 작성
        makeTransaction(parentWallet.get(), childWallet.get(), sendAllowance.getAmount());
        // 2. 아이 지갑 +
        myWalletRepository.updateByWalletId(childWallet.get().getWalletId(), childWallet.get().getBalance() + sendAllowance.getAmount());
        // 3. 부모 지갑 -
        myWalletRepository.updateByWalletId(parentWallet.get().getWalletId(), childWallet.get().getBalance() - sendAllowance.getAmount());

        return response.success("용돈 이체에 성공했습니다.");
    }

    public void makeTransaction(Requests request, Wallets parent, Wallets child, AllowanceRequestDto.ParentApprove parentApprove) {
        // 부모 -> 아이 거래내역
        Transactions parentToChildTransaction = Transactions.builder()
                .walletId(parent.getWalletId())
                .counterId(child.getWalletId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 이체")
                .amount(request.getAllowanceAmount())
                .balance((int) (parent.getBalance() - request.getAllowanceAmount()))
                .build();
        // 아이 -> 부모 거래내역
        Transactions childToParentTransaction = Transactions.builder()
                .walletId(child.getWalletId())
                .counterId(parent.getWalletId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 입금")
                .amount(request.getAllowanceAmount())
                .balance((int) (child.getBalance() + request.getAllowanceAmount()))
                .build();

        myWalletTransactionsRepository.save(parentToChildTransaction);
        myWalletTransactionsRepository.save(childToParentTransaction);
    }

    public void makeTransaction(Wallets parent, Wallets child, Integer amount) {
        // 부모 -> 아이 거래내역
        Transactions parentToChildTransaction = Transactions.builder()
                .walletId(parent.getWalletId())
                .counterId(child.getWalletId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 이체")
                .amount(amount)
                .balance((int) (parent.getBalance() - amount))
                .build();
        // 아이 -> 부모 거래내역
        Transactions childToParentTransaction = Transactions.builder()
                .walletId(child.getWalletId())
                .counterId(parent.getWalletId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 입금")
                .amount(amount)
                .balance((int) (child.getBalance() + amount))
                .build();

        myWalletTransactionsRepository.save(parentToChildTransaction);
        myWalletTransactionsRepository.save(childToParentTransaction);
    }

}
