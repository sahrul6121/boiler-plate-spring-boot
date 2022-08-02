package base.project.restapi.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import base.project.restapi.auth.JwtProvider;
import base.project.restapi.base.controller.BaseController;
import base.project.restapi.classes.ErrorResponse;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.service.UserService;
import base.project.restapi.util.ApiUrl;
import base.project.restapi.util.ResponseMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping()
public class GateWayController extends BaseController {
    JwtProvider tokenProvider;

    UserService userService;

    @Autowired
    public GateWayController(
        AuthenticationManager authenticationManager,
        JwtProvider tokenProvider,
        UserService userService,
        MessageHelper messageHelper,
        ModelMapper modelMapper
    ) {
        super(GateWayController.class, messageHelper, modelMapper);

        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping(ApiUrl.VALIDATE_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<?> validate(
        HttpServletRequest request
    ) {
        String path = request.getHeader("X-Forwarded-Path");

        if (!path.contains("auth")) {
            String headerAuth = request.getHeader("Authorization");

            HttpStatus status = HttpStatus.UNAUTHORIZED;

            if (headerAuth == null) {
                return ResponseEntity
                    .status(status)
                    .body(
                        new ErrorResponse(
                            this.messageHelper.responseMessage(ResponseMessage.AUTH_ERROR_UNAUTHORIZED)
                        ).responseError(HttpStatus.UNAUTHORIZED)
                    );
            }

            String token = headerAuth.substring(7, headerAuth.length());

            Claims claims =  Jwts.parser()
                .setSigningKey(
                    this.tokenProvider.getJwtSecret()
                )
                .parseClaimsJws(token)
                .getBody();

            JSONObject result = new JSONObject(this.userService.loadUserByUsername(claims.getSubject()));

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("auth-user-data", result.toString());

            return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(result);
        }

        return null;
    }
}
