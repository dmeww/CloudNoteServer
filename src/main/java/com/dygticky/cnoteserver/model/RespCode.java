package com.dygticky.cnoteserver.model;

public enum RespCode {
    SUCCESS(200),
    FAIL_CLIENT(401),
    FAIL_SERVER(501);

    private final int value;

    RespCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

