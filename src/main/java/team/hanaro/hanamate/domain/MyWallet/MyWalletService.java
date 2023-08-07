package team.hanaro.hanamate.domain.MyWallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.entities.Account;
import team.hanaro.hanamate.entities.Transactions;
import team.hanaro.hanamate.entities.Wallets;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MyWalletService {
    private final MyWalletRepository myWalletRepository;
    private final MyWalletTransactionsRepository myWalletTransactionsRepository;
    private final AccountRepository accountRepository;

    public ResponseDto.MyWallet myWallet(RequestDto.MyWallet myWalletReqDto) {
        Optional<Wallets> myWalletInfo = myWalletRepository.findById(myWalletReqDto.getWalletId());
        if (myWalletInfo.isPresent()) {
            ResponseDto.MyWallet myWalletResDto = new ResponseDto.MyWallet(myWalletInfo.get());
            return myWalletResDto;
        } else {
            return null;
        }
    }

    public List<ResponseDto.MyTransactions> myWalletTransactions(RequestDto.MyWallet myWalletReqDto) {
        Integer year;
        Integer month;

        if (myWalletReqDto.getYear() != null && myWalletReqDto.getMonth() != null) {
            year = myWalletReqDto.getYear();
            month = myWalletReqDto.getMonth();
        } else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }

        HashMap<String, Timestamp> map = getDate(year, month);
        Optional<List<Transactions>> myTransactionsInfoList = myWalletTransactionsRepository.findAllByWalletIdAndTransactionDateBetween(myWalletReqDto.getWalletId(), map.get("startDate"), map.get("endDate"));

        if (myTransactionsInfoList.isPresent()) {
            List<Transactions> transactionsList = myTransactionsInfoList.get();
            List<ResponseDto.MyTransactions> myWalletTransactionResDtoList = new ArrayList<>();
            for (Transactions transaction : transactionsList) {
                ResponseDto.MyTransactions myWalletTransactionResDTO = new ResponseDto.MyTransactions(transaction);
                myWalletTransactionResDtoList.add(myWalletTransactionResDTO);
            }
            return myWalletTransactionResDtoList;
        } else {
            return null;
        }
    }

    public ResponseDto.AccountBalance getAccountBalance(RequestDto.AccountBalance accountReqDto) {
        Optional<Account> account = accountRepository.findByMemberId(accountReqDto.getMemberId());
        if (account.isPresent()) {
            ResponseDto.AccountBalance accountResDto = new ResponseDto.AccountBalance(account.get());
            return accountResDto;
        } else {
            return null;
        }
    }

    public String getMoneyFromAccount(RequestDto.RequestAmount requestAmount) {
        Optional<Account> account = accountRepository.findByMemberId(requestAmount.getMemberId());
        Optional<Wallets> wallet = myWalletRepository.findById(requestAmount.getWalletId());
        if (account.isPresent() && wallet.isPresent()) {

            if (account.get().getBalance() < requestAmount.getAmount()) {   // 1. 남은 잔액보다 돈이 적을 때
                return "계좌 잔액이 부족합니다.";
            } else {  // 2. 남은 잔액보다 돈이 많을 때
                // 2-1. transaction 추가
                makeTransaction(account.get(), wallet.get(), requestAmount);
                // 2-2. wallet 잔액 추가
                myWalletRepository.updateByWalletId(wallet.get().getWalletId(), Long.valueOf(wallet.get().getBalance() + requestAmount.getAmount()));
                // 2-3. account 잔액 차감
                accountRepository.updateByMemberId(requestAmount.getMemberId(), account.get().getBalance() - requestAmount.getAmount());
                return "success";
            }
        } else {
            return "계좌가 존재하지 않습니다.";
        }
    }

    public String connectAccount(RequestDto.ConnectAccount connectAccount) {
        Optional<Account> account = accountRepository.findByMemberId(connectAccount.getMemberId());
        // 1. 연결된 계좌가 없을 때
        if (account.isEmpty()) {// 새로운 계좌 만들어서 save
            // balance 난수 생성
            double randomValue = Math.random();
            int intValue = (int) (randomValue * 100);
            Account newAccount = Account.builder()
                    .memberId(connectAccount.getMemberId())
                    .accountId(connectAccount.getAccountId())
                    .openDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                    .name(connectAccount.getName())
                    .balance(10000 * intValue)
                    .build();
            accountRepository.save(newAccount);
            return "success";
        } else {
            return "이미 계좌가 존재합니다.";
        }
    }

    public void makeTransaction(Account account, Wallets wallet, RequestDto.RequestAmount requestAmount) {
        Transactions transactions = Transactions.builder()
                .walletId(wallet.getWalletId())
                .transactionDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
                .transactionType("충전")
                .amount(requestAmount.getAmount())
                .balance(account.getBalance() - requestAmount.getAmount())
                .build();

        myWalletTransactionsRepository.save(transactions);
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
