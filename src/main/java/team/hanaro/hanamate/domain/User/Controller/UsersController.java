package team.hanaro.hanamate.domain.User.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.hanaro.hanamate.domain.User.Dto.UserRequestDto;
import team.hanaro.hanamate.domain.User.Helper;
import team.hanaro.hanamate.domain.User.Service.UsersService;
import team.hanaro.hanamate.global.Response;
import team.hanaro.hanamate.jwt.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "인증인가", description = "인증/인가와 관련된 기능")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final Response response;

    @Operation(summary = "회원가입", description = "회원가입", tags = {"인증인가"})
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserRequestDto.SignUp signUp, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.signUp(signUp);
    }

    @Operation(summary = "로그인", description = "로그인", tags = {"인증인가"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserRequestDto.Login login, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.login(login);
    }

    @Operation(summary = "Init", description = "FE에서 첫 진입 시 AT토큰 유효 여부를 확인한다.", tags = {"인증인가"})
    @PostMapping("/init")
    public ResponseEntity<?> init(@Validated UserRequestDto.Init init, Errors errors, HttpServletRequest request) {
        log.info("init 컨트롤러");

        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.init(request);
    }


    @Operation(summary = "AT 재발급", description = "RT로 AT를 재발급한다.", tags = {"인증인가"})
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated UserRequestDto.Reissue reissue, Errors errors, HttpServletRequest request) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.reissue(request);
    }

    @Operation(summary = "로그아웃", description = "로그아웃", tags = {"인증인가"})
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated UserRequestDto.Logout logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.logout(logout);
    }

}
