package com.jhondoe.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.jhondoe.entities.Entity;
import com.jhondoe.main.Main;

public class F {
        public static final int DRAW_CENTERED_FONT_IGNORE_XY = -999;

        public static boolean isColliding(Entity e1, Entity e2) {
                Rectangle e1Mask = new Rectangle((int) e1.getX() + e1.getMaskX(), (int) e1.getY() + e1.getMaskY(),
                                e1.getMaskW(), e1.getMaskH());
                Rectangle e2Mask = new Rectangle((int) e2.getX() + e2.getMaskX(), (int) e2.getY() + e2.getMaskY(),
                                e2.getMaskW(), e2.getMaskH());

                return e1Mask.intersects(e2Mask);
        }

        public static Point drawCenteredFontBold(Graphics g, String text, int fontSize, Color color, int offSetX,
                        int offSetY) {
                return drawCenteredFont(g, text, fontSize, color, Font.BOLD, offSetX, offSetY,
                                DRAW_CENTERED_FONT_IGNORE_XY, DRAW_CENTERED_FONT_IGNORE_XY);
        }

        public static Point drawCenteredFontBold(Graphics g, String text, int fontSize, Color color, int offSetX,
                        int offSetY, int xPos, int yPos) {
                return drawCenteredFont(g, text, fontSize, color, Font.BOLD, offSetX, offSetY, xPos, yPos);
        }

        public static Point drawCenteredFont(Graphics g, String text, int fontSize, Color color, int fontStyle,
                        int offSetX, int offSetY, int xPos, int yPos) {
                Font f = new Font("arial", fontStyle, fontSize);

                Rectangle2D rec2 = f.getStringBounds(text, ((Graphics2D) g).getFontRenderContext());

                g.setColor(color);
                g.setFont(f);
                int x = xPos == DRAW_CENTERED_FONT_IGNORE_XY
                                ? (int) ((Main.game.getWidth() / 2) - (rec2.getWidth() / 2))
                                : xPos;
                int y = yPos == DRAW_CENTERED_FONT_IGNORE_XY
                                ? (int) ((Main.game.getHeight() / 2) - (rec2.getHeight() / 2))
                                : yPos;

                g.drawString(text, x + offSetX, y + offSetY);
                return new Point(x + offSetX, y + offSetY);
        }

        public static Rectangle2D getRectangle2DFont(String text, Graphics g, int fontSize, int fontStyle) {
                Font f = new Font("arial", fontStyle, fontSize);

                return f.getStringBounds(text, ((Graphics2D) g).getFontRenderContext());
        }
}
