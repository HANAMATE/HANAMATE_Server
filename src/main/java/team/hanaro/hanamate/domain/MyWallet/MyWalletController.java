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
    public MyWalletResDto myWallet(@RequestBody MyWalletReqDto myWalletReqDTO) {
        return myWalletService.myWallet(myWalletReqDTO);
    }

    @GetMapping("/my-wallet/transactions")
    public List<MyWalletTransactionResDto> myWalletTransactions(@RequestBody MyWalletReqDto myWalletReqDTO) {
        return myWalletService.myWalletTransactions(myWalletReqDTO);
    }


}
