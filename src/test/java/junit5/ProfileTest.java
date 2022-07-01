package junit5;

import com.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileTest {
    Profile profile;

    @BeforeEach
    void setUp() {
        profile = Profile.getTestState();
    }

    @Test
    void addSuperList() {
        profile.addSuperList("Title");
        assertEquals(2,profile.getElements().size());
    }

    @Test
    void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("serialisation/profileSerializeTest.json"),profile);

        Profile profile1 = mapper.readValue(new File("serialisation/profileSerializeTest.json") ,Profile.class);

        assertEquals(profile1.getElements().size(),profile.getElements().size());
        assertEquals(profile1.getProfileExp(),profile.getProfileExp());
    }
}