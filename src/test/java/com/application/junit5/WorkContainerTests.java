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
        addChildren();

        workContainer2.complete();

        try {
            assertEquals(0.5, workContainer1.calculatePercentComplete());
        } catch (Exception e) {
            fail("Should not fail (non-zero denominator)");
        }
    }

    @Test
    void testCalculateProgress_Exception() {
        workContainer1.setManualProgressCalculation(true);
        try {
            workContainer1.calculatePercentComplete();
            fail("zero denominator should have failed");
        } catch (Exception e) {
            //pass
        }
    }

    @Test
    void testCalculateProgress_Zero() {
        addChildren();

        try {
            assertEquals(0, workContainer1.calculatePercentComplete());
        } catch (Exception e) {
            fail("Zero percent complete");
        }
    }

    @Test
    void testWorkItemSearch_NoFail() {
        addChildren();

        try {
            assertEquals(workContainer3,workContainer1.searchWorkItem("mine"));
        } catch (Exception e) {
            fail("should search a valid name, should not fail");
        }
    }

    @Test
    void testSearchWorkItem_Fail() {
        addChildren();

        try {
            workContainer1.searchWorkItem("asohdoi");
            fail("Invalid name should have thrown an error");
        } catch (Exception e) {
            //pass
        }
    }

    @Test
    void testAddParent_ParentIsNull() {
        assertNull(workContainer2.getParent());
        assertEquals(0, workContainer1.getChildren().size());

        workContainer2.addParent(workContainer1);

        assertTrue(workContainer1.getChildren().contains(workContainer2));
        assertEquals(workContainer1,workContainer2.getParent());
    }

    @Test
    void testAddParent_ParentIsSame() {
        workContainer2.addParent(workContainer1);

        assertTrue(workContainer1.getChildren().contains(workContainer2));
        assertEquals(workContainer1,workContainer2.getParent());

        workContainer2.addParent(workContainer1);

        assertTrue(workContainer1.getChildren().contains(workContainer2));
        assertEquals(workContainer1,workContainer2.getParent());
    }

    @Test
    void testAddParent_ParentIsOther() {
        workContainer2.addParent(workContainer1);

        assertTrue(workContainer1.getChildren().contains(workContainer2));
        assertEquals(workContainer1,workContainer2.getParent());

        workContainer2.addParent(workContainer3);
        assertTrue(workContainer3.getChildren().contains(workContainer2));
        assertFalse(workContainer1.getChildren().contains(workContainer2));
        assertEquals(workContainer3, workContainer2.getParent());
    }

    private void addChildren() {
        workContainer1.addItem(workContainer3);
        workContainer1.addItem(workContainer2);
    }
}
