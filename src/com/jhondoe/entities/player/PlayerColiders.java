package com.jhondoe.entities.player;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import com.jhondoe.entities.bullet.Bullet;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.sound.Sound;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.Camera;

public class PlayerColiders extends PlayerSprites {

    public PlayerColiders(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    protected void checkMouseShoot() {
        if (!isMouseShoot() || powerAmmo <= 0) {
            return;
        }
        Sound.fireBullet.play();
        setMouseShoot(false);
        double y = mouseY - (getY() + (Tile.HEIGHT / 2) - Camera.getY());
        double x = mouseX - (getX() + (Tile.WIDTH / 2) - Camera.getX());
        double angle = Math.atan2(y, x);
        double dx = Math.cos(angle);
        double dy = Math.sin(angle);
        int customX = 8;
        int customY = 8;

        Game.bullets.add(new Bullet(getX() + customX, getY() + customY, 3, 3, null, dx, dy));

        if (powerAmmo > 0) {
            powerAmmo--;
        }
    }

    protected void checkShoot() {
        if (!isShoot() || powerAmmo <= 0) {
            return;
        }
        Sound.fireBullet.play();
        setShoot(false);
        int dx = 0;
        int dy = 0;

        int customX = 0;
        int customY = 0;

        if (isLeft()) {
            dx = -1;
            customX = 5;
            customY = 10;
        }
        if (isRight()) {
            dx = 1;
            customX = 5;
            customY = 10;
        }
        if (isUp()) {
            dy = -1;
            customX = 6;
            customY = 1;
        }
        if (isDown()) {
            dy = 1;
            customX = 6;
            customY = 10;
        }
        if (dx == 0 && dy == 0) {
            switch (last_dir) {
                case LEFT:
                    dx = -1;
                    customX = 5;
                    customY = 10;
                    break;
                case RIGHT:
                    dx = 1;
                    customX = 5;
                    customY = 10;
                    break;
                case UP:
                    dy = -1;
                    customX = 6;
                    customY = 1;
                    break;
                case DOWN:
                    dy = 1;
                    customX = 6;
                    customY = 10;
                    break;
            }
        }

        Game.bullets.add(new Bullet(getX() + customX, getY() + customY, 3, 3, null, dx, dy));

        if (powerAmmo > 0) {
            powerAmmo--;
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) getX(), (int) getY(), Tile.WIDTH, Tile.HEIGHT);
    }

}
