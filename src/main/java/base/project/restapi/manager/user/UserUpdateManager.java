package base.project.restapi.manager.user;

import base.project.restapi.base.manager.BaseManager;
import base.project.restapi.dto.RoleDTO;
import base.project.restapi.dto.UserUpdateDTO;
import base.project.restapi.exception.CustomErrorException;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.model.RoleModel;
import base.project.restapi.model.UserModel;
import base.project.restapi.service.UserService;
import base.project.restapi.util.ResponseMessage;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class UserUpdateManager extends BaseManager<UserModel, UserService> {
    UserUpdateDTO userUpdateDTO;

    public UserUpdateManager(
        UserModel userModel,
        UserUpdateDTO userUpdateDTO,
        UserService userService,
        MessageHelper messageHelper
    ) {
        super(userModel, userService, messageHelper);

        this.userUpdateDTO = userUpdateDTO;
    }

    public UserModel apply() {
        this.validation();

        return this.service.save(this.entity);
    }

    public UserModel format() {
        this.entity.setFirst_name(this.userUpdateDTO.getFirst_name());
        this.entity.setLast_name(this.userUpdateDTO.getLast_name());
        this.entity.setRole(formatRole(this.userUpdateDTO.getRole()));
        this.entity.setDate_birth(this.userUpdateDTO.getDate_birth());
        this.entity.setPoint(this.userUpdateDTO.getPoint());
        this.entity.setBalance(this.userUpdateDTO.getBalance());

        return this.entity;
    }

    public void validation() throws CustomErrorException {
        Optional<UserModel> userModel = Optional.ofNullable(
            this.service.findOneByUsername(this.entity.getUsername(), this.entity.getId())
        );

        if (userModel.isPresent()) {
            throw new CustomErrorException(
                HttpStatus.BAD_REQUEST,
                this.messageHelper.responseMessage(ResponseMessage.USER_USERNAME_EXIST)
            );
        }
    }

    public RoleModel formatRole(RoleDTO roleDTO) {
        RoleModel roleModel = new RoleModel();

        roleModel.setId(roleDTO.getId());
        roleModel.setName(roleDTO.getName());

        return roleModel;
    }
}
