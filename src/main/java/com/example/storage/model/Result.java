package com.example.storage.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class Result<T> {
    private int code;
    private T data;
    private String message;

    private final static int SUCCESS = 0;
    private final static int FAIL = -1;

    public static <T> Result<T> ok(T data) {
        return new Result<T>()
                .setCode(SUCCESS)
                .setData(data);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<T>()
                .setCode(FAIL)
                .setMessage(message);
    }
}
