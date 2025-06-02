import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;
    @BeforeEach
    void setUp(){
        person = new Person("290$$YFUGE", "BOB", "JAMES", "09-10-2030", "25|Sneydes Road|Melbourne|Victoria|Australia");

    }

    @Test
    void addPerson() {
    }

    @Test
    void updatePersonalDetails() {

        // Add assertions here if the method has effects you can verify
        assertTrue(person.updatePersonalDetails(null, null, null, null, null));
    }

    @Test
    void addDemeritPoints() {
    }
}