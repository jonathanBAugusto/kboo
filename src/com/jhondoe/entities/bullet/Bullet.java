package com.jhondoe.entities.bullet;

import java.awt.image.BufferedImage;

import com.jhondoe.common.F;
import com.jhondoe.entities.Entity;
import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.sound.Sound;
import com.jhondoe.tiles.Tile;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Entity {

    private double dx;
    private double dy;
    private double speed = 3;
    private int maxDistance = 1 * (Tile.WIDTH * Tile.HEIGHT);
    private int damage = 5;
    private double distance = 0;

    public Bullet(double x, double y, int width, int height, BufferedImage sprite, double dx, double dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx * speed;
        y += dy * speed;
        distance += (dx * speed) + (dy * speed);

        isColliding();
        if (distance >= maxDistance || distance <= (maxDistance * -1)) {
            Game.bullets.remove(this);
            distance = 0;
            return;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) getXCamera(), (int) getYCamera(), width, height);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void isColliding() {
        for (int i = 0; i < Game.enemies.size(); i++) {
            Enemy enemy = Game.enemies.get(i);
            if (F.isColliding(this, enemy)) {
                Sound.hitBullet.play();
                enemy.subLife(getDamage());
                enemy.setDamaged(true);
                Game.bullets.remove(this);
                return;
            }
        }
    }
}
