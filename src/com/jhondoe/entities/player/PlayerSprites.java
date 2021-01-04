package com.jhondoe.entities.player;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.jhondoe.entities.Entity;
import com.jhondoe.enums.Dir;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class PlayerSprites extends PlayerCore {

    public PlayerSprites(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    protected void initSprites() {
        rightPlayer = new BufferedImage[maxIndex + 1];
        leftPlayer = new BufferedImage[maxIndex + 1];
        upPlayer = new BufferedImage[maxIndex + 1];
        downPlayer = new BufferedImage[maxIndex + 1];

        rightStoppedPlayer = new BufferedImage[stoppedMaxIndex + 1];
        leftStoppedPlayer = new BufferedImage[stoppedMaxIndex + 1];
        upStoppedPlayer = new BufferedImage[stoppedMaxIndex + 1];
        downStoppedPlayer = new BufferedImage[stoppedMaxIndex + 1];

        rightPlayerRunner = new BufferedImage[runnerMaxIndex + 1];
        leftPlayerRunner = new BufferedImage[runnerMaxIndex + 1];
        upPlayerRunner = new BufferedImage[runnerMaxIndex + 1];
        downPlayerRunner = new BufferedImage[runnerMaxIndex + 1];

        instanceSprites();
    }

    protected void instanceSprites() {
        for (int i = 0; i < rightPlayer.length; i++) {
            rightPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 0, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < leftPlayer.length; i++) {
            leftPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 16, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < upPlayer.length; i++) {
            upPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 32, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < downPlayer.length; i++) {
            downPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 48, Tile.WIDTH, Tile.HEIGHT);
        }

        for (int i = 0; i < rightStoppedPlayer.length; i++) {
            rightStoppedPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 64, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < leftStoppedPlayer.length; i++) {
            leftStoppedPlayer[i] = Game.playerSheet.getSprite(32 + (i * Tile.WIDTH), 64, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < upStoppedPlayer.length; i++) {
            upStoppedPlayer[i] = Game.playerSheet.getSprite(0 + (i * Tile.WIDTH), 80, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < downStoppedPlayer.length; i++) {
            downStoppedPlayer[i] = Game.playerSheet.getSprite(32 + (i * Tile.WIDTH), 80, Tile.WIDTH, Tile.HEIGHT);
        }

        for (int i = 0; i < rightPlayerRunner.length; i++) {
            rightPlayerRunner[i] = Game.playerSheetRunner.getSprite(0 + (i * Tile.WIDTH), 0, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < leftPlayerRunner.length; i++) {
            leftPlayerRunner[i] = Game.playerSheetRunner.getSprite(0 + (i * Tile.WIDTH), 16, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < upPlayerRunner.length; i++) {
            upPlayerRunner[i] = Game.playerSheetRunner.getSprite(0 + (i * Tile.WIDTH), 32, Tile.WIDTH, Tile.HEIGHT);
        }
        for (int i = 0; i < downPlayerRunner.length; i++) {
            downPlayerRunner[i] = Game.playerSheetRunner.getSprite(0 + (i * Tile.WIDTH), 48, Tile.WIDTH, Tile.HEIGHT);
        }

        rightPlayerDamaged = Game.playerSheet.getSprite((9 * Tile.WIDTH), 0, Tile.WIDTH, Tile.HEIGHT);
        leftPlayerDamaged = Game.playerSheet.getSprite((9 * Tile.WIDTH), 16, Tile.WIDTH, Tile.HEIGHT);
        upPlayerDamaged = Game.playerSheet.getSprite((9 * Tile.WIDTH), 32, Tile.WIDTH, Tile.HEIGHT);
        downPlayerDamaged = Game.playerSheet.getSprite((9 * Tile.WIDTH), 48, Tile.WIDTH, Tile.HEIGHT);
    }

    protected void drawSprite(Graphics g, BufferedImage sprite) {
        g.drawImage(sprite, (int) getXCamera(), (int) (getYCamera() - z), null);
    }

    protected void animateMouse(Graphics g) {
        animate(g, getMouseDir());
    }

    protected void animate(Graphics g, Dir dir) {
        boolean stopped = false;

        switch (dir) {
            case LEFT:
                if (isDamaged()) {
                    drawSprite(g, leftPlayerDamaged);
                } else if (animateShift()) {
                    drawSprite(g, leftPlayerRunner[runnerIndex]);
                } else if (moved) {
                    drawSprite(g, leftPlayer[index]);
                } else {
                    drawSprite(g, leftStoppedPlayer[stoppedIndex]);
                    stopped = true;
                }
                if (this.powerAmmo > 0) {
                    if (stopped) {
                        drawSprite(g, stoppedIndex <= 0 ? Entity.hat1 : Entity.hat2);
                    } else {
                        drawSprite(g, Entity.hat1);
                    }
                }
                break;
            case UP:
                if (isDamaged()) {
                    drawSprite(g, upPlayerDamaged);
                } else if (animateShift()) {
                    drawSprite(g, upPlayerRunner[runnerIndex]);
                } else if (moved) {
                    drawSprite(g, upPlayer[index]);
                } else {
                    drawSprite(g, upStoppedPlayer[stoppedIndex]);
                    stopped = true;
                }
                if (this.powerAmmo > 0) {
                    if (stopped) {
                        drawSprite(g, stoppedIndex <= 0 ? Entity.hat1 : Entity.hat2);
                    } else {
                        drawSprite(g, Entity.hat1);
                    }
                }
                break;
            case DOWN:
                if (isDamaged()) {
                    drawSprite(g, downPlayerDamaged);
                } else if (animateShift()) {
                    drawSprite(g, downPlayerRunner[runnerIndex]);
                } else if (moved) {
                    drawSprite(g, downPlayer[index]);
                } else {
                    drawSprite(g, downStoppedPlayer[stoppedIndex]);
                    stopped = true;
                }
                if (this.powerAmmo > 0) {
                    if (stopped) {
                        drawSprite(g, stoppedIndex <= 0 ? Entity.hat1 : Entity.hat2);
                    } else {
                        drawSprite(g, Entity.hat1);
                    }
                }
                break;
            case RIGHT:
            default:
                if (isDamaged()) {
                    drawSprite(g, rightPlayerDamaged);
                } else if (animateShift()) {
                    drawSprite(g, rightPlayerRunner[runnerIndex]);
                } else if (moved) {
                    drawSprite(g, rightPlayer[index]);
                } else {
                    drawSprite(g, rightStoppedPlayer[stoppedIndex]);
                    stopped = true;
                }
                if (this.powerAmmo > 0) {
                    if (stopped) {
                        drawSprite(g, stoppedIndex <= 0 ? Entity.hat1 : Entity.hat2);
                    } else {
                        drawSprite(g, Entity.hat1);
                    }
                }
                break;
        }
        if (isJumping) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, 110));
            g2d.fillOval((int) (getXCamera() + ((Game.player.getWidth() / 2) * 0.5)),
                    (int) getYCamera() + (Game.player.getHeight() / 2), 10, 7);
        }
        System.out.println("Z: " + getZ());
    }

}
