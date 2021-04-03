package com.jhondoe.entities.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jhondoe.entities.Entity;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class Items extends Entity {

    protected int frames = 0, maxFrames = 10, index = 0, maxIndex = 3;

    protected BufferedImage[] animationSprite;

    public Items(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        setDepth(2);
    }

    protected void initSprites() {
        animationSprite = new BufferedImage[maxIndex + 1];

        for (int i = 0; i < animationSprite.length; i++) {
            animationSprite[i] = Game.spritesheet.getSprite(0 + (i * Tile.WIDTH), (1 * Tile.HEIGHT), Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    protected void animate(Graphics g) {
        g.drawImage(animationSprite[index], (int) getXCamera(), (int) getYCamera(), null);
    }

    public void update() {
        frames++;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            if (index > maxIndex) {
                index = 0;
            }
        }
    }

    public void render(Graphics g) {
        animate(g);
    }

}
