package team.hanaro.hanamate.domain.Allowance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import team.hanaro.hanamate.domain.Allowance.Dto.RequestDto;
import team.hanaro.hanamate.domain.Allowance.Dto.ResponseDto;
import team.hanaro.hanamate.domain.MyWallet.Repository.TransactionRepository;
import team.hanaro.hanamate.domain.MyWallet.Repository.WalletRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.entities.*;
import team.hanaro.hanamate.global.Response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowanceService {

    private final RequestsRepository requestsRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final AllowancesRepository allowancesRepository;
    private final UsersRepository usersRepository;
    private final Response response;

    /* 1. 아이 : 용돈 조르기 요청 조회*/
    public ResponseEntity<?> getAllowanceRequestList(RequestDto.ChildRequestList childRequestList) {
        Optional<List<Requests>> myRequests = requestsRepository.findAllByRequesterId(childRequestList.getUserId());
        if (myRequests.isPresent()) {
            List<Requests> requestsList = myRequests.get();
            List<ResponseDto.ChildResponseList> childResponseListArrayList = new ArrayList<>();
            for (Requests request : requestsList) {
                ResponseDto.ChildResponseList childResponseList = new ResponseDto.ChildResponseList(request);
                childResponseListArrayList.add(childResponseList);
            }
            return response.success(childResponseListArrayList, "용돈 조르기 요청 리스트 조회에 성공했습니다.", HttpStatus.OK);
        } else {
            return response.fail("대기 상태의 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /* 2. 아이 : 용돈 조르기 생성 */
    public ResponseEntity<?> makeAllowanceRequest(RequestDto.ChildRequest childRequest) {
        Calendar cal = Calendar.getInstance();
        Timestamp requestDate = new Timestamp(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 7);
        Timestamp expiredDate = new Timestamp(cal.getTimeInMillis());

        if (childRequest.getUserId() == null || ObjectUtils.isEmpty(childRequest.getParentId()) || childRequest.getAllowanceAmount() == null) {
            return response.fail("용돈 조르기 요청에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        Requests requests = Requests.builder()
                .targetId(childRequest.getParentId()) //TODO: 부모 아이디로 설정 [코드 작성 08.11 / 안식]
                .requesterId(childRequest.getUserId())
                .allowanceAmount(childRequest.getAllowanceAmount())
                .requestDate(requestDate)
                .expirationDate(expiredDate)
                .requestDescription(childRequest.getRequestDescription())
                .build();
        requestsRepository.save(requests);
        requestsRepository.flush();
        return response.success("용돈 조르기에 성공했습니다.");
    }

    /* 3. 부모 : 용돈 조르기 승인 */
    public ResponseEntity<?> updateRequestStatus(RequestDto.ParentApprove parentApprove) {
        Optional<Requests> request = requestsRepository.findByRequestId(parentApprove.getRequestId());
        Optional<MyWallet> parentWallet = walletRepository.findById(parentApprove.getWalletId());

        if (request.isEmpty()) {
            return response.fail("유효하지 않은 요청Id 입니다.", HttpStatus.BAD_REQUEST);
        }

        if (parentWallet.isEmpty()) {
            return response.fail("유효하지 않은 유저Id 입니다.", HttpStatus.BAD_REQUEST);
        }

        // 거부
        if (!parentApprove.getAskAllowance()) {
            int result = requestsRepository.updateByRequestId(parentApprove.getRequestId(), parentApprove.getAskAllowance());
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
            Optional<User> child = usersRepository.findById(request.get().getRequestId());
            Optional<MyWallet> childWallet = walletRepository.findById(child.get().getMyWallet().getId());

            if (child.isEmpty()) {
                response.fail("용돈 조르기 요청의 아이Id가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }
            if (childWallet.isEmpty()) {
                response.fail("용돈 조르기 요청의 아이 지갑Id가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
            }
            // 2-1. Transaction 작성
            makeTransaction(parentWallet.get(), childWallet.get(), request.get(), parentApprove);
            // 2-2. 아이 지갑 +
            walletRepository.updateByWalletId(childWallet.get().getId(), childWallet.get().getBalance() + request.get().getAllowanceAmount());
            // 2-3. 부모 지갑 -
            walletRepository.updateByWalletId(parentApprove.getWalletId(), childWallet.get().getBalance() - request.get().getAllowanceAmount());
            return response.success("용돈 조르기 요청을 승인했습니다.");
        }
    }

    /* 4. 부모 : 용돈 보내기 */
    public ResponseEntity<?> sendAllowance(RequestDto.SendAllowance sendAllowance) {
        //아이 지갑 존재 여부
        Optional<User> child = usersRepository.findById(sendAllowance.getChildId());
        Optional<MyWallet> childWallet = walletRepository.findById(child.get().getMyWallet().getId());
        //부모 지갑 존재 여부
        Optional<User> parent = usersRepository.findById(sendAllowance.getUserId());
        Optional<MyWallet> parentWallet = walletRepository.findById(parent.get().getMyWallet().getId());

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
        walletRepository.updateByWalletId(childWallet.get().getId(), childWallet.get().getBalance() + sendAllowance.getAmount());
        // 3. 부모 지갑 -
        walletRepository.updateByWalletId(parentWallet.get().getId(), childWallet.get().getBalance() - sendAllowance.getAmount());

        return response.success("용돈 이체에 성공했습니다.");
    }

    public void makeTransaction(MyWallet parent, MyWallet child, Requests request, RequestDto.ParentApprove parentApprove) {
        // 부모 -> 아이 거래내역
        Transactions parentToChildTransaction = Transactions.builder()
                .id(parent.getId())
                .counterId(child.getId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 이체")
                .amount(request.getAllowanceAmount())
                .balance((int) (parent.getBalance() - request.getAllowanceAmount()))
                .build();
        // 아이 -> 부모 거래내역
        Transactions childToParentTransaction = Transactions.builder()
                .id(child.getId())
                .counterId(parent.getId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 입금")
                .amount(request.getAllowanceAmount())
                .balance((int) (child.getBalance() + request.getAllowanceAmount()))
                .build();

        transactionRepository.save(parentToChildTransaction);
        transactionRepository.save(childToParentTransaction);
    }

    public void makeTransaction(MyWallet parent, MyWallet child, Integer amount) {
        // 부모 -> 아이 거래내역
        Transactions parentToChildTransaction = Transactions.builder()
                .id(parent.getId())
                .counterId(child.getId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 이체")
                .amount(amount)
                .balance((int) (parent.getBalance() - amount))
                .build();
        // 아이 -> 부모 거래내역
        Transactions childToParentTransaction = Transactions.builder()
                .id(child.getId())
                .counterId(parent.getId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("용돈 입금")
                .amount(amount)
                .balance((int) (child.getBalance() + amount))
                .build();

        transactionRepository.save(parentToChildTransaction);
        transactionRepository.save(childToParentTransaction);
    }

    /* 5. 부모 : 정기 용돈 조회 */
    public ResponseEntity<?> getPeriodicAllowance(RequestDto.PeriodicAllowance periodicAllowance) {
        Optional<List<Allowances>> allowances = allowancesRepository.findAllByParentId(periodicAllowance.getUserId());

        if (allowances.isPresent()) {
            List<Allowances> allowancesList = allowances.get();
            List<ResponseDto.PeriodicAllowance> childPeriodicAllowanceList = new ArrayList<>();
            for (Allowances allowance : allowancesList) {
                ResponseDto.PeriodicAllowance periodicAllowanceList = new ResponseDto.PeriodicAllowance(allowance);
                childPeriodicAllowanceList.add(periodicAllowanceList);
            }
            return response.success(childPeriodicAllowanceList, "정기 용돈 조회에 성공했습니다.", HttpStatus.OK);
        } else {
            return response.fail("정기 용돈 리스트가 없습니다.", HttpStatus.BAD_REQUEST);
        }

    }

    /* 6. 부모 : 정기 용돈 생성 */
    public ResponseEntity<?> makePeriodicAllowance(RequestDto.makePeriodicAllowance periodicAllowance) {
        /* 아이-부모 정기 용돈은 최대 한개까지 */
        Optional<Allowances> myallowance = allowancesRepository.findByChildrenIdAndParentId(periodicAllowance.getChildrenId(), periodicAllowance.getParentId());
        if (myallowance.isPresent()) {
            return response.fail("아이-부모 사이에 정기 용돈이 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        boolean isPeriodicNoneExists = periodicAllowance.getEveryday() == null && periodicAllowance.getDayOfWeek() == null && periodicAllowance.getTransferDate() == null;
        if (isPeriodicNoneExists) {
            return response.fail("정기적으로 용돈을 줄 날짜를 입력해주세요", HttpStatus.BAD_REQUEST);
        }

        boolean isPeriodicConditionMorethanTwoExists = (periodicAllowance.getEveryday() != null && periodicAllowance.getDayOfWeek() != null)
                || (periodicAllowance.getEveryday() != null && periodicAllowance.getTransferDate() != null)
                || (periodicAllowance.getDayOfWeek() != null && periodicAllowance.getTransferDate() != null);
        if (isPeriodicConditionMorethanTwoExists) {
            return response.fail("정기적으로 용돈을 줄 날짜를 두개 이상 선택했습니다. 매일/매주/매월 중 하나만 선택해서 전달해주세요.", HttpStatus.BAD_REQUEST);
        }

        Allowances allowance = Allowances.builder()
                .childrenId(periodicAllowance.getChildrenId())
                .parentId(periodicAllowance.getParentId())
                .allowanceAmount(periodicAllowance.getAllowanceAmount())
                .transferDate(periodicAllowance.getTransferDate())
                .dayOfWeek(periodicAllowance.getDayOfWeek())
                .everyday(periodicAllowance.getEveryday())
                .build();

        allowancesRepository.save(allowance);

        return response.success("정기 용돈을 생성했습니다.");

    }

    /* 7. 부모 : 정기 용돈 업데이트 */
    public ResponseEntity<?> updatePeriodicAllowance(RequestDto.updatePeriodicAllowance periodicAllowance) {
        Optional<Allowances> allowances = allowancesRepository.findByAllowanceId(periodicAllowance.getAllowanceId());

        if (allowances.isEmpty()) {
            return response.fail("해당 Id의 정기 용돈이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        boolean isPeriodicNoneExists = periodicAllowance.getEveryday() == null && periodicAllowance.getDayOfWeek() == null && periodicAllowance.getTransferDate() == null;
        if (isPeriodicNoneExists) {
            return response.fail("정기적으로 용돈을 줄 날짜를 입력해주세요", HttpStatus.BAD_REQUEST);
        }

        boolean isPeriodicConditionMorethanTwoExists = (periodicAllowance.getEveryday() != null && periodicAllowance.getDayOfWeek() != null)
                || (periodicAllowance.getEveryday() != null && periodicAllowance.getTransferDate() != null)
                || (periodicAllowance.getDayOfWeek() != null && periodicAllowance.getTransferDate() != null);
        if (isPeriodicConditionMorethanTwoExists) {
            return response.fail("정기적으로 용돈을 줄 날짜를 두개 이상 선택했습니다. 매일/매주/매월 중 하나만 선택해서 전달해주세요.", HttpStatus.BAD_REQUEST);
        }

        allowances.get().setAllowanceAmount(periodicAllowance.getAllowanceAmount());
        allowances.get().setTransferDate(periodicAllowance.getTransferDate());
        allowances.get().setDayOfWeek(periodicAllowance.getDayOfWeek());
        allowances.get().setEveryday(periodicAllowance.getEveryday());

        allowancesRepository.save(allowances.get());

        return response.success("정기 용돈 정보를 Update 했습니다.");
    }

    /* 8. 부모 : 정기 용돈 삭제 */
    public ResponseEntity<?> deletePeriodicAllowance(RequestDto.deletePeriodicAllowance periodicAllowance) {
        Optional<Allowances> allowance = allowancesRepository.findByAllowanceId(periodicAllowance.getAllowanceId());
        if (allowance.isEmpty()) {
            return response.fail("해당 Id의 정기 용돈이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        allowancesRepository.delete(allowance.get());
        return response.success("정기 용돈을 삭제했습니다.");
    }
}
