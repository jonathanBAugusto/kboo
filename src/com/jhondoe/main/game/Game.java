package com.jhondoe.main.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.jhondoe.entities.Entity;
import com.jhondoe.entities.bullet.Bullet;
import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.entities.player.Player;
import com.jhondoe.graphics.Spritesheet;
import com.jhondoe.graphics.UI;
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

		player = new Player(0, 0, Tile.WIDTH, Tile.HEIGHT, playerSheet.getSprite(0, 48, Tile.WIDTH, Tile.HEIGHT));
		gameUi = new UI();
		entities.add(player);
		world = new World("/map200.png");
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

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.update();
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
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.render(g);
		}
		/*****/
		gameUi.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
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
