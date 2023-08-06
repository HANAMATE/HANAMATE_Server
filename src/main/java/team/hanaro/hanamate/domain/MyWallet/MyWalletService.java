package team.hanaro.hanamate.domain.MyWallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.entities.Transactions;
import team.hanaro.hanamate.entities.Wallets;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyWalletService {
    private final MyWalletRepository myWalletRepository;
    private final MyWalletTransactionsRepository myWalletTransactionsRepository;

    public MyWalletResDto myWallet(MyWalletReqDto myWalletReqDTO) {
        Optional<Wallets> myWalletInfo = myWalletRepository.findById(myWalletReqDTO.getWalletId());
        if (myWalletInfo.isPresent()) {
            MyWalletResDto myWalletResDTO = new MyWalletResDto(myWalletInfo.get());
            return myWalletResDTO;
        } else {
            return null;
        }
    }

    public List<MyWalletTransactionResDto> myWalletTransactions(MyWalletReqDto myWalletReqDTO) {
        Integer year;
        Integer month;

        if (myWalletReqDTO.getYear() != null && myWalletReqDTO.getMonth() != null) {
            year = myWalletReqDTO.getYear();
            month = myWalletReqDTO.getMonth();
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }

        HashMap<String, Timestamp> map = getDate(year, month);
        Optional<List<Transactions>> myTransactionsInfoList = myWalletTransactionsRepository.findAllByWalletIdAndTransactionDateBetween(myWalletReqDTO.getWalletId(), map.get("startDate"), map.get("endDate"));

        if (myTransactionsInfoList.isPresent()) {
            List<Transactions> transactionsList = myTransactionsInfoList.get();
            List<MyWalletTransactionResDto> myWalletTransactionResDtoList = new ArrayList<>();
            for (Transactions transaction : transactionsList) {
                MyWalletTransactionResDto myWalletTransactionResDTO = new MyWalletTransactionResDto(transaction);
                myWalletTransactionResDtoList.add(myWalletTransactionResDTO);
            }
            return myWalletTransactionResDtoList;
        } else {
            return null;
        }
    }

    public HashMap<String, Timestamp> getDate(Integer year, Integer month) {

        HashMap<String, Timestamp> map = new HashMap<String, Timestamp>();

        Calendar startDate = Calendar.getInstance();  // 현재 시간정보 가지고오기
        startDate.set(Calendar.YEAR, year);  //년 설정
        startDate.set(Calendar.MONTH, month - 1);  //월 설정
        startDate.set(Calendar.DATE, 1);  //일 설정
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);

        Calendar endDate = Calendar.getInstance();  // 현재 시간정보 가지고오기
        endDate.set(Calendar.YEAR, year);  //년 설정
        endDate.set(Calendar.MONTH, month - 1);  //월 설정
        endDate.set(Calendar.DATE, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);
        endDate.set(Calendar.MILLISECOND, 249);

        Timestamp start = new Timestamp(startDate.getTimeInMillis());
        Timestamp end = new Timestamp(endDate.getTimeInMillis());

        map.put("startDate", start);
        map.put("endDate", end);

        return map;
    }
}
