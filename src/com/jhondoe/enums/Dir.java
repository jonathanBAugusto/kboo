package com.jhondoe.enums;

public enum Dir {
    LEFT(1), RIGHT(2), UP(3), DOWN(4);

    private final int value;

    Dir(int valueOpt) {
        value = valueOpt;
    }

    public int getValue() {
        return value;
    }
}
