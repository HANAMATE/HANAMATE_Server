package team.hanaro.hanamate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

@Entity
@Table(name = "images")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
