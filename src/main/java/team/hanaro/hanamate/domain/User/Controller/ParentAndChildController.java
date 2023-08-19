package team.hanaro.hanamate.domain.User.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.User.Dto.UserRequestDto;
import team.hanaro.hanamate.domain.User.Service.ParentAndChildService;
import team.hanaro.hanamate.global.Response;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/parent")
public class ParentAndChildController {
    private final Response response;
    private final ParentAndChildService parentAndChildService;

    @PostMapping("/parent/children")
    public ResponseEntity<?> addChild(@Validated @RequestBody UserRequestDto.ParentAddOrDeleteChildRequestDTO dto) {
        return parentAndChildService.addChild(dto);
    }

    @PostMapping("/parent/search")
    public ResponseEntity<?> findByUserPhoneNum(@Validated @RequestBody UserRequestDto.FindUserRequestDTO dto) {
        return parentAndChildService.findByUserPhoneNum(dto);
    }

    @PostMapping("/parent/my-child")
    public ResponseEntity<?> findMyChild(@Validated @RequestBody UserRequestDto.getMyChildOrParentRequestDTO dto) {
        return parentAndChildService.getMyChildList(dto);
    }
    @PostMapping("/child/my-parent")
    public ResponseEntity<?> findMyParent(@Validated @RequestBody UserRequestDto.getMyChildOrParentRequestDTO dto) {
        return parentAndChildService.getMyParentList(dto);
    }

    @DeleteMapping("/parent-child")
    public ResponseEntity<?> deleteMapping(@Validated @RequestBody UserRequestDto.ParentAddOrDeleteChildRequestDTO dto){
        return parentAndChildService.deleteMyChild(dto);
    }
}
