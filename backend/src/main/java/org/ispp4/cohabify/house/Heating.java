package org.ispp4.cohabify.house;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Heating {
    NATURAL_GAS("Gas natural"), 
    RADIATOR("Radiador"), 
    CENTRAL_HEATING("Calefacción central"), 
    AIR_CONDITIONING("Aire acondicionado");

    private String name;

    Heating(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() { return name; }

    @Override 
    public String toString() { return name; }
}