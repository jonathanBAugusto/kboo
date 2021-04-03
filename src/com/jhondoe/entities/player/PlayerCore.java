package com.jhondoe.entities.player;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.jhondoe.entities.Entity;
import com.jhondoe.enums.Dir;
import com.jhondoe.main.Main;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.Camera;

public class PlayerCore extends Entity {

    protected boolean left, right, up, down, shift, enter, shoot = false, mouseShoot = false, moved = false,
            damaged = false, jump = false, isJumping = false, jumpUp = false, jumpDown = false;

    protected int mouseX, mouseY, lastMouseX = 0, lastMouseY = 0;

    protected Dir last_dir = Dir.DOWN, lastMouseDir = Dir.DOWN;

    protected int stamina = 0, powerAmmo = 0;

    protected int frames = 0, staminaFrames = 0, maxFrames = 20, index = 0, maxIndex = 7, stoppedIndex = 0,
            stoppedMaxIndex = 1, runnerIndex = 0, runnerMaxIndex = 7, jumpFrames = 0, jumpMaxFrames = 30, jumpSpeed = 1;

    protected double speed = 1.2, shiftSpeed = 1.5;

    protected static BufferedImage[] rightPlayer;
    protected static BufferedImage[] leftPlayer;
    protected static BufferedImage[] upPlayer;
    protected static BufferedImage[] downPlayer;

    protected static BufferedImage[] rightStoppedPlayer;
    protected static BufferedImage[] leftStoppedPlayer;
    protected static BufferedImage[] upStoppedPlayer;
    protected static BufferedImage[] downStoppedPlayer;

    protected static BufferedImage[] rightPlayerRunner;
    protected static BufferedImage[] leftPlayerRunner;
    protected static BufferedImage[] upPlayerRunner;
    protected static BufferedImage[] downPlayerRunner;

    protected static BufferedImage rightPlayerDamaged;
    protected static BufferedImage leftPlayerDamaged;
    protected static BufferedImage upPlayerDamaged;
    protected static BufferedImage downPlayerDamaged;

    public double life = 100, maxLife = 100;

    public PlayerCore(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setSpeed(int value) {
        this.speed = value;
    }

    public boolean isShift() {
        return shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean isMouseShoot() {
        return mouseShoot;
    }

    public void setMouseShoot(boolean mouseShoot) {
        this.mouseShoot = mouseShoot;
    }

    public int getStamina() {
        return stamina;
    }

    public void subStamina(int stamina) {
        if ((this.stamina - stamina) <= 0) {
            this.stamina = 0;
        } else {
            this.stamina -= stamina;
        }
    }

    public void addStamina(int stamina) {
        this.stamina += stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getPowerAmmo() {
        return powerAmmo;
    }

    public void subPowerAmmo(int powerAmmo) {
        if ((this.powerAmmo - powerAmmo) <= 0) {
            this.powerAmmo = 0;
        } else {
            this.powerAmmo -= powerAmmo;
        }
    }

    public void addPowerAmmo(int powerAmmo) {
        this.powerAmmo += powerAmmo;
    }

    public void setPowerAmmo(int powerAmmo) {
        this.powerAmmo = powerAmmo;
    }

    protected boolean animateShift() {
        return isShift() && stamina > 0 && moved;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getLife() {
        return (int) life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public int getMaxLife() {
        return (int) maxLife;
    }

    public void setMaxLife(double maxLife) {
        this.maxLife = maxLife;
    }

    protected double getMouseAngleByClick() {
        double y = mouseY - (getY() + (Tile.HEIGHT / 2) - Camera.getY());
        double x = mouseX - (getX() + (Tile.WIDTH / 2) - Camera.getX());
        return Math.toDegrees(Math.atan2(y, x));
    }

    protected double getMousePositionAngle() {
        Point point = Main.game.getMousePosition();
        if (point != null) {

            double y = (point.getLocation().getY() / Game.SCALE) - (getY() + (Tile.HEIGHT / 2) - Camera.getY());
            double x = (point.getLocation().getX() / Game.SCALE) - (getX() + (Tile.WIDTH / 2) - Camera.getX());
            return Math.toDegrees(Math.atan2(y, x));
        }
        return 200;
    }

    protected Dir getMouseDir() {
        double mouseAngle = getMousePositionAngle();
        if (mouseAngle > -45 && mouseAngle <= 45) {
            lastMouseDir = Dir.RIGHT;
        }
        if (mouseAngle > 45 && mouseAngle <= 135) {
            lastMouseDir = Dir.DOWN;
        }
        if (mouseAngle > 135 && mouseAngle <= 180 || mouseAngle >= -180 && mouseAngle <= -135) {
            lastMouseDir = Dir.LEFT;
        }
        if (mouseAngle > -135 && mouseAngle <= -45) {
            lastMouseDir = Dir.UP;
        }
        return lastMouseDir;
    }

    public boolean isEnter() {
        return enter;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public void setPosition(Point p) {
        x = p.x;
        y = p.y;
    }
}
