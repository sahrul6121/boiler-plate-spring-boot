package base.project.restapi.controller;

import base.project.restapi.base.controller.BaseController;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public Page<UserTransformerModel> index(
        Pageable pageable
    ) {
        return new PageImpl<>(
            this.userService.paginate(pageable)
                .stream()
                .map(UserTransformer::transformerModel)
                .toList(),
            pageable,
            0
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserTransformerModel store(
        @RequestBody UserDTO userDTO
    ) {
        UserModel userModel = modelMapper.map(userDTO, UserModel.class);

        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        UserModel savedUser = new UserCreateManager(
            userModel,
            this.userService,
            this.messageHelper
        ).apply();

        return UserTransformer.transformerModel(savedUser);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserTransformerModel update(
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

        return UserTransformer.transformerModel(updateResult);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String delete(
        @PathVariable("id") int id
    ) {
        userService.findById(id).orElseThrow(
            () -> new UserNotFoundException(
                this.messageHelper.responseMessage(ResponseMessage.USER_NOT_FOUND)
            )
        );

        userService.deleteById(id);

        return this.messageHelper.responseMessage(ResponseMessage.USER_DELETE);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserTransformerModel show(
        @PathVariable("id") int id
    ) {
        UserModel userModel = userService.findById(id).orElseThrow(
            () -> new UserNotFoundException(
                this.messageHelper.responseMessage(ResponseMessage.USER_NOT_FOUND)
            )
        );

        return UserTransformer.transformerModel(userModel);
    }
}
