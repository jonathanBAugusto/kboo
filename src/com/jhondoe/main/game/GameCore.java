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
import com.jhondoe.enums.GameState;
import com.jhondoe.graphics.Spritesheet;
import com.jhondoe.graphics.UI;
import com.jhondoe.main.menu.Menu;
import com.jhondoe.world.World;

public class GameCore extends Canvas {

    public static final long serialVersionUID = 1L;
    public static final int ENCODE_GAME = 21;
    public static JFrame frame;
    protected Thread thread;
    protected boolean isRunning, saveGame = false;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 4;

    protected BufferedImage image;
    protected GameState gameState = GameState.MENU;
    protected boolean showMessageGameOver = false;

    protected int currentLevel = 0, maxLevel = 3, framesGameOver = 0, maxFramesGameOver = 30;
    protected int[] pixels;

    protected static BufferedImage minimapImage;

    public static int CURRENT_LEVEL = 0;

    public static final double FPS = 60.0;
    public static int nowFps = 0;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    public static List<Bullet> bullets;

    public static Menu menu;
    public static Player player;
    public static UI gameUi;

    public static Spritesheet spritesheet;
    public static Spritesheet playerSheet;
    public static Spritesheet playerSheetRunner;
    public static Spritesheet enemySheet;
    public static World world;

    public static Random rand;

    public static int[] minimapPixels;

    public int getWidth() {
        return WIDTH * SCALE;
    }

    public int getHeight() {
        return HEIGHT * SCALE;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        CURRENT_LEVEL = currentLevel;
    }

    public void sumCurrentLevel(int sumValue) {
        currentLevel += sumValue;
        CURRENT_LEVEL = currentLevel;
    }

    public boolean isShowMessageGameOver() {
        return showMessageGameOver;
    }

    public void toggleShowMessageGameOver() {
        this.showMessageGameOver = !this.showMessageGameOver;
    }

    public void setShowMessageGameOver(boolean showMessageGameOver) {
        this.showMessageGameOver = showMessageGameOver;
    }

    public int getFramesGameOver() {
        return framesGameOver;
    }

    public void setFramesGameOver(int framesGameOver) {
        this.framesGameOver = framesGameOver;
    }

    public void sumFramesGameOver(int sumValue) {
        this.framesGameOver += sumValue;
    }
}
