package MediLinkPlus;

import java.util.ArrayList;

public class Bill {
    private static final double CONSULTATION_FEE = 50.00;
    private static final double SST_RATE = 0.06;

    private Appointment appointment;
    private ArrayList<Prescription> prescriptions;

    public Bill(Appointment appointment, ArrayList<Prescription> prescriptions) {
        this.appointment = appointment;
        this.prescriptions = prescriptions;
    }

    public double calcSubtotal() {
        double sum = CONSULTATION_FEE; // By default if no medicine is purchased, there is still consultation fee
        for (Prescription p : prescriptions) {
            if (p.getAptCode().equals(appointment.getAptCode())) {
                sum += p.calcLineTotal();
            }
        }
        return sum;
    }

    public double calcSST() {
        return calcSubtotal() * SST_RATE;
    }

    public double calcGrandTotal() {
        return calcSubtotal() + calcSST();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("+===========================================================================+\n");
        sb.append("|                             MEDILINK PLUS BILL                            |\n");
        sb.append("+===========================================================================+\n");
        sb.append(String.format("| %s %-55s|%n","Appointment Code :", appointment.getAptCode()));
        sb.append(String.format("| %s %-55s|%n", "Patient ID       :", appointment.getMaskedPatientId()));

        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append("|                         Consultation & Prescription                       |\n");
        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %s %.2f %-45s|%n", "Consultation Fee : RM ",CONSULTATION_FEE, " "));
        sb.append("|                                                                           |\n");
        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-10s | %-17s | %-17s |\n",
                "Medicine Name", "Qty", "Unit Price (RM)", "Line Total (RM)"));
        sb.append("+---------------------------------------------------------------------------+\n");

        for (Prescription p : prescriptions) {
            if (p.getAptCode().equals(appointment.getAptCode())) {
                sb.append(String.format("| %-20s | %-10s | %-17s | %17s |\n",
                        p.getMedName(), p.getQuantity(), p.getUnitPrice(), p.calcLineTotal()));
            }
        }

        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-39sRM %-8.2f |\n", "Subtotal", "", calcSubtotal()));
        sb.append(String.format("| %-20s | %-39sRM %-8.2f |\n", "SST (6%)", "", calcSST()));
        sb.append(String.format("| %-20s | %-39sRM %-8.2f |\n", "Grand Total", "", calcGrandTotal()));
        sb.append("+---------------------------------------------------------------------------+\n\n");

        return sb.toString();
    }

}

