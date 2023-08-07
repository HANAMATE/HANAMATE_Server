package team.hanaro.hanamate.domain.Allowance;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.User.Dto.Response;
import team.hanaro.hanamate.entities.Requests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;
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
        //1. 용돈 조르기 거부
        if (!parentApprove.getAskAllowance()) {
            int result = allowanceRepository.updateByRequestId(parentApprove.getRequestId(), parentApprove.getAskAllowance());
            if (result != -1) {
                return response.success("해당 용돈 조르기 요청을 거부했습니다.");
            } else {
                return response.fail("용돈 조르기 요청 상태 변경에 실패했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
        //2. 용돈 조르기 성공
        else {
            return response.fail("개발중입니다.", HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
        }
    }
}
