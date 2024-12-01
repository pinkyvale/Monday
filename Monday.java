package monday;

import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

interface Alarm {
    void setAlarm(String time);
    void showAlarm();
}

abstract class Weekday implements Alarm {
}

public class Monday extends Weekday {
    private String time; 

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Monday monday = new Monday(); 

        while (true) { 
            System.out.print("Enter time for alarm in this format (HH:MM): ");
            String alarmTime = sc.nextLine().trim(); 

            if (alarmTime == null || alarmTime.isEmpty()) {
                System.out.println("(Time cannot be empty. Please provide a valid time.)");
                continue;  // Loop again for valid input
            }

            try {
                monday.setAlarm(alarmTime); 
                break;  
            } catch (IllegalArgumentException | DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        
        monday.showAlarm(); 
    }

    public void setAlarm(String time) {
        try {
            if (time == null || time.trim().isEmpty()) {
                throw new IllegalArgumentException("(Input cannot be empty.)");
            }

            String[] parts = time.split(":");
            if (parts.length != 2) {
                throw new DateTimeParseException("(Invalid time format)", time, 0);
            }

            if (parts[0].length() == 1) {
                parts[0] = "0" + parts[0];
            }

            this.time = String.join(":", parts); 
            LocalTime.parse(this.time); 
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("(Invalid time format. Please use HH:MM (e.g., 05:30 or 23:45).)", time, 0);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void showAlarm() {
        LocalTime alarm = LocalTime.parse(time); 
        LocalTime now = LocalTime.now(); 

        if (alarm.isAfter(now)) {
            System.out.println("Alarm is set for tomorrow!");
        } else {
            System.out.println("I'll wake you up later!");
        }
    }
}
