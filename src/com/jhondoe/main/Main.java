package com.jhondoe.main;

import com.jhondoe.main.game.Game;

public class Main {
    public static Game game;

    public static void main(String args[]) {
        try {
            game = new Game();
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
