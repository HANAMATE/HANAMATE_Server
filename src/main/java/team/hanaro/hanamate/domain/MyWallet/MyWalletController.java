package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController
public class MyWalletController {

    private final MyWalletService myWalletService;

    public MyWalletController(MyWalletService myWalletService) {
        this.myWalletService = myWalletService;
    }

    @GetMapping("/my-wallet")
    public ResponseDto.MyWallet myWallet(@RequestBody RequestDto.MyWallet myWallet) {
        return myWalletService.myWallet(myWallet);
    }

    @GetMapping("/my-wallet/transactions")
    public List<ResponseDto.MyTransactions> myWalletTransactions(@RequestBody RequestDto.MyWallet myWallet) {
        return myWalletService.myWalletTransactions(myWallet);
    }

    @GetMapping("/my-wallet/account")
    public ResponseDto.AccountBalance GetAccountBalance(@RequestBody RequestDto.AccountBalance account) {
        return myWalletService.getAccountBalance(account);
    }
}
