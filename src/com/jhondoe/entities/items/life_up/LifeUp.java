package com.jhondoe.entities.items.life_up;

import java.awt.image.BufferedImage;

import com.jhondoe.common.F;
import com.jhondoe.entities.Entity;
import com.jhondoe.entities.items.Items;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.sound.Sound;
import com.jhondoe.tiles.Tile;

public class LifeUp extends Items {
    public static final int amountLifeUp = 25;

    public LifeUp(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        initSprites();
    }

    public void initSprites() {
        animationSprite = new BufferedImage[maxIndex + 1];

        for (int i = 0; i < animationSprite.length; i++) {
            animationSprite[i] = Game.spritesheet.getSprite(0 + (i * Tile.WIDTH), (3 * Tile.HEIGHT), Tile.WIDTH,
                    Tile.HEIGHT);
        }
    }

    public static void checkCollision(Entity entity) {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity ent = Game.entities.get(i);
            if (ent instanceof LifeUp) {
                if (F.isColliding(entity, ent)) {
                    Sound.powerUp.play();
                    Game.player.maxLife += amountLifeUp;
                    Game.player.life += amountLifeUp;
                    Game.entities.remove(ent);
                    return;
                }
            }
        }
    }

}
