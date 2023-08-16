package team.hanaro.hanamate.domain.moimWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.domain.moimWallet.dto.MoimWalletRequestDto;
import team.hanaro.hanamate.global.Response;

@Tag(name = "모임 통장", description = "모임통장 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/moim")
@RestController
public class MoimWalletController {

    private final MoimWalletService moimWalletService;
    private final Response response;

    @GetMapping("/healthy")
    public ResponseEntity<?> HealthyCheck() {
        return response.success("moimWallet is healthy");
    }
    @Operation(summary = "지갑 잔액 조회", description = "내 지갑 잔액 가져오기", tags = {"내 지갑"})

    @GetMapping("")
    public ResponseEntity<?> myWallet(@Validated @RequestBody MoimWalletRequestDto.findAllMoimWalletDTO moimWallet, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.getMoimWalletByLoginId(moimWallet);
    }
    @PostMapping("")
    public ResponseEntity<?> createMoimWallet(@Validated @RequestBody MoimWalletRequestDto.JoinMoimWalletDTO moimWallet, Errors errors){
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.createMoimWallet(moimWallet);
    }

}
