package com.pixeon.PixeonChallenge.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthCareInstitutionDTO {

    private Long id;
    private String name;
    private String cnpj;
    private Integer pixeonCoins;

}
