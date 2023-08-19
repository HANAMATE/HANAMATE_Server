package team.hanaro.hanamate.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "parent_and_child")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentAndChild {
    @Id
    private Long id;
//    @Column(name = "parent_id")
//    private Long parentId;
//    @Column(name = "children_id")
//    private Long childrenId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parentId;
    @ManyToOne
    @JoinColumn(name = "children_id")
    private Child childrenId;

}
