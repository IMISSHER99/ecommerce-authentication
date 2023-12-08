package org.acme.dto;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionFormat {

    private String message;
    private Integer status;
    private LocalDateTime timestamp;
}
