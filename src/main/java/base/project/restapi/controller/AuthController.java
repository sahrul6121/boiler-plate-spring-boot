package base.project.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import base.project.restapi.auth.JwtProvider;
import base.project.restapi.dto.LoginDTO;
import base.project.restapi.service.UserService;
import base.project.restapi.transformers.UserTransformer;
import base.project.restapi.transformers.model.UserPrincipalTransformModel;
import base.project.restapi.util.ApiUrl;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class AuthController {
    AuthenticationManager authenticationManager;
    JwtProvider tokenProvider;

    UserService userService;

    @Autowired
    public AuthController(
        AuthenticationManager authenticationManager,
        JwtProvider tokenProvider,
        UserService userService
    ) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(ApiUrl.LOGIN_PATH)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO login) {
        Authentication authentication = authenticationManager
            .authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(
            UserTransformer.transformLogin(
                (UserPrincipalTransformModel) authentication.getPrincipal(),
                jwt
            )
        );
    }
}
