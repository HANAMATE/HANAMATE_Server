package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//@Controller
@RestController
public class MyWalletController {

    private final MyWalletService myWalletService;

    public MyWalletController(MyWalletService myWalletService) {
        this.myWalletService = myWalletService;
    }

    @GetMapping("/healthy")
    public String myWallet(){
        return "healthy";
    }

    @GetMapping("/my-wallet")
    public MyWalletResDTO myWallet(@RequestBody MyWalletReqDTO myWalletReqDTO){
        return myWalletService.myWallet(myWalletReqDTO);
    }
}
