package team.hanaro.hanamate.domain.MyWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.MyWallet.Dto.RequestDto;
import team.hanaro.hanamate.global.Response;


@Tag(name = "내 지갑", description = "내 지갑과 관련된 기능")
@RequiredArgsConstructor
@RequestMapping("/my-wallet")
@RestController
public class MyWalletController {

    private final WalletService walletService;
    private final Response response;

    @Operation(summary = "지갑 잔액 조회", description = "내 지갑 잔액 가져오기", tags = {"내 지갑"})
    @GetMapping("")
    public ResponseEntity<?> myWallet(@AuthenticationPrincipal UserDetails userDetails) {
        return walletService.myWallet(userDetails.getUsername());
    }

    @Operation(summary = "거래내역 조회", description = "내 지갑 거래내역 가져오기", tags = {"내 지갑"})
    @GetMapping("/transactions")
    public ResponseEntity<?> myWalletTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        return walletService.myWalletTransactions(userDetails.getUsername());
    }

    @Operation(summary = "계좌 잔액 조회", description = "연결된 은행계좌 잔액 조회", tags = {"내 지갑"})
    @GetMapping("/account")
    public ResponseEntity<?> getAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return walletService.getAccount(userDetails.getUsername());
    }

    @Operation(summary = "충전하기", description = "연결된 은행계좌에서 돈 가져오기", tags = {"내 지갑"})
    @PostMapping("/account")
    public ResponseEntity<?> chargeFromAccount(@Validated @RequestBody RequestDto.Charge charge, @AuthenticationPrincipal UserDetails userDetails) {
        return walletService.chargeFromAccount(charge, userDetails.getUsername());
    }

    @Operation(summary = "계좌 연결", description = "은행 계좌 연결하기", tags = {"내 지갑"})
    @PostMapping("/connect")
    public ResponseEntity<?> connectAccount(@Validated @RequestBody RequestDto.AccountInfo accountInfo, @AuthenticationPrincipal UserDetails userDetails) {
        return walletService.connectAccount(accountInfo, userDetails.getUsername());
    }

    @Operation(summary = "지갑 to 지갑 이체", description = "지갑에서 지갑으로 이체하기", tags = {"내 지갑"})
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Validated @RequestBody RequestDto.Transfer transfer) {
        return walletService.transfer(transfer);
    }
}
