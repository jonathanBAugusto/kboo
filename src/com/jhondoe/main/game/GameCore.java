package com.jhondoe.main.game;

import java.awt.Canvas;
import java.awt.image.BufferedImage;

import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.jhondoe.entities.Entity;
import com.jhondoe.entities.bullet.Bullet;
import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.entities.player.Player;
import com.jhondoe.graphics.Spritesheet;
import com.jhondoe.graphics.UI;
import com.jhondoe.world.World;

public class GameCore extends Canvas {

    public static final long serialVersionUID = 1L;
    public static JFrame frame;
    protected Thread thread;
    protected boolean isRunning;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 4;

    protected BufferedImage image;

    public static final double FPS = 30.0;
    public static int nowFps = 0;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<Bullet> bullets;

    public static Player player;
    public static UI gameUi;

    public static Spritesheet spritesheet;
    public static Spritesheet playerSheet;
    public static Spritesheet playerSheetRunner;
    public static Spritesheet enemySheet;
    public static World world;

    public static Random rand;

    public int getWidth() {
        return WIDTH * SCALE;
    }

    public int getHeight() {
        return HEIGHT * SCALE;
    }
}
