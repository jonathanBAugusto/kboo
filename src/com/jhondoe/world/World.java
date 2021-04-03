package com.jhondoe.world;

import javax.imageio.ImageIO;

import com.jhondoe.entities.Entity;
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
import java.awt.Point;
import java.io.IOException;

public class World {

    public static Tile[] tiles;
    private int mapArea;
    public static int WIDTH, HEIGHT;

    public World() {
        Game.player.setPosition(new Point(16, 16));
        WIDTH = 25;
        HEIGHT = 25;
        tiles = new Tile[WIDTH * HEIGHT];
        for (int x1 = 0; x1 < WIDTH; x1++) {
            for (int y1 = 0; y1 < HEIGHT; y1++) {
                tiles[x1 + (y1 * WIDTH)] = new WallTile(x1 * Tile.WIDTH, y1 * Tile.HEIGHT, Tile.TILE_WALL_CRACKED);
                int rand = Game.rand.nextInt(100);

                if (rand >= 30) {
                    tiles[x1 + (y1 * WIDTH)] = new WallTile(x1 * Tile.WIDTH, y1 * Tile.HEIGHT, Tile.TILE_WALL_CRACKED);
                } else {
                    tiles[x1 + (y1 * WIDTH)] = new WallTile(x1 * Tile.WIDTH, y1 * Tile.HEIGHT, Tile.TILE_WALL);
                }

            }
        }

        int dir = 0, xx = 1, yy = 1;

        if (Game.rand.nextInt(200) > 50) {
            tiles[xx + (yy * WIDTH)] = new GrassTile(xx * Tile.WIDTH, yy * Tile.HEIGHT, Tile.TILE_GRASS_ROCK);
        } else {
            tiles[xx + (yy * WIDTH)] = new GrassTile(xx * Tile.WIDTH, yy * Tile.HEIGHT, Tile.TILE_GRASS);
        }

        for (int i = 0; i < (WIDTH * HEIGHT); i++) {

            if (dir == 0 && xx < WIDTH) {
                xx++;
            } else if (dir == 1 && xx > 0) {
                xx--;
            } else if (dir == 2 && yy < HEIGHT) {
                yy++;
            } else if (dir == 3 && yy > 0) {
                yy--;
            }

            if (Game.rand.nextInt(100) < 20) {
                dir = Game.rand.nextInt(4);
            }

            if (yy == HEIGHT - 1 || xx == WIDTH - 1) {
                continue;
            }

            if ((xx + (yy * WIDTH)) > 0 && (xx + (yy * WIDTH)) < WIDTH * HEIGHT) {
                if (Game.rand.nextInt(200) > 50) {
                    tiles[xx + (yy * WIDTH)] = new GrassTile(xx * Tile.WIDTH, yy * Tile.HEIGHT, Tile.TILE_GRASS_ROCK);
                } else {
                    tiles[xx + (yy * WIDTH)] = new GrassTile(xx * Tile.WIDTH, yy * Tile.HEIGHT, Tile.TILE_GRASS);
                }
            }
        }
    }

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource("/maps" + path));
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            mapArea = WIDTH * HEIGHT;
            drawMap(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawMap(BufferedImage map) {
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

    private static boolean isFreeDiagonal(int xNext, int yNext) {
        return false;
    }

    public static boolean isFree(int xNext, int yNext) {
        return isFree(xNext, yNext, false);
    }

    public static boolean isFree(int xNext, int yNext, boolean ignore) {
        return isFree(xNext, yNext, Tile.WIDTH, Tile.HEIGHT, ignore);
    }

    public static boolean isFree(int xNext, int yNext, int tileW, int tileH, boolean ignore) {
        if (ignore) {
            return true;
        }
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

    public static void renderMinimap() {
        for (int xx = 0; xx < WIDTH; xx++) {
            for (int yy = 0; yy < HEIGHT; yy++) {
                Tile tile = tiles[xx + (yy * WIDTH)];
                if (tile instanceof WallTile) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBB323c39;
                    continue;
                }
                if (tile instanceof GrassTile) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBB000000;
                    continue;
                }

            }
        }

        if (Game.player.getLife() > 0) {
            int xx = (int) Game.player.getX() / 16;
            int yy = (int) Game.player.getY() / 16;
            Game.minimapPixels[xx + (yy * WIDTH)] = 0xBB00FF00;
        }

        if (Game.entities.toArray().length > 0) {
            for (int i = 0; i < Game.entities.toArray().length; i++) {
                Entity e = Game.entities.get(i);
                int xx = (int) e.getX() / 16;
                int yy = (int) e.getY() / 16;
                if (e instanceof Enemy) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBBFF0000;
                } else if (e instanceof LifePack) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBBFFFFFF;
                } else if (e instanceof LifeUp) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBB56a20e;
                } else if (e instanceof PowerAmmo) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBB4d6bc0;
                } else if (e instanceof Stamina) {
                    Game.minimapPixels[xx + (yy * WIDTH)] = 0xBBfbf236;
                }
            }
        }

    }

    // public boolean isCollided() {

    // }
}
