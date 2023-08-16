package team.hanaro.hanamate.domain.User.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import team.hanaro.hanamate.domain.MyWallet.WalletService;
import team.hanaro.hanamate.domain.User.Authority;
import team.hanaro.hanamate.domain.User.Dto.UserRequestDto;
import team.hanaro.hanamate.domain.User.Dto.UserResponse;
import team.hanaro.hanamate.domain.User.Dto.UserResponseDto;
import team.hanaro.hanamate.domain.User.Repository.UsersRepository;
import team.hanaro.hanamate.entities.User;
import team.hanaro.hanamate.global.Response;
import team.hanaro.hanamate.jwt.JwtTokenProvider;
import team.hanaro.hanamate.security.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;
    private final UserResponse userResponse;
    private final WalletService walletService;

    public ResponseEntity<?> signUp(UserRequestDto.SignUp signUp) {
        if (usersRepository.existsByLoginId(signUp.getId())) {
            return response.fail("이미 회원가입된 아이디입니다.", HttpStatus.BAD_REQUEST);
        }

        //DTO(Signup)을 이용하여 User(Entity)로 반환하는 Builder (DTO-> Entity)
        User user = User.builder()
                .name(signUp.getName())
                .loginId(signUp.getId())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .identification(signUp.getIdentification())
                .phoneNumber(signUp.getPhoneNumber())
                .userType(signUp.getUserType())
                .roles(Collections.singletonList(Authority.ROLE_USER.name())) //SpringSecurity 관련
                .build();

        walletService.makeMyWallet(user);
        usersRepository.save(user); //repository의 save 메서드 호출 (조건. entity객체를 넘겨줘야 함)
        return response.success("회원가입에 성공했습니다.");
    }

    public ResponseEntity<?> login(UserRequestDto.Login login) {

        //로그인 실패
        if (usersRepository.findByLoginId(login.getId()).orElse(null) == null) {
            //(ResponseEntity<?>)StatusCode, ResponseMessage, ResponseData를 담아서 클라이언트에게 응답을 보냄
            return response.fail("해당하는 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        //로그인 성공
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        String refreshToken = tokenInfo.getRefreshToken();
        String accessToken = tokenInfo.getAccessToken();


        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);


        // 헤더를 포함하여 ResponseEntity 생성
        // userResponse.success()를 호출하여 ResponseEntity 생성
        ResponseEntity<?> responseEntity = userResponse.success(accessToken, refreshToken, null, "로그인에 성공했습니다", HttpStatus.OK);

        return responseEntity;


    }

    //토큰 재발급
    public ResponseEntity<?> reissue(HttpServletRequest request) {
        String get_refreshToken = request.getHeader("RefreshToken");

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(get_refreshToken)) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User Id를 가져옵니다.
        String get_accessToken = request.getHeader("AccessToken");
        Authentication authentication = jwtTokenProvider.getAuthentication(get_accessToken);

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String redisRefreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(redisRefreshToken)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        if (!redisRefreshToken.equals(get_refreshToken)) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDto.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        String refreshToken = tokenInfo.getRefreshToken();
        String accessToken = tokenInfo.getAccessToken();

        ResponseEntity<?> responseEntity = userResponse.success(accessToken, refreshToken, null, "Token 정보가 갱신되었습니다.", HttpStatus.OK);

        return responseEntity;

    }


    public ResponseEntity<?> logout(UserRequestDto.Logout logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다.");
    }

    public ResponseEntity<?> authority() {
        // SecurityContext에 담겨 있는 authentication userEamil 정보
        String userId = SecurityUtil.getCurrentUserEmail();

        User user = usersRepository.findByLoginId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        // add ROLE_ADMIN
        user.getRoles().add(Authority.ROLE_ADMIN.name());
        usersRepository.save(user);

        return response.success();
    }
}
