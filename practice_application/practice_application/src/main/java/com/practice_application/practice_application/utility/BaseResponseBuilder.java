package com.practice_application.practice_application.utility;

import java.io.Serial;
import java.io.Serializable;

public class BaseResponseBuilder extends BaseResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Method to set response with status and code.
     *
     * @param status
     * @param code
     *
     * @return
     */
    public BaseResponse setBaseResponseWithStatusAndCode(String status, Integer code) {
        return new BaseResponse(status, code);
    }

    /**
     *
     * Method to set response with status, code and data.
     *
     * @param status
     * @param code
     * @param data
     *
     * @return
     */
    public BaseResponse setBaseResponseWithStatusAndCodeAndData(String status, Integer code, Object data) {
        return new BaseResponse(status, code).withData(data);

    }

    /**
     *
     * Method to set response with status, code and custom message.
     *
     * @param status
     * @param code
     * @param msg
     *
     * @return
     */
    public BaseResponse setBaseResponseWithStatusAndCodeAndCustomMessage(String status, Integer code, String msg) {
        return new BaseResponse(status, code).withCustomMessage(msg);
    }

    /**
     *
     * Method to set response with status, code, message and data.
     *
     * @param status
     * @param code
     * @param msg
     * @param data
     *
     * @return
     */
    public BaseResponse setBaseResponseWithStatusAndCodeAndCustomMessageAndData(String status, Integer code, String msg,
                                                                                Object data) {
        return new BaseResponse(status, code).withCustomMessage(msg).withData(data);

    }

}
