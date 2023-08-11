package team.hanaro.hanamate.domain.moimWallet.repository;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.github.gavlyukovskiy.boot.jdbc.decorator.p6spy.P6SpyConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import team.hanaro.hanamate.domain.MyWallet.Repository.WalletRepository;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.MyWallet;
import team.hanaro.hanamate.entities.Transactions;
import team.hanaro.hanamate.entities.Wallets;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DataJpaTest(showSql = false)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@Import(P6SpyConfiguration.class)
//@AutoCOnfigur
@Rollback(value = false)
class MoimWalletRepositoryTest {

    @Autowired
    MoimWalletRepository moimWalletRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    MyWalletRepository myWalletRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("가입 테스트")
    void initTest() {
        MoimWallet moimWallet = new MoimWallet();
        moimWallet.setBalance(100);
        moimWallet.setTarget_amount(20000);
        List<Transactions> transactions = new ArrayList<>();
        Transactions t1 = Transactions.builder()
                .amount(100)
                .balance(200)
                .success(Boolean.TRUE)
                .wallet(moimWallet)
                .counterId(moimWallet.getId())
                .location("알파코 아이스티 성수점")
                .build();
        Transactions t2 = Transactions.builder()
                .amount(200)
                .balance(300)
                .success(Boolean.TRUE)
                .wallet(moimWallet)
                .counterId(moimWallet.getId())
                .location("알파코 아이스티 성수점")
                .build();
        transactions.add(t1);
        transactions.add(t2);
        moimWallet.setTransactions(transactions);
        MoimWallet savedWallet = moimWalletRepository.save(moimWallet);

        em.flush();
        em.clear();
        Assertions.assertThat(savedWallet.getId()).isEqualTo(moimWallet.getId());
    }

    @Test
    @DisplayName("그냥 통장도 되나")
    void downCastTest(){
        List<Transactions> transactions = new ArrayList<>();
        Transactions t1 = Transactions.builder()
                .amount(100)
                .balance(200)
                .success(Boolean.TRUE)
                .location("알파코 아이스티 성수점")
                .build();
        Transactions t2 = Transactions.builder()
                .amount(200)
                .balance(300)
                .success(Boolean.TRUE)
                .location("알파코 아이스티 성수점")
                .build();
        System.out.println("위에 모임 월렛");
        MyWallet myWallet = new MyWallet();
        t1.setWallet(myWallet);
        myWallet.setBalance(100);
        myWallet.addTransactions(t1);
        myWalletRepository.save(myWallet);
        System.out.println("위에 마이 월렛");
//        em.flush();

        Transactions t3 = Transactions.builder()
                .amount(100)
                .balance(200)
                .success(Boolean.TRUE)
                .location("T3 로케이션")
                .build();
        MyWallet downWallet = new MoimWallet();
        t3.setWallet(downWallet);
        downWallet.setBalance(100);
        downWallet.addTransactions(t3);
        myWalletRepository.save(downWallet);
        myWalletRepository.save(myWallet);
        System.out.println("위에 다운 월렛");
        System.out.println(t3.getId());
        List<MyWallet> all = myWalletRepository.findAll();
        for (MyWallet wallet : all) {
            System.out.println("전부 뽑아보기" + wallet.toString());
        }
        em.flush();
        em.clear();

    }
}
