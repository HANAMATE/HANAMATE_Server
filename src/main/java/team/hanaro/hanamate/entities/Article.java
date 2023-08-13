package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private Long walletId;
    private Long imageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transactions transaction;

    @Builder.Default
    @OneToMany(mappedBy = "article",cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();
    //List<Commnets> comments = new ArrayList<>() 필드 초기화를 했으나
    //NPE(Nuill Pointer Exception)이 발생함. 디버깅 해보니 초기화가 안되어서 null 상태임

    public void addComment(Comments comment){
        comments.add(comment);
        comment.setArticle(this);
    }
//    public void addComment(Comments comment){
//        this.comments.add(comment);
//        comment.setArticle(this);
//    }
//    private String title;
//    private String content;
//    private Timestamp postedDate;
//    private Long likes; //TODO: likes 관련 요구사항 다시 확인하기
}
