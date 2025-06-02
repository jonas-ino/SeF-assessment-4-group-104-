import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;
    @BeforeEach
    void setUp(){
        person = new Person("390$$YFUGE", "BOB", "JAMES", "09-10-1983", "25|Sneydes Road|Melbourne|Victoria|Australia");

    }

    @AfterEach
    void tearDown() throws IOException {
        person = null;
        System.out.println("UNIT TEST COMPLETE. REMOVING person.txt\n");
        Files.deleteIfExists(Paths.get("people.txt"));
    }

    // ADD PERSON
    // TEST CASE 1:
    @Test
    void addPerson() {
    }
    // TEST CASE 2:
    // TEST CASE 3:

    // UPDATE USER DETAILS
    // TEST CASE 1: Update Person with valid inputs
    @Test
    void updateAllNull() {
        // Add assertions here if the method has effects you can verify
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, null, null));
        if (result) {
            System.out.println("TEST SUCCESS: " + "Update with all null values");
        }
        assertTrue(result);
    }
    @Test
    void updateBirthDate() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, "01-02-2000", null));
        if (result) {
            System.out.println("TEST SUCCESS: " + "Update birth date with all null values");
        }
        assertTrue(result);
    }
    @Test
    void updateAllValues() {
        // Birth Date not included
        person.addPerson();
        boolean result = (person.updatePersonalDetails("3333!!33AA", "GREGORY", "CAINE", null, "10|Main Street|Melbourne|Victoria|Australia"));
        if (result) {
            System.out.println("TEST SUCCESS: " + "Update all values except birth date");
        }
        assertTrue(result);
    }
    @Test
    void updateAddress() {
        // Given person is older than 18
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, null, "10|Main Street|Melbourne|Victoria|Australia"));
        if (result) {
            System.out.println("TEST SUCCESS: " + "Update address when user is over 18");
        }
        assertTrue(result);
    }

    // TEST CASE 1.5: Update Person with no matching ID in user.txt
    @Test
    void updateWithNoRecord() {
        // add Person so that people.txt exists
        person.addPerson();
        Person dummy = new Person("1111$$11AA", "TEST", "SUBJECT", "10-10-1910", "10|Place Street|Melbourne|Victoria|Australia");
        boolean result = (dummy.updatePersonalDetails(null, "NORECORD", null, null, null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update user when no matching record can be found");
        }
        assertFalse(result);
    }

    //TEST CASE 2: Update Person with invalid inputs
    @Test
    void updateInvalidID() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails("1111111111", null, null, null, null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update with an invalid ID");
        }
        assertFalse(result);
    }
    @Test
    void updateInvalidAddress() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, null, "13 Forrest Street, Melbourne, Victoria, Australia"));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update with an invalid address");
        }
        assertFalse(result);
    }
    @Test
    void updateInvalidBirthDate() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, "01.02.1953", null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update with an invalid birth date");
        }
        assertFalse(result);
    }

    // TEST CASE 3: Update birthDate and other details simultaneously
    @Test
    void updateBDandID() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails("3456!!78AC", null, null, "01-01-2000", null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update birth date and ID simultaneously");
        }
        assertFalse(result);
    }
    @Test
    void updateBDandFName() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, "MARK", null, "01-01-2000", null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update birth date and first name simultaneously");
        }
        assertFalse(result);
    }
    @Test
    void updateBDandLName() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, "FARRUGIA", "01-01-2000", null) );
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update birth date and last name simultaneously");
        }
        assertFalse(result);
    }
    @Test
    void updateBDandAddress() {
        person.addPerson();
        boolean result = (person.updatePersonalDetails(null, null, null, "01-01-2000", "32|Blackwood Avenue|Melbourne|Victoria|Australia"));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update birth date and address simultaneously");
        }
        assertFalse(result);
    }

    // TEST CASE 4: Update address when Person's age < 18
    @Test
    void updateAddress1() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2008", "10|Forest Road|Sunshine North|Victoria|Australia");
        youngPerson.addPerson();
        boolean result = (youngPerson.updatePersonalDetails(null, null, null, null, "73|Garbage Street|Glenferrie|Victoria|Australia "));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update address when user is below 18 (1)");
        }
        assertFalse(result);
    }
    @Test
    void updateAddress2() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2008", "10|Forest Road|Sunshine North|Victoria|Australia");
        youngPerson.addPerson();
        boolean result = (youngPerson.updatePersonalDetails(null, null, null, null, "24|Hainthorpe Grove|Mulgrave|Victoria|Australia "));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update address when user is below 18 (2)");
        }
        assertFalse(result);
    }
    @Test
    void updateAddress3() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2008", "10|Forest Road|Sunshine North|Victoria|Australia");
        youngPerson.addPerson();
        boolean result = (youngPerson.updatePersonalDetails(null, null, null, null, "19|Eisner Street|St Albans|Victoria|Australia"));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update address when user is below 18 (3)");
        }
        assertFalse(result);
    }


    // TEST CASE 5: Update personID when personID starts with an even number
    @Test
    void updateID1() {
        Person evenPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2000", "10|Forest Road|Sunshine North|Victoria|Australia");
        evenPerson.addPerson();
        boolean result = (evenPerson.updatePersonalDetails("332$53#2AB", null, null, null, null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update ID when existing ID begins with an even number (1)");
        }
        assertFalse(result);
    }
    @Test
    void updateID2() {
        Person evenPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2000", "10|Forest Road|Sunshine North|Victoria|Australia");
        evenPerson.addPerson();
        boolean result = (evenPerson.updatePersonalDetails("78^*7PEPSI", null, null, null, null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update ID when existing ID begins with an even number (2)");
        }
        assertFalse(result);
    }
    @Test
    void updateID3() {
        Person evenPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2000", "10|Forest Road|Sunshine North|Victoria|Australia");
        evenPerson.addPerson();
        boolean result = (evenPerson.updatePersonalDetails("66386!%POJ", null, null, null, null));
        if (!result) {
            System.out.println("TEST SUCCESS: " + "Update ID when existing ID begins with an even number (3)");
        }
        assertFalse(result);
    }



    // ADD DEMERIT POINTS
    // TEST CASE 1:
    @Test
    void addDemeritPoints() {
    }
    // TEST CASE 2:
}