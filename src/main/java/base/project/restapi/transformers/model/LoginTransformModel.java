package base.project.restapi.transformers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTransformModel {
    private UserPrincipalTransformModel userPrincipal;

    private String token;
}
