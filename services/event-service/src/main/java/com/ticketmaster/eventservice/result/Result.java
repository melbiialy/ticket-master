package com.ticketmaster.eventservice.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class Result<T> {

    T          data;
    ApiError   error;
    boolean    success;

    private Result(T data, ApiError err, boolean success) {
        this.data = data;
        this.error = err;
        this.success = success;
    }

    public static <T> Result<T> Success(T data) {
        return new Result<T>(data, ApiError.None(""), true);
    }

    public static Result<Void> Failure(ApiError error) {
        return new Result<Void>(null, error, false);
    }
}
