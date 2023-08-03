package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "article")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private Long walletId;
    private Long imageId;
    private Long transactionId;

    private String title;
    private String content;
    private Timestamp postedDate;
    private Long likes; //TODO: likes 관련 요구사항 다시 확인하기
}
