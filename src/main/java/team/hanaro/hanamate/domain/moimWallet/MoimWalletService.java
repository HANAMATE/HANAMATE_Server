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
import team.hanaro.hanamate.domain.moimWallet.repository.ArticleRepository;
import team.hanaro.hanamate.domain.moimWallet.repository.MoimWalletRepository;
import team.hanaro.hanamate.domain.moimWallet.repository.MoimWalletAndUserRepository;
import team.hanaro.hanamate.entities.*;
import team.hanaro.hanamate.global.Response;

import java.io.IOException;
import java.sql.SQLException;
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
    private final ArticleRepository articleRepository;
    private final Response response;

    public ResponseEntity<?> getMoimWalletByLoginId(MoimWalletRequestDto.findAllMoimWalletDTO moimWalletDto) {
        Optional<User> byLoginId = usersRepository.findByLoginId(moimWalletDto.getUserId());
        if (byLoginId.isEmpty()) {
            return response.fail("해당하는 유저 아이디가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<MoimWalletAndUser> moimWalletAndUsers = byLoginId.get().getMoimWalletAndUsers();
        if (!moimWalletAndUsers.isEmpty()) {
            List<MoimWalletResponseDto.MoimWalletList> moimWalletLists = new ArrayList<>();
            for (MoimWalletAndUser moimWalletAndUser : moimWalletAndUsers) {
                Optional<MoimWallet> moimWallet = moimWalletRepository.findById(moimWalletAndUser.getMoimWalletId());
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
     */
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

    public ResponseEntity<?> deleteMoimWallet(MoimWalletRequestDto.DeleteRequestDTO deleteRequestDTO) {
        Optional<MoimWallet> savedMoimwallet = moimWalletRepository.findById(deleteRequestDTO.getRequestId());
        if (savedMoimwallet.isEmpty()) {
            return response.fail("삭제 실패 : 잘못된 모임통장 ID 입니다.", HttpStatus.BAD_REQUEST);
        }
        else{
            moimWalletRepository.delete(savedMoimwallet.get());
            if (walletRepository.findById(deleteRequestDTO.getRequestId()).isEmpty()) {
                return response.success(deleteRequestDTO.getRequestId() + "번 통장이 성공적으로 삭제되었습니다.");
            }
            else{
                return response.fail("삭제에 실패 했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<?> getArticle() {
        return response.success("제작중");
    }

    public ResponseEntity<?> writeArticle(MoimWalletRequestDto.WriteArticleRequestDTO articleDTO) throws SQLException, IOException {
        //1. 트랜잭션 id로 해당 트랜잭션 찾아온다.
        Optional<Transactions> savedTransaction = transactionRepository.findById(articleDTO.getTransactionId());
        if (savedTransaction.isEmpty()) {
            return response.fail("잘못된 거래내역 입니다.", HttpStatus.BAD_REQUEST);
        }
        MoimWalletResponseDto.ArticleResponseDTO articleResponseDTO = null;
        if (savedTransaction.get().getArticle() == null) {
            //존재하지 않으면 생성
            Article article = Article.builder()
                    .content(articleDTO.getContent())
                    .title(articleDTO.getTitle())
                    .imageId(articleDTO.getImage())
                    .transaction(savedTransaction.get()).build();
            articleRepository.save(article);
            articleResponseDTO = new MoimWalletResponseDto.ArticleResponseDTO(article);
            return response.success(articleResponseDTO,"성공적으로 글이 작성되었습니다.",HttpStatus.OK);
        } else {
            //존재하면 수정
            Article savedArticle = savedTransaction.get().getArticle();
            savedArticle.setContent(articleDTO.getContent());
            savedArticle.setTitle(articleDTO.getTitle());
            savedArticle.setImageId(articleDTO.getImage());
            articleRepository.save(savedArticle);
            articleResponseDTO = new MoimWalletResponseDto.ArticleResponseDTO(savedArticle);
            List<MoimWalletResponseDto.ArticleResponseDTO.CommentDTO> commentDTOList = new ArrayList<>();
            for (Comment comment : savedArticle.getComments()) {
                MoimWalletResponseDto.ArticleResponseDTO.CommentDTO temp = new MoimWalletResponseDto.ArticleResponseDTO.CommentDTO(comment);
                commentDTOList.add(temp);
            }
            articleResponseDTO.setComments(commentDTOList);
            return response.success(articleResponseDTO,"글이 수정되었습니다.",HttpStatus.OK);
        }
        //2. 받아온 작성글(Article)을 DTO에서 새 엔티티로 반환
    }



    public ResponseEntity<?> deleteArticle(MoimWalletRequestDto.DeleteRequestDTO deleteRequestDTO) {
        Optional<Article> savedArticle = articleRepository.findById(deleteRequestDTO.getRequestId());
        if (savedArticle.isEmpty()) {
            return response.fail("존재하지 않는 글입니다.", HttpStatus.BAD_REQUEST);
        }
        else{
            articleRepository.delete(savedArticle.get());
            articleRepository.flush();
            if (articleRepository.findById(deleteRequestDTO.getRequestId()).isEmpty()) {
                return response.success(deleteRequestDTO.getRequestId() + "글이 성공적으로 삭제되었습니다.");
            }
            else{
                return response.fail("글 삭제에 실패 했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
