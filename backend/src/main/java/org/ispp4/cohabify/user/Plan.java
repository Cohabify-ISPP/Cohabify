package org.ispp4.cohabify.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Plan {
    BASIC, EXPLORER, OWNER;

    @JsonCreator
    public static Plan fromString(String value) {
        return Plan.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}
