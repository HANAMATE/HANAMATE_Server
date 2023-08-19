package team.hanaro.hanamate.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Parent")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "parents")
public class Parent extends User {
    @Builder.Default
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParentAndChild> myChildList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loans> LoansRequestList = new ArrayList<>();
}
