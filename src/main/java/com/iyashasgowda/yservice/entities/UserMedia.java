package com.iyashasgowda.yservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedia extends Media {
    private boolean isFavourite;
    private boolean isCommented;
    private boolean isReported;
}
