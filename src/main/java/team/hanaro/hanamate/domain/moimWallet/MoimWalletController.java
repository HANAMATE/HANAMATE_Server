package team.hanaro.hanamate.domain.moimWallet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.domain.moimWallet.dto.MoimWalletRequestDto;
import team.hanaro.hanamate.global.Response;

import java.io.IOException;
import java.sql.SQLException;

@Tag(name = "모임 통장", description = "모임통장 컨트롤러")
@RequiredArgsConstructor
@RestController
public class MoimWalletController {

    private final MoimWalletService moimWalletService;
    private final Response response;

    @GetMapping("/healthy")
    public ResponseEntity<?> HealthyCheck() {
        return response.success("moimWallet is healthy");
    }
    @Operation(summary = "내 모임통장 만들기", description = "내 모임통장 만들기", tags = {"모임통장"})
    @PostMapping("/moim/create")
    public ResponseEntity<?> createMoimWallet(@Validated @RequestBody MoimWalletRequestDto.JoinMoimWalletDTO moimWallet, Errors errors){
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.createMoimWallet(moimWallet);
    }

    @GetMapping("/moim/{id}")
    @Operation(summary = "모임통장 내용 가져오기", description = "지정한 모임통장의 세부내역(거래내역, 글, 댓글) 등을 모두 가져옵니다.)", tags = {"모임통장"})
    public ResponseEntity<?> getMoimWallet( @Validated @PathVariable("id")Long id) {
        return moimWalletService.getMoimWallet(id);
    }


    @Operation(summary = "내 모임통장 전부 가져오기", description = "내 모임통장 전부 가져오기", tags = {"모임통장"})
    @PostMapping("/moims")
    public ResponseEntity<?> myMoimWallet(@Validated @RequestBody MoimWalletRequestDto.findAllMoimWalletDTO moimWallet, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.getMoimWalletListByLoginId(moimWallet);
    }
    @DeleteMapping("/moim")
    public ResponseEntity<?> deleteMoimWallet(@Validated @RequestBody MoimWalletRequestDto.DeleteRequestDTO deleteRequestDTO, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.deleteMoimWallet(deleteRequestDTO);
    }

    @PostMapping("/moim/article")
    public ResponseEntity<?> writeArticle(@Validated @RequestBody MoimWalletRequestDto.WriteArticleRequestDTO articleDTO, Errors errors){
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        try {//이미지 안들어왔으면 그냥 초기화해서 넣어둠
            if (articleDTO.getImage() == null) {
                articleDTO.setImage(new byte[]{});
            }
            return moimWalletService.writeArticle(articleDTO);
        } catch (SQLException e) {
            return response.fail(articleDTO, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return response.fail(articleDTO, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/moim/article")
    public ResponseEntity<?> deleteArticle(@Validated @RequestBody MoimWalletRequestDto.DeleteRequestDTO deleteRequestDTO, Errors errors) {

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return moimWalletService.deleteArticle(deleteRequestDTO);
    }


}
