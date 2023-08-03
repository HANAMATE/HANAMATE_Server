package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private Long walletId;
    private Long counterId;
    private Timestamp transactionDate;
    private String transactionType;
    private Integer amount;
    private String location;
    private Integer balance;
    private Boolean success;
}
