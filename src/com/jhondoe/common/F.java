package com.jhondoe.common;

import java.awt.Rectangle;

import com.jhondoe.entities.Entity;

public class F {

    public static boolean isColliding(Entity e1, Entity e2) {
        Rectangle e1Mask = new Rectangle((int) e1.getX() + e1.getMaskX(), (int) e1.getY() + e1.getMaskY(),
                e1.getMaskW(), e1.getMaskH());
        Rectangle e2Mask = new Rectangle((int) e2.getX() + e2.getMaskX(), (int) e2.getY() + e2.getMaskY(),
                e2.getMaskW(), e2.getMaskH());

        return e1Mask.intersects(e2Mask);
    }
}
