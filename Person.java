import java.util.HashMap;
import java.util.Date;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints;
    private boolean isSuspended;

    public boolean addPerson() {
        // TODO: This method adds information about a person to a TXT file

        // Condition 1: PersonID should be exactly 10 characters long;
            // the first two characters should be numbers between 2 and 9, there should be at least two special
            // characters between 3 and 8, and the last two characters should be upper case letters (A - Z).
                // example: 56s_d%&fAB

        // Condition 2: The address of the Person should follow the format:
            // Street Number | Street | City | State | Country.
            // The state should only be Victoria
                // Example: 32|Highland Street|Melbourne|Victoria|Australia

        // Condition 3: The format of the birthdate of the person should follow the following format:
            // DD-MM-YYYY.
                // Example: 15-11-1990

        // If the Person's information meets the above conditions and any other conditions you may want to consider,
        // the information should be inserted into a TXT file, and the addPerson function should return True. Otherwise,
        // the information should not be inserted into the TXT file, and the addPerson function should return false.
        return true;
    }

    public boolean updatePersonalDetails() {
        return true;
    }

    public String addDemeritPoints() {
        return "Success";
    }

}