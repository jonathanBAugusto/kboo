package com.jhondoe.entities.enemy;

import java.awt.image.BufferedImage;

import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class EnemySprites extends EnemyCore {
    protected int index = 0, maxIndex = 3, stoppedIndex = 0, stoppedMaxIndex = 1;

    protected static BufferedImage[] rightRun;
    protected static BufferedImage[] leftRun;
    protected static BufferedImage[] upRun;
    protected static BufferedImage[] downRun;

    protected static BufferedImage[] rightStoppedRun;
    protected static BufferedImage[] leftStoppedRun;
    protected static BufferedImage[] upStoppedRun;
    protected static BufferedImage[] downStoppedRun;

    public EnemySprites(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    protected void initSprites() {
        rightRun = new BufferedImage[maxIndex + 1];
        leftRun = new BufferedImage[maxIndex + 1];
        upRun = new BufferedImage[maxIndex + 1];
        downRun = new BufferedImage[maxIndex + 1];
        rightStoppedRun = new BufferedImage[stoppedMaxIndex + 1];
        leftStoppedRun = new BufferedImage[stoppedMaxIndex + 1];
        upStoppedRun = new BufferedImage[stoppedMaxIndex + 1];
        downStoppedRun = new BufferedImage[stoppedMaxIndex + 1];

        for (int i = 0; i < rightRun.length; i++) {
            rightRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 0, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < leftRun.length; i++) {
            leftRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 16, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < upRun.length; i++) {
            upRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 32, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < downRun.length; i++) {
            downRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 48, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < rightStoppedRun.length; i++) {
            rightStoppedRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 64, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < leftStoppedRun.length; i++) {
            leftStoppedRun[i] = Game.enemySheet.getSprite(32 + (i * Tile.WIDTH), 64, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < upStoppedRun.length; i++) {
            upStoppedRun[i] = Game.enemySheet.getSprite(0 + (i * Tile.WIDTH), 80, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < downStoppedRun.length; i++) {
            downStoppedRun[i] = Game.enemySheet.getSprite(32 + (i * Tile.WIDTH), 80, Tile.WIDTH, Tile.HEIGHT);
        }
    }

}
