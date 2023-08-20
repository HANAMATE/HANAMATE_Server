package team.hanaro.hanamate.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "Child")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "childs")
public class Child extends User{
    @Builder.Default
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParentAndChild> myParentList = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loans> LoansRequestList = new ArrayList<>();
}
