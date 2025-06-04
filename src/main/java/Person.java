import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
import java.io.File;
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
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private HashMap<Date, Integer> demeritPoints;
    private String filename = "people.txt";

    public Person(){
        System.out.println("So empty");
    }


    public Person(String personID, String firstName, String lastName, String birthDate, String address){
        if (personID == null || personID.trim().isEmpty() ||
            firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            birthDate == null || birthDate.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            System.out.println("Error: One or more fields are null or empty.");
            throw new IllegalArgumentException("Null or empty values are not allowed.");
        } else{
            this.personID = personID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.birthDate = birthDate;
        }
    }


    // Adds the details of Person to the "people.txt" file for storage
    public boolean addPerson(){
        // checks to see if address, birth date, id and name is in correct format
        if (!validId(personID) || !validName(firstName) || !validName(lastName) || !validDate(birthDate) || !validAddress(address)) {
            System.out.println("User error: Inputs do not fulfill criteria");
            return false;
        }
        // writes details into file
        try (FileWriter writer = new FileWriter(filename, true)) {
            //format the information in the text file so it is more readable
            this.firstName = firstName.toUpperCase();
            this.lastName = lastName.toUpperCase();
            this.address = address.toUpperCase();
            

            writer.write(personID + "," + lastName + "," +  firstName + "," + address + "," + birthDate + "\n");
        } catch (IOException e) {
            System.out.println("System error: Failure to write in file");
            return false;
        }

        System.out.println("Success: Person added to RoadRegistry");
        return true;
    }

    // Updates user information based on the data passed on by the API. .txt file data is updated accordingly.
    public boolean updatePersonalDetails(String inID, String inFirstName, String inLastName, String inBirthDate, String inAddress){
        // ASSUMPTION: If the User does not change specific values, the current values are passed along

        // Check if any input variables are NULL, and if so, set them to the current details
        if (inFirstName == null)  { inFirstName = firstName; }
        if (inLastName == null)   { inLastName = lastName; }
        if (inID == null)         { inID = personID; }
        if (inBirthDate == null)  { inBirthDate = birthDate; }
        if (inAddress == null)    { inAddress = address; }

        // Check if user details are recorded in people.txt
        int index = getIndex();
        if (index < 0) {
            System.out.println("User details not found");
            return false;
        }

        // Ensures that all new values are valid
        if (!validId(inID) || !validName(inFirstName) || !validName(inLastName) || !validDate(inBirthDate) || !validAddress(inAddress)) {
            System.out.println("Invalid update details. No changes have been made.");
            return false;
        }

        // Capitalise all letters
        inID = inID.toUpperCase();
        inFirstName = inFirstName.toUpperCase();
        inLastName = inLastName.toUpperCase();
        inBirthDate = inBirthDate.toUpperCase();
        inAddress = inAddress.toUpperCase();

        // Calculate person's age
        int birthYear = Integer.parseInt(birthDate.split("-")[2]);
        int currentYear = LocalDate.now().getYear();
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
        int firstDigit = Character.getNumericValue(personID.charAt(0));
        if (firstDigit % 2 == 0 && !inID.equals(personID)) {
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

    public void deleteDemeritFile(String fileID) {
        // Remove File After Test
        String testFile = fileID + "_demerit.txt";
        File checkFile = new File(testFile);
        System.out.println(testFile);

        if (checkFile.exists()) {
            checkFile.delete();
        }
    }


    // Adds demerit points for a given person in a TXT file.
    public String addDemeritPoints(String currentID, String currentBirthDate, HashMap<Date, Integer> currentDemeritPoints){
        //and the addDemeritPoints function should return "Success". Otherwise, the addDemeritPoints function should return "Failed".
        String exitMessage = "Success";
        boolean isSuspended = false;
        int totalValidPoints = 0;
        int age = 0;

        if (!validId(currentID) || !validDate(currentBirthDate)) {
            exitMessage = "Failed";
        }

        // HashMap<Date, Integer> validEntries;
        Map<Date, Integer> validEntries = new LinkedHashMap<>();

        //CONDITION 2: The demerit points must be a whole number between 1-6
        for (Integer demerits : currentDemeritPoints.values()) {
            if (demerits < 1 || demerits > 6) {
            exitMessage = "Failed";
            }
        }

        //CONDITION 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        Date currentDate = new Date();
        Date twoYearsAgo = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.YEAR, -2);
        twoYearsAgo = cal.getTime();


        // Find Age
        if (validDate(currentBirthDate)) {
            int birthYear = Integer.parseInt(currentBirthDate.split("-")[2]);
            int currentYear = LocalDate.now().getYear();
            age = currentYear - birthYear;

            for (Date deductionDate : currentDemeritPoints.keySet()) {
                if (!deductionDate.before(twoYearsAgo)) {
                    totalValidPoints += currentDemeritPoints.get(deductionDate);
                    validEntries.put(deductionDate, currentDemeritPoints.get(deductionDate));
                }
            }
        } else {
            exitMessage = "Failed";
        }


        //CONDITION 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        //If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
        if (age < 21 && age > 0) {
            if (totalValidPoints >= 6) {
                isSuspended = true;
            }
        } else if (age >= 21) {
            if (totalValidPoints >= 12) {
                isSuspended = true;
            }
        } else {
            exitMessage = "Failed";
        }


        //Instruction: If the above conditions and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        // Add demerit points function creates/updates file named "{id}_demerit" and records the date of each new demerit point addition
        if (exitMessage.equals("Success")) {
            String demeritFile = currentID + "_demerit.txt";
            File checkFile = new File(demeritFile);


            deleteDemeritFile(currentID);

            try (FileWriter writer = new FileWriter(checkFile)) {

                for (Map.Entry<Date, Integer> violations : validEntries.entrySet()) {
                    writer.write(String.format("%1$td-%1$tm-%1$tY,%2$d\n", violations.getKey(), violations.getValue()));
                }

            } catch (IOException e) {
                exitMessage = "Failed";
            }
        }

        return exitMessage;
    }


    // checks to see if the address is in valid format
    private boolean validAddress(String inputAddress){
        // CONDITION 2: address should follow the following format:
        //      Street Number|Street|City|State|Country
        //      The State should be only Victoria
        //      Example: 32|Highland Street|Melbourne|Victoria|Australia

        //splits address up so it can be checked individually later on
        String[] parts = inputAddress.split("\\|");
        if (parts.length != 5) {
            System.out.println("User error: Invalid Address format");
            return false;
        }
        // checks to see if the address is within the operating zone of Roadregistry
        if(!parts[3].trim().equalsIgnoreCase("Victoria")
        || !parts[4].trim().equalsIgnoreCase("Australia")){
            System.out.println("User error: Invalid Address or outside address is outside victoria");
            return false;
        }

        return true;
    }
    private boolean validDate(String date){
        // CONDITION 3: birthDate should follow the following format:
        //      DD-MM-YYYY
        //      Example: 15-11-1990


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
        Calendar cal = Calendar.getInstance();
        try {
            if(!((Integer.parseInt(parts[2]) > cal.get(Calendar.YEAR) - 120 && Integer.parseInt(parts[2]) <= cal.get(Calendar.YEAR))
            && (Integer.parseInt(parts[0]) > 0 && Integer.parseInt(parts[0]) <=31)
            && (Integer.parseInt(parts[1]) > 0 && Integer.parseInt(parts[1]) <= 12))){
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
            LocalDate currenDate =  LocalDate.now();
            int cmp = (parsedDate.getYear() - currenDate.getYear());
            if (cmp == 0) {
                cmp = (parsedDate.getMonthValue() - currenDate.getMonthValue());
                if (cmp == 0) {
                    cmp = (parsedDate.getDayOfMonth() - currenDate.getDayOfMonth());
                    System.out.println(cmp);
                } 
            }
            if(cmp > 0){
                System.out.println("User error: Date selected is from the future");
                return false;

            }
        } catch(Exception e) {
            System.out.println("User error: Date is invalid");
            return false;
        }

        

        return true;
    }

    private boolean validId(String inputPersonID){
        // CONDITION 1: personID should follow the following format:
        //       personID should be exactly 10 characters long
        //       The first two characters should be numbers between 2 and 9
        //       There should be at least two special characters between characters 3 and 8
        //       The last two characters should be uppercase letters (A-Z)
        //       Example: "56s_d%&fAB"

        if(inputPersonID.length() != 10){
            System.out.println("User error: ID length is not 10");
            return false;
        }
        //checks first characters of id to see if they are digits
        if(!Character.isDigit(inputPersonID.charAt(0)) || !Character.isDigit(inputPersonID.charAt(1)) ){
            System.out.println("User error: First two characters in ID are not digits");
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

    private boolean validName(String input){
        // Ensures that names are only letters
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c)) {
                System.out.println("User error: There is a digit or special character in name");
                return false;
            }
        }
        return true;
    }

    private int getIndex () {
        // Add the contents of the "people.txt" file to a scanner
        List<String[]> people = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new java.io.File(filename));

            // Append each line to the list in a String array of each value
            while (scanner.hasNextLine()) {
                String curr = scanner.nextLine();
                people.add(curr.split(","));
            }
            scanner.close(); // Always close resources
        } catch (java.io.FileNotFoundException e) {
            System.out.println("System error: people.txt not found");
            return -1;
        }

        // people[i][0] = ID
        // people[i][1] = Last Name
        // people[i][2] = First Name
        // people[i][3] = Address
        // people[i][4] = Birth Date

        // Check for user in "database"
        int userIndex = -1;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i)[0].equals(personID)) {
                userIndex = i;
                break;
            }
        }
        return userIndex;
    }

    public String personID() {
        return personID;
    }

    public String personBD() {
        return birthDate;
    }

}
