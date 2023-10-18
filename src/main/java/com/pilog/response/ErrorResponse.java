package com.pilog.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code": "HTTP Status"
 *     "message": "status 에 따른 에러 메시지"
 *     "validation":{
 *         "field": "필드값에 따른 에러 이유"
 *     }
 * }
 */

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String field, String defaultMessage) {
        this.validation.put(field, defaultMessage);
    }
}
