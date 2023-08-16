package team.hanaro.hanamate.domain.moimWallet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.hanaro.hanamate.domain.MyWallet.Repository.AccountRepository;
import team.hanaro.hanamate.domain.MyWallet.Repository.MyWalletRepository;
import team.hanaro.hanamate.domain.MyWallet.Repository.TransactionRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.moimWallet.dto.MoimWalletRequestDto;
import team.hanaro.hanamate.domain.moimWallet.dto.MoimWalletResponseDto;
import team.hanaro.hanamate.domain.moimWallet.repository.MoimWalletRepository;
import team.hanaro.hanamate.domain.moimWallet.repository.MoimWalletAndUserRepository;
import team.hanaro.hanamate.entities.MoimWallet;
import team.hanaro.hanamate.entities.MoimWalletAndUser;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoimWalletService {
    private final MyWalletRepository walletRepository;
    private final MoimWalletRepository moimWalletRepository;
    private final MoimWalletAndUserRepository moimWalletAndUserRepository;
    private final UsersRepository usersRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final Response response;

    public ResponseEntity<?> getMoimWalletByLoginId(MoimWalletRequestDto.findAllMoimWalletDTO moimWalletDto){
        Optional<User> byLoginId = usersRepository.findByLoginId(moimWalletDto.getUserId());
        if (byLoginId.isEmpty()) {
            return response.fail("해당하는 유저 아이디가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<MoimWalletAndUser> moimWalletAndUsers = byLoginId.get().getMoimWalletAndUsers();
        if (!moimWalletAndUsers.isEmpty()) {
            List<MoimWalletResponseDto.MoimWalletList> moimWalletLists = new ArrayList<>();
            for (MoimWalletAndUser moimWalletAndUser : moimWalletAndUsers) {
                Optional<MoimWallet> moimWallet =  moimWalletRepository.findById(moimWalletAndUser.getMoimWalletId());
                MoimWalletResponseDto.MoimWalletList moimWalletListDTO = new MoimWalletResponseDto.MoimWalletList(moimWallet.get());
                moimWalletLists.add(moimWalletListDTO);
            }
            return response.success(moimWalletLists, "내 모임통장 조회에 성공했습니다.", HttpStatus.OK);
        } else {
            return response.fail("내 모임통장이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 새로운 모임 통장 만들기어서 새로운 모임통장 idx 값 반환하기.
     * */
    public ResponseEntity<?> createMoimWallet(MoimWalletRequestDto.JoinMoimWalletDTO joinMoimWalletDTO) {

        Optional<User> savedUser = usersRepository.findByLoginId(joinMoimWalletDTO.getUserId());
        if (savedUser.isEmpty()) {
            return response.fail("모임통장 개설 실패 - 잘못된 유저 ID 입니다.", HttpStatus.BAD_REQUEST);
        }
        //1. 모임 통장 만들기
        MoimWallet moimWallet = MoimWallet.builder()
                .targetAmount(joinMoimWalletDTO.getTargetAmount())
                .walletName(joinMoimWalletDTO.getWalletName())
                .build();

        MoimWallet savedMoimWallet = (MoimWallet) walletRepository.save(moimWallet);
        walletRepository.flush();
        //2.모임&유저 매핑용 엔티티 만들기
        MoimWalletAndUser moimWalletAndUser = new MoimWalletAndUser();

        //duplicateCheck()
        //3. 모임통장 - 사용자 매핑 ==> WalletAndMember로 만들고 다대다 매핑
        moimWalletAndUser.setMoimWalletId(savedMoimWallet.getId());
        moimWalletAndUser.setUserId(savedUser.get().getIdx());

        //4.다대다 매핑 엔티티 저장하기
        MoimWalletAndUser savedMoimWalletAndUser = moimWalletAndUserRepository.save(moimWalletAndUser);
        moimWalletAndUserRepository.flush();
//        moim.save(moimWalletAndUser);
        return response.success(savedMoimWalletAndUser, "모임통장 개설에 성공했습니다.", HttpStatus.OK);
    }
}
