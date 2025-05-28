import java.util.HashMap;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthDate;
    private HashMap<Date, Integer> demeritPoints;

    public boolean addPerson(String inputPersonID, String inputFirstName, String inputLastName, String inputBirthDate, String inputAddress){
        // checks to see if address, birth date, id and name is in correct format
        if(!validAddress(inputAddress) || !validDate(inputBirthDate) || !validId(inputPersonID) || !validName(inputFirstName, inputLastName)){
            System.out.println("Failure");
            return false;
        }
        // writes details into file
        try (FileWriter writer = new FileWriter("people.txt", true)) {
            writer.write(inputPersonID + "," + inputFirstName + " " +  inputLastName + "," + inputAddress + "," + inputBirthDate + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        System.out.println("Success");
        return true;
    }

    public boolean updatePersonalDetails(){

        return true;
    }

    public String addDemeritPoints(){

        return "";
    }

    private boolean validAddress(String inputAddress){
        String[] parts = inputAddress.split("\\|");
        if (parts.length != 5) {
            System.out.println("User error: Invalid Address format");
            return false;
        }
        if(!parts[2].trim().equalsIgnoreCase("Melbourne") || !parts[3].trim().equalsIgnoreCase("Victoria") || !parts[4].trim().equals("Australia")){
            System.out.println("User error: Invalid Address or outside address is outside victoria");
            return false;
        }
        return true;
    }
     private boolean validDate(String date){
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);
        Matcher matcher = pattern.matcher(date);
        // need to ensure that date is within actual date boundaries
        if(!matcher.matches()){
            System.out.println("User error: Invalid date format");
            return false;
        }
        String[] parts = date.split("-");

        if(!(Integer.parseInt(parts[2]) > 1900 && Integer.parseInt(parts[2]) <= 2025)){
            System.out.println("User error: Year must be greater than 1900 and less than 2025");

        }
        try{
            LocalDate parsedDate = LocalDate.parse(date, formater);
        } catch(Exception e) {
            System.out.println("User error: Date is invalid");
            return false;
        }
        return true;
    }

    private boolean validId(String inputPersonID){
        if(inputPersonID.length() != 10){
            System.out.println("User error: ID length is too long");
            return false;
        }
        if(!Character.isDigit(inputPersonID.charAt(0)) || !Character.isDigit(inputPersonID.charAt(1)) ){
            System.out.println("User error: First characters in ID are not digits");
            return false;
        }
        int firstNumber = inputPersonID.charAt(0) - '0';
        int secondNumber = inputPersonID.charAt(1) - '0';
        if(!(firstNumber >= 2 && firstNumber <= 9) ||  !(secondNumber >= 2 && secondNumber <= 9)){
            System.out.println("User error: First two digits are not within 2 to 9 range");
            return false;
        }

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

        if (!Character.isUpperCase(inputPersonID.charAt(8)) || !Character.isUpperCase(inputPersonID.charAt(9))) {
            System.out.println("User error: Last two characters are not alphabetical or not uppercase");
            return false;
        }

        return true;
    }
    
    private boolean validName(String inputFirstName,  String inputLastName){
        String mashedFullName = inputFirstName + inputLastName;
        for (char c : mashedFullName.toCharArray()) {
            if (Character.isDigit(c) || !Character.isLetterOrDigit(c)) {
                System.out.println("User error: There is a digit or special character in name");
                return false;
            }
        }
        return true;
    }
    private boolean validAge(int age, int requiredAge){
        try {
            int ageInt = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            System.out.println("User error: Age is not a number");
        }
        if(age < 0){
             System.out.println("User error: Age is a negative number");
            return false;
        }
        if(age < requiredAge){
            System.out.println("User error: Age is below required age");
            return false;
        }
        return true;
    }

}
