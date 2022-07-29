package base.project.restapi.controller;

import base.project.restapi.base.controller.BaseController;
import base.project.restapi.classes.PaginationResult;
import base.project.restapi.classes.SuccessPaginateResponse;
import base.project.restapi.classes.SuccessSingleResponse;
import base.project.restapi.dto.UserDTO;
import base.project.restapi.dto.UserUpdateDTO;
import base.project.restapi.exception.UserNotFoundException;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.manager.user.UserCreateManager;
import base.project.restapi.manager.user.UserUpdateManager;
import base.project.restapi.model.UserModel;
import base.project.restapi.service.UserService;
import base.project.restapi.transformers.UserTransformer;
import base.project.restapi.transformers.model.UserTransformerModel;
import base.project.restapi.util.ApiUrl;
import base.project.restapi.util.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.USER_PREFIX)
public class UserController extends BaseController {
    UserService userService;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(
        UserService userService,
        ModelMapper modelMapper,
        PasswordEncoder passwordEncoder,
        MessageHelper messageSource
    ) {
        super(UserController.class, messageSource, modelMapper);

        this.userService = userService;

        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<SuccessPaginateResponse> index(
        Pageable pageable
    ) {
        PaginationResult<UserModel> paginationResult = this.userService.paginate(pageable);

        return new SuccessPaginateResponse(
            this.messageHelper.responseMessage(ResponseMessage.USER_PAGINATE),
            paginationResult.getTotal(),
            pageable.getPageSize(),
            pageable.getPageNumber(),
            paginationResult.getData()
        ).response(HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<SuccessSingleResponse> store(
        @RequestBody UserDTO userDTO
    ) {
        UserModel userModel = modelMapper.map(userDTO, UserModel.class);

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        UserModel savedUser = new UserCreateManager(
            userModel,
            this.userService,
            this.messageHelper
        ).apply();

        return new SuccessSingleResponse(
            this.messageHelper.responseMessage(ResponseMessage.USER_SAVE),
            UserTransformer.transformerModel(savedUser)
        ).response(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<SuccessSingleResponse> update(
        @PathVariable("id") int id,
        @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        UserModel userModel = userService.findById(id).orElseThrow(
            () -> new UserNotFoundException(
                this.messageHelper.responseMessage(ResponseMessage.USER_NOT_FOUND)
            )
        );

        UserModel updateResult = new UserUpdateManager(
            userModel,
            userUpdateDTO,
            this.userService,
            this.messageHelper
        ).apply();

        return new SuccessSingleResponse(
            this.messageHelper.responseMessage(ResponseMessage.USER_SAVE),
            UserTransformer.transformerModel(updateResult)
        ).response(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<SuccessSingleResponse> delete(
        @PathVariable("id") int id
    ) {
        userService.findById(id).orElseThrow(
            () -> new UserNotFoundException(
                this.messageHelper.responseMessage(ResponseMessage.USER_NOT_FOUND)
            )
        );

        userService.deleteById(id);

        return new SuccessSingleResponse(
            this.messageHelper.responseMessage(ResponseMessage.USER_DELETE),
            null
        ).response(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<SuccessSingleResponse> show(
        @PathVariable("id") int id
    ) {
        UserModel userModel = userService.findById(id).orElseThrow(
            () -> new UserNotFoundException(
                this.messageHelper.responseMessage(ResponseMessage.USER_NOT_FOUND)
            )
        );

        return new SuccessSingleResponse(
            this.messageHelper.responseMessage(ResponseMessage.USER_SAVE),
            UserTransformer.transformerModel(userModel)
        ).response(HttpStatus.OK);
    }
}
