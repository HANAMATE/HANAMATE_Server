package team.hanaro.hanamate.domain.Allowance;

import org.springframework.stereotype.Service;
import team.hanaro.hanamate.entities.Requests;

import java.util.Optional;

@Service
public class AllowanceService {

    private final AllowanceRepository allowanceRepository;

    public AllowanceService(AllowanceRepository allowanceRepository) {
        this.allowanceRepository = allowanceRepository;
    }


    public AllowanceResponseDto.ChildResponseList getChildRequestList(AllowanceRequestDto.ChildRequestList childRequestList) {
        System.out.println(childRequestList.getUserId());
        Optional<Requests> myRequests = allowanceRepository.findAllByRequesterId(childRequestList.getUserId());
        if (myRequests.isPresent()) {
            AllowanceResponseDto.ChildResponseList childResponseList = new AllowanceResponseDto.ChildResponseList(myRequests.get());
            return childResponseList;
        } else {
            return null;
        }
    }
}
