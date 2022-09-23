package com.mortalenigma.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedia extends Media {
    private boolean liked;
    private boolean commented;
    private boolean reported;
}
