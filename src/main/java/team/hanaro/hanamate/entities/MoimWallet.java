package team.hanaro.hanamate.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("moim")
@Getter @Setter
public class MoimWallet extends MyWallet{

    //목표 금액
    private Integer target_amount;

    //비즈니스 로직
    public void updateTargetAmount(int target_amount)
    {
        this.target_amount = target_amount;
    }
}
