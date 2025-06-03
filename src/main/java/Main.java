import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        Person p = new Person("37GDG^#@AB", "Adley", "Johnson", "09-08-2030", "25|Sneydes Road|Melbourne|Victoria|Australia");
        p.addPerson();
        // Person p2 = new Person(null, null, null, null, null);
        // p2.addPerson();



        // Dummy Data for addDemeritPoints
        HashMap<Date, Integer> currentDemeritPoints = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        // 4 points 6 months ago
        cal.set(2024, Calendar.NOVEMBER, 29);
        currentDemeritPoints.put(cal.getTime(), 6);

        // // 2 points today
        cal.setTime(new Date());
        currentDemeritPoints.put(cal.getTime(), 6);

        // 3 points 3 years ago (ignored)
        cal.set(2021, Calendar.MARCH, 10);
        currentDemeritPoints.put(cal.getTime(), 3);
        System.out.println("test");

        p.addDemeritPoints("37GDG^#@AB", "09-08-2000", currentDemeritPoints);
    }
}

