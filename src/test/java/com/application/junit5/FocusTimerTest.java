package com.application.junit5;

import com.focus.focusTimer.FocusTimer;
import com.focus.focusTimer.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.focus.focusTimer.FocusTimer.DEFAULT_FOCUS_TIMER_LENGTH;
import static org.junit.jupiter.api.Assertions.*;

public class FocusTimerTest {
    public FocusTimer focusTimer;

    @BeforeEach
    public void setup() {
        focusTimer = new FocusTimer();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, focusTimer.getTimers().size());
        assertEquals(0,focusTimer.getFocusList().size());
        assertEquals(0,focusTimer.getTimeFocused());
        assertEquals(2, focusTimer.getFocusExpMod());
        assertEquals(0, focusTimer.getFocusTimerSessionsUsed());
        assertEquals(DEFAULT_FOCUS_TIMER_LENGTH, focusTimer.getFocusTimerTotalLength());
        assertNull(focusTimer.getCurrentTimer());
        assertFalse(focusTimer.isRunning());
    }

    @Test
    public void testGenerateTimers_NotTimeStrict() {
        focusTimer.setFocusTimerBreakLength(1000);
        focusTimer.setFocusTimerTotalLength(10000);
        focusTimer.setIdealFocusTimerFocusLength(5000);
        focusTimer.generateTimers_NotTimeStrict();
        assertEquals(3,focusTimer.getTimers().size());
        long timeTotal = 0;
        for (Timer timer : focusTimer.getTimers()) {
            timeTotal = timeTotal + timer.getTimerLength();
        }
        assertEquals(11000,timeTotal);
    }

    @Test
    public void testGenerateTimers_TimeStrict() {
        focusTimer.setFocusTimerBreakLength(1000);
        focusTimer.setFocusTimerTotalLength(10000);
        focusTimer.setIdealFocusTimerFocusLength(5000);
        focusTimer.generateTimers_TimeStrict();
        assertEquals(3,focusTimer.getTimers().size());
        long timeTotal = 0;
        for (Timer timer : focusTimer.getTimers()) {
            timeTotal = timeTotal + timer.getTimerLength();
        }
        assertEquals(10000,timeTotal);

        long focusTimeTotal = 0;
        for (Timer timer : focusTimer.getTimers()) {
            if (timer.getId().equals(Timer.TimerId.FOCUS)) {
                focusTimeTotal = focusTimeTotal + timer.getTimerLength();
            }
        }

        assertEquals(9000,focusTimeTotal);
    }

    @Test
    public void testStartFocusTimer_Delta20ms_runThrough() {
        long delta = 20;


        focusTimer.setFocusTimerBreakLength(100);
        focusTimer.setFocusTimerTotalLength(1000);
        focusTimer.setIdealFocusTimerFocusLength(500);
        focusTimer.generateTimers_NotTimeStrict();

        focusTimer.startFocusTimer();
        while (System.currentTimeMillis() < focusTimer.getCurrentTimer().getStartTime() + 1200) {
            //wait for 1.2 seconds
        }

        assertEquals(1000, focusTimer.getTimeFocused(),20);
        assertFalse(focusTimer.isRunning());
    }

    //This is a UI method
//    @Test
//    public void testStopFocusTimer_Delta20ms() {
//        focusTimer.setFocusTimerBreakLength(1000);
//        focusTimer.setFocusTimerTotalLength(10000);
//        focusTimer.setIdealFocusTimerFocusLength(5000);
//        focusTimer.generateTimers_NotTimeStrict();
//
//        focusTimer.startFocusTimer();
////        while (System.currentTimeMillis() < focusTimer.getCurrentTimer().getStartTime() + 500) {
////            //wait for 0.6 seconds
////        }
//
//        focusTimer.stopFocusTimer();
//
//        assertEquals(500, focusTimer.getTimeFocused(),20);
//        assertFalse(focusTimer.isRunning());
//
//    }
}
