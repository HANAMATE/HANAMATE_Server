package team.hanaro.hanamate.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@DiscriminatorValue("my")
@Table(name="my_wallets")
@Getter @Setter
public class MyWallet {

    @Id
    @GeneratedValue
    @Column(name = "wallet_id")
    private Long id;

    private  Integer balance=0;

    //cascade = Persist 속성을 명시해줌으로써, 영속성 전이를 사용하였음.
    //orphanRemoval = true 를 사용해서 고아 객체를 자동으로 제거 함.
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Transactions> transactions = new ArrayList<>();

    public void addTransactions(Transactions transaction){
        this.transactions.add(transaction);
        transaction.setWallet(this);
    }

    // local~~ /loan/request_id = 1 & id = 2

    //아이
    //요청하기 -> 얼마, 기간 -> 요청하기 -> loa

}
