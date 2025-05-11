package com.soa.team_service.util;

public enum Role {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
