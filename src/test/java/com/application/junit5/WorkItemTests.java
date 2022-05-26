package com.application.junit5;

import com.focus.userModel.lists.WorkItem;
import com.focus.userModel.lists.materials.Action;
import com.focus.userModel.lists.workContainers.Epic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkItemTests {
    public WorkItem workItem1;
    public WorkItem workItem2;

    @BeforeEach
    void setup() {
        workItem1 = new Epic("test",100);
        workItem2 = new Action("testing",100,0.5);
    }

    @Test
    void testConstructor() {
        assertEquals(workItem1.getTitle(),"test");
        assertFalse(workItem1.isComplete());
        assertEquals(workItem1.getExpValue(),100);
        assertNull(workItem1.getParent());
        assertEquals(workItem1.getProgress(),0);

        assertEquals(workItem2.getTitle(),"testing");
        assertFalse(workItem2.isComplete());
        assertEquals(workItem2.getExpValue(),100);
        assertNull(workItem2.getParent());
        assertEquals(workItem2.getProgress(),0.5);
    }
}
