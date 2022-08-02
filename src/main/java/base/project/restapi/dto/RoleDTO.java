package base.project.restapi.dto;

import base.project.restapi.enums.RoleName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDTO {
    private int id;

    private RoleName name;
}
