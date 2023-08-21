package team.hanaro.hanamate.domain.moimWallet;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.GetOrDeleteArticleRequestDTO;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.WriteArticleRequestDTO;
import team.hanaro.hanamate.global.Response;

import java.io.IOException;
import java.sql.SQLException;

import static team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.*;

@RestController
@RequestMapping("/moim/sns")
@RequiredArgsConstructor
public class SNSController {

    private final SNSService snsService;
    private final MoimWalletService moimWalletService;
    private final Response response;

    @Operation(summary = "거래내역 글쓰기", description = "거래내역 1개에 대해서 1개의 글을 쓸 수 있습니다.", tags = {"모임통장"})
    @PostMapping("/article")
    public ResponseEntity<?> writeArticle(@Validated @RequestBody WriteArticleRequestDTO articleDTO) {
        try {//이미지 안들어왔으면 그냥 초기화해서 넣어둠
            if (articleDTO.getImage() == null) {
                articleDTO.setImage(new byte[]{});
            }
            return snsService.writeArticle(articleDTO);
        } catch (SQLException e) {
            return response.fail(articleDTO, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return response.fail(articleDTO, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "거래내역 글 삭제하기", description = "거래내역 글을 삭제합니다.", tags = {"모임통장"})
    @DeleteMapping("/article")
    public ResponseEntity<?> deleteArticle(@Validated @RequestBody GetOrDeleteArticleRequestDTO deleteRequestDTO) {
        return snsService.deleteArticle(deleteRequestDTO);
    }

    //3.글 및 댓글 읽어오기 기능 (Read)
    @PostMapping("/article/detail")
    public ResponseEntity<?> getArticleAndAllComment(@Validated @RequestBody GetOrDeleteArticleRequestDTO getRequestDTO) {
        return snsService.getArticleAndAllComment(getRequestDTO);
    }

    //글에 좋아요 클릭 기능
    @PostMapping("/article/like")
    public ResponseEntity<?> addLike(@Validated @RequestBody AddLikeRequestDTO requestDTO) {
        return snsService.addLike(requestDTO);
    }

    //댓글 작성 기능
    @PostMapping("/article/comment")
    public ResponseEntity<?> writeComment(@Validated @RequestBody WriteCommentRequestDTO requestDTO) {
        return snsService.writeComment(requestDTO);
    }

    //댓글 수정 기능
    @PutMapping("/article/comment")
    public ResponseEntity<?> writeComment(@Validated @RequestBody UpdateCommentRequestDTO requestDTO) {
        return snsService.updateComment(requestDTO);
    }

    //댓글 삭제 기능
    @DeleteMapping("/article/comment")
    public ResponseEntity<?> deleteCommnet(@Validated @RequestBody UpdateCommentRequestDTO requestDTO) {
        return snsService.deleteComment(requestDTO);
    }
}
