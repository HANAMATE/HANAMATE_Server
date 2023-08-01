package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyWalletReqDto {
    //TODO: 입력값 Validation Check
    @NotBlank
    private Long walletId;

    @Nullable
    private Integer year;
    @Nullable
    private Integer month;
}
