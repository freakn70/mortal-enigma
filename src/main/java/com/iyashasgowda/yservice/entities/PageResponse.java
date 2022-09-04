package com.iyashasgowda.yservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {
    private int current_page;
    private int per_page;
    private int total_pages;
    private Object data;
}
