package com.alpine.cnema.dto.response.format;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasicFormat<T> {
    private T data;
    private HttpStatus status;
    private String message;
}