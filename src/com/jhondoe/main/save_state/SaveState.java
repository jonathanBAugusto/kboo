package com.jhondoe.main.save_state;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jhondoe.enums.GameState;
import com.jhondoe.main.Main;
import com.jhondoe.main.game.Game;

public class SaveState {

    private static final String pathSaveGame = "./res/saves";
    private static final String fileNameSaveGame = "/save.kbo";

    public static String getFullPathName() {
        return pathSaveGame + fileNameSaveGame;
    }

    private static boolean createDir() {
        File file = new File(pathSaveGame);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(pathSaveGame + fileNameSaveGame);
        if (file.exists()) {
            return true;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void applySave(String str) {
        String[] spl = str.split("/");
        for (int i = 0; i < spl.length; i++) {
            String[] settings = spl[i].split(":");
            switch (settings[0]) {
                case "level":
                    Main.game.setCurrentLevel(Integer.parseInt(settings[1]));
                    Game.menu.setPause(false);

                    Main.game.initEntities();
                    Main.game.setGameState(GameState.PLAY);
                    break;
                case "life":
                    Game.player.setLife(Double.parseDouble(settings[1]));
                    break;
                case "maxLife":
                    Game.player.setMaxLife(Double.parseDouble(settings[1]));
                    break;
                case "stamina":
                    Game.player.setStamina(Integer.parseInt(settings[1]));
                    break;
                case "powerAmmo":
                    Game.player.setPowerAmmo(Integer.parseInt(settings[1]));
                    break;
            }
        }
    }

    public static String loadGame(int encode) {
        String line = "";
        File file = new File(getFullPathName());
        if (file.exists()) {
            try {
                String singleLine = "";
                BufferedReader bfReader = new BufferedReader(new FileReader(getFullPathName()));
                try {
                    while ((singleLine = bfReader.readLine()) != null) {
                        String[] transition = singleLine.split(":");
                        char[] value = transition[1].toCharArray();
                        transition[1] = "";
                        for (int i = 0; i < value.length; i++) {
                            value[i] -= encode;
                            transition[1] += value[i];
                        }
                        line += String.format("%s:%s/", transition[0], transition[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    public static void saveGame(String[] value1, int[] value2, int encode) {
        if (!createDir()) {
            return;
        }
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter(getFullPathName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < value1.length; i++) {
            String current = value1[i] + ":";
            char[] value = Integer.toString(value2[i]).toCharArray();
            for (int j = 0; j < value.length; j++) {
                value[j] += encode;
                current += value[j];
            }
            try {
                write.write(current);
                if (i < value1.length - 1) {
                    write.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            write.flush();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
