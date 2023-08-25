package team.hanaro.hanamate.domain.User.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.hanaro.hanamate.domain.User.Dto.UserRequestDto;
import team.hanaro.hanamate.domain.User.Service.ParentAndChildService;

@Tag(name = "부모아이관계", description = "부모-아이 관계 매핑 기능")
@RestController
@RequiredArgsConstructor
public class ParentAndChildController {
    private final ParentAndChildService parentAndChildService;

    @Operation(summary = "[부모] 아이 추가", description = "부모가 아이를 추가한다.", tags = {"부모아이관계"})
    @PostMapping("/parent/children")
    public ResponseEntity<?> addChild(@Validated @RequestBody UserRequestDto.ParentAddOrDeleteChildRequestDTO dto) {
        return parentAndChildService.addChild(dto);
    }

    @Operation(summary = "[부모] 아이 찾기", description = "전화번호로 유저를 찾는다.", tags = {"부모아이관계"})
    @PostMapping("/parent/search")
    public ResponseEntity<?> findByUserPhoneNum(@Validated @RequestBody UserRequestDto.FindUserRequestDTO dto) {
        return parentAndChildService.findByUserPhoneNum(dto);
    }

    @Operation(summary = "[부모] 내 아이 조회", description = "부모가 내 아이를 조회한다.", tags = {"부모아이관계"})
    @PostMapping("/parent/my-child")
    public ResponseEntity<?> findMyChild(@Validated @RequestBody UserRequestDto.getMyChildOrParentRequestDTO dto) {
        return parentAndChildService.getMyChildList(dto);
    }

    @Operation(summary = "[아이] 내 부모 조회", description = "아이가 내 부모를 조회한다.", tags = {"부모아이관계"})
    @PostMapping("/child/my-parent")
    public ResponseEntity<?> findMyParent(@Validated @RequestBody UserRequestDto.getMyChildOrParentRequestDTO dto) {
        return parentAndChildService.getMyParentList(dto);
    }

    @Operation(summary = "[부모] 내 아이 삭제", description = "부모가 내 아이를 삭제한다.", tags = {"부모아이관계"})
    @DeleteMapping("/parent-child")
    public ResponseEntity<?> deleteMapping(@Validated @RequestBody UserRequestDto.ParentAddOrDeleteChildRequestDTO dto) {
        return parentAndChildService.deleteMyChild(dto);
    }
}
