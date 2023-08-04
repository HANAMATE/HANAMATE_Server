package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long articleId;
    private Long writerId;

    private String content;
    private Timestamp postedDate;
}
