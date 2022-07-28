package base.project.restapi.transformers;

import base.project.restapi.model.UserModel;
import base.project.restapi.transformers.model.LoginTransformModel;
import base.project.restapi.transformers.model.UserPrincipalTransformModel;
import base.project.restapi.transformers.model.UserTransformerModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserTransformer {
    public static UserPrincipalTransformModel transformToPrincipal(UserModel user) {
        UserPrincipalTransformModel transform = new UserPrincipalTransformModel();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));

        transform.setId(user.getId());

        transform.setUsername(user.getUsername());

        transform.setPassword(user.getPassword());

        transform.setEnabled(user.isEnabled());

        transform.setAuthorities(authorities);

        return transform;
    }

    public static LoginTransformModel transformLogin(
        UserPrincipalTransformModel userPrincipalTransformModel,
        String token
    ) {
        LoginTransformModel loginTransformModel = new LoginTransformModel();

        loginTransformModel.setUserPrincipal(userPrincipalTransformModel);

        loginTransformModel.setToken(token);

        return loginTransformModel;
    }

    public static UserTransformerModel transformerModel(UserModel userModel) {
        UserTransformerModel userTransformerModel = new UserTransformerModel();

        userTransformerModel.setId(userModel.getId());
        userTransformerModel.setUsername(userModel.getUsername());
        userTransformerModel.setFirst_name(userModel.getFirst_name());
        userTransformerModel.setLast_name(userModel.getLast_name());
        userTransformerModel.setDate_birth(userModel.getDate_birth());
        userTransformerModel.setRole(userModel.getRole());
        userTransformerModel.setPoint(userModel.getPoint());
        userTransformerModel.setBalance(userModel.getBalance());

        return userTransformerModel;
    }
}
