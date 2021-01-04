package com.jhondoe.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jhondoe.main.game.Game;
import com.jhondoe.world.Camera;

public class Tile {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static BufferedImage TILE_GRASS = Game.spritesheet.getSprite(0, 0, WIDTH, HEIGHT);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, WIDTH, HEIGHT);
    public static BufferedImage TILE_PLAYER = Game.enemySheet.getSprite(0, 48, WIDTH, HEIGHT);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.getX(), y - Camera.getY(), null);
    }
}
