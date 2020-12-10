package com.jhondoe.world;

import javax.imageio.ImageIO;

import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.entities.items.life_pack.LifePack;
import com.jhondoe.entities.items.power_ammo.PowerAmmo;
import com.jhondoe.entities.items.stamina.Stamina;
import com.jhondoe.entities.items.life_up.LifeUp;
import com.jhondoe.enums.TileItens;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.GrassTile;
import com.jhondoe.tiles.Tile;
import com.jhondoe.tiles.WallTile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    public static Tile[] tiles;
    private int mapArea;
    public static int WIDTH, HEIGHT;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            mapArea = WIDTH * HEIGHT;
            createMap(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMap(BufferedImage map) {
        int[] pixels = new int[mapArea];
        tiles = new Tile[mapArea];
        map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
        scannMap(map, pixels);
    }

    private void scannMap(BufferedImage map, int[] pixels) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int pixel = pixels[x + (y * WIDTH)];
                tiles[x + (y * WIDTH)] = new GrassTile(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.TILE_GRASS);
                if (pixel == TileItens.ENEMY.getValue()) {
                    Enemy enm = new Enemy(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, null);
                    Game.entities.add(enm);
                    Game.enemies.add(enm);
                } else if (pixel == TileItens.GRASS.getValue()) {
                    tiles[x + (y * WIDTH)] = new GrassTile(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.TILE_GRASS);
                } else if (pixel == TileItens.LIFE.getValue()) {
                    Game.entities.add(new LifePack(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, null));
                } else if (pixel == TileItens.POWERAMOO.getValue()) {
                    Game.entities.add(new PowerAmmo(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, null));
                } else if (pixel == TileItens.STAMINA.getValue()) {
                    Game.entities.add(new Stamina(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, null));
                } else if (pixel == TileItens.LIFEUP.getValue()) {
                    Game.entities.add(new LifeUp(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT, null));
                } else if (pixel == TileItens.WALL.getValue()) {
                    tiles[x + (y * WIDTH)] = new WallTile(x * Tile.WIDTH, y * Tile.HEIGHT, Tile.TILE_WALL);
                } else if (pixel == TileItens.PLAYER.getValue()) {
                    Game.player.setX(x * Tile.WIDTH);
                    Game.player.setY(y * Tile.HEIGHT);
                }
            }
        }
    }

    public static boolean isFree(int xNext, int yNext) {
        return isFree(xNext, yNext, Tile.WIDTH, Tile.HEIGHT);
    }

    public static boolean isFree(int xNext, int yNext, int tileW, int tileH) {
        int x1 = xNext / tileW;
        int y1 = yNext / tileH;

        int x2 = (xNext + (tileW - 1)) / tileW;
        int y2 = yNext / tileH;

        int x3 = xNext / tileW;
        int y3 = (yNext + (tileH - 1)) / tileH;

        int x4 = (xNext + (tileW - 1)) / tileW;
        int y4 = (yNext + (tileH - 1)) / tileH;

        return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
                || (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
                || (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
                || (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public void render(Graphics g) {
        int xStart = Camera.getX() / Tile.WIDTH;
        int yStart = Camera.getY() / Tile.HEIGHT;

        int xFinal = xStart + (Game.WIDTH / Tile.WIDTH);
        int yFinal = yStart + (Game.HEIGHT / Tile.HEIGHT);

        for (int x = xStart; x <= xFinal; x++) {
            for (int y = yStart; y <= yFinal; y++) {
                if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT)
                    continue;
                tiles[x + (y * WIDTH)].render(g);
            }
        }
    }

    // public boolean isCollided() {

    // }
}
