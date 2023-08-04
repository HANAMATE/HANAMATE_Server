package team.hanaro.hanamate.domain.MyWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
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

    @Operation(summary = "잔액 조회", description = "내 지갑 잔액 가져오기", tags = {"내 지갑"})
    @GetMapping("/my-wallet")
    public MyWalletResDto myWallet(@RequestBody MyWalletReqDto myWalletReqDTO) {
        return myWalletService.myWallet(myWalletReqDTO);
    }

    @Operation(summary = "거래내역 조회", description = "내 지갑 거래내역 가져오기", tags = {"내 지갑"})
    @GetMapping("/my-wallet/transactions")
    public List<MyWalletTransactionResDto> myWalletTransactions(@RequestBody MyWalletReqDto myWalletReqDTO) {
        return myWalletService.myWalletTransactions(myWalletReqDTO);
    }
}
