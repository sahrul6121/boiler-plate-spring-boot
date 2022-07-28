package base.project.restapi.util;

/**
 * Class for register response message const
 */
public abstract class ResponseMessage {
    public static final String USER_ERROR = "user.error.";
    public static final String USER_NOT_FOUND = USER_ERROR + "notFound";
    public static final String USER_USERNAME_EXIST = USER_ERROR + "usernameExist";

    public static final String USER_SUCCESS = "user.success.";
    public static final String USER_SAVE = USER_SUCCESS + "save";
    public static final String USER_UPDATE = USER_SUCCESS + "update";
    public static final String USER_DELETE = USER_SUCCESS + "delete";

    public static final String AUTH_ERROR = "auth.error.";
    public static final String AUTH_ERROR_UNAUTHORIZED = AUTH_ERROR + "unauthorized";
}
