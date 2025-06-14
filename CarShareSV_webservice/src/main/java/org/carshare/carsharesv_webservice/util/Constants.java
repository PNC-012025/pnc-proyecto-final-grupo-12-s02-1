package org.carshare.carsharesv_webservice.util;

public final class Constants {
    private Constants() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static final String API = "/api";

    public static final String AUTH_CONTROLLER = "/auth";
    public static final String USER_CONTROLLER = "/user";

    //Common methods
    public static final String CREATE = "/create";
    public static final String GET_ALL = "/getAll";
    public static final String GET_BY_ID = "/getById";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";

    //Other methods
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String GET_ALL_ACTIVE = "/getAllActive";
    public static final String GET_ALL_NOT_ACTIVE = "/getAllNotActive";
    public static final String GET_BY_USERNAME_OR_EMAIL = "/getByUsernameOrEmail";
    public static final String GET_USER_ROLES = "/getUserRoles";
    public static final String ACTIVATE = "/activate";
    public static final String DEACTIVATE = "/deactivate";



    //Roles
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String SYSADMIN = "ROLE_SYSADMIN";
}
