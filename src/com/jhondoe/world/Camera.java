package com.jhondoe.world;

public class Camera {
    private static int x;
    private static int y;

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        Camera.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        Camera.y = y;
    }

    public static int clamp(int pos, int min, int max) {
        if (pos < min) {
            pos = min;
        }

        if (pos > max) {
            pos = max;
        }

        return pos;
    }
}
