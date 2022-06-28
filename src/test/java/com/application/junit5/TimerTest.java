package com.application.junit5;

import focusTimer.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static focusTimer.Timer.TimerId.BREAK;
import static focusTimer.Timer.TimerId.FOCUS;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {
    public Timer timer1;
    public Timer timer2;
    public Timer timer3;

    @BeforeEach
    public void setup() {
        timer1 = new Timer(1000, FOCUS);
        timer2 = new Timer(2000,BREAK);
        timer3 = new Timer(3000,FOCUS);
    }

    @Test
    public void testConstructor() {
        assertEquals(FOCUS,timer1.getId());
        assertEquals(1000,timer1.getTimerLength());
        assertEquals(0,timer1.getStartTime());
        assertEquals(0,timer1.getElapsedTime());
        assertFalse(timer1.isComplete());
        assertFalse(timer1.isRunning());
    }

    @Test
    public void testStartTimer_Delta20ms() {
        timer1.startTimer();
        assertEquals(System.currentTimeMillis(),timer1.getStartTime(),20);
    }

    @Test
    public void testPauseTimer_Delta20ms() {
        timer1.startTimer();
        while (System.currentTimeMillis() < timer1.getStartTime() + 500) {
            //wait for half a second
        }

        timer1.pauseTimer();
        assertEquals(500,timer1.getElapsedTime(),20);
        assertEquals(500,timer1.getTimerLength(),20);
    }

    @Test
    public void testStopTimer() {
        timer1.startTimer();
        while (System.currentTimeMillis() < timer1.getStartTime() + 500) {
            //wait for half a second
        }
        timer1.resetTimer();
        assertEquals(1000,timer1.getTimerLength());
    }

    @Test
    public void testRunTimer() {
        timer1.run();
        while (System.currentTimeMillis() < timer1.getStartTime() + 2000) {
            //wait for two seconds
        }
        assertFalse(timer1.isRunning());
        assertTrue(timer1.getElapsedTime() >= 1000);
    }
}
