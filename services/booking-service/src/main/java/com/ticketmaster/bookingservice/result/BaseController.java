package com.ticketmaster.bookingservice.result;

import org.springframework.http.HttpStatus;

import static com.ticketmaster.bookingservice.result.ApiError.*;

public abstract class BaseController {

    protected HttpStatus resolveStatus(Result<?> result) {
        return switch (result.getError().getCode()) {
            case BAD_REQ_ERR_CODE -> HttpStatus.BAD_REQUEST;
            case FORBIDDEN_ERR_CODE -> HttpStatus.FORBIDDEN;
            case UNAUTHORIZED_ERR_CODE -> HttpStatus.UNAUTHORIZED;
            case NOT_FOUND_ERR_CODE -> HttpStatus.NOT_FOUND;
            case NONE_ERR_CODE -> HttpStatus.OK;
            default -> HttpStatus.OK;
        };
    }
}
