package com.application.junit5;

import com.focus.userModel.lists.WorkContainer;
import com.focus.userModel.lists.workContainers.List;
import com.focus.userModel.lists.workContainers.Project;
import com.focus.userModel.lists.workContainers.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.focus.ExpCounter.getExpCounterInstance;
import static org.junit.jupiter.api.Assertions.*;

public class WorkContainerTests {
    public WorkContainer workContainer1;
    public WorkContainer workContainer3;
    public WorkContainer workContainer2;

    @BeforeEach
    void setup() {
        workContainer1 = new List("thing",200);
        workContainer3 = new Project("mine",300);
        workContainer2 = new Task("yum",20);

        getExpCounterInstance().resetExpCounter();
    }

    @Test
    void testVerboseConstructor() {
        //WorkContainer workContainer4;
    }

    @Test
    void testShorterConstructor() {
        //WorkContainer workContainer4;
    }

    @Test
    void testCompleteSingle() {
        assertEquals(getExpCounterInstance().getExpCount(),0);
        assertFalse(workContainer1.isComplete());

        workContainer1.complete();

        assertEquals(getExpCounterInstance().getExpCount(), workContainer1.getExpValue());
        assertTrue(workContainer1.isComplete());
    }

    @Test
    void testCompleteDepth() {
        workContainer1.addItem(workContainer2);
        workContainer1.addItem(workContainer3);

        assertEquals(getExpCounterInstance().getExpCount(),0);
        assertFalse(workContainer1.isComplete());

        workContainer1.complete();

        int totalExp = workContainer1.getExpValue() + workContainer2.getExpValue() + workContainer3.getExpValue();

        assertEquals(totalExp, getExpCounterInstance().getExpCount());
        assertTrue(workContainer1.isComplete());
        assertTrue(workContainer2.isComplete());
        assertTrue(workContainer3.isComplete());
    }

    @Test
    void testCalculatePercentComplete_NonZeroDenom() {
        workContainer1.addItem(workContainer3);
        workContainer1.addItem(workContainer2);
        workContainer2.complete();

        try {
            assertEquals(0.5, workContainer1.calculatePercentComplete());
        } catch (Exception e) {
            fail("Should not fail (non-zero denominator)");
        }
    }
}
