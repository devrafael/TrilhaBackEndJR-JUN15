package com.cordigocerto.trilhabackend.entities;

public enum RoleEnum {
    ADMIN("admin"),
    USER("user");

    private String role;
    private RoleEnum(String role) {
        this.role = role;
    }
}
