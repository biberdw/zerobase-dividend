package com.zerobase.dividend.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private final int code;
    private final String message;
}
