package com.thentrees.lab_week5_www.backend.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String method;
    private String path;
    private String error;
    private String message;
}
