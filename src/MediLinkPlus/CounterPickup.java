package MediLinkPlus;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CounterPickup implements Fulfilment {
    @Override
    public void fulfil(String appointmentId) {
        DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now().plusMinutes(10);
        String readyTime= time.format(formattedTime);
        int counterNum = (int)(Math.random() * (6 - 1 + 1) + 1);
        System.out.println("\n=== Medicine Pickup ===");
        System.out.println("Appointment " + appointmentId + ": Ready for collection at Counter " + counterNum + " by " + readyTime);
    }
}
