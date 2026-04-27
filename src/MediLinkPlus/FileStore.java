package MediLinkPlus;

import java.io.*;
import java.util.ArrayList;

public class FileStore {

    // Initialize static file attributes
    private static final String CAREGIVER_FILE = "caregivers.txt";
    private static final String DEPENDENT_FILE = "dependents.txt";
    private static final String DOCTOR_FILE = "doctors.txt";
    private static final String APPOINTMENT_FILE = "appointments.txt";
    private static final String PRESCRIPTION_FILE = "prescriptions.txt";

    // Caregiver file
    public static void saveCaregivers(ArrayList<Caregiver> caregivers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CAREGIVER_FILE))) {
            pw.println("CaregiverID|IDType|CaregiverName");
            for (Caregiver c : caregivers) {
                pw.println(c.getPatientId() + "|" + c.getPatientIdType() + "|" + c.getPatientName());
            }
        } catch (IOException e) {
            System.out.println("Error saving caregivers: " + e.getMessage());
        }
    }

    public static ArrayList<Caregiver> loadCaregivers() {
        ArrayList<Caregiver> caregiverList = new ArrayList<>();
        File f = new File(CAREGIVER_FILE);
        if (!f.exists()) { return caregiverList; }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null){
                // Skip header line
                if(line.startsWith("CaregiverID|")) continue;
                String[] parts = line.split("\\|");
                if(parts.length >=3){
                    caregiverList.add(new Caregiver(parts[0], parts[1], parts[2]));
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read caregivers.txt: " + e.getMessage());
        }
        return caregiverList;
    }

    // Dependent file
    public static void saveDependents(ArrayList<Dependent> dependents) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DEPENDENT_FILE))) {
            pw.println("DependentID|IDType|DependentName|CaregiverID");
            for (Dependent d : dependents) {
                pw.println(d.getPatientId() + "|" + d.getPatientIdType() + "|" +
                        d.getPatientName() + "|" + d.getCareGiverId());
            }
        } catch (IOException e) {
            System.out.println("Error saving dependents: " + e.getMessage());
        }
    }

    public static ArrayList<Dependent> loadDependents() {
        ArrayList<Dependent> dependentList = new ArrayList<>();
        File f = new File(DEPENDENT_FILE);
        if (!f.exists()) { return dependentList; }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null){
                // Skip header line
                if(line.startsWith("DependentID|")) continue;
                String[] parts = line.split("\\|");
                if(parts.length >=4){
                    dependentList.add(new Dependent(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dependents.txt: " + e.getMessage());
        }
        return dependentList;
    }

    // Doctor file
    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DOCTOR_FILE))) {
            pw.println("DoctorCode|DoctorName");
            for (Doctor d : doctors) {
                pw.println(d.getDrCode() + "|" + d.getDrName());
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }

    public static ArrayList<Doctor> loadDoctors(){
        ArrayList<Doctor> doctorList = new ArrayList<>();
        File f = new File(DOCTOR_FILE);
        if (!f.exists()) { return doctorList; }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null){
                // Skip header line
                if(line.startsWith("DoctorCode|")) continue;
                String[] parts = line.split("\\|");
                if(parts.length >=2){
                    doctorList.add(new Doctor(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read caregivers.txt: " + e.getMessage());
        }
        return doctorList;
    }

    // Appointment file
    public static void saveAppointments(ArrayList<Appointment> appointments) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(APPOINTMENT_FILE))) {
            pw.println("AppointmentCode|AppointmentType|PatientID|DoctorCode|DoctorName|AppointmentTime|Status|Paid|MedicineFulfilled|PaymentMethod");
            for (Appointment a : appointments) {
                pw.println(a.getAptCode() + "|" + a.getAptType() + "|" + a.getPatientId() + "|" +
                        a.getDrCode() + "|" + a.getDrName() + "|" + a.getAptTime() + "|" + a.getStatus().name() + "|" + a.getIsPaid() + "|" + a.getMedFulfilled() + "|" + a.getPaymentMethod());
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }

    public static ArrayList<Appointment> loadAppointments() {
        ArrayList<Appointment> appointmentList = new ArrayList<>();
        File f = new File(APPOINTMENT_FILE);
        if (!f.exists()) return appointmentList;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip header line
                if(line.startsWith("AppointmentCode|")) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 10)
                    appointmentList.add(new Appointment(
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], Appointment.Status.valueOf(parts[6]), Boolean.parseBoolean(parts[7]), Boolean.parseBoolean(parts[8]), parts[9]));
            }
        } catch (IOException e) {
            System.out.println("Error reading appointments.txt: " + e.getMessage());
        }
        return appointmentList;
    }

    // Prescription file
    public static void savePrescriptions(ArrayList<Prescription> prescriptions) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRESCRIPTION_FILE))) {
            pw.println("AppointmentCode|MedicineName|Dosage|Quantity|UnitPrice");
            for (Prescription p : prescriptions) {
                pw.println(p.getAptCode() + "|" + p.getMedName() + "|" +
                        p.getDosage() + "|" + p.getQuantity() + "|" + p.getUnitPrice());
            }
        } catch (IOException e) {
            System.out.println("Error saving prescriptions: " + e.getMessage());
        }
    }

    public static ArrayList<Prescription> loadPrescriptions() {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        File f = new File(PRESCRIPTION_FILE);
        if (!f.exists()) return prescriptionList;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip header line
                if(line.startsWith("AppointmentCode|")) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 5)
                    prescriptionList.add(new Prescription(parts[0], parts[1], parts[2],
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4])));
            }
        } catch (IOException e) {
            System.out.println("Error reading prescriptions.txt: " + e.getMessage());
        }
        return prescriptionList;
    }
}
