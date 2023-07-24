package team.hanaro.hanamate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parents_and_child")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParentsAndChilds {
    @Id
    private Long childrenId;

    private Long parentsId;
}
