package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parents_and_child")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentsAndChilds {
    @Id
    private Long childrenId;

    private Long parentsId;
}
