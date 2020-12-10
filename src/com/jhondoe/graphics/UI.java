package com.jhondoe.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class UI {
    // private static final int P_HEIGHT = 2, P_WIDTH = 20;
    private static final int UI_HEIGHT = 10, UI_WIDTH = 60;

    // private void playerLifeBar(Graphics g) {
    // g.setColor(Color.red);
    // g.fillRect((int) ((Game.WIDTH / 2) - (P_WIDTH - (Tile.WIDTH + 2))),
    // (int) ((Game.HEIGHT / 2) - ((Tile.HEIGHT - (P_HEIGHT + (Tile.HEIGHT / 2))) /
    // 2)), P_WIDTH, P_HEIGHT);
    // g.setColor(Color.green);
    // g.fillRect((int) ((Game.WIDTH / 2) - (P_WIDTH - (Tile.WIDTH + 2))),
    // (int) ((Game.HEIGHT / 2) - ((Tile.HEIGHT - (P_HEIGHT + (Tile.HEIGHT / 2))) /
    // 2)),
    // (int) ((Game.player.life / Game.player.maxLife) * P_WIDTH), P_HEIGHT);
    // }

    private void uiLifeBar(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(8, 4, UI_WIDTH, UI_HEIGHT);
        g.setColor(Color.green);
        g.fillRect(8, 4, (int) ((Game.player.life / Game.player.maxLife) * UI_WIDTH), UI_HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString((int) Game.player.life + " / " + (int) Game.player.maxLife, 9, 12);
    }

    private void uiAmmo(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, Tile.HEIGHT / 2));
        String text = "Ammo: " + Game.player.getPowerAmmo();
        g.drawString(text, (Game.WIDTH - (text.length() * 5)), 10);
    }

    private void uiStamina(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, Tile.HEIGHT / 2));
        String text = "Stamina: " + Game.player.getStamina();
        g.drawString(text, (Game.WIDTH - ((text.length() * 4) + 1)), 20);
    }

    public void render(Graphics g) {
        // playerLifeBar(g);
        uiLifeBar(g);
        uiAmmo(g);
        uiStamina(g);
    }
}
