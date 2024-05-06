package org.ispp4.cohabify.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAdvertisementFiltersDTO {

    private Integer maxBudget = 0;
    private Integer maxCohabitants = 0;
    private LocalDate entranceDate = null;


}