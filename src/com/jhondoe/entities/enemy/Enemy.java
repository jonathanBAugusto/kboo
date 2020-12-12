package com.jhondoe.entities.enemy;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jhondoe.enums.Dir;
import com.jhondoe.main.game.Game;
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

    public void update() {
        moved = false;
        frames++;
        hitFrame++;
        if (!isCollidingPlayer()) {
            if (x < Game.player.getX() && World.isFree((int) (x + speed), (int) y)
                    && !isColliding((int) (x + speed), (int) y)) {
                moved = true;
                last_dir = Dir.RIGHT;
                x += speed;
            }
            if (x > Game.player.getX() && World.isFree((int) (x - speed), (int) y)
                    && !isColliding((int) (x - speed), (int) y)) {
                moved = true;
                last_dir = Dir.LEFT;
                x -= speed;
            }
            if (y < Game.player.getY() && World.isFree((int) x, (int) (y + speed))
                    && !isColliding((int) x, (int) (y + speed))) {
                moved = true;
                last_dir = Dir.DOWN;
                y += speed;
            }
            if (y > Game.player.getY() && World.isFree((int) x, (int) (y - speed))
                    && !isColliding((int) x, (int) (y - speed))) {
                moved = true;
                last_dir = Dir.UP;
                y -= speed;
            }
        } else {
            if (hitFrame >= MAXHITFRAMES) {
                hitFrame = 0;
                if (Game.rand.nextInt(100) > 20) {
                    int damage = Game.rand.nextInt(maxHitDamage) + 1;
                    if (Game.player.getStamina() > 0) {
                        if (Game.player.getStamina() >= damage) {
                            int newDamage = Game.rand.nextInt(damage) + 1;
                            Game.player.subStamina(newDamage);
                            damage -= newDamage;
                        } else {
                            int newStamina = Game.rand.nextInt(Game.player.getStamina());
                            int diff = damage - newStamina;
                            Game.player.subStamina(newStamina);
                            damage -= diff;
                        }
                    }
                    if ((Game.player.life - damage) <= 0) {
                        Game.player.life = 0;
                    } else {
                        Game.player.life -= damage;
                    }
                    Game.player.setDamaged(true);
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

    private boolean isCollidingPlayer() {
        return new Rectangle((int) (getX() + maskX), (int) (getY() + maskY), maskW, maskH)
                .intersects(Game.player.getRectangle());
    }

    private void selfDestruct() {
        Game.entities.remove(this);
        Game.enemies.remove(this);
    }

    public boolean isColliding(int xNext, int yNext) {
        Rectangle current = new Rectangle(xNext + maskX, yNext + maskY, maskW, maskH);

        for (Enemy enemy : Game.enemies) {
            if (enemy == this) {
                continue;
            }

            if (current
                    .intersects(new Rectangle((int) enemy.getX() + maskX, (int) enemy.getY() + maskY, maskW, maskH))) {
                return true;
            }
        }

        return false;
    }

    public void render(Graphics g) {
        animate(g);
    }
}
