package base.project.restapi.util;

/**
 * Class for register api const
 */
public abstract class ApiUrl {
    public static final String TOKEN_TYPE = "Bearer";

    public static final String AUTH_PREFIX = "/api/v1/auth";
    public static final String LOGIN_PATH = AUTH_PREFIX + "/login";
    public static final String VALIDATE_PATH = AUTH_PREFIX + "/validate";

    public static final String USER_PREFIX = "/api/v1/user";
}
