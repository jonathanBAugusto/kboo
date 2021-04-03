package com.jhondoe.enums;

public enum TileItens {
    GRASS(0xFF000000), GRASS_ROCK(0xFF00FF00), WALL(0xFFFFFFFF), ENEMY(0xFFFF0000), LIFE(0xFF4B692F),
    LIFEUP(0xFFCBDBFC), STAMINA(0xFFFBF236), PLAYER(0xFF0000FF), POWERAMOO(0xFFFF00FF);

    private final int value;

    private TileItens(int valueOpt) {
        value = valueOpt;
    }

    public int getValue() {
        return value;
    }
}
