package team.hanaro.hanamate.domain.Allowance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.domain.Allowance.Dto.RequestDto;
import team.hanaro.hanamate.domain.Allowance.Dto.ResponseDto;
import team.hanaro.hanamate.domain.MyWallet.WalletService;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.entities.Allowances;
import team.hanaro.hanamate.entities.MyWallet;
import team.hanaro.hanamate.entities.Requests;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowanceService {

    private final RequestsRepository requestsRepository;
    private final AllowancesRepository allowancesRepository;
    private final UsersRepository usersRepository;
    private final Response response;
    private final WalletService walletService;

    /* 1. 아이 : 용돈 조르기(대기중) 요청 조회*/
    public ResponseEntity<?> getMyAllowancePendingRequestList(UserDetails userDetails) {
        Optional<User> userInfo = usersRepository.findByLoginId(userDetails.getUsername());

        if (userInfo.isEmpty()) {
            return response.fail("유저 Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        List<Requests> myRequests = requestsRepository.findTop20ByRequesterIdxAndAskAllowanceIsNullOrderByCreateDateDesc(userInfo.get().getIdx());

        if (myRequests.isEmpty()) {
            return response.fail("대기 상태의 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<ResponseDto.Request> responseRequestList = new ArrayList<>();
        for (Requests request : myRequests) {
            Optional<User> targetUser = usersRepository.findById(request.getTargetIdx());
            Optional<User> requesterUser = usersRepository.findById(request.getRequesterIdx());
            ResponseDto.Request responseRequest = new ResponseDto.Request(request, requesterUser.get().getLoginId(), targetUser.get().getLoginId());
            responseRequestList.add(responseRequest);
        }

        return response.success(responseRequestList, "용돈 조르기(대기중) 요청 리스트 조회에 성공했습니다.", HttpStatus.OK);
    }

    /* 2. 아이 : 용돈 조르기(승인/거절) 요청 조회*/
    public ResponseEntity<?> getMyAllowanceApprovedRequestList(UserDetails userDetails) {
        Optional<User> userInfo = usersRepository.findByLoginId(userDetails.getUsername());

        if (userInfo.isEmpty()) {
            return response.fail("유저 Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        List<Requests> myRequests = requestsRepository.findTop20ByRequesterIdxAndAskAllowanceIsNotNullOrderByModifiedDateDesc(userInfo.get().getIdx());
        if (myRequests.isEmpty()) {
            return response.fail("승인/거절된 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<ResponseDto.Request> responseRequestList = new ArrayList<>();
        for (Requests request : myRequests) {
            Optional<User> targetUser = usersRepository.findById(request.getTargetIdx());
            Optional<User> requesterUser = usersRepository.findById(request.getRequesterIdx());

            ResponseDto.Request responseRequest = new ResponseDto.Request(request, requesterUser.get().getLoginId(), targetUser.get().getLoginId());
            responseRequestList.add(responseRequest);
        }

        return response.success(responseRequestList, "용돈 조르기(승인/거절) 요청 리스트 조회에 성공했습니다.", HttpStatus.OK);
    }

    /* 3. 아이 : 용돈 조르기 생성 */
    public ResponseEntity<?> makeAllowanceRequest(RequestDto.ChildRequest request, UserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredDate = now.plus(7, ChronoUnit.DAYS);

        Optional<User> child = usersRepository.findByLoginId(userDetails.getUsername());
        Optional<User> parent = usersRepository.findByLoginId(request.getParentId());

        if (child.isEmpty()) {
            return response.fail("childId가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        if (parent.isEmpty()) {
            return response.fail("parentId가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        Long pendingRequestCnt = requestsRepository.countAllByRequesterIdxAndAskAllowanceIsNull(child.get().getIdx());

        if (pendingRequestCnt >= 20) {
            return response.fail("pending 상태인 요청이 20개입니다. 더이상의 용돈 조르기 요청 생성은 불가능합니다.", HttpStatus.BAD_REQUEST);
        }

        Requests requests = Requests.builder()
                .targetIdx(parent.get().getIdx()) //부모 아이디로 설정: [코드 작성 08.11 / 안식]
                .requesterIdx(child.get().getIdx())
                .allowanceAmount(request.getAllowanceAmount())
                .expirationDate(expiredDate)
                .requestDescription(request.getRequestDescription())
                .build();

        requestsRepository.save(requests);
        requestsRepository.flush();

        return response.success("용돈 조르기에 성공했습니다.");
    }

    /* 4. 부모 : 용돈 조르기(대기중) 요청 조회 */
    public ResponseEntity<?> getMyChildAllowancePendingRequestList(UserDetails userDetails) {
        Optional<User> userInfo = usersRepository.findByLoginId(userDetails.getUsername());

        if (userInfo.isEmpty()) {
            return response.fail("유저 Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        List<Requests> myRequests = requestsRepository.findTop20ByTargetIdxAndAskAllowanceIsNullOrderByCreateDateDesc(userInfo.get().getIdx());

        if (myRequests.isEmpty()) {
            return response.fail("대기 상태의 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<ResponseDto.Request> responseRequestList = new ArrayList<>();
        for (Requests request : myRequests) {
            Optional<User> targetUser = usersRepository.findById(request.getTargetIdx());
            Optional<User> requesterUser = usersRepository.findById(request.getRequesterIdx());

            ResponseDto.Request responseRequest = new ResponseDto.Request(request, requesterUser.get().getLoginId(), targetUser.get().getLoginId());
            responseRequestList.add(responseRequest);
        }

        return response.success(responseRequestList, "용돈 조르기(대기) 리스트 조회에 성공했습니다.", HttpStatus.OK);
    }

    /* 5. 부모 : 용돈 조르기(승인,거절) 요청 조회 */
    public ResponseEntity<?> getMyChildAllowanceApprovedRequestList(UserDetails userDetails) {
        Optional<User> userInfo = usersRepository.findByLoginId(userDetails.getUsername());

        if (userInfo.isEmpty()) {
            return response.fail("유저 Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        List<Requests> myRequests = requestsRepository.findTop20ByTargetIdxAndAskAllowanceIsNotNullOrderByModifiedDateDesc(userInfo.get().getIdx());

        if (myRequests.isEmpty()) {
            return response.fail("승인/거절된 용돈 조르기 요청이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<ResponseDto.Request> responseRequestList = new ArrayList<>();
        for (Requests request : myRequests) {
            Optional<User> targetUser = usersRepository.findById(request.getTargetIdx());
            Optional<User> requesterUser = usersRepository.findById(request.getRequesterIdx());

            ResponseDto.Request responseRequest = new ResponseDto.Request(request, requesterUser.get().getLoginId(), targetUser.get().getLoginId());
            responseRequestList.add(responseRequest);
        }

        return response.success(responseRequestList, "용돈 조르기(성공,실패) 리스트 조회에 성공했습니다.", HttpStatus.OK);
    }

    /* 6. 부모 : 용돈 조르기 상태 변경(대기중 -> 승인/거절) */
    @Transactional
    public ResponseEntity<?> updateRequestStatus(RequestDto.Approve approve) {
        Optional<Requests> request = requestsRepository.findByRequestId(approve.getRequestId());

        if (request.isEmpty()) {
            return response.fail("유효하지 않은 requestId 입니다.", HttpStatus.BAD_REQUEST);
        }

        if (request.get().getAskAllowance() != null) {
            return response.fail("대기중인 상태의 용돈 조르기 요청만 상태 update가 가능합니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> parent = usersRepository.findById(request.get().getTargetIdx());
        MyWallet parentWallet = parent.get().getMyWallet();

        if (parent.isEmpty()) {
            return response.fail("해당 용돈 조르기 요청의 부모Id가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
        }


        // 거절
        if (!approve.getAskAllowance()) {
            request.get().setAskAllowance(approve.getAskAllowance());
            requestsRepository.save(request.get());
            return response.success("용돈 조르기 요청을 거절했습니다.");
        }
        // 승인
        else {
            // 1. 잔액 부족
            if (parentWallet.getBalance() < request.get().getAllowanceAmount()) {
                return response.fail("잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
            }
            // 2. 성공
            Optional<User> child = usersRepository.findById(request.get().getRequesterIdx());
            MyWallet childWallet = child.get().getMyWallet();

            if (child.isEmpty()) {
                response.fail("용돈 조르기 요청의 아이Id가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);
            }

            walletService.transfer(parentWallet, childWallet, request.get().getAllowanceAmount(), "용돈 조르기 출금", "용돈 조르기 입금");
            
            request.get().setAskAllowance(approve.getAskAllowance());
            requestsRepository.save(request.get());

            requestsRepository.flush();
            return response.success("용돈 조르기 요청을 승인했습니다.");
        }
    }

    /* 7. 부모 : 용돈 보내기 */
    @Transactional
    public ResponseEntity<?> sendAllowance(RequestDto.ParentRequest request, UserDetails userDetails) {
        Optional<User> child = usersRepository.findByLoginId(request.getChildId());
        Optional<User> parent = usersRepository.findByLoginId(userDetails.getUsername());

        if (child.isEmpty()) {
            response.fail("아이Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if (parent.isEmpty()) {
            response.fail("부모Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        MyWallet childWallet = child.get().getMyWallet();
        MyWallet parentWallet = parent.get().getMyWallet();

        walletService.transfer(parentWallet, childWallet, request.getAllowanceAmount(), "용돈 출금", "용돈 입금");

        return response.success("용돈 이체에 성공했습니다.");
    }

    /* 8. 부모 : 정기 용돈 조회 */
    public ResponseEntity<?> getPeriodicAllowance(UserDetails userDetails) {
        Optional<User> userInfo = usersRepository.findByLoginId(userDetails.getUsername());

        if (userInfo.isEmpty()) {
            return response.fail("유저 Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Allowances> allowances = allowancesRepository.findByParentIdxAndValidIsTrue(userInfo.get().getIdx());

        if (allowances.isEmpty()) {
            return response.fail("정기 용돈 리스트가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> parent = usersRepository.findById(allowances.get().getParentIdx());
        Optional<User> child = usersRepository.findById(allowances.get().getChildrenIdx());


        ResponseDto.Allowance responseAllowance = new ResponseDto.Allowance(allowances.get(), child.get().getLoginId(), parent.get().getLoginId());
        return response.success(responseAllowance, "정기 용돈 조회에 성공했습니다.", HttpStatus.OK);
    }

    /* 9. 부모 : 정기 용돈 생성 */
    public ResponseEntity<?> makePeriodicAllowance(RequestDto.Periodic periodic, UserDetails userDetails) {
        Optional<User> child = usersRepository.findByLoginId(periodic.getChildId());
        Optional<User> parent = usersRepository.findByLoginId(userDetails.getUsername());

        if (child.isEmpty()) {
            response.fail("아이Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        if (parent.isEmpty()) {
            response.fail("부모Id가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        /* 아이-부모 정기 용돈은 최대 한개까지 */
        Optional<Allowances> myallowance = allowancesRepository.findByChildrenIdxAndParentIdxAndValidIsTrue(child.get().getIdx(), parent.get().getIdx());
        if (myallowance.isPresent()) {
            return response.fail("아이-부모 사이에 정기 용돈이 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPeriodicCondition(periodic.getEveryday(), periodic.getDayOfWeek(), periodic.getTransferDate())) {
            return response.fail("정기적으로 용돈을 줄 날짜를 잘못 입력했습니다.", HttpStatus.BAD_REQUEST);
        }

        Allowances allowance = Allowances.builder()
                .childrenIdx(child.get().getIdx())
                .parentIdx(parent.get().getIdx())
                .allowanceAmount(periodic.getAllowanceAmount())
                .transferDate(periodic.getTransferDate())
                .dayOfWeek(periodic.getDayOfWeek())
                .everyday(periodic.getEveryday())
                .valid(true)
                .build();

        allowancesRepository.save(allowance);

        return response.success("정기 용돈을 생성했습니다.");

    }

    /* 10. 부모 : 정기 용돈 업데이트 */
    @Transactional
    public ResponseEntity<?> updatePeriodicAllowance(RequestDto.UpdatePeriodic periodic) {
        Optional<Allowances> allowances = allowancesRepository.findByAllowanceIdAndValidIsTrue(periodic.getAllowanceId());

        if (allowances.isEmpty()) {
            return response.fail("해당 Id의 정기 용돈이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPeriodicCondition(periodic.getEveryday(), periodic.getDayOfWeek(), periodic.getTransferDate())) {
            return response.fail("정기적으로 용돈을 줄 날짜를 잘못 입력했습니다.", HttpStatus.BAD_REQUEST);
        }

        allowances.get().setAllowanceAmount(periodic.getAllowanceAmount());
        allowances.get().setTransferDate(periodic.getTransferDate());
        allowances.get().setDayOfWeek(periodic.getDayOfWeek());
        allowances.get().setEveryday(periodic.getEveryday());

        allowancesRepository.save(allowances.get());

        return response.success("정기 용돈 정보를 Update 했습니다.");
    }

    /* 11. 부모 : 정기 용돈 삭제 */
    @Transactional
    public ResponseEntity<?> deletePeriodicAllowance(RequestDto.Allowance periodic) {
        Optional<Allowances> allowance = allowancesRepository.findByAllowanceIdAndValidIsTrue(periodic.getAllowanceId());
        if (allowance.isEmpty()) {
            return response.fail("해당 Id의 정기 용돈이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        allowance.get().setValid(false);
        allowancesRepository.save(allowance.get());
        return response.success("정기 용돈을 삭제했습니다.");
    }

    private boolean isValidPeriodicCondition(Boolean everyday, Integer dayOfWeek, Integer transferDate) {
        // 모두 다 0
        boolean isPeriodicNoneExists = everyday.equals(false) && dayOfWeek.equals(0) && transferDate.equals(0);
        // 둘 이상 값이 있을 경우
        boolean isPeriodicConditionMorethanTwoExists = (!everyday.equals(false) && !dayOfWeek.equals(0))
                || (!everyday.equals(false) && !transferDate.equals(0))
                || (!dayOfWeek.equals(0) && !transferDate.equals(0));

        if (isPeriodicConditionMorethanTwoExists || isPeriodicNoneExists) {
            return false;
        }
        return true;
    }

    public Integer getPeriodicAllowanceByChildId(User child){
        Optional<Allowances> allowances = allowancesRepository.findByChildrenIdxAndValidIsTrue(child.getIdx());

        if (allowances.isEmpty()) {
            return null;
        }

        return allowances.get().getAllowanceAmount();
    }
}
