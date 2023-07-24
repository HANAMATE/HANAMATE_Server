package team.hanaro.hanamate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private Long articleId;
    private Long writerId;

    private String content;
    private Timestamp postedDate;
}
