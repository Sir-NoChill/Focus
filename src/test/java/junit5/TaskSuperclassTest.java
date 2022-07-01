package junit5;

import com.exceptions.LeafAddChildException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elementStructure.Element;
import elementStructure.MaterialSuperclass;
import elementStructure.TaskSuperclass;
import elementStructure.iterator.ElementIterator;
import elementStructure.material.Reading;
import elementStructure.tasks.Project;
import elementStructure.tasks.SuperList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TaskSuperclassTest {
    TaskSuperclass task1;
    TaskSuperclass task2;

    MaterialSuperclass material1;
    MaterialSuperclass material2;

    @BeforeEach
    void setUp() throws LeafAddChildException {
        task1 = new SuperList();
        task2 = new Project();

        material1 = new Reading();
        material2 = new Reading();

        task1.addChildren(task2);
        task2.addChildren(material1);
        task2.addChildren(material2);

        material2.setProgress(30);
        material2.setTime(3);

        material1.setProgress(40);
        material1.setTime(4);

        task1.setExp(30);
        task2.setExp(40);
        material1.setExp(40);
        material2.setExp(70);
    }

    @Test
    void complete() {
        task1.complete();

        assertTrue(material1.isComplete());
        assertTrue(material2.isComplete());
        assertTrue(task1.isComplete());
        assertTrue(task2.isComplete());
    }

    @Test
    void createIterator() {
        Iterator<Element> elementIterator = task2.createIterator();

        assertEquals(elementIterator.getClass(), ElementIterator.class);
    }

    @Test
    void isLeaf() {
        assertFalse(task1.isLeaf());
    }

    @Test
    void addChildren() throws LeafAddChildException {
        TaskSuperclass task3 = new Project();
        task3.addChildren(material1);

        assertTrue(task3.getChildren().contains(material1));
    }

    @Test
    void getChildren() {
        assertTrue(task1.getChildren().contains(task2));
        assertFalse(task1.getChildren().contains(material1));
    }

    @Test
    void testIsComplete() {
        assertFalse(task1.isComplete());
    }

    @Test
    void testGetProgress() {
        assertEquals((30.0+40.0)/2.0, task1.getProgress());
        assertEquals(task1.getProgress(),task2.getProgress());
    }

    @Test
    void unComplete() {
        task1.complete();

        assertTrue(material1.isComplete());
        assertTrue(material2.isComplete());
        assertTrue(task1.isComplete());
        assertTrue(task2.isComplete());
        assertEquals(100,material1.getProgress());
        assertEquals(100,material2.getProgress());
        assertEquals(100,task2.getProgress());

        task1.unComplete();

        assertFalse(material1.isComplete());
        assertFalse(material2.isComplete());
        assertFalse(task2.isComplete());
        assertEquals(30, material2.getProgress());
        assertEquals(40, material1.getProgress());
    }

    @Test
    void getRemainingExp() {
        assertEquals(180,task1.getRemainingExp());
        assertEquals(30,task1.getExp());
    }

    @Test
    void countChildren() {
        assertEquals(3, task1.countChildren());
    }

    @Test
    void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(new File("serialisation/taskSerializeTest.json"),task1);

        TaskSuperclass task = mapper.readValue(new File("serialisation/taskSerializeTest.json") ,TaskSuperclass.class);

        assertEquals(task.getTitle(),task1.getTitle());
        assertEquals(task.getExp(),task1.getExp());
        assertEquals(task.getRemainingExp(),task1.getRemainingExp());
        assertEquals(task.getProgress(),task1.getProgress());
        assertEquals(task.getDescription(),task1.getDescription());
    }
}