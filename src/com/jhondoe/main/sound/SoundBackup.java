package com.jhondoe.main.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundBackup {
    private String filePath = "/sound";
    private Thread t;

    private Clip clip;
    private AudioInputStream inputStream;

    public static final SoundBackup background = new SoundBackup("/star-way-low.wav");
    public static final SoundBackup fireBullet = new SoundBackup("/fire-bullet.wav");
    public static final SoundBackup hitBullet = new SoundBackup("/hit-bullet.wav");
    public static final SoundBackup menuItem = new SoundBackup("/menu-item.wav");
    public static final SoundBackup menuEnter = new SoundBackup("/menu-enter.wav");
    public static final SoundBackup pause = new SoundBackup("/pause.wav");
    public static final SoundBackup hurt = new SoundBackup("/hurt.wav");
    public static final SoundBackup powerUp = new SoundBackup("/power-up.wav");

    private AudioInputStream getAudioInputStream() {
        try {
            return AudioSystem.getAudioInputStream(SoundBackup.class.getResourceAsStream(filePath));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SoundBackup(String file) {
        // if (true) {
        // return;
        // }
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
                // clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }.start();
    }

    public void stop() {
        clip.stop();
    }
}
