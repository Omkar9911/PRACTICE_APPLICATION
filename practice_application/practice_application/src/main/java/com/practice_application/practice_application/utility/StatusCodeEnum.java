package com.practice_application.practice_application.utility;

import java.util.Arrays;

public enum StatusCodeEnum {


    SUCCESS_CODE(200, "Request Successful"),
    SUCCESS_LOGIN(200, "WELCOME!  "),
    SUCCESS_USER_CODE(200, "User has been created successfully"),
    PARAMS_MISSING_CODE(400, "Request parameters are missing/in-valid"),
    ILLEGAL_STATUS_CODE(-1, "Illegal status code"),
    EXCEPTION_CODE(500, "exception occured."),
    UNAUTHORIZED_CODE(401, "Session expired."),
    EXISTING_EXCEPTION_CODE(406, "Email/Phone-Number is already existing."),
    NOT_EXISTING_EXCEPTION_CODE(404, "Unregistered email id!."),
    UNPAID(201, "Your subscribtion plan is not activated!"),
    BAD_REQUEST(400, "Something went to wrong!"),
    VALIDATION_ERROR_CODE(422, "Validation error occurred!");

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private StatusCodeEnum(Integer code, String status) {
        this.code = code;
        this.message = status;
    }

    public static StatusCodeEnum findByCode(Integer code) {

        return Arrays.stream(StatusCodeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst()
                .orElse(StatusCodeEnum.ILLEGAL_STATUS_CODE);

    }

}
