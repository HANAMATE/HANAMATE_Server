package team.hanaro.hanamate.domain.moimWallet;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.GetOrDeleteArticleRequestDTO;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.WriteArticleRequestDTO;
import team.hanaro.hanamate.domain.moimWallet.repository.ArticleRepository;
import team.hanaro.hanamate.domain.moimWallet.service.MoimWalletService;
import team.hanaro.hanamate.domain.moimWallet.service.SNSService;
import team.hanaro.hanamate.global.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.*;

@RestController
@RequestMapping("/moim/sns")
@RequiredArgsConstructor
@Slf4j
public class SNSController {

    private final SNSService snsService;
    private final MoimWalletService moimWalletService;
    private final Response response;
    private final AwsS3Service awsS3Service;

    @Operation(summary = "거래내역 글쓰기", description = "거래내역 1개에 대해서 1개의 글을 쓸 수 있습니다.", tags = {"모임통장"})
    @PostMapping("/article")
    public ResponseEntity<?> writeArticle(@Validated @ModelAttribute WriteArticleRequestDTO articleDTO,
                                          @RequestPart List<MultipartFile> multipartFile) {
        try {//이미지 안들어왔으면 그냥 초기화해서 넣어둠
            return snsService.writeArticle(articleDTO, multipartFile);
        } catch (ResponseStatusException e) {
            return response.fail(articleDTO, "이미지 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException | SQLException e) {
            return response.fail(articleDTO, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<?> deleteImage(@RequestBody Map<String,String> param) {
        if (param.get("fileName").isEmpty() || param.isEmpty()) {
            return response.fail("fileName을 입력해야 합니다.",HttpStatus.BAD_REQUEST);
        }
        return snsService.deleteImage(param.get("fileName"));
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

    @Autowired
    private ArticleRepository articleRepository;

//    @PostMapping("/article/upload")
//    @Transactional
//    public ResponseEntity<String> uploadArticleWithImage(
//            @RequestParam("title") String title,
//            @RequestParam("content") String content,
//            @RequestParam("image") MultipartFile image) {
//
//        try {
//            Article article = new Article();
//            article.setTitle(title);
//            article.setContent(content);
////            article.setImage(image.getBytes()); // 이미지 바이트 데이터 저장
//
//            articleRepository.save(article);
//
//            return ResponseEntity.ok("Article with image uploaded successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error uploading article with image.");
//        }
//    }

}
