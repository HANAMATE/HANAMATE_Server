package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "images")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private String fileName;
    private Integer fileSize;
    private String fileType;
    // TODO: 2023-08-15(015) fileData 전송 방식 알아보기
    @Lob
    private Blob fileData;
    private Timestamp createdAt;
}
