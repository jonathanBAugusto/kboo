package com.jhondoe.main.logic;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jhondoe.entities.enemy.Enemy;
import com.jhondoe.main.game.GameCore;
import com.jhondoe.tiles.Tile;
import com.jhondoe.tiles.WallTile;
import com.jhondoe.world.World;

public class AStar {
    public static double lastTime = System.currentTimeMillis();
    private static Comparator<Node> nodeSorter = new Comparator<Node>() {
        @Override
        public int compare(Node n0, Node n1) {
            if (n1.fCost < n0.fCost)
                return +1;
            if (n1.fCost > n0.fCost)
                return -1;
            return 0;
        }
    };

    private static boolean vecInList(List<Node> list, Vector2i item) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).tile.equals(item))
                return true;
        }
        return false;
    }

    private static double getDistance(Vector2i tile, Vector2i goal) {
        double xx = tile.x - goal.x;
        double yy = tile.y - goal.y;
        double sqrt = Math.sqrt((xx * xx) + (yy * yy));
        return sqrt;
    }

    private static boolean isCollidingEnemy(int xNext, int yNext) {
        Rectangle current = new Rectangle(xNext + Enemy.maskX, yNext + Enemy.maskY, Enemy.maskW, Enemy.maskH);

        for (Enemy enemy : GameCore.enemies) {
            // if (enemy == this) {
            // continue;
            // }

            if (current.intersects(new Rectangle((int) enemy.getX() + Enemy.maskX, (int) enemy.getY() + Enemy.maskY,
                    Enemy.maskW, Enemy.maskH))) {
                return true;
            }
        }

        return false;
    }

    public static List<Node> findPath(World world, Vector2i start, Vector2i end) {
        lastTime = System.currentTimeMillis();
        List<Node> opennedList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();

        Node current = new Node(start, null, 0, AStar.getDistance(start, end));
        opennedList.add(current);
        while (opennedList.size() > 0) {
            Collections.sort(opennedList, nodeSorter);
            current = opennedList.get(0);
            if (current.tile.equals(end)) {
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }
                opennedList.clear();
                closedList.clear();
                return path;
            }

            opennedList.remove(current);
            closedList.add(current);

            for (int i = 0; i < 9; i++) {
                if (i == 4)
                    continue;
                int x = current.tile.x;
                int y = current.tile.y;
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile tile = World.tiles[x + xi + ((y + yi) * World.WIDTH)];
                if (tile == null)
                    continue;
                if (tile instanceof WallTile)
                    continue;

                if (i == 0) {
                    Tile test = World.tiles[(x + xi + 1) + ((y + yi) * World.WIDTH)];
                    Tile test2 = World.tiles[(x + xi + 1) + ((y + yi) * World.WIDTH)];
                    if ((test == null || test2 == null) || (test instanceof WallTile || test2 instanceof WallTile)) {
                        continue;
                    }
                } else if (i == 2) {
                    Tile test = World.tiles[(x + xi + 1) + ((y + yi) * World.WIDTH)];
                    Tile test2 = World.tiles[(x + xi) + ((y + yi) * World.WIDTH)];
                    if ((test == null || test2 == null) || (test instanceof WallTile || test2 instanceof WallTile)) {
                        continue;
                    }
                } else if (i == 6) {
                    Tile test = World.tiles[(x + xi) + ((y + yi - 1) * World.WIDTH)];
                    Tile test2 = World.tiles[(x + xi + 1) + ((y + yi) * World.WIDTH)];
                    if ((test == null || test2 == null) || (test instanceof WallTile || test2 instanceof WallTile)) {
                        continue;
                    }
                } else if (i == 8) {
                    Tile test = World.tiles[(x + xi) + ((y + yi - 1) * World.WIDTH)];
                    Tile test2 = World.tiles[(x + xi - 1) + ((y + yi) * World.WIDTH)];
                    if ((test == null || test2 == null) || (test instanceof WallTile || test2 instanceof WallTile)) {
                        continue;
                    }
                }

                Vector2i vec = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + getDistance(current.tile, vec);
                double hCost = getDistance(vec, end);
                Node node = new Node(vec, current, gCost, hCost);

                if (vecInList(closedList, vec) && gCost >= current.gCost)
                    continue;

                if (!vecInList(opennedList, vec)) {
                    opennedList.add(node);
                } else if (gCost < current.gCost) {
                    opennedList.remove(current);
                    opennedList.add(node);
                }
            }
        }
        closedList.clear();
        return null;
    }

    public static boolean clear() {
        return (System.currentTimeMillis() - lastTime) >= 1000;
    }
}
