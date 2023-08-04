package team.hanaro.hanamate.domain.MyWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "내 지갑", description = "내 지갑과 관련된 기능")
@RestController
public class MyWalletController {

    private final MyWalletService myWalletService;

    public MyWalletController(MyWalletService myWalletService) {
        this.myWalletService = myWalletService;
    }

    @Operation(summary = "Healthy Check", description = "API Healthy Check", tags = {"내 지갑"})
    @GetMapping("/my-wallet/healthy")
    public String HealthyCheck() {
        return "healthy";
    }

    @Operation(summary = "지갑 잔액 조회", description = "내 지갑 잔액 가져오기", tags = {"내 지갑"})
    @GetMapping("/my-wallet")
    public ResponseDto.MyWallet myWallet(@RequestBody RequestDto.MyWallet myWallet) {
        return myWalletService.myWallet(myWallet);
    }

    @Operation(summary = "거래내역 조회", description = "내 지갑 거래내역 가져오기", tags = {"내 지갑"})
    @GetMapping("/my-wallet/transactions")
    public List<ResponseDto.MyTransactions> myWalletTransactions(@RequestBody RequestDto.MyWallet myWallet) {
        return myWalletService.myWalletTransactions(myWallet);
    }

    @Operation(summary = "계좌 잔액 조회", description = "연결된 은행계좌 잔액 조회", tags = {"내 지갑"})
    @GetMapping("/my-wallet/account")
    public ResponseDto.AccountBalance GetAccountBalance(@RequestBody RequestDto.AccountBalance account) {
        return myWalletService.getAccountBalance(account);
    }

    @Operation(summary = "충전하기", description = "연결된 은행계좌에서 돈 가져오기", tags = {"내 지갑"})
    @PostMapping("/my-wallet/account")
    public String GetMoneyFromAccount(@RequestBody RequestDto.RequestAmount requestAmount) {
        return myWalletService.getMoneyFromAccount(requestAmount);
    }
}
