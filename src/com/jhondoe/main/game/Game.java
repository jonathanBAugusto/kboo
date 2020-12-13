package com.jhondoe.main.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.jhondoe.common.F;
import com.jhondoe.entities.Entity;
import com.jhondoe.entities.bullet.Bullet;
import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.entities.player.Player;
import com.jhondoe.enums.GameState;
import com.jhondoe.graphics.Spritesheet;
import com.jhondoe.graphics.UI;
import com.jhondoe.main.menu.Menu;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.World;

public class Game extends GameListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Game() {
		addKeyListener(this);
		addMouseListener(this);
		setPreferredSize(new Dimension(getWidth(), getHeight()));
		initFrame();
		initCommons();
		initEntities();
	}

	private void initCommons() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		rand = new Random();
	}

	public void initEntities() {
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();

		spritesheet = new Spritesheet("/spritesheet.png");
		playerSheet = new Spritesheet("/playersheet.png");
		playerSheetRunner = new Spritesheet("/playersheetrunner.png");
		enemySheet = new Spritesheet("/enemy1.png");

		menu = new Menu();
		player = new Player(0, 0, Tile.WIDTH, Tile.HEIGHT, playerSheet.getSprite(0, 48, Tile.WIDTH, Tile.HEIGHT));
		gameUi = new UI();
		entities.add(player);
		world = new World("/map" + (currentLevel + 1) + ".png");
	}

	public void initFrame() {
		frame = new JFrame("KBoo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateNormal() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.update();
		}

		if (enemies.size() == 0) {
			sumCurrentLevel(1);
			if (getCurrentLevel() >= maxLevel) {
				setCurrentLevel(0);
			}
			initEntities();
			return;
		}
	}

	private void updateGameOver() {
		framesGameOver++;
		if (framesGameOver >= maxFramesGameOver) {
			framesGameOver = 0;
			toggleShowMessageGameOver();
		}

		if (Game.player.isEnter()) {
			setCurrentLevel(0);
			setGameState(GameState.PLAY);
			initEntities();
		}
	}

	private void checkStatusGame(Graphics g) {
		switch (getGameState()) {
			case GAME_OVER:
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(new Color(0, 0, 0, 100));
				g2d.fillRect(0, 0, getWidth() * SCALE, getHeight() * SCALE);

				F.drawCenteredFontBold(g, "GAME OVER", 80, Color.white, 0, -40);
				if (isShowMessageGameOver()) {
					F.drawCenteredFontBold(g, ">>>Press Enter to try again...<<<", 30, Color.white, 0, 60);
				}
				break;
			case MENU:
				menu.render(g);
				break;
			case PLAY:
				break;
			default:
				break;

		}
	}

	public void update() {
		switch (getGameState()) {
			case PLAY:
				updateNormal();
			case GAME_OVER:
				updateGameOver();
			case MENU:
				menu.update();
				break;
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		/* Game Render */
		world.render(g);
		if (getGameState().equals(GameState.PLAY) || getGameState().equals(GameState.GAME_OVER)) {
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.render(g);
			}
			for (int i = 0; i < bullets.size(); i++) {
				Bullet b = bullets.get(i);
				b.render(g);
			}
		}
		/*****/
		gameUi.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		checkStatusGame(g);
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = FPS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				render();
				delta--;
			}
		}

		stop();
	}
}
