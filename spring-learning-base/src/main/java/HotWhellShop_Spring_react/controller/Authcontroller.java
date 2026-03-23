package HotWhellShop_Spring_react.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import HotWhellShop_Spring_react.domain.User;
import HotWhellShop_Spring_react.domain.Login.LoginDTO;
import HotWhellShop_Spring_react.domain.Login.ResLoginDTO;
import HotWhellShop_Spring_react.service.UserService;
import HotWhellShop_Spring_react.util.SecurityUtil;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Authcontroller {
        private final SecurityUtil securityUtil;
        private final AuthenticationManagerBuilder authenticationManagerBuilder;
        private final UserService userService;
        @Value("${hieunumdum.jwt.refresh-token-validity-in-seconds}")
        private long jwtRefreshExpiration;

        public Authcontroller(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
                        UserService userService) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.userService = userService;
                this.securityUtil = securityUtil;
        }

        @PostMapping("/login")
        public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
                // lay thong tin nguoi dung
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.getUsername(), loginDTO.getPassword());

                // xác thực người dùng => cần viết hàm loadUserByUsername
                Authentication authentication = authenticationManagerBuilder.getObject()
                                .authenticate(authenticationToken);

                // create token

                ResLoginDTO res = new ResLoginDTO();
                User currentUserDB = this.userService.handleGetUserByUsername(loginDTO.getUsername());
                ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                                currentUserDB.getId(),
                                currentUserDB.getEmail(),
                                currentUserDB.getName());
                res.setUserLogin(userLogin);
                String accessToken = this.securityUtil.CreateToken(authentication, res.getUserLogin());
                res.setAccessToken(accessToken);

                // create refresh token
                String refreshToken = this.securityUtil.CreateRefreshToken(loginDTO.getUsername(), res);

                // update user
                this.userService.updateUserToken(refreshToken, loginDTO.getUsername());

                // set cookie
                ResponseCookie resCookie = ResponseCookie.from("refreshToken", refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(jwtRefreshExpiration)
                                .build();

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                                .body(res);
        }

        @GetMapping("/account")
        public ResponseEntity<ResLoginDTO.UserLogin> getAccount() {
                String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get()
                                : "";
                User currentUserDB = this.userService.handleGetUserByUsername(email);
                ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
                if (currentUserDB != null) {
                        userLogin.setId(currentUserDB.getId());
                        userLogin.setEmail(currentUserDB.getEmail());
                        userLogin.setUsername(currentUserDB.getName());
                }
                return ResponseEntity.ok().body(userLogin);
        }

        @GetMapping("/refresh")
        public ResponseEntity<String> getRefreshToken(
                        @CookieValue(name = "refreshToken") String refreshToken) {
                Jwt decodedToken = this.securityUtil.checkvalidRefreshToken(refreshToken);
                String email = decodedToken.getSubject();
                return ResponseEntity.ok().body(email);
        }

}
