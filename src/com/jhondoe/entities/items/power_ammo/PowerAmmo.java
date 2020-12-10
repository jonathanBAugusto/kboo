package com.jhondoe.entities.items.power_ammo;

import java.awt.image.BufferedImage;

import com.jhondoe.common.F;
import com.jhondoe.entities.Entity;
import com.jhondoe.entities.items.Items;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class PowerAmmo extends Items {
    public static final int amountPowerAmmo = 300;

    public PowerAmmo(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        initSprites();
    }

    public void initSprites() {
        animationSprite = new BufferedImage[maxIndex + 1];

        for (int i = 0; i < animationSprite.length; i++) {
            animationSprite[i] = Game.spritesheet.getSprite(0 + (i * Tile.WIDTH), (5 * Tile.HEIGHT), Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    public static void checkCollision(Entity entity) {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity ent = Game.entities.get(i);
            if (ent instanceof PowerAmmo) {
                if (F.isColliding(entity, ent)) {
                    Game.player.addPowerAmmo(amountPowerAmmo);
                    Game.entities.remove(ent);
                    return;
                }
            }
        }
    }
}
