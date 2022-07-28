package base.project.restapi.transformers.model;

import base.project.restapi.model.RoleModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserTransformerModel {
    private int id;

    private String username;

    private String first_name;

    private String last_name;

    private Date date_birth;

    private float balance;

    private float point;

    private RoleModel role;
}
