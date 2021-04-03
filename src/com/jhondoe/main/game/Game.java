package com.jhondoe.main.game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
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
import com.jhondoe.main.save_state.SaveState;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.World;

public class Game extends GameListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public int xx = 0, yy = (HEIGHT / 2) - 16;

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
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		rand = new Random();
	}

	private void customPointer(JFrame frame) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/pointer-crosshair.png"));
		if (image != null) {
			Cursor c = toolkit.createCustomCursor(image, new Point(15, 15), "pointer");
			frame.setCursor(c);
		}
	}

	private void customIcon(JFrame frame) {
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResource("/icon.png"));
			frame.setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		// world = new World();
		minimapImage = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		minimapPixels = ((DataBufferInt) minimapImage.getRaster().getDataBuffer()).getData();
	}

	public void initFrame() {
		frame = new JFrame("KBoo");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		customPointer(frame);
		customIcon(frame);
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
		xx++;
		if (saveGame) {
			saveGame = false;
			String[] opt1 = { "level", "life", "maxLife", "stamina", "powerAmmo" };
			int[] opt2 = { currentLevel, player.getLife(), player.getMaxLife(), player.getStamina(),
					player.getPowerAmmo() };
			SaveState.saveGame(opt1, opt2, ENCODE_GAME);
		}

		Collections.sort(entities, Entity.depthSorter);

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
			player.updateCamera();
			menu.render(g);
			break;
		case PLAY:
			break;
		default:
			break;

		}
	}

	public void drawRectangleEx(int xO, int yO) {
		for (int xx = 0; xx < 32; xx++) {
			for (int yy = 0; yy < 32; yy++) {
				int xOff = xx + xO;
				int yOff = yy + yO;
				if (xOff < 0 || xOff >= WIDTH || yOff < 0 || yOff >= HEIGHT)
					continue;
				pixels[xOff + (yOff * GameCore.WIDTH)] = 0x00ff00c1;
			}
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
		gameUi.uiLifeBar(g);
		g.dispose();
		g = bs.getDrawGraphics();
		drawRectangleEx(xx, yy);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		gameUi.renderCustom(g);
		World.renderMinimap();
		gameUi.miniMapDraw(g, minimapImage);
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
