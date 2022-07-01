package junit5;

import com.exceptions.LeafAddChildException;
import elementStructure.Element;
import elementStructure.ElementSuperclass;
import elementStructure.TaskSuperclass;
import elementStructure.material.Reading;
import elementStructure.tasks.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementSuperclassTest {

    ElementSuperclass testElement1;
    ElementSuperclass testElement2;
    Element testElement3;
    ElementSuperclass testElement4;

    @BeforeEach
    void setUp() {
        testElement1 = new Project();
        testElement2 = new Reading();

        testElement3 = new Project();
        testElement4 = new Reading();
    }

    @Test
    void getTitle() {
        testElement1.setTitle("test");
        assertEquals("test",testElement1.getTitle());
    }

    @Test
    void getDescription() {
        testElement1.setDescription("thist");
        assertEquals("thist", testElement1.getDescription());
    }

    //Note that this is overriden for tasks
    @Test
    void getProgress() {
        double progress = 30;
        testElement4.setProgress(progress);
        assertEquals(progress, testElement4.getProgress());
    }

    @Test
    void isComplete() {
        assertFalse(testElement2.isComplete());
    }

    @Test
    void getTime() {
        testElement1.setTime(2);
        assertEquals(2, testElement1.getTime());
    }

    @Test
    void getThisTaskExp() {
        testElement1.setExp(30);
        assertEquals(30, testElement1.getExp());
    }

    @Test
    void setParent() throws LeafAddChildException {
        testElement1.setParent((Project) testElement3);
        assertTrue(((TaskSuperclass) testElement3).getChildren().contains(testElement1));
    }
}