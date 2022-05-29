package com.focus.focusTimer;

import com.application.focus.console.ConsoleMain;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import static jdk.internal.loader.BootLoader.findResource;

//The class responsible for playing the sound when you finish a timer
public class TimerNotifier {
    private static String fileName;
    private static Clip soundFile; //mixkit-rooster-crowing-in-the-morning-2462.wav
    private static String filePath; // "//Focus/src/main/resources/sound";

    public TimerNotifier() {
        this.fileName = "mixkit-rooster-crowing-in-the-morning-2462.wav";
        this.filePath = "//Focus/src/main/resources/sound";
    }

    public static synchronized void playSound() {
        new Runnable() {
            public void run() {
                try {
                    soundFile = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            ConsoleMain.class.getResourceAsStream(
                                    String.valueOf(findResource(filePath,fileName))));
                    soundFile.open(inputStream);
                    soundFile.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        TimerNotifier.fileName = fileName;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        TimerNotifier.filePath = filePath;
    }
}
