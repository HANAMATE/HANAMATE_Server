package team.hanaro.hanamate.domain.moimWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.domain.moimWallet.dto.MoimWalletRequestDto;
import team.hanaro.hanamate.domain.moimWallet.service.MoimWalletService;
import team.hanaro.hanamate.global.Response;

@Tag(name = "모임통장", description = "모임통장 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MoimWalletController {

    private final MoimWalletService moimWalletService;
    private final Response response;

    @ApiIgnore
    @GetMapping("/healthy")
    public ResponseEntity<?> HealthyCheck() {
        return response.success("moimWallet is healthy");
    }
    @Operation(summary = "내 모임통장 만들기", description = "내 모임통장 만들기", tags = {"모임통장"})
    @PostMapping("/moim")
    public ResponseEntity<?> createMoimWallet(@Validated @RequestBody MoimWalletRequestDto.JoinMoimWalletDTO moimWallet){
        return moimWalletService.createMoimWallet(moimWallet);
    }

    @Operation(summary = "모임통장 내용 가져오기", description = "지정한 모임통장의 세부내역(거래내역, 글, 댓글) 등을 모두 가져옵니다.)", tags = {"모임통장"})
    @GetMapping("/moim/{id}")
        public ResponseEntity<?> getMoimWallet( @Validated @PathVariable("id")Long id) {
        return moimWalletService.getMoimWallet(id);
    }


    @Operation(summary = "내 모임통장 전부 가져오기", description = "내 모임통장 전부 가져오기", tags = {"모임통장"})
    @PostMapping("/moims")
    public ResponseEntity<?> myMoimWallet(@Validated @RequestBody MoimWalletRequestDto.findAllMoimWalletDTO moimWallet, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.getMoimWalletListByLoginId(moimWallet);
    }
    @Operation(summary = "모임 통장 정보 수정하기", description = "모임 통장 정보 수정하기", tags = {"모임통장"})
    @PutMapping("/moim")
    public ResponseEntity<?> updateMoimWalletInfo(@Validated @RequestBody MoimWalletRequestDto.UpdateMoimWalletInfoRequestDTO updateRequestDTO) {
        return moimWalletService.updateMoimWalletInfo(updateRequestDTO);
    }

    @Operation(summary = "모임 통장 삭제하기", description = "모임통장 삭제하기", tags = {"모임통장"})
    @DeleteMapping("/moim")
    public ResponseEntity<?> deleteMoimWallet(@Validated @RequestBody MoimWalletRequestDto.DeleteRequestDTO deleteRequestDTO, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.deleteMoimWallet(deleteRequestDTO);
    }



}
