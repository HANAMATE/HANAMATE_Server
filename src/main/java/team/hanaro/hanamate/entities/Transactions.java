package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
// TODO: 2023/08/09 Transactions -> Transaction 으로 변경인 후 톡 주세요
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    @Setter
    private MyWallet wallet;

    private Long counterId;
    private Timestamp transactionDate;
    private String transactionType;
    private Integer amount;
    private String location;
    private Integer balance;
    private Boolean success;

//    @OneToOne(mappedBy = "transaction",cascade = CascadeType.ALL,orphanRemoval = true)
//    private Article article;




}
