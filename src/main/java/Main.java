import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        
        Person p = new Person("37GDG^#@AB", "aDlEy", "JoHnSon", "09-08-2010", "25|Sneydes ROad|Melbourne|Victoria|Australia");
        p.addPerson();
        // p.updatePersonalDetails(null, "Tony", null, null, null);



        // Dummy Data for addDemeritPoints
        HashMap<Date, Integer> currentDemeritPoints = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        // 4 points 6 months ago
        cal.set(2024, Calendar.NOVEMBER, 29);
        currentDemeritPoints.put(cal.getTime(), 6);

        // // 2 points today
        cal.setTime(new Date());
        currentDemeritPoints.put(cal.getTime(), 6);

        p.addDemeritPoints("37GDG^#@AB", "09-08-2000", currentDemeritPoints);
    }
}

