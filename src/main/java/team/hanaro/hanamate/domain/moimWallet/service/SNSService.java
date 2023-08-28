package team.hanaro.hanamate.domain.moimWallet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.hanaro.hanamate.domain.MyWallet.Repository.TransactionRepository;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.domain.moimWallet.AwsS3Service;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSResponseDTO;
import team.hanaro.hanamate.domain.moimWallet.dto.SNSResponseDTO.AddLikeResponseDTO;
import team.hanaro.hanamate.domain.moimWallet.repository.ArticleRepository;
import team.hanaro.hanamate.domain.moimWallet.repository.CommentRepository;
import team.hanaro.hanamate.domain.moimWallet.repository.ImageRepository;
import team.hanaro.hanamate.entities.*;
import team.hanaro.hanamate.global.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static team.hanaro.hanamate.domain.moimWallet.dto.SNSRequestDTO.*;
import static team.hanaro.hanamate.domain.moimWallet.dto.SNSResponseDTO.*;

@Service
@RequiredArgsConstructor
public class SNSService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final TransactionRepository transactionRepository;
    private final ImageRepository imageRepository;
    private final Response response;
    private final AwsS3Service awsS3Service;

    //1. 글 작성/수정 기능 (Create,Update)
    public ResponseEntity<?> writeArticle(SNSRequestDTO.WriteArticleRequestDTO articleDTO,
                                          List<MultipartFile> multipartFile) throws SQLException, IOException {
        //1-1. 트랜잭션 id로 해당 트랜잭션 찾아온다.
        Optional<Transactions> savedTransaction = transactionRepository.findById(articleDTO.getTransactionId());
        boolean fileIsPresent = false;
        if (savedTransaction.isEmpty()) {
            return response.fail("잘못된 거래내역 입니다.", HttpStatus.BAD_REQUEST);
        }
        SNSResponseDTO.ArticleResponseDTO articleResponseDTO = null;
        if (savedTransaction.get().getArticle() == null) {
            //1-2존재하지 않으면 생성
            Article article = Article.builder()
                    .content(articleDTO.getContent())
                    .title(articleDTO.getTitle())
                    .transaction(savedTransaction.get()).build();
            for (MultipartFile file : multipartFile) {
                if(!file.isEmpty()){
                    fileIsPresent=true;
                    break;
                }
            }
            if (fileIsPresent) {
                article.setImagesList(awsS3Service.uploadImage(multipartFile,article));
            }
            Article savedArticle = articleRepository.save(article);
            articleResponseDTO = new SNSResponseDTO.ArticleResponseDTO(savedArticle,fileIsPresent);
            return response.success(articleResponseDTO, "성공적으로 글이 작성되었습니다.", HttpStatus.OK);
        } else {
            //1-3존재하면 수정
            Article savedArticle = savedTransaction.get().getArticle();
            savedArticle.setContent(articleDTO.getContent());
            savedArticle.setTitle(articleDTO.getTitle());

            //글의 사진 수정은 입력, 삭제만 하도록 하여서 여기에서는 수정 X
            articleRepository.save(savedArticle);
            if (!savedArticle.getImagesList().isEmpty()) {
                //이미지가 존재하면 true
                fileIsPresent = true;
            }
            articleResponseDTO = new SNSResponseDTO.ArticleResponseDTO(savedArticle,fileIsPresent);
            List<SNSResponseDTO.CommentResponseDTO> commentDTOList = new ArrayList<>();
            for (Comment comment : savedArticle.getComments()) {
                SNSResponseDTO.CommentResponseDTO temp = new SNSResponseDTO.CommentResponseDTO(comment);
                commentDTOList.add(temp);
            }
            articleResponseDTO.setCommentList(commentDTOList);
            return response.success(articleResponseDTO, "글이 수정되었습니다.", HttpStatus.OK);
        }
    }

    //2. 글 삭제 기능 (Delete)
    public ResponseEntity<?> deleteArticle(GetOrDeleteArticleRequestDTO deleteRequestDTO) {
        Optional<Article> savedArticle = articleRepository.findById(deleteRequestDTO.getArticleId());
        if (savedArticle.isEmpty()) {
            return response.fail("존재하지 않는 글입니다.", HttpStatus.BAD_REQUEST);
        } else {
            articleRepository.delete(savedArticle.get());
            articleRepository.flush();
            if (articleRepository.findById(deleteRequestDTO.getArticleId()).isEmpty()) {
                return response.success(deleteRequestDTO.getArticleId() + "글이 성공적으로 삭제되었습니다.");
            } else {
                return response.fail("글 삭제에 실패 했습니다.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Transactional
    public ResponseEntity<?> deleteImage(String fileName){
        Optional<Images> optionalImage = imageRepository.findBySavedName(fileName);
        if (optionalImage.isEmpty()) {
            return response.fail("없는 이미지 입니다.",HttpStatus.NOT_FOUND);
        }
        else{
            awsS3Service.deleteImage(fileName);
            imageRepository.delete(optionalImage.get());
            return response.success("삭제 되었습니다.");
        }
    }
    //3.글 및 댓글 읽어오기 기능 (Read)
    public ResponseEntity<?> getArticleAndAllComment(GetOrDeleteArticleRequestDTO requestDTO) {
        Optional<Article> optionalArticle = articleRepository.findFetchById(requestDTO.getArticleId());
        if (optionalArticle.isEmpty()) {
            return response.fail("잘못된 글이거나 없는 글입니다.", HttpStatus.NOT_FOUND);
        }
        return response.success(getArticleResponseDTO(optionalArticle));
    }

    //1.글 좋아요 기능 (Feature)
    public ResponseEntity<?> addLike(AddLikeRequestDTO requestDTO) {
        Optional<Article> optionalArticle = articleRepository.findById(requestDTO.getArticleId());
        if (optionalArticle.isEmpty()) {
            return response.fail("잘못된 글이거나 없는 글입니다.", HttpStatus.NOT_FOUND);
        }
        Article article = optionalArticle.get();
        article.setLikes(optionalArticle.get().getLikes() + 1);
        articleRepository.save(article);
        return response.success(new AddLikeResponseDTO(optionalArticle.get()), "좋아요 누르기에 성공했습니다.", HttpStatus.OK);
    }

    //1.댓글 작성 기능 (댓글 Create)
    public ResponseEntity<?> writeComment(WriteCommentRequestDTO requestDTO) {
        Optional<Article> optionalArticle = articleRepository.findById(requestDTO.getArticleId());
        if (optionalArticle.isEmpty()) {
            return response.fail("잘못된 글이거나 없는 글입니다.", HttpStatus.NOT_FOUND);
        }
        Optional<User> optionalUser = usersRepository.findByLoginId(requestDTO.getUserId());
        if (optionalUser.isEmpty()) {
            return response.fail("잘못된 유저 아이디 입니다.", HttpStatus.UNAUTHORIZED);
        }
        Article article = optionalArticle.get();
        User user = optionalUser.get();
        Comment comment = new Comment(null, article, user,user.getName(), requestDTO.getContent());
        commentRepository.save(comment);
        return response.success(new WriteCommentResponseDTO(comment), "댓글 작성에 성공했습니다.", HttpStatus.CREATED);
    }

    //2. 댓글 불러오기 기능 (해당 기능은 위의 글 및 댓글 읽어오기 기능에 함께 있음)
    //3. 댓글 수정 기능
    public ResponseEntity<?> updateComment(UpdateCommentRequestDTO requestDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(requestDTO.getCommentId());
        if (optionalComment.isEmpty()) {
            return response.fail("잘못된 댓글이거나 없는 댓글입니다.", HttpStatus.NOT_FOUND);
        }
        Comment comment = optionalComment.get();
        comment.setContent(requestDTO.getContent());
        commentRepository.save(comment);
        return response.success(new CommentResponseDTO(comment),"댓글 수정에 성공했습니다.", HttpStatus.OK);
    }

    //4. 댓글 삭제 기능
    public ResponseEntity<?> deleteComment(DeleteCommentRequestDTO requestDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(requestDTO.getCommentId());
        if (optionalComment.isEmpty()) {
            return response.fail("이미 삭제된 댓글이거나 없는 댓글입니다.", HttpStatus.NOT_FOUND);
        }
        Comment comment = optionalComment.get();
        commentRepository.delete(comment);
        return response.success("댓글 삭제에 성공했습니다.");
    }

/**=================아래는 SNSService에서 반환값을 DTO로  제공하는 메소드=======================*/

    //ArticleResponseDTO를 반환해주는 메소드, 해당하는 글(글 내용, 댓글)을 DTO로 반환한다.
    public static Optional<ArticleResponseDTO> getArticleResponseDTO(Optional<Article> optionalArticle) {
        return optionalArticle.map(article -> {
            ArticleResponseDTO dto = new ArticleResponseDTO(article);
            List<CommentResponseDTO> commentResponseDTOList = getCommentResponseDTOList(article);
            dto.setCommentList(commentResponseDTOList);
            return dto;
        });
    }

    // param:Article,글의 댓글들을 DTO로 반환한다.
    public static List<CommentResponseDTO> getCommentResponseDTOList(Article article) {
        return article.getComments()
                .stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }
}
