package team.hanaro.hanamate.domain.MyWallet;

import org.springframework.stereotype.Service;
import team.hanaro.hanamate.entities.Wallets;

import java.util.Optional;

@Service
public class MyWalletService {
    private final MyWalletRepository myWalletRepository;

    public MyWalletService(MyWalletRepository myWalletRepository) {
        this.myWalletRepository = myWalletRepository;
    }

    public MyWalletResDTO myWallet(MyWalletReqDTO myWalletReqDTO){
        Optional<Wallets> myWalletInfo = myWalletRepository.findById(myWalletReqDTO.getWalletId());
        if (myWalletInfo.isPresent()){
            // 값이 존재
            MyWalletResDTO myWalletResDTO = new MyWalletResDTO().fromWalletsEntity(myWalletInfo.get());
            return myWalletResDTO;
        } else{
           //값이 없음
           return null;
        }
    }
}
