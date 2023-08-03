package team.hanaro.hanamate.domain.Allowance;

import org.springframework.stereotype.Service;
import team.hanaro.hanamate.entities.Requests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;

    public AllowanceService(AllowanceRepository allowanceRepository) {
        this.allowanceRepository = allowanceRepository;
    }

    public List<AllowanceResponseDto.ChildResponseList> getChildRequestList(AllowanceRequestDto.ChildRequestList childRequestList) {
        System.out.println(childRequestList.getUserId());
        Optional<List<Requests>> myRequests = allowanceRepository.findAllByRequesterId(childRequestList.getUserId());
        if (myRequests.isPresent()) {
            List<Requests> requestsList = myRequests.get();
            List<AllowanceResponseDto.ChildResponseList> childResponseListArrayList = new ArrayList<>();
            for (Requests request : requestsList) {
                AllowanceResponseDto.ChildResponseList childResponseList = new AllowanceResponseDto.ChildResponseList(request);
                childResponseListArrayList.add(childResponseList);
            }
            return childResponseListArrayList;
        } else {
            return null;
        }
    }

    public String makeChildRequest(AllowanceRequestDto.ChildRequest childRequest) {
        Calendar cal = Calendar.getInstance();
        Timestamp requestDate = new Timestamp(cal.getTimeInMillis());
        cal.add(Calendar.DATE, 7);
        Timestamp expiredDate = new Timestamp(cal.getTimeInMillis());

        if (childRequest.getUserId() == null || childRequest.getAllowanceAmount() == null) {
            return "failed";
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
        return "success";
    }

    public String approveRequest(AllowanceRequestDto.ParentApprove parentApprove) {
        //1. 용돈 조르기 거부
        if (!parentApprove.getAskAllowance()) {
            int result = allowanceRepository.updateByRequestId(parentApprove.getRequestId(), parentApprove.getAskAllowance());
            if (result != -1) {
                return "success";
            } else {
                return "failed";
            }
        }
        //2. 용돈 조르기 성공
        else {
            return "개발 중";
        }
    }
}
