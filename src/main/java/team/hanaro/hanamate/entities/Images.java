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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private String fileName;
    private Integer fileSize;
    private String fileType;
    @Lob
    private Blob fileData;
    private Timestamp createdAt;
}
