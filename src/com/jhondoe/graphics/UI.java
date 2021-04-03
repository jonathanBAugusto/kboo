package com.jhondoe.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jhondoe.common.F;
import com.jhondoe.main.Main;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.game.GameCore;
import com.jhondoe.world.World;

import java.awt.image.BufferedImage;

public class UI {
    // private static final int P_HEIGHT = 2, P_WIDTH = 20;
    private static final int UI_HEIGHT = 10;
    private static final int UI_WIDTH = 60;

    // private void playerLifeBar(Graphics g) {
    // g.setColor(Color.red);
    // g.fillRect((int) ((Game.WIDTH / 2) - (P_WIDTH - (Tile.WIDTH + 2))),
    // (int) ((Game.HEIGHT / 2) - ((Tile.HEIGHT - (P_HEIGHT + (Tile.HEIGHT / 2))) /
    // 2)), P_WIDTH, P_HEIGHT);
    // g.setColor(Color.green);
    // g.fillRect((int) ((Game.WIDTH / 2) - (P_WIDTH - (Tile.WIDTH + 2))),
    // (int) ((Game.HEIGHT / 2) - ((Tile.HEIGHT - (P_HEIGHT + (Tile.HEIGHT / 2))) /
    // 2)),
    // (int) ((GameCore.player.life / GameCore.player.maxLife) * P_WIDTH),
    // P_HEIGHT);
    // }

    public void uiLifeBar(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(8, 4, UI_WIDTH, UI_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(8, 4, (int) ((GameCore.player.life / GameCore.player.maxLife) * UI_WIDTH), UI_HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString((int) GameCore.player.life + " / " + (int) GameCore.player.maxLife, 9, 12);
    }

    public void uiAmmo(Graphics g) {
        g.setColor(Color.white);
        String text = "Ammo: " + GameCore.player.getPowerAmmo();
        Font font = Fonts.getFontFreak(20);
        g.setFont(font);
        int widthStr = (int) F.getRectangle2DFont(text, g, font.getSize(), font.getStyle()).getWidth();
        g.drawString(text, (Main.game.getWidth() - (widthStr + 80)), 20);
    }

    public void uiStamina(Graphics g) {
        g.setColor(Color.white);
        String text = "Stamina: " + GameCore.player.getStamina();
        Font font = Fonts.getFontFreak(20);
        g.setFont(font);
        int widthStr = (int) F.getRectangle2DFont(text, g, font.getSize(), font.getStyle()).getWidth();
        g.drawString(text, (Main.game.getWidth() - (widthStr + 100)), 40);
    }

    public void uiInfo(Graphics g) {
        String text = "Press -T- to Save the Game";
        F.drawCenteredFont(g, text, Fonts.getFontRubbb(20), Color.white, 0, 0, F.DRAW_CENTERED_FONT_IGNORE_XY,
                Main.game.getHeight() - 20);

    }

    public void miniMapDraw(Graphics g, BufferedImage minimapImage) {
        g.drawImage(minimapImage, (Game.WIDTH * Game.SCALE) - ((int) ((World.WIDTH * Game.SCALE) * 1.5)), 60,
                World.HEIGHT * 5, World.WIDTH * 5, null);
    }

    public void renderCustom(Graphics g) {
        uiAmmo(g);
        uiStamina(g);
        uiInfo(g);
    }

    public void renderAll(Graphics g) {
        // playerLifeBar(g);
        uiLifeBar(g);
        uiAmmo(g);
        uiStamina(g);
        uiInfo(g);
    }
}
