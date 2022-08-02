package base.project.restapi.manager.user;

import base.project.restapi.base.manager.BaseManager;
import base.project.restapi.exception.CustomErrorException;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.model.UserModel;
import base.project.restapi.service.UserService;
import base.project.restapi.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import java.util.Optional;

public class UserCreateManager extends BaseManager<UserModel, UserService> {
    public UserCreateManager(
        UserModel userModel,
        UserService userService,
        MessageHelper messageHelper
    ) {
        super(userModel, userService, messageHelper);
    }

    public UserModel apply() {
        this.validation();

        return this.service.save(this.entity);
    }

    public void validation() throws CustomErrorException {
        Optional<UserModel> userModel = Optional.ofNullable(this.service.findOneByUsername(this.entity.getUsername()));

        if (userModel.isPresent()) {
            throw new CustomErrorException(
                HttpStatus.BAD_REQUEST,
                this.messageHelper.responseMessage(ResponseMessage.USER_USERNAME_EXIST)
            );
        }
    }
}
