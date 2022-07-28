package base.project.restapi.service.model;

import base.project.restapi.base.service.IBaseService;
import base.project.restapi.model.UserModel;
import base.project.restapi.transformers.model.UserPrincipalTransformModel;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IBaseService<UserModel> {
    List<UserModel> findMany();

    Optional<UserModel> findById(int id);

    UserModel save(UserModel payload);

    void deleteById(int id);

    UserModel findOneByUsername(String username, int id);

    UserPrincipalTransformModel loadUserByUsername(String username);

    UserModel findOneByUsername(String value);
}
