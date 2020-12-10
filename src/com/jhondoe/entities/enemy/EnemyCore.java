package com.jhondoe.entities.enemy;

import java.awt.image.BufferedImage;

import com.jhondoe.entities.Entity;
import com.jhondoe.enums.Dir;

public class EnemyCore extends Entity {
    public EnemyCore(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    protected static double speed = 1;

    protected static final int maskX = 3, maskY = 6, maskW = 10, maskH = 10;

    protected int frames = 0, maxFrames = 20, hitFrame = 0, damageFrame = 0, damageMaxFrame = 5;

    protected double life = 30;
    protected boolean moved = false, damaged = false;

    protected Dir last_dir = Dir.DOWN;

    public static final int MAXHITFRAMES = 40;

    public void subLife(double value) {
        if (life >= value) {
            life -= value;
        } else {
            life = 0;
        }
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

}
