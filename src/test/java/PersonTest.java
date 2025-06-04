import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
    // TEST CASE 1: Adding a person to the road registry with valid formats 
    @Test
    void testUppercaseValidData() {
        Person p = new Person("290$$YFUGE", "BOB", "JAMES", "09-10-2010",
                "25|SNEyDes RoaD|MelbOurNe|VictorIa|AustRalia");
        assertTrue(p.addPerson());
    }

    // Lowercase valid data
    @Test
    void testLowercaseValidData() {
        Person p = new Person("33hot*p^JI", "ethan", "pan", "03-02-2005",
                "10|portelli drive|Geelong|victoria|australia");
        assertTrue(p.addPerson());
    }
    //test case 2: Adding a person to the road registry with invalid ID 
    // More than 10 characters in ID
    @Test
    void testIdTooLong() {
        Person p = new Person("39GR^A&J+IK", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // Less than 10 characters in ID
    @Test
    void testIdTooShort() {
        Person p = new Person("39^A&J+IK", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // First digit not 2-9
    @Test
    void testIdFirstDigitInvalid() {
        Person p = new Person("19GR^&J+IK", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // Second digit not 2-9
    @Test
    void testIdSecondDigitInvalid() {
        Person p = new Person("70GR^&J+IK", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    //Less than 2 special chars in 3–8
    @Test
    void testIdTooFewSpecialChars() {
        Person p = new Person("19GR^&J+IK", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // 9th char is not uppercase
    @Test
    void testIdNinthCharNotUppercase() {
        Person p = new Person("19GR^&J+6K", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // 10th char is not uppercase
    @Test
    void testIdTenthCharNotUppercase() {
        Person p = new Person("19GR^&J+Ha", "SaLly", "KiNg", "09-10-2010",
                "25|BaLlArAt CoVe|BaLlARat|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }
    //Test case 3: Adding a person to the road registry with incorrect name format 
    // Non-alphabetical first name
    @Test
    void testFirstNameWithNonAlpha() {
        Person p = new Person("39)8^A&J+I", "98_8j", "KiJ", "02-08-2010",
                "13|ScReAM PrOmENaDe|fOotScRay|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // Non-alphabetical last name
    @Test
    void testLastNameWithNonAlpha() {
        Person p = new Person("39)8^A&J+I", "HeRbert", "8O0*", "05-08-1999",
                "13|ScRAM PrOmENaDe|fOotScRay|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }
    //test case 4: Adding a person to the road registry with incorrect date of birth  
    // Future date of birth
    @Test
    void testFutureDateOfBirth() {
        Person p = new Person("39)8^A&JIK", "Herbert", "Berb", "05-08-2099",
                "13|ScRAM PrOmENaDe|fOotScRay|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }

    // Older than 120 years
    @Test
    void testAgeTooOld() {
        Person p = new Person("39)8^A&JIK", "Herbert", "Berb", "05-08-1904",
                "13|ScRAM PrOmENaDe|fOotScRay|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }
    //The user has selected a date that does not exist
    void testInvalidDate() {
        Person p = new Person("39)8^A&JIK", "Herbert", "Berb", "05-13-1920",
                "13|ScRAM PrOmENaDe|fOotScRay|VictorIa|AustRalia");
        assertFalse(p.addPerson());
    }
    //Test case 5: Adding a person to the road registry with incorrect address format 
    // Invalid address format
    @Test
    void testInvalidAddressFormat() {
        Person p = new Person("39I()*UYJK", "BORB", "SUMM", "09-11-1940",
                "25/Trash court/Essondon/Victoria/Australia");
        assertFalse(p.addPerson());
    }

    // Address outside Victoria
    @Test
    void testAddressNotInVictoria() {
        Person p = new Person("39I()*UYJK", "BORB", "SUMM", "09-11-1940",
                "25|Maccas street|Cairns|Queensland|Australia");
        assertFalse(p.addPerson());
    }

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



    /* --------------------------------- ADD DEMERIT POINTS --------------------------------- */
    // TEST CASE 1: Check the function with valid inputs.
    @Test
    void demeritValid1() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2025, 1, 30);
        demeritTest.put(cal.getTime(), 1);

        cal.set(2024, 11, 17);
        demeritTest.put(cal.getTime(), 3);


        // Test Person
        Person testPerson = new Person("28@d#r^gXZ", "null", "null", "14-03-1994", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);

        // Remove File After Test
        testPerson.deleteDemeritFile(testPerson.personID());

        assertEquals("Success", result);
    }

    @Test
    void demeritValid2() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2024, 2, 27);
        demeritTest.put(cal.getTime(), 2);

        cal.set(2024, 1, 18);
        demeritTest.put(cal.getTime(), 3);


        // Test Person
        Person testPerson = new Person("26!s#r^mLO", "null", "null", "23-08-1999", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
                
        // Remove File After Test
        testPerson.deleteDemeritFile(testPerson.personID());

        assertEquals("Success", result);
    }


    // TEST CASE 2: Check the function with invalid ID
    @Test
    void demeritInvalidID1() {

        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2024, 8, 25);
        demeritTest.put(cal.getTime(), 4);

        cal.set(2024, 6, 12);
        demeritTest.put(cal.getTime(), 3);


        // Test Person
        Person testPerson = new Person("94s!_k&LM", "null", "null", "27-11-1986", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidID2() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2024, 5, 8);
        demeritTest.put(cal.getTime(), 5);

        cal.set(2024, 4, 21);
        demeritTest.put(cal.getTime(), 1);


        // Test Person
        Person testPerson = new Person("35&h$f@p2TR", "null", "null", "09-07-1993", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidID3() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2024, 3, 4);
        demeritTest.put(cal.getTime(), 6);

        cal.set(2024, 1, 19);
        demeritTest.put(cal.getTime(), 4);

        // Test Person
        Person testPerson = new Person("19x*+m!eJK", "null", "null", "02-02-2003", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidID4() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2023, 12, 6);
        demeritTest.put(cal.getTime(), 3);

        cal.set(2023, 10, 27);
        demeritTest.put(cal.getTime(), 2);


        // Test Person
        Person testPerson = new Person("62#qzdawAZ", "null", "null", "18-10-1992", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidID5() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2023, 9, 11);
        demeritTest.put(cal.getTime(), 4);

        cal.set(2023, 8, 29);
        demeritTest.put(cal.getTime(), 5);


        // Test Person
        Person testPerson = new Person("83!z%f@bLa", "null", "null", "25-05-1997", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }


    // TEST CASE 3: Check the function with invalid birth date
    @Test
    void demeritInvalidBD1() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2023, 7, 3);
        demeritTest.put(cal.getTime(), 2);

        cal.set(2023, 6, 15);
        demeritTest.put(cal.getTime(), 5);


        // Test Person
        Person testPerson = new Person("47^k#j!sER", "null", "null", "31-12-20001", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidBD2() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2025, 2, 06);
        demeritTest.put(cal.getTime(), 1);

        cal.set(2024, 12, 11);
        demeritTest.put(cal.getTime(), 6);


        // Test Person
        Person testPerson = new Person("58*r@l%zCV", "null", "null", "061-06-1989", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidBD3() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2024, 9, 23);
        demeritTest.put(cal.getTime(), 2);

        cal.set(2024, 7, 07);
        demeritTest.put(cal.getTime(), 3);


        // Test Person
        Person testPerson = new Person("69$w^m&eQW", "null", "null", "20-097-1996", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }
    

    // TEST CASE 4: Check the function with invalid demerit date
    @Test
    void demeritInvalidDD1() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(20234, 5, 29);
        demeritTest.put(cal.getTime(), 5);

        cal.set(20124, 4, 13);
        demeritTest.put(cal.getTime(), 6);


        // Test Person
        Person testPerson = new Person("72&v*g@dUI", "null", "null", "11-01-1984", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }


    // TEST CASE 5: Check the function with invalid demerit values
    @Test
    void demeritInvalidValues1() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2023, 10, 14);
        demeritTest.put(cal.getTime(), 7);

        cal.set(2023, 9, 05);
        demeritTest.put(cal.getTime(), 5);


        // Test Person
        Person testPerson = new Person("88^p@q%wNZ", "null", "null", "30-10-1990", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }

    @Test
    void demeritInvalidValues2() {
        // Prepare Demerit Hash
        HashMap<Date, Integer> demeritTest = new HashMap<>();
        
        Calendar cal = Calendar.getInstance();

        cal.set(2023, 7, 22);
        demeritTest.put(cal.getTime(), 0);

        cal.set(2023, 6, 10);
        demeritTest.put(cal.getTime(), 3);


        // Test Person
        Person testPerson = new Person("53*f!g#bYT", "null", "null", "05-03-1995", "null");

        String result = testPerson.addDemeritPoints(testPerson.personID(), testPerson.personBD(), demeritTest);
        assertEquals("Failed", result);
    }
}