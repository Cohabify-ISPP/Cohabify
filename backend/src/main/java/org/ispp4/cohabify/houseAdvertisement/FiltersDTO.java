package org.ispp4.cohabify.houseAdvertisement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FiltersDTO {

    private Integer price = 0;
    private Integer meters = 0;
    private Boolean empty = false;
    private Integer tenants = 0;
    private Integer minBathrooms = null;
    private Integer maxBathrooms = null;
    private Integer minBedrooms = null;
    private Integer maxBedrooms = null;


}