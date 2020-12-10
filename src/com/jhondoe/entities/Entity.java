package com.jhondoe.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.Camera;

public class Entity {
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected BufferedImage sprite;

	protected int maskX, maskY, maskW, maskH;

	public static BufferedImage hat1 = Game.spritesheet.getSprite(0, (6 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage hat2 = Game.spritesheet.getSprite(16, (6 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage ENEMY_FEEDBACK = Game.enemySheet.getSprite(0, (6 * Tile.HEIGHT), Tile.WIDTH,
			Tile.HEIGHT);

	public Entity(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskX = 0;
		this.maskY = 0;
		this.maskW = width;
		this.maskH = height;
	}

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}

	protected double getXCamera() {
		return x - Camera.getX();
	}

	protected double getXCamera(int customValue) {
		return (x + customValue) - Camera.getX();
	}

	protected double getYCamera() {
		return y - Camera.getY();
	}

	protected double getYCamera(int customValue) {
		return (y + customValue) - Camera.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaskX() {
		return maskX;
	}

	public void setMaskX(int maskX) {
		this.maskX = maskX;
	}

	public int getMaskY() {
		return maskY;
	}

	public void setMaskY(int maskY) {
		this.maskY = maskY;
	}

	public int getMaskW() {
		return maskW;
	}

	public void setMaskW(int maskW) {
		this.maskW = maskW;
	}

	public int getMaskH() {
		return maskH;
	}

	public void setMaskH(int maskH) {
		this.maskH = maskH;
	}

	public void update() {

	}

	public void render(Graphics g) {
		g.drawImage(sprite, (int) getXCamera(), (int) getYCamera(), null);
	}
}
