package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "images")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private Long id;
    private String fileName;
    //UUID로 저장된 실제 이름
    private String savedName;
    private Long fileSize;
    //S3에 저장된 실제 위치
    private String savedPath;
    //저장된 파일 형식 전부
    private String fullFileType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id")
    Article article;

}
