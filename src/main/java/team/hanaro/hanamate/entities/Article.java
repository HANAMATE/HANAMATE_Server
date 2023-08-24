package team.hanaro.hanamate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    //    @Builder.Default
//    @Lob
//    private byte[] image;
    @Nullable
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Images> imagesList = new ArrayList<>();

    private String title;
    private String content;
    @Builder.Default
    private Long likes = 0L; //TODO: likes 관련 요구사항 다시 확인하기

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    @JsonIgnore
    private Transactions transaction;

    @Builder.Default
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    //List<Commnets> comments = new ArrayList<>() 필드 초기화를 했으나
    //NPE(Nuill Pointer Exception)이 발생함. 디버깅 해보니 초기화가 안되어서 null 상태임

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setArticle(this);
    }
//    public void addComment(Comments comment){
//        this.comments.add(comment);
//        comment.setArticle(this);
//    }

}
