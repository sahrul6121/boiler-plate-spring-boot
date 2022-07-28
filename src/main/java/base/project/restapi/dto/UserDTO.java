package base.project.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO implements Serializable {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotBlank
    private Date date_birth;

    @PositiveOrZero
    private float balance = 0;

    @PositiveOrZero
    private float point = 0;

    private RoleDTO role;
}
