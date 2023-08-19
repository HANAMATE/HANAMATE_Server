package team.hanaro.hanamate.domain.User.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.hanaro.hanamate.domain.User.Dto.UserResponseDto;
import team.hanaro.hanamate.domain.User.Repository.ChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentAndChildRepository;
import team.hanaro.hanamate.domain.User.Repository.ParentRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.entities.Child;
import team.hanaro.hanamate.entities.Parent;
import team.hanaro.hanamate.entities.ParentAndChild;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static team.hanaro.hanamate.domain.User.Dto.UserRequestDto.*;

@Service
@RequiredArgsConstructor
public class ParentAndChildService {

    private final Response response;
    private final ParentAndChildRepository parentAndChildRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final UsersRepository usersRepository;

    // TODO: 2023/08/19  1.전화번호로 유저 검색
    public ResponseEntity<?> findByUserPhoneNum(FindUserRequestDTO findUserRequestDTO) {
        String requesterId = findUserRequestDTO.getRequesterId();
        String phoneNumber = findUserRequestDTO.getPhoneNumber();
        Optional<User> savedUser = usersRepository.findByLoginId(requesterId);
        if (savedUser.isEmpty()) {
            return response.fail("잘못된 요청 : 유저 아이디가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }
        Optional<User> savedTargetUser = usersRepository.findUserByPhoneNumber(phoneNumber);
        if (savedTargetUser.isEmpty()) {
            return response.success("검색 결과 없음");
        } else {
            return response.success(new UserResponseDto.UserInfo(savedTargetUser.get()), "검색 성공!", HttpStatus.OK);
        }
    }

    // TODO: 2023/08/19  2. 부모가 아이 추가
    public ResponseEntity<?> addChild(ParentAddOrDeleteChildRequestDTO parentAddChildRequestDTO) {
        String requesterId = parentAddChildRequestDTO.getRequesterId();
        String targetId = parentAddChildRequestDTO.getTargetId();
        Optional<Parent> savedRequesterUser = parentRepository.findByLoginId(requesterId);
        if (savedRequesterUser.isEmpty()) {
            Optional<Child> byLoginId = childRepository.findByLoginId(requesterId);
            if (byLoginId.isPresent())
                return response.fail("잘못된 요청 : 아이는 이용할 수 없습니다.", HttpStatus.BAD_REQUEST);
            return response.fail("잘못된 요청 : 요청자 아이디가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }
        Optional<Child> savedTargetUser = childRepository.findByLoginId(targetId);
        if (savedTargetUser.isEmpty()) {
            return response.success("잘못된 요청 : 대상 아이디가 잘못되었습니다.");
        } else {
            Parent parentUser = savedRequesterUser.get();
            Child childUser = savedTargetUser.get();
            if (parentAndChildRepository.existsByParentIdxAndChildIdx(parentUser.getIdx(), childUser.getIdx())) {
                return response.fail("잘못된 요청 : 이미 동일한 아이가 등록되어 있습니다.", HttpStatus.BAD_REQUEST);
            }
            //아이 추가 로직
            ParentAndChild parentAndChild = ParentAndChild.builder().parent(parentUser).child(childUser).build();
            ParentAndChild savedParentAndChild = parentAndChildRepository.save(parentAndChild);
            return response.success(new UserResponseDto.ParentAndChildResponseDTO(savedParentAndChild), "아이 추가에 성공했습니다!", HttpStatus.OK);
        }
    }


    //공통화 부분 전달받은 List를 DTO로 변환, Parent와 Child가 User라는 공통 속성을 지니고 그것만 사용함으로 Function의 apply를 사용함
    //apply를 사용하면
    public List<UserResponseDto.findMyListDTO> getUserDTOListByParentAndChild(
            List<ParentAndChild> parentAndChildList, Function<ParentAndChild, User> userExtractor) {
        return parentAndChildList.stream()
                .map(parentAndChild -> new UserResponseDto.findMyListDTO(userExtractor.apply(parentAndChild)))
                .collect(Collectors.toList());
    }

    // TODO: 2023/08/19  3. 부모ID로 아이 정보 리스트 가져오기
    //아래는 기존 코드
//    public ResponseEntity<?> getMyChildList(getMyChildOrParentRequestDTO requestDTO) {
//        if(parentRepository.findByLoginId(requestDTO.getUserId()).isEmpty())
//            return response.fail("존재하지 않거나 잘못된 유저입니다.", HttpStatus.BAD_REQUEST);
//        List<UserResponseDto.findMyListDTO> childList =
//                parentAndChildRepository.findAllChildByParentLoginId(requestDTO.getUserId())
//                        .stream()
//                        .map(parentAndChild -> new UserResponseDto.findMyListDTO(parentAndChild.getChild()))
//                        .collect(Collectors.toList());
//        return response.success(childList, "성공", HttpStatus.OK);
//    }
    //공통화 및 스트림,람다를 사용한 코드
    public ResponseEntity<?> getMyChildList(getMyChildOrParentRequestDTO requestDTO) {
        if(parentRepository.findByLoginId(requestDTO.getUserId()).isEmpty())
            return response.fail("존재하지 않거나 잘못된 유저입니다.", HttpStatus.BAD_REQUEST);

        List<ParentAndChild> allChildByParentLoginId = parentAndChildRepository.findAllChildByParentLoginId(requestDTO.getUserId());
        List<UserResponseDto.findMyListDTO> childDTOList = getUserDTOListByParentAndChild(
                allChildByParentLoginId, ParentAndChild::getChild);
        return response.success(childDTOList, "성공", HttpStatus.OK);
    }
    // TODO: 2023/08/19  4. 아이ID로 부모 정보 가져오기
    public ResponseEntity<?> getMyParentList(getMyChildOrParentRequestDTO requestDTO) {
        if(childRepository.findByLoginId(requestDTO.getUserId()).isEmpty())
            return response.fail("존재하지 않거나 잘못된 유저입니다.", HttpStatus.BAD_REQUEST);

        List<ParentAndChild> allParentByChildLoginId = parentAndChildRepository.findAllParentByChildLoginId(requestDTO.getUserId());
        List<UserResponseDto.findMyListDTO> parentDTOList = getUserDTOListByParentAndChild(
                allParentByChildLoginId, ParentAndChild::getParent);
        return response.success(parentDTOList, "성공", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteMyChild(ParentAddOrDeleteChildRequestDTO requestDTO){
        Optional<User> requestOptional = usersRepository.findByLoginId(requestDTO.getRequesterId());
        Optional<User> targetOptional = usersRepository.findByLoginId(requestDTO.getTargetId());
        if (requestOptional.isEmpty()) {
            return response.fail("존재하지 않거나 잘못된 요청아이디입니다.", HttpStatus.BAD_REQUEST);
        }
        if (targetOptional.isEmpty()) {
            return response.fail("존재하지 않거나 잘못된 타겟아이디입니다.", HttpStatus.BAD_REQUEST);
        }
        Long parent_idx = requestOptional.get().getIdx();
        Long child_idx = targetOptional.get().getIdx();
        if (requestOptional.get().getUserType().equals("Parent")) {
            //부모일때.
            if(!parentAndChildRepository.existsByParentIdxAndChildIdx(parent_idx,child_idx))
                return response.fail("존재하지 않는 관계입니다.", HttpStatus.BAD_REQUEST);
            parentAndChildRepository.deleteByParentIdxAndChildIdx(parent_idx, child_idx);
            return response.success("성공적으로 아이 연결이 삭제되었습니다.");
        }
        else  if(requestOptional.get().getUserType().equals("Child")){
            //아이일떄
            if(!parentAndChildRepository.existsByParentIdxAndChildIdx(child_idx,parent_idx))
                return response.fail("존재하지 않는 관계입니다.", HttpStatus.BAD_REQUEST);
            parentAndChildRepository.deleteByParentIdxAndChildIdx(child_idx, parent_idx);
            return response.success("성공적으로 부모 연결이 삭제되었습니다.");
        }
        return response.fail("잘못된 유저 타입입니다.", HttpStatus.BAD_REQUEST);
    }
}
