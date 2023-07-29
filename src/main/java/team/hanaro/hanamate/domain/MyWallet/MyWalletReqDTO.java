package team.hanaro.hanamate.domain.MyWallet;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyWalletReqDTO {
    @NotBlank
    private Long walletId;
}
