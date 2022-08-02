package base.project.restapi.repository;

import base.project.restapi.enums.RoleName;
import base.project.restapi.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
    RoleModel findByName(RoleName name);
}
