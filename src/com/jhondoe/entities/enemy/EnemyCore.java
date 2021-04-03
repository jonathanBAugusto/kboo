package com.jhondoe.entities.enemy;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.jhondoe.entities.Entity;
import com.jhondoe.enums.Dir;
import com.jhondoe.main.game.Game;

public class EnemyCore extends Entity {
    protected static double speed = 1;

    public static final int maskX = 3, maskY = 6, maskW = 10, maskH = 10;

    protected int frames = 0, maxFrames = 20, hitFrame = 0, damageFrame = 0, damageMaxFrame = 5, maxHitDamage = 50;

    protected double life = 30;
    protected boolean moved = false, damaged = false;

    protected Dir last_dir = Dir.DOWN;

    protected Map<Integer, Double> speedMap = new HashMap<Integer, Double>();
    protected Map<Integer, Double> lifeMap = new HashMap<Integer, Double>();
    protected Map<Integer, Integer> damageMaxMap = new HashMap<Integer, Integer>();

    public static final int MAXHITFRAMES = 40;

    public EnemyCore(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        initMaps();
        setLevelVars();
    }

    private void initMaps() {
        speedMap.put(0, 1.0);
        speedMap.put(1, 1.1);
        speedMap.put(2, 1.2);
        lifeMap.put(0, 30.0);
        lifeMap.put(1, 40.0);
        lifeMap.put(2, 50.0);
        damageMaxMap.put(0, 5);
        damageMaxMap.put(1, 7);
        damageMaxMap.put(2, 9);

    }

    private void setLevelVars() {
        speed = speedMap.get(Game.CURRENT_LEVEL);
        life = lifeMap.get(Game.CURRENT_LEVEL);
        damageMaxFrame = damageMaxMap.get(Game.CURRENT_LEVEL);
    }

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
