import java.util.HashMap;
import java.util.Date;

// addPerson imports
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

// updatePersonalDetails imports
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private HashMap<Date, Integer> demeritPoints;
    private String filename = "people.txt";

    //add person to people.txt file for storage
    public boolean addPerson(String inputPersonID, String inputFirstName, String inputLastName, String inputBirthDate, String inputAddress){
        // checks to see if address, birth date, id and name is in correct format
        if (!validId(inputPersonID) || !validName(inputFirstName) || !validName(inputLastName) || !validDate(inputBirthDate) || !validAddress(inputAddress)) {
            System.out.println("Failure");
            return false;
        }

        /*
        if(!validAddress(inputAddress) || !validDate(inputBirthDate) || !validId(inputPersonID) || !validName(inputFirstName, inputLastName)){
            System.out.println("Failure");
            return false;
        }
         */

        personID = inputPersonID;
        firstName = inputFirstName;
        lastName = inputLastName;
        address = inputAddress;
        birthDate = inputBirthDate;

        // writes details into file
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(personID + "," + lastName + "," +  firstName + "," + address + "," + birthDate + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        System.out.println("Success");
        return true;
    }

    public boolean updatePersonalDetails(String inID, String inFirstName, String inLastName, String inBirthDate, String inAddress){
        // Updates user details passed on by the API

        // ASSUMPTION: If the User does not change specific values, the current values are passed along

        // TODO: Ensure no values have commas
        // TODO: Update private values in the addPerson function, or make a proper constructor
        // TODO: If demerit point file exists, change name if ID updates

        // Check if user details are recorded in people.txt
        index = getIndex();
        if (index < 0) {
            System.out.println("User details not found");
            return false;
        }

        // Check if any input variables are NULL, and if so, set them to the current details
        if (inFirstName == null) { inFirstName = firstName; }
        if (inLastName == null)  { inLastName = lastName; }
        if (inID == null)        { inID = personID; }
        if (inBirthDate == null) { inBirthDate = birthDate; }
        if (inAddress == null)   { inAddress = address; }

        // Ensures that all new values are valid
        if (!validId(inID) || !validName(inFirstName) || !validName(inLastName) || !validDate(inBirthDate) || !validAddress(inAddress)) {
            System.out.println("Failure");
            return false;
        }

        // Calculate person's age
        int birthYear = Integer.parseInt(birthDate.split("-")[2]);
        Date currentDate = new Date();
        int currentYear = currentDate.getYear() + 1900;
        int age = currentYear - birthYear;

        // CONDITION 1: If birthdate is being changed, cannot update any other value
        if (!inBirthDate.equals(birthDate)) {
            if (!inID.equals(personID) || !inFirstName.equals(firstName) || !inLastName.equals(lastName) || !inAddress.equals(address)) {
                System.out.println("If updating birth date, no other information may be changed. No changes have been made.");
                return false;
            }
        }

        // CONDITION 2: If person's age < 18, cannot change address
        if (age < 18 && !address.equals(inAddress)) {
            System.out.println("User is is younger than 18. Address cannot be changed. No changes have been made.");
            return false;
        }

        // CONDITION 3: If first character/digit of person's id is an even number, ID cannot be changed
        int firstDigit = Character.getNumericValue(ID.charAt(0));
        if (firstDigit % 2 == 0 && !inID.equals(ID)) {
            System.out.println("Initial ID digit is even. ID cannot be changed. No changes have been made.");
            return false;
        }

        // Continue if all 3 conditions have been met
        String newData = inID + "," + inLastName + "," + inFirstName + "," + inAddress + "," + inBirthDate;

        // Set line in txt file as newData
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            lines.set(index, newData);
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("User information successfully updated.");
        return true;
    }

    public String addDemeritPoints(){
        // Add demerit points function creates/updates file named "{id}_demerit" and records the date of each new demerit point addition
        // Updates isSuspended to true if total demerit points within 2 years is >= 12

        // MIGHT NOT NEED TO RECORD ALL, MAYBE JUST LATEST INFRINGEMENT
        return "";
    }
    // checks to see if the address is in valid format
    private boolean validAddress(String inputAddress){
        //splits address up so it can be check individually later on
        String[] parts = inputAddress.split("\\|");
        if (parts.length != 5) {
            System.out.println("User error: Invalid Address format");
            return false;
        }
        // checks to see if the address is within the operating zone of Roadregistry
        if(!parts[2].trim().equalsIgnoreCase("Melbourne") || !parts[3].trim().equalsIgnoreCase("Victoria")
        || !parts[4].trim().equals("Australia")){
            System.out.println("User error: Invalid Address or outside address is outside victoria");
            return false;
        }
        return true;
    }
    private boolean validDate(String date){
        //creates a pattern and checks the pattern with the user date
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);
        Matcher matcher = pattern.matcher(date);

        if(!matcher.matches()){
            System.out.println("User error: Invalid date format");
            return false;
        }
        String[] parts = date.split("-");
        //checks to see if the values of the dates are valid
        try {
            if(!((Integer.parseInt(parts[2]) > 1900 && Integer.parseInt(parts[2]) <= 2025
            && Integer.parseInt(parts[0]) > 0 && Integer.parseInt(parts[0]) <31)
            && Integer.parseInt(parts[1]) > 1 && Integer.parseInt(parts[1]) <= 12)){
                System.out.println("User error: Date has invalid values");
                return false;

        }
        } catch (Exception e) {
            System.out.println("System error: There is something wrong");
            return false;
        }
       //double checks to see if the actual date is a real date
        try{
            LocalDate parsedDate = LocalDate.parse(date, formater);
        } catch(Exception e) {
            System.out.println("User error: Date is invalid");
            return false;
        }
        return true;
    }

    private boolean validId(String inputPersonID){
        //checks length of id
        if(inputPersonID.length() != 10){
            System.out.println("User error: ID length is too long");
            return false;
        }
        //checks first characters of id to see if they are digits
        if(!Character.isDigit(inputPersonID.charAt(0)) || !Character.isDigit(inputPersonID.charAt(1)) ){
            System.out.println("User error: First characters in ID are not digits");
            return false;
        }
        //checks to see if the digits are within values of 2 and 9
        int firstNumber = inputPersonID.charAt(0) - '0';
        int secondNumber = inputPersonID.charAt(1) - '0';
        if(!(firstNumber >= 2 && firstNumber <= 9) ||  !(secondNumber >= 2 && secondNumber <= 9)){
            System.out.println("User error: Values of first two digits are not within 2 to 9 range");
            return false;
        }
        //checks to see if there are 2 or more special characters in id
        int specialCount = 0;
        for(int i = 2; i < 8; i++){
            if (!Character.isLetterOrDigit(inputPersonID.charAt(i))) {
                specialCount++;
            }
        }
        if(specialCount < 2){
            System.out.println("User error: Not enough special characters");
            return false;
        }
        //checks to see if last 2 characters of id are uppercase letters
        if (!Character.isUpperCase(inputPersonID.charAt(8)) || !Character.isUpperCase(inputPersonID.charAt(9))) {
            System.out.println("User error: Last two characters are not alphabetical or not uppercase");
            return false;
        }

        return true;
    }

    /*
    private boolean validName(String inputFirstName,  String inputLastName){
        //create full name
        String mashedFullName = inputFirstName + inputLastName;
        //checked to see if the full name is invalid
        for (char c : mashedFullName.toCharArray()) {
            if (Character.isDigit(c) || !Character.isLetterOrDigit(c)) {
                System.out.println("User error: There is a digit or special character in name");
                return false;
            }
        }
        return true;
    }
    */

    private boolean validName(String input){
        // Ensures that names are only letters
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || !Character.isLetterOrDigit(c)) {
                System.out.println("User error: There is a digit or special character in name");
                return false;
            }
        }
        return true;
    }

    private boolean validAge(int age, int requiredAge){
        /*try {
            int ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            System.out.println("User error: Age is not a number");
        }*/
        //checks to see if age is negative
        if(age < 0){
             System.out.println("User error: Age is a negative number");
            return false;
        }
        //checks to see if age gits the requirement of the age in the selected task
        if(age < requiredAge){
            System.out.println("User error: Age is below required age");
            return false;
        }
        return true;
    }

    private int getIndex () {
        // Add the contents of the "people.txt" file to a scanner
        Scanner scanner = new Scanner(File(filename));
        List<String[]> people = new ArrayList<>();

        // Append each line to the list in a String array of each value
        while (scanner.hasNextLine()) {
            String curr = scanner.nextLine();
            people.add(curr.split(","));
        }

        // people[i][0] = ID
        // people[i][1] = Last Name
        // people[i][2] = First Name
        // people[i][3] = Address
        // people[i][4] = Birth Date

        // Check for user in "database"
        int userIndex == -1;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i)[0].equals(currID)) {
                userIndex = i;
                break;
            }
        }
        return userIndex;
    }

}
