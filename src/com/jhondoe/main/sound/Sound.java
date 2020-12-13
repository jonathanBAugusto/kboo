package com.jhondoe.main.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private String filePath = "/sound";
    private Thread t;

    private Clip clip;
    private AudioInputStream inputStream;

    public static final Sound background = new Sound("/star-way-low.wav");
    public static final Sound fireBullet = new Sound("/fire-bullet.wav");
    public static final Sound hitBullet = new Sound("/hit-bullet.wav");
    public static final Sound menuItem = new Sound("/menu-item.wav");
    public static final Sound menuEnter = new Sound("/menu-enter.wav");
    public static final Sound pause = new Sound("/pause.wav");
    public static final Sound hurt = new Sound("/hurt.wav");
    public static final Sound powerUp = new Sound("/power-up.wav");

    private AudioInputStream getAudioInputStream() {
        try {
            return AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(filePath));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Sound(String file) {
        this.filePath += file;
        try {
            clip = AudioSystem.getClip(null);
            inputStream = getAudioInputStream();
            clip.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        t = new Thread() {
            public void run() {
                clip.setFramePosition(0);
                clip.start();
            }
        };
        t.start();
        while (t.isAlive()) {
            Thread.yield();
        }
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        new Thread() {
            public void run() {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }.start();
    }

    public void stop() {
        clip.stop();
    }
}
