package com.ticketmaster.searchservice.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiError {

    public final static int BAD_REQ_ERR_CODE = 400;
    public final static int VALIDATION_ERR_CODE = 400;
    public final static int NOT_FOUND_ERR_CODE = 404;
    public final static int UNAUTHORIZED_ERR_CODE = 403;
    public final static int FORBIDDEN_ERR_CODE = 401;
    public final static int NONE_ERR_CODE = 0;

    private Integer code;
    private String msg;

    public static ApiError BadRequest(String msg) {
        return new ApiError(BAD_REQ_ERR_CODE, msg);
    }

    public static ApiError Validation(String msg) {
        return new ApiError(VALIDATION_ERR_CODE, msg);
    }

    public static ApiError NotFound(String msg) {
        return new ApiError(NOT_FOUND_ERR_CODE, msg);
    }

    public static ApiError UnAuthorized(String msg) {
        return new ApiError(UNAUTHORIZED_ERR_CODE, msg);
    }

    public static ApiError Forbidden(String msg) {
        return new ApiError(FORBIDDEN_ERR_CODE, msg);
    }

    public static ApiError None(String msg) {
        return new ApiError(NONE_ERR_CODE, msg);
    }
}
