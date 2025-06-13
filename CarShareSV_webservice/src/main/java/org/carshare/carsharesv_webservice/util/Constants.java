package org.carshare.carsharesv_webservice.util;

public final class Constants {
    private Constants() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static final String API = "/api";

    public static final String AUTH_CONTROLLER = "/auth";

    //Common methods
    public static final String CREATE = "/create";
    public static final String GET_ALL = "/getAll";
    public static final String GET_BY_ID = "/getById";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";

    //Other methods
    public static final String REGISTER = "/register";

    //Roles
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String SYSADMIN = "ROLE_SYSADMIN";
}
