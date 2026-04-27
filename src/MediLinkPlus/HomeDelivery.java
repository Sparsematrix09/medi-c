package MediLinkPlus;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomeDelivery implements Fulfilment {
    @Override
    public void fulfil(String appointmentId) {
        String address;
        while (true) {
            System.out.print("Enter delivery address: ");
            address = Main.sc.nextLine();

            // Check for empty input
            if (address.isEmpty()) {
                System.out.println("Address is compulsory. Please fill it in. ");
            } else {
                break;
            }
        }

        DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now().plusHours(2);
        String eta = time.format(formattedTime);
        System.out.println("\n=== Home Delivery ===");
        System.out.println("Appointment " + appointmentId + ": Delivery scheduled to " + address);
        System.out.println("Estimated arrival time: " + eta);
    }
}
