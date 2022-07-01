package junit5;

import com.exceptions.LeafAddChildException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elementStructure.Element;
import elementStructure.MaterialSuperclass;
import elementStructure.iterator.NullIterator;
import elementStructure.material.Reading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MaterialSuperclassTest {

    MaterialSuperclass material1;
    MaterialSuperclass material2;
    MaterialSuperclass material3;
    Reading reading1;
    Reading reading2;
    Reading reading3;

    @BeforeEach
    void setUp() {
        reading1 = new Reading();
        reading2 = new Reading();
        reading3 = new Reading();

        material1 = new Reading();
        material2 = new Reading();
        material3 = new Reading();

        reading1.setProgress(30);
        reading2.setTitle("EEE");
        reading3.setDescription("HELELELELEL");
    }

    @Test
    void complete() {
        reading1.complete();

        assertTrue(reading1.isComplete());
    }

    @Test
    void createIterator() {
        Iterator<Element> elementIterator = reading1.createIterator();
        assertEquals(elementIterator.getClass(), NullIterator.class);
    }

    @Test
    void testGetTitle() {
        assertEquals("EEE",reading2.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("HELELELELEL", reading3.getDescription());
    }

    @Test
    void isLeaf() {
        assertTrue(reading1.isLeaf());
    }

    @Test
    void addChildren() {
        try {
            reading1.addChildren(reading2);
            fail("should not be able to add a child to a leaf");
        } catch (LeafAddChildException e) {
            //pass
        }
    }

    @Test
    void unComplete() {
        reading1.complete();
        assertEquals(100,reading1.getProgress());
        assertTrue(reading1.isComplete());

        reading1.unComplete();
        assertEquals(30, reading1.getProgress());
        assertFalse(reading1.isComplete());
    }

    @Test
    void getRemainingExp() {
        assertEquals(reading1.getExp(),reading1.getRemainingExp());
    }

    @Test
    void countChildren() {
        assertEquals(0,reading1.countChildren());
    }

    @Test
    void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(new File("serialisation/materialSerializeTest.json"),reading1);

        Reading reading = mapper.readValue(new File("serialisation/materialSerializeTest.json") ,Reading.class);

        assertEquals(reading.getTitle(),reading1.getTitle());
        assertEquals(reading.getExp(),reading1.getExp());
        assertEquals(reading.getRemainingExp(),reading1.getRemainingExp());
        assertEquals(reading.getProgress(),reading1.getProgress());
        assertEquals(reading.getDescription(),reading1.getDescription());

        mapper.writeValue(new File("serialisation/materialSuperclassSerializeTest.json"),material1);

        MaterialSuperclass material = mapper.readValue(new File("serialisation/materialSuperclassSerializeTest.json"),MaterialSuperclass.class);

        assertEquals(material.getTitle(),material1.getTitle());
        assertEquals(material.getExp(),material1.getExp());
        assertEquals(material.getRemainingExp(),material1.getRemainingExp());
        assertEquals(material.getProgress(),material1.getProgress());
        assertEquals(material.getDescription(),material1.getDescription());
    }
}