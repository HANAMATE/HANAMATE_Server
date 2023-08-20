package team.hanaro.hanamate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="parent_idx")
    @JsonIgnore
    private Parent parent;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="child_idx")
    @JsonIgnore
    private Child child;

}
