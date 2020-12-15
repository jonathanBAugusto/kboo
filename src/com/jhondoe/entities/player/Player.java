package com.jhondoe.entities.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jhondoe.entities.items.life_pack.LifePack;
import com.jhondoe.entities.items.life_up.LifeUp;
import com.jhondoe.entities.items.power_ammo.PowerAmmo;
import com.jhondoe.entities.items.stamina.Stamina;
import com.jhondoe.enums.Dir;
import com.jhondoe.enums.GameState;
import com.jhondoe.main.Main;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.Camera;
import com.jhondoe.world.World;

public class Player extends PlayerColiders {
	public double life = 100, maxLife = 100;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		initSprites();
	}

	private void checkJump() {
		if (jump) {
			if (!isJumping) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}

		}
		if (isJumping) {
			if (jumpUp) {
				jumpFrames += jumpSpeed;
			} else if (jumpDown) {
				jumpFrames -= jumpSpeed;
				if (jumpFrames <= 0) {
					jumpUp = false;
					jumpDown = false;
					isJumping = false;
				}
			}
			setZ(jumpFrames);
			if (jumpFrames >= jumpMaxFrames) {
				jumpUp = false;
				jumpDown = true;
			}
		}
	}

	public void update() {
		checkJump();

		double customSpeed = speed;
		moved = false;
		frames++;
		staminaFrames++;
		if (shift) {
			if (stamina > 0) {
				customSpeed *= shiftSpeed;
			}
		}

		if (left && World.isFree((int) (x - customSpeed), (int) y, (isJumping))) {
			moved = true;
			last_dir = Dir.LEFT;
			x -= customSpeed;
		}
		if (right && World.isFree((int) (x + customSpeed), (int) y, (isJumping))) {
			moved = true;
			last_dir = Dir.RIGHT;
			x += customSpeed;
		}
		if (up && World.isFree((int) x, (int) (y - customSpeed), (isJumping))) {
			moved = true;
			last_dir = Dir.UP;
			y -= customSpeed;
		}
		if (down && World.isFree((int) x, (int) (y + customSpeed), (isJumping))) {
			moved = true;
			last_dir = Dir.DOWN;
			y += customSpeed;
		}
		if (shift) {
			if (stamina > 0 && staminaFrames >= (int) Game.FPS / 2 && moved) {
				staminaFrames = 0;
				stamina--;
			}
		}

		if (frames == maxFrames) {
			frames = 0;
			setDamaged(false);
			if (moved) {
				index++;
				if (animateShift()) {
					runnerIndex++;
				} else {
					runnerIndex = 0;
				}
				stoppedIndex = 0;
			} else {
				stoppedIndex++;
				index = 0;
				runnerIndex = 0;
			}

			if (index > maxIndex) {
				index = 0;
			}

			if (runnerIndex > runnerMaxIndex) {
				runnerIndex = 0;
			}
			if (stoppedIndex > stoppedMaxIndex) {
				stoppedIndex = 0;
			}
		}

		LifePack.checkCollision(this);
		PowerAmmo.checkCollision(this);
		LifeUp.checkCollision(this);
		Stamina.checkCollision(this);

		// checkShoot();
		checkMouseShoot();

		if (this.life <= 0) {
			Main.game.setGameState(GameState.GAME_OVER);
		}

		Camera.setX(Camera.clamp((int) getX() - (Game.WIDTH / 2), 0, (World.WIDTH * Tile.WIDTH) - Game.WIDTH));
		Camera.setY(Camera.clamp((int) getY() - (Game.HEIGHT / 2), 0, (World.HEIGHT * Tile.HEIGHT) - Game.HEIGHT));
	}

	public void render(Graphics g) {
		animateMouse(g);
	}
}
