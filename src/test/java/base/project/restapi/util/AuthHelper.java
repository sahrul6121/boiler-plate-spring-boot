package base.project.restapi.util;

import base.project.restapi.enums.RoleName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import base.project.restapi.dto.LoginDTO;
import base.project.restapi.model.RoleModel;
import base.project.restapi.model.UserModel;
import base.project.restapi.repository.RoleRepository;
import base.project.restapi.repository.UserRepository;

public class AuthHelper {
    public static String getToken(MockMvc mockMvc) throws Exception {
        LoginDTO loginDTO = new LoginDTO();

        ObjectMapper mapper = new ObjectMapper();

        loginDTO.setUsername("super-admin");
        loginDTO.setPassword("password");

        MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders
                .post(ApiUrl.LOGIN_PATH, loginDTO)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginDTO))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());

        return ApiUrl.TOKEN_TYPE + " " + jsonObject.getString("token");
    }

    public static void generateUser(
        RoleRepository roleRepository,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        String customUsername,
        Boolean deleteData
    ) {
        if (deleteData) {
            userRepository.deleteAll();

            roleRepository.deleteAll();
        }

        RoleModel roleModel = new RoleModel();

        roleModel.setName(RoleName.ADMIN);

        RoleModel savedRole = roleRepository.save(roleModel);

        UserModel userModel = new UserModel();

        userModel.setUsername(customUsername != null ? customUsername : "super-admin");
        userModel.setPassword(passwordEncoder.encode("password"));
        userModel.setFirst_name("super");
        userModel.setLast_name("admin");
        userModel.setDate_birth(null);
        userModel.setPoint(0);
        userModel.setBalance(0);
        userModel.setEnabled(true);
        userModel.setRole(savedRole);

        userRepository.save(userModel);
    }
}
