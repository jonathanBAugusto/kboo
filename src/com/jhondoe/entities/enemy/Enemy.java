package com.jhondoe.entities.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.jhondoe.common.F;
import com.jhondoe.enums.Dir;
import com.jhondoe.main.game.Game;
import com.jhondoe.main.game.GameCore;
import com.jhondoe.main.logic.AStar;
import com.jhondoe.main.logic.Vector2i;
import com.jhondoe.main.sound.Sound;
import com.jhondoe.tiles.Tile;
import com.jhondoe.world.World;

public class Enemy extends EnemySprites {

    public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        initSprites();
    }

    public void animate(Graphics g) {
        switch (last_dir) {
        case LEFT:
            if (moved) {
                g.drawImage(leftRun[index], (int) getXCamera(), (int) getYCamera(), null);
            } else {
                g.drawImage(leftStoppedRun[stoppedIndex], (int) getXCamera(), (int) getYCamera(), null);
            }
            break;
        case UP:
            if (moved) {
                g.drawImage(upRun[index], (int) getXCamera(), (int) getYCamera(), null);
            } else {
                g.drawImage(upStoppedRun[stoppedIndex], (int) getXCamera(), (int) getYCamera(), null);
            }
            break;
        case DOWN:
            if (moved) {
                g.drawImage(downRun[index], (int) getXCamera(), (int) getYCamera(), null);
            } else {
                g.drawImage(downStoppedRun[stoppedIndex], (int) getXCamera(), (int) getYCamera(), null);
            }
            break;
        case RIGHT:
        default:
            if (moved) {
                g.drawImage(rightRun[index], (int) getXCamera(), (int) getYCamera(), null);
            } else {
                g.drawImage(rightStoppedRun[stoppedIndex], (int) getXCamera(), (int) getYCamera(), null);
            }
            break;
        }

        if (isDamaged()) {
            g.drawImage(ENEMY_FEEDBACK, (int) getXCamera(), (int) getYCamera(), null);
        }
    }

    /*
     * Old IA Enemy { moved = false; frames++; hitFrame++;
     *
     * if (calculeDistance(this.getX(), this.getY(), GameCore.player.getX(),
     * GameCore.player.getY()) < 100) { if (!F.isColliding(this, GameCore.player,
     * true)) { if (x < GameCore.player.getX() && World.isFree((int) (x + speed),
     * (int) y, false) && !isCollidingEnemy((int) (x + speed), (int) y)) { moved =
     * true; last_dir = Dir.RIGHT; x += speed; } if (x > GameCore.player.getX() &&
     * World.isFree((int) (x - speed), (int) y, false) && !isCollidingEnemy((int) (x
     * - speed), (int) y)) { moved = true; last_dir = Dir.LEFT; x -= speed; } if (y
     * < GameCore.player.getY() && World.isFree((int) x, (int) (y + speed), false)
     * && !isCollidingEnemy((int) x, (int) (y + speed))) { moved = true; last_dir =
     * Dir.DOWN; y += speed; } if (y > GameCore.player.getY() && World.isFree((int)
     * x, (int) (y - speed), false) && !isCollidingEnemy((int) x, (int) (y -
     * speed))) { moved = true; last_dir = Dir.UP; y -= speed; } } else { if
     * (hitFrame >= MAXHITFRAMES) { hitFrame = 0; if (GameCore.rand.nextInt(100) >
     * 20) { int damage = GameCore.rand.nextInt(maxHitDamage) + 1; if
     * (GameCore.player.getStamina() > 0) { if (GameCore.player.getStamina() >=
     * damage) { int newDamage = GameCore.rand.nextInt(damage) + 1;
     * GameCore.player.subStamina(newDamage); damage -= newDamage; } else { int
     * newStamina = GameCore.rand.nextInt(GameCore.player.getStamina()); int diff =
     * damage - newStamina; GameCore.player.subStamina(newStamina); damage -= diff;
     * } } if ((GameCore.player.life - damage) <= 0) { GameCore.player.life = 0; }
     * else { GameCore.player.life -= damage; } Sound.hurt.play();
     * GameCore.player.setDamaged(true); } } } } }
     */

    public void update() {
        setDepth(1);
        moved = false;
        frames++;
        hitFrame++;

        if (calculeDistance(this.getX(), this.getY(), GameCore.player.getX(), GameCore.player.getY()) < 80) {
            if (path == null || path.size() == 0) {
                Vector2i start = new Vector2i((int) (x / Tile.WIDTH), (int) (y / Tile.HEIGHT));
                Vector2i end = new Vector2i((int) (Game.player.getX() / Tile.WIDTH),
                        (int) (Game.player.getY() / Tile.HEIGHT));
                path = AStar.findPath(Game.world, start, end);
            }

            if (new Random().nextInt(100) > 5) {
                Vector2i start = new Vector2i((int) (x / Tile.WIDTH), (int) (y / Tile.HEIGHT));
                Vector2i end = new Vector2i((int) (Game.player.getX() / Tile.WIDTH),
                        (int) (Game.player.getY() / Tile.HEIGHT));
                path = AStar.findPath(Game.world, start, end);
            }

            followPath(path, speed);

            if (!F.isColliding(this, GameCore.player, true)) {
                if (x < GameCore.player.getX() && World.isFree((int) (x + speed), (int) y, false)
                        && !isCollidingEnemy((int) (x + speed), (int) y)) {
                    moved = true;
                    last_dir = Dir.RIGHT;
                }
                if (x > GameCore.player.getX() && World.isFree((int) (x - speed), (int) y, false)
                        && !isCollidingEnemy((int) (x - speed), (int) y)) {
                    moved = true;
                    last_dir = Dir.LEFT;
                }
                if (y < GameCore.player.getY() && World.isFree((int) x, (int) (y + speed), false)
                        && !isCollidingEnemy((int) x, (int) (y + speed))) {
                    moved = true;
                    last_dir = Dir.DOWN;
                }
                if (y > GameCore.player.getY() && World.isFree((int) x, (int) (y - speed), false)
                        && !isCollidingEnemy((int) x, (int) (y - speed))) {
                    moved = true;
                    last_dir = Dir.UP;
                }
            } else {
                if (hitFrame >= MAXHITFRAMES) {
                    hitFrame = 0;
                    if (GameCore.rand.nextInt(100) > 20) {
                        int damage = GameCore.rand.nextInt(maxHitDamage) + 1;
                        if (GameCore.player.getStamina() > 0) {
                            if (GameCore.player.getStamina() >= damage) {
                                int newDamage = GameCore.rand.nextInt(damage) + 1;
                                GameCore.player.subStamina(newDamage);
                                damage -= newDamage;
                            } else {
                                int newStamina = GameCore.rand.nextInt(GameCore.player.getStamina());
                                int diff = damage - newStamina;
                                GameCore.player.subStamina(newStamina);
                                damage -= diff;
                            }
                        }
                        if ((GameCore.player.life - damage) <= 0) {
                            GameCore.player.life = 0;
                        } else {
                            GameCore.player.life -= damage;
                        }
                        Sound.hurt.play();
                        GameCore.player.setDamaged(true);
                    }
                }
            }
        }

        if (frames == maxFrames) {
            frames = 0;
            if (moved) {
                index++;
                stoppedIndex = 0;
            } else {
                stoppedIndex++;
                index = 0;
            }
            if (index > maxIndex) {
                index = 0;
            }
            if (stoppedIndex > stoppedMaxIndex) {
                stoppedIndex = 0;
            }
        }

        if (isDamaged()) {
            damageFrame++;
            if (damageFrame >= damageMaxFrame) {
                damageFrame = 0;
                setDamaged(false);
            }
        }

        if (life <= 0) {
            selfDestruct();
            return;
        }
    }

    private void selfDestruct() {
        GameCore.entities.remove(this);
        GameCore.enemies.remove(this);
    }

    public void render(Graphics g) {
        animate(g);
    }
}
