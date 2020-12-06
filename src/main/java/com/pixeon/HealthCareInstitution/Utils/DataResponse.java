package com.pixeon.HealthCareInstitution.Utils;

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
