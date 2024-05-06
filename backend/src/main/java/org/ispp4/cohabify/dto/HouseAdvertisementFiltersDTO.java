package org.ispp4.cohabify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HouseAdvertisementFiltersDTO {

    private Integer price = 0;
    private Integer meters = 0;
    private Boolean empty = false;
    private Integer tenants = 0;
    private Integer minBathrooms = null;
    private Integer minBedrooms = null;

}