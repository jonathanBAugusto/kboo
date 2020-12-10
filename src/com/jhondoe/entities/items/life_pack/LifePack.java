package com.jhondoe.entities.items.life_pack;

import java.awt.image.BufferedImage;

import com.jhondoe.common.F;
import com.jhondoe.entities.Entity;
import com.jhondoe.entities.items.Items;
import com.jhondoe.main.game.Game;
import com.jhondoe.tiles.Tile;

public class LifePack extends Items {
    public static final int amountLife = 30;

    public LifePack(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        initSprites();
    }

    public void initSprites() {
        animationSprite = new BufferedImage[maxIndex + 1];

        for (int i = 0; i < animationSprite.length; i++) {
            animationSprite[i] = Game.spritesheet.getSprite(0 + (i * Tile.WIDTH), (2 * Tile.HEIGHT), Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    public static void checkCollision(Entity entity) {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity ent = Game.entities.get(i);
            if (ent instanceof LifePack) {
                if (F.isColliding(entity, ent)) {
                    Game.player.life += amountLife;
                    if (Game.player.life >= Game.player.maxLife) {
                        Game.player.life = Game.player.maxLife;
                    }
                    Game.entities.remove(ent);
                    return;
                }
            }
        }
    }

}
