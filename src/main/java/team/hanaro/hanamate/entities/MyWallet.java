package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@DiscriminatorValue("my")
@Table(name="my_wallets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString
@Getter
public class MyWallet {

    @Id
    @GeneratedValue
    @Column(name = "wallet_id")
    private Long id;

    @Setter
    private  Integer balance=0;

    //cascade = Persist 속성을 명시해줌으로써, 영속성 전이를 사용하였음.
    //orphanRemoval = true 를 사용해서 고아 객체를 자동으로 제거 함.
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions = new ArrayList<>();

    public void addTransactions(Transactions transaction){
        this.transactions.add(transaction);
        transaction.setWallet(this);
    }

    /**
     * DescrimainatorValue는 구분자를 뜻함.
     * 모임 통장(모임 지갑), 개인 지갑 둘의 구분을 dtype 컬럼에서 moim 또는 my 로 구분하는데
     * 아래의 메소드를 사용하여 해당 값을 가져올 수 있습니다.
     * */
    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
    // local~~ /loan/request_id = 1 & id = 2
}
