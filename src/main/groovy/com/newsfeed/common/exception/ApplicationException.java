package com.newsfeed.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        super(message);         // 부모의 생성자
        this.status = status;
    }
}
