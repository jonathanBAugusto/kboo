package com.jhondoe.main.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.jhondoe.common.F;
import com.jhondoe.enums.GameState;
import com.jhondoe.main.Main;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class Menu {
    public static final String[] OPTIONS = { "New Game", "Load Game", "Quit" };
    public static BufferedImage ARROW_MENU = Game.spritesheet.getSprite(0, (7 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT);

    private int currentOption = 0;
    public static int MAX_OPTION = OPTIONS.length - 1;

    private boolean up, down, enter, pause;

    private void upMenu() {
        currentOption--;
        if (currentOption < 0) {
            currentOption = MAX_OPTION;
        }
        up = false;
    }

    private void downMenu() {
        currentOption++;
        if (currentOption > MAX_OPTION) {
            currentOption = 0;
        }
        down = false;
    }

    private void enterMenu() {
        switch (currentOption) {
            case 0:// New Game
                if (!pause) {
                    Main.game.setCurrentLevel(0);
                }
                Main.game.setGameState(GameState.PLAY);
                pause = false;
                break;
            case 1:
                Main.game.setCurrentLevel(2);
                if (pause) {
                    Main.game.initEntities();
                }
                Main.game.setGameState(GameState.PLAY);
                pause = false;
                break;
            case 2:
                System.exit(1);
                break;
        }
        enter = false;
    }

    public void update() {
        if (up) {
            upMenu();
        } else if (down) {
            downMenu();
        } else if (enter) {
            enterMenu();
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        int y = 110;
        F.drawCenteredFontBold(g, "KbO.O", 80, Color.white, 0, 0, F.DRAW_CENTERED_FONT_IGNORE_XY, y);

        y += 150;
        for (int i = 0; i < OPTIONS.length; i++) {
            int fontSize = 40;
            String text = OPTIONS[i];
            if (pause && i == 0) {
                text = "Continue";
            }
            Point pFont = F.drawCenteredFont(g, text, fontSize, Color.white, Font.PLAIN, 0, 0,
                    F.DRAW_CENTERED_FONT_IGNORE_XY, y);

            if (currentOption == i) {
                Rectangle2D selected = F.getRectangle2DFont(text, g, fontSize, Font.PLAIN);
                g.drawImage(ARROW_MENU, (int) (pFont.getX() - (Tile.WIDTH * 2)),
                        (int) (pFont.getY() - (selected.getHeight() - (Tile.HEIGHT * 1.5))), null);

            }
            y += 60;
        }
    }

    public int getCurrentOption() {
        return currentOption;
    }

    public void setCurrentOption(int currentOption) {
        this.currentOption = currentOption;
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

    public boolean isEnter() {
        return enter;
    }

    public void setEnter(boolean enter) {
        this.enter = enter;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
