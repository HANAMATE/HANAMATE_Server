package team.hanaro.hanamate.domain.MyWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.MyWallet.Dto.RequestDto;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.global.Response;


@Tag(name = "내 지갑", description = "내 지갑과 관련된 기능")
@RequiredArgsConstructor
@RequestMapping("/my-wallet")
@RestController
public class MyWalletController {

    private final WalletService walletService;
    private final Response response;

    @GetMapping("/healthy")
    public ResponseEntity<?> HealthyCheck() {
        return response.success("healthy");
    }

    @Operation(summary = "지갑 잔액 조회", description = "내 지갑 잔액 가져오기", tags = {"내 지갑"})
    @PostMapping("")
    public ResponseEntity<?> myWallet(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return walletService.myWallet(user);
    }

    @Operation(summary = "거래내역 조회", description = "내 지갑 거래내역 가져오기", tags = {"내 지갑"})
    @PostMapping("/transactions")
    public ResponseEntity<?> myWalletTransactions(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return walletService.myWalletTransactions(user);
    }

    @Operation(summary = "계좌 잔액 조회", description = "연결된 은행계좌 잔액 조회", tags = {"내 지갑"})
    @GetMapping("/account")
    public ResponseEntity<?> getAccount(@Validated @RequestBody RequestDto.User user, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return walletService.getAccount(user);
    }

    @Operation(summary = "충전하기", description = "연결된 은행계좌에서 돈 가져오기", tags = {"내 지갑"})
    @PostMapping("/account")
    public ResponseEntity<?> chargeFromAccount(@RequestBody RequestDto.Charge charge, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return walletService.chargeFromAccount(charge);
    }

    @Operation(summary = "계좌 연결", description = "은행 계좌 연결하기", tags = {"내 지갑"})
    @PostMapping("/connect")
    public ResponseEntity<?> connectAccount(@RequestBody RequestDto.AccountInfo accountInfo, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return walletService.connectAccount(accountInfo);
    }
}
