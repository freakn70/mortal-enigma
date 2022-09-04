package com.iyashasgowda.yservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {
    private int page;
    private int size;
    private Object data;
}
