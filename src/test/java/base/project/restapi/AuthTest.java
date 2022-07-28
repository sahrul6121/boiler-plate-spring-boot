package base.project.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import base.project.restapi.dto.LoginDTO;
import base.project.restapi.repository.RoleRepository;
import base.project.restapi.repository.UserRepository;
import base.project.restapi.util.ApiUrl;
import base.project.restapi.util.AuthHelper;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        AuthHelper.generateUser(
            roleRepository,
            userRepository,
            passwordEncoder,
            null,
            true
        );
    }

    @Test
    public void registered_user_can_login() throws Exception {
        LoginDTO loginDTO = new LoginDTO();

        ObjectMapper mapper = new ObjectMapper();

        loginDTO.setUsername("super-admin");
        loginDTO.setPassword("password");

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(ApiUrl.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginDTO))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void registered_user_can_t_login_with_wrong_password() throws Exception {
        LoginDTO loginDTO = new LoginDTO();

        ObjectMapper mapper = new ObjectMapper();

        loginDTO.setUsername("super-admin");
        loginDTO.setPassword("test");

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(ApiUrl.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginDTO))
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void registered_user_can_t_login_with_wrong_username() throws Exception {
        LoginDTO loginDTO = new LoginDTO();

        ObjectMapper mapper = new ObjectMapper();

        loginDTO.setUsername("test");
        loginDTO.setPassword("password");

        mockMvc.perform(
            MockMvcRequestBuilders
                .post(ApiUrl.LOGIN_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginDTO))
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void login_user_will_get_token() throws Exception {
        Assertions.assertNotNull(AuthHelper.getToken(mockMvc));
    }
}
