package org.carshare.carsharesv_webservice.util;

public final class Constants {
    private Constants() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static final String API = "/api";

    public static final String AUTH_CONTROLLER = "/auth";
    public static final String USER_CONTROLLER = "/user";
    public static final String CARS_CONTROLLER = "/cars";

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
    public static final String GET_ALL_CARS_BY_MODEL = "/getAllCarsByModel";
    public static final String GET_ALL_CARS_BY_BRAND = "/getAllCarsByBrand";
    public static final String GET_ALL_CARS_BY_YEAR = "/getAllCarsByYear";
    public static final String GET_CAR_BY_ID = "/getCarById";
    public static final String GET_CAR_BY_USER_ID = "/getCarByUserId";
    public static final String ACTIVATE = "/activate";
    public static final String DEACTIVATE = "/deactivate";
    public static final String UPDATE_FIRSTNAME = "/updateFirstName";
    public static final String UPDATE_LASTNAME = "/updateLastName";
    public static final String UPDATE_USERNAME = "/updateUsername";
    public static final String UPDATE_EMAIL = "/updateEmail";
    public static final String UPDATE_PHONENUMBER = "/updatePhoneNumber";
    public static final String UPDATE_DAILY_PRICE = "/updateDailyPrice";
    public static final String GRANT_ADMIN_ROLE = "/grantAdminRole";
    public static final String REVOKE_ADMIN_ROLE = "/revokeAdminRole";

    //Roles
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String SYSADMIN = "ROLE_SYSADMIN";
}
