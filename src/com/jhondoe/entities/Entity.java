package com.jhondoe.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.game.GameCore;
import com.jhondoe.main.logic.Node;
import com.jhondoe.main.logic.Vector2i;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.Camera;

public class Entity {
	protected double x;
	protected double y;
	protected double z;
	protected int width;
	protected int height;
	protected BufferedImage sprite;

	protected int maskX, maskY, maskW, maskH;

	protected List<Node> path;

	public static BufferedImage hat1 = Game.spritesheet.getSprite(0, (6 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage hat2 = Game.spritesheet.getSprite(16, (6 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage ENEMY_FEEDBACK = Game.enemySheet.getSprite(0, (6 * Tile.HEIGHT), Tile.WIDTH,
			Tile.HEIGHT);
	private int depth;

	public Entity() {

	}

	public static Comparator<Entity> depthSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity n0, Entity n1) {
			if (n1.getDepth() < n0.getDepth())
				return 1;
			if (n1.getDepth() > n0.getDepth())
				return -1;
			return 0;
		}
	};

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

	public int getDepth() {
		return depth;
	}

	public void setDepth(int newDepth) {
		depth = newDepth;
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
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

	public boolean isCollidingEnemy(int xNext, int yNext) {
		Rectangle current = new Rectangle(xNext + maskX, yNext + maskY, maskW, maskH);

		for (Enemy enemy : GameCore.enemies) {
			if (enemy == this) {
				continue;
			}

			if (current
					.intersects(new Rectangle((int) enemy.getX() + maskX, (int) enemy.getY() + maskY, maskW, maskH))) {
				return true;
			}
		}

		return false;
	}

	public void followPath(List<Node> path, double speed) {
		if (path != null) {
			if (path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				// if (this.x < target.x * Tile.WIDTH && !isCollidingEnemy((int) (x + speed),
				// (int) y)) {
				if (this.x < target.x * Tile.WIDTH) {
					this.x += speed;
				} else if (this.x > target.x * Tile.WIDTH) {
					this.x -= speed;
				}

				// if (this.y < target.y * Tile.HEIGHT && !isCollidingEnemy((int) x, (int) (y +
				// speed))) {
				if (this.y < target.y * Tile.HEIGHT) {
					this.y += speed;
				} else if (this.y > target.y * Tile.HEIGHT) {
					this.y -= speed;
				}
				int tileX = target.x * Tile.WIDTH;
				int tileY = target.y * Tile.HEIGHT;
				if ((int) this.x == tileX && (int) this.y == tileY) {
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprite, (int) getXCamera(), (int) getYCamera(), null);
	}

	public double calculeDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}
}
