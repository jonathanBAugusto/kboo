package com.jhondoe.main.sound;

import java.io.*;
import java.security.DigestInputStream;

import javax.sound.sampled.*;

public class Sound {
    public static class Clips {
        public Clip[] clips;
        private int p;
        private int count;

        public Clips(byte[] buffer, int count)
                throws LineUnavailableException, IOException, UnsupportedAudioFileException {
            if (buffer == null) {
                return;
            }

            clips = new Clip[count];
            this.count = count;

            for (int i = 0; i < count; i++) {
                clips[i] = AudioSystem.getClip();
                clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
            }
        }

        public void play() {
            if (clips == null || clips.length == 0)
                return;

            clips[p].stop();
            clips[p].setFramePosition(0);
            clips[p].start();
            p++;

            if (p >= count)
                p = 0;
        }

        public void loop() {
            if (clips == null || clips.length == 0)
                return;

            clips[p].loop(Clip.LOOP_CONTINUOUSLY);
        }

        public void stop() {
            if (clips == null || clips.length == 0)
                return;

            clips[p].stop();
        }
    }

    private static Clips load(String name, int count) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));

        byte[] buffer = new byte[1024];
        int read = 0;
        try {
            while ((read = dis.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            dis.close();
            byte[] data = baos.toByteArray();
            return new Clips(data, count);

        } catch (Exception e) {
            try {
                return new Clips(null, 0);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    public static Clips flow = load("/sound/flow.wav", 1);
    public static Clips background = load("/sound/star-way-low.wav", 1);
    public static Clips fireBullet = load("/sound/fire-bullet.wav", 1);
    public static Clips hitBullet = load("/sound/hit-bullet.wav", 1);
    public static Clips menuItem = load("/sound/menu-item.wav", 1);
    public static Clips menuEnter = load("/sound/menu-enter.wav", 1);
    public static Clips pause = load("/sound/pause.wav", 1);
    public static Clips hurt = load("/sound/hurt.wav", 1);
    public static Clips powerUp = load("/sound/power-up.wav", 1);
}
