import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;
    @BeforeEach
    void setUp(){
        person = new Person("390$$YFUGE", "BOB", "JAMES", "09-10-1983", "25|Sneydes Road|Melbourne|Victoria|Australia");

    }

    // TODO: Add a @AfterEach which wipes the "people.txt" file and any files that we don't need any longer

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
        assertTrue(person.updatePersonalDetails(null, null, null, null, null));
    }
    @Test
    void updateAllEmpty() {
        assertTrue(person.updatePersonalDetails("", "","", "", ""));
    }
    @Test
    void updateBirthDate() {
        assertTrue(person.updatePersonalDetails(null, null, null, "01-02-2000", null));
    }
    @Test
    void updateAllValues() {
        // Birth Date not included
        assertTrue(person.updatePersonalDetails("3333!!33AA", "GREGORY", "CAINE", null, "10|Main Street|Melbourne|Victoria|Australia"));
    }
    @Test
    void updateAddress() {
        // Given person is older than 18
        assertTrue(person.updatePersonalDetails(null, null, null, null, "10|Main Street|Melbourne|Victoria|Australia"));
    }

    //TEST CASE 2: Update Person with invalid inputs
    @Test
    void updateInvalidID() {
        assertTrue(person.updatePersonalDetails("1111111111", null, null, null, null));
    }
    @Test
    void updateInvalidAddress() {
        assertTrue(person.updatePersonalDetails(null, null, null, null, "13 Forrest Street, Melbourne, Victoria, Australia"));
    }
    @Test
    void updateInvalidBirthDate() {
        assertTrue(person.updatePersonalDetails(null, null, null, "01.02.1953", null));
    }

    // TEST CASE 3: Update birthDate and other details simultaneously
    @Test
    void updateBDandID() {
        assertTrue(person.updatePersonalDetails("3456!!78AC", null, null, "01-01-2000", null));
    }
    @Test
    void updateBDandFName() {
        assertTrue(person.updatePersonalDetails(null, "MARK", null, "01-01-2000", null));
    }
    @Test
    void updateBDandLName() {
        assertTrue(person.updatePersonalDetails(null, null, "FARRUGIA", "01-01-2000", null) );
    }
    @Test
    void updateBDandAddress() {
        assertTrue(person.updatePersonalDetails(null, null, null, "01-01-2000", "32|Blackwood Avenue|Melbourne|Victoria|Australia"));
    }

    // TEST CASE 4: Update address when Person's age < 18
    @Test
    void updateAddress1() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails(null, null, null, null, "73|Garbage Street|Glenferrie|Victoria|Australia "));
    }
    @Test
    void updateAddress2() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails(null, null, null, null, "24|Hainthorpe Grove|Mulgrave|Victoria|Australia "));
    }
    @Test
    void updateAddress3() {
        Person youngPerson = new Person("3456!!78AB", "MARK", "FARRUGIA", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails(null, null, null, null, "19|Eisner Street|St Albans|Victoria|Australia"));
    }


    // TEST CASE 5: Update personID when personID starts with an even number
    @Test
    void updateID1() {
        Person youngPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails("32$53#2AB", null, null, null, null));
    }
    @Test
    void updateID2() {
        Person youngPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails("78^*7PEPSI", null, null, null, null));
    }
    @Test
    void updateID3() {
        Person youngPerson = new Person("2222!!8DAB", "DANH", "TRAN", "01-01-2012", "10|Forest Road|Sunshine North|Victoria|Australia");
        assertTrue(youngPerson.updatePersonalDetails("66386!%POJ", null, null, null, null));
    }



    // ADD DEMERIT POINTS
    // TEST CASE 1:
    @Test
    void addDemeritPoints() {
    }
    // TEST CASE 2:
}