package com.iyashasgowda.yservice.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    private HttpStatus status_code;
    private String message;
    private Object data;
}
