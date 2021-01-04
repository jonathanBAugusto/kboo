package com.jhondoe.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {

    public static InputStream getStream712() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/712.ttf");
    }

    public static InputStream getStreamFreak() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/freaksofnature.ttf");
    }

    public static InputStream getStreamRubbb() {
        return ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/rubbb.ttf");
    }

    public static Font getFont712(int fontSize) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getStream712()).deriveFont((float) fontSize);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font getFontFreak(int fontSize) {
        try {
            Font newF = Font.createFont(Font.TRUETYPE_FONT, getStreamFreak()).deriveFont((float) fontSize);
            return newF;

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Font getFontRubbb(int fontSize) {
        try {
            Font newF = Font.createFont(Font.TRUETYPE_FONT, getStreamRubbb()).deriveFont((float) fontSize);
            // fontStreamRubbb.close();
            return newF;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
