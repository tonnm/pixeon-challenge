package com.pixeon.PixeonChallenge.Utils;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DataResponse<T> {
    private String message;
    private T data;
}
