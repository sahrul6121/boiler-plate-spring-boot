package base.project.restapi.service.model;

import base.project.restapi.base.service.IBaseService;
import base.project.restapi.enums.RoleName;
import base.project.restapi.model.RoleModel;

import java.util.List;
import java.util.Optional;

public interface IRoleService extends IBaseService<RoleModel> {
    List<RoleModel> findMany();

    Optional<RoleModel> findById(int id);

    RoleModel save(RoleModel payload);

    void deleteById(int id);

     RoleModel findByName(RoleName name);
}
