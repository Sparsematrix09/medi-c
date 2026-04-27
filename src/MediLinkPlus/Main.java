package MediLinkPlus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {

    // Initialize static attributes (accessed by different methods)
    private static ArrayList<Caregiver> caregivers = new ArrayList<>();
    private static ArrayList<Dependent> dependents = new ArrayList<>();
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Appointment> appointments = new ArrayList<>();
    private static ArrayList<Prescription> prescriptions = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    // 0) Start and load

    // Load data from all saved files
    static void loadAllFiles() {
        caregivers = FileStore.loadCaregivers(); // Load data from caregivers.txt
        dependents = FileStore.loadDependents(); // Load data from dependents.txt
        doctors = FileStore.loadDoctors(); // Load data from doctors.txt
        appointments = FileStore.loadAppointments(); // Load data from appointments.txt
        prescriptions = FileStore.loadPrescriptions(); // Load data from prescriptions.txt
        System.out.println("Loaded all files successfully.");
    }

    // Save data in all files
    static void saveAllFiles() {
        FileStore.saveCaregivers(caregivers); // Save data from caregivers.txt
        FileStore.saveDependents(dependents); // Save data from dependents.txt
        FileStore.saveDoctors(doctors); // Save data from doctors.txt
        FileStore.saveAppointments(appointments); // Save data from appointments.txt
        FileStore.savePrescriptions(prescriptions); // Save data from prescriptions.txt
        System.out.println("Saved all files successfully.");
    }

    // If nothing exists, start fresh and seed one doctor
    static void seedDoctor() {
        if (doctors.isEmpty()) {
            Doctor d = new Doctor("D001", "Lim");
            doctors.add(d);
            System.out.println("Seeded one default doctor: " + d.getDrName());
        }
    }

    // Reusable components to avoid code redundancy

    // a) Find appointment by code
    static Appointment findAppointmentByCode(String aptCode) {
        for (Appointment apt : appointments) {
            if (apt.getAptCode().equalsIgnoreCase(aptCode)) {
                return apt;
            }
        }
        return null;
    }

    // b) Press "*" to go back to main menu
    static void backToMenu(String choice) {
        if (choice != null && choice.equalsIgnoreCase("*")) {
            System.out.print("Exiting... Back to main menu. \n");
        }
    }

    // c) Determine ID Type
    static String selectIdType() {
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu");
            System.out.print("ID Types: \n1. NRIC \n2. Passport \n\nEnter ID Type (1/2):");
            String choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("1")) {
                return "NRIC";
            } else if (choice.equalsIgnoreCase("2")) {
                return "Passport";
            } else if (choice.equalsIgnoreCase("*")) {
                return null;
            } else {
                System.out.println("\nInvalid choice. Please enter 1 or 2. ");
            }
        }
    }

    // d) Validate NRIC
    static String validateNRIC() {
        while (true) {
            System.out.print("Enter NRIC Number (e.g. 780923110142): ");
            String cgId = sc.nextLine().trim();

            if (cgId.equalsIgnoreCase("*")) {
                return null;
            }

            // Validate NRIC format (YYMMDD + SSSS/place of birth code + GG/gender or
            // sequence digit)
            if (!cgId.matches("^(?:0[0-9]|[1-9]\\d)(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{6}$")) {
                System.out.println("Invalid NRIC format! Please enter in YYMMDDSSSSGG format (12 digits, valid date).");
            } else {
                return cgId;
            }
        }
    }

    // e) Validate passport
    static String validatePassport() {
        while (true) {
            System.out.print("Enter Passport Number (e.g. A1234567): ");
            String cgId = sc.nextLine().trim();

            if (cgId.equalsIgnoreCase("*")) {
                return null;
            }

            // Validate passport format (letters or digits only, length from 6-9)
            if (!cgId.matches("^[A-Za-z0-9]{6,9}$")) {
                System.out.println(
                        "Invalid passport format! Please enter in correct format (6-9 characters long, only letters and digits).");
            } else {
                return cgId;
            }
        }
    }

    // Print Menu
    static void printMenu() {
        System.out.println("\n====Main Menu====");
        System.out.println("1) Register Caregiver");
        System.out.println("2) Add Dependent");
        System.out.println("3) List Family");
        System.out.println("4) Add / List Doctors");
        System.out.println("5) Book Appointment");
        System.out.println("6) Update Appointment Status");
        System.out.println("7) Show Appointments");
        System.out.println("8) Add Prescription");
        System.out.println("9) Calculate Bill");
        System.out.println("10) Take Payment");
        System.out.println("11) Print Receipt");
        System.out.println("12) Fulfil Medicine");
        System.out.println("13) Save and Exit");
        System.out.print("Enter your choice (1 - 13): ");
    }

    // 1) Register caregiver
    static void registerCaregiver() {
        String cgName;

        while (true) {
            // Prompt to enter caregiver name
            System.out.println("\nPress \"*\" to return to Main Menu");
            System.out.print("Enter Caregiver Name: ");
            cgName = sc.nextLine().trim();

            // Check for empty input
            if (cgName.isEmpty()) {
                System.out.println("Caregiver name is compulsory. Please fill it in. ");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (cgName.equalsIgnoreCase("*")) {
            backToMenu(cgName);
            return;
        }

        // Prompt to enter caregiver ID type
        String cgIdType = "";
        cgIdType = selectIdType();

        if (cgIdType == null) {
            backToMenu("*");
            return;
        }

        // Prompt to enter caregiver ID
        System.out.println("\nPress \"*\" to return to Main Menu");

        String cgId = "";
        if (cgIdType.equals("NRIC")) {
            cgId = validateNRIC();
        } else {
            cgId = validatePassport();
        }

        if (cgId == null) {
            backToMenu("*");
            return;
        }

        // Reject duplicate caregiver IDs
        for (Caregiver cg : caregivers) {
            if (cg.getPatientId().equalsIgnoreCase(cgId)) {
                System.out.println("Error: Duplicate ID found for caregiver.");
                return;
            }
        }

        // Reject caregiver == dependent registered (same ID)
        for (Dependent dpt : dependents) {
            if (dpt.getDependentId().equalsIgnoreCase(cgId)) {
                System.out.println("Error: A dependent cannot be a caregiver at the same time.");
                return;
            }
        }

        // Create and add new caregiver
        Caregiver cg = new Caregiver(cgId, cgIdType, cgName);
        caregivers.add(cg);

        // Print confirmation for caregiver created
        System.out.println("\nCaregiver added successfully.\n" + cg.toString());

        // Save caregiver data
        FileStore.saveCaregivers(caregivers);
    }

    // 2) Add dependent
    static void addDependent() {

        // Prompt to enter dependent name
        String dptName;
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Enter Dependent Name: ");
            dptName = sc.nextLine().trim();

            // Check for empty input
            if (dptName.isEmpty()) {
                System.out.println("Dependent name is compulsory. Please fill it in. ");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (dptName.equalsIgnoreCase("*")) {
            backToMenu(dptName);
            return;
        }

        // Prompt to enter dependent ID type
        String dptIdType = "";
        dptIdType = selectIdType();

        if (dptIdType == null) {
            backToMenu("*");
            return;
        }

        // Prompt to enter dependent ID
        System.out.println("\nPress \"*\" to return to Main Menu");

        String dptId = "";
        if (dptIdType.equals("NRIC")) {
            dptId = validateNRIC();
        } else {
            dptId = validatePassport();
        }

        if (dptId == null) {
            backToMenu("*");
            return;
        }

        // Reject duplicate dependent IDs
        for (Dependent dpt : dependents) {
            if (dpt.getDependentId().equalsIgnoreCase(dptId)) {
                System.out.println("Error: Duplicate dependent ID found.");
                return;
            }
        }

        // Reject dependent == caregiver registered (same ID)
        for (Caregiver cg : caregivers) {
            if (cg.getCareGiverId().equalsIgnoreCase(dptId)) {
                System.out.println("Error: A caregiver cannot be a dependent at the same time.");
                return;
            }
        }

        // Prompt to enter caregiver ID
        String cgId;

        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Enter linked caregiver's ID (IC or Passport): ");
            cgId = sc.nextLine().trim();

            // Back to menu if enter "*"
            if (cgId.equalsIgnoreCase("*")) {
                backToMenu(cgId);
                return;
            }

            // Assign linked caregiver
            Caregiver linkedCg = null;
            for (Caregiver cg : caregivers) {
                if (cg.getCareGiverId().equalsIgnoreCase(cgId)) { // error
                    linkedCg = cg;
                    break;
                }
            }

            // Reject unknown caregiver IDs
            if (linkedCg == null) {
                System.out.println("Error: Caregiver ID not found. Please check spelling or register caregiver first.");
            } else {
                break;
            }
        }

        // Create and add new dependent
        Dependent dpt = new Dependent(dptId, dptIdType, dptName, cgId);
        dependents.add(dpt);

        // Print confirmation linking dependent to the caregiver
        System.out.println("\nDependent added successfully.\n" + dpt.toString());

        // Save dependent data
        FileStore.saveDependents(dependents);
    }

    // 3) List family
    static void listFamily() {

        // Check caregivers arraylist is empty or not
        if (caregivers.isEmpty()) {
            System.out.println("No caregivers found.");
            return;
        }

        // Print caregivers and linked dependents list
        System.out.println("+====+======================+===============+======================+===============+");
        System.out.println("|No. | Caregiver Name       | Caregiver ID  | Dependent Name       | Dependent ID  |");
        System.out.println("+====+======================+===============+======================+===============+");

        for (int i = 0; i < caregivers.size(); i++) {
            Caregiver cg = caregivers.get(i);
            boolean hasDependent = false;

            for (Dependent d : dependents) {
                if (d.getCareGiverId().equals(cg.getCareGiverId())) {
                    // Print caregiver info only for first dependent
                    if (!hasDependent) {
                        System.out.printf("| %-2d | %-20s | %-13s | %-20s | %-13s |\n",
                                (i + 1), cg.getCareGiverName(), cg.getMaskedId(), d.getDependentName(),
                                d.getMaskedId());
                        hasDependent = true;
                    } else {
                        // For additional dependents, leave caregiver columns empty
                        System.out.printf("| %-2s | %-20s | %-13s | %-20s | %-13s |\n",
                                "", "", "", d.getDependentName(), d.getMaskedId());
                    }
                }
            }

            // If caregiver has no dependents, show empty dependent columns
            if (!hasDependent) {
                System.out.printf("| %-2d | %-20s | %-13s | %-20s | %-13s |\n",
                        (i + 1), cg.getCareGiverName(), cg.getMaskedId(), "-", "-");
            }

            System.out.println("+----+----------------------+---------------+----------------------+---------------+");
        }
    }

    // 4) Add/List doctors
    static void addOrListDoctors() {
        String drName = "";

        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Available functions: \n1. Add Doctor \n2. List Doctors \n\nEnter a function(1/2): ");
            String choice = sc.nextLine().trim();

            // Back to menu if enter "*"
            if (choice.equalsIgnoreCase("*")) {
                backToMenu(choice);
                break;
            }

            // 4.1) Add doctor
            else if (choice.equalsIgnoreCase("1")) {

                while (true) {
                    System.out.println("\nPress \"*\" to return to Main Menu\n");
                    // Prompt to enter doctor name
                    System.out.print("Enter doctor name: Dr ");
                    drName = sc.nextLine().trim();

                    // Check for empty input
                    if (drName.isEmpty()) {
                        System.out.println("Doctor name is compulsory. Please fill it in. ");
                    } else {
                        break;
                    }
                }

                // Back to menu if enter "*"
                if (drName.equalsIgnoreCase("*")) {
                    backToMenu(drName);
                    break;
                }

                // Avoid doctor duplicates by name
                for (Doctor d : doctors) {
                    if (d.getDrName().equalsIgnoreCase(drName)) {
                        System.out.println("Doctor already exists.");
                        return;
                    }
                }

                // Auto increment doctor code by 1 (D + three digits numbers)
                int maxNum = 0;
                for (Doctor d : doctors) {
                    String drCode = d.getDrCode();
                    if (drCode != null && drCode.length() > 1 && drCode.toUpperCase().startsWith("D")) {
                        try {
                            int num = Integer.parseInt(drCode.substring(1)); // get numeric part
                            if (num > maxNum) {
                                maxNum = num;
                            }
                        } catch (NumberFormatException ignored) {
                            // Skip bad codes like "DABC"
                        }
                    }
                }

                String drCode = String.format("D%03d", maxNum + 1);

                // Create and add new doctor
                Doctor doctor = new Doctor(drCode, drName);
                doctors.add(doctor);

                // Print confirmation for doctor added
                System.out.println("\nDoctor added successfully.");
                StringBuilder sb = new StringBuilder();
                sb.append("+=======================================================+\n");
                sb.append(String.format("| %-25s | %-25s |\n", "Doctor Code", "Doctor Name"));
                sb.append("+=======================================================+\n");
                System.out.print(sb + doctor.toString() + "\n");

                // Save doctor data
                FileStore.saveDoctors(doctors);

            } else if (choice.equalsIgnoreCase("2")) { // 4.2) List doctors

                // Check doctors arraylist is empty or not
                if (doctors.isEmpty()) {
                    System.out.println("No doctors found.");
                    return;
                }

                // Print doctors list
                StringBuilder sb = new StringBuilder();
                sb.append("+==================================================================+\n");
                sb.append(String.format("| %-8s | %-25s | %-25s |\n", "No.", "Doctor Code", "Doctor Name"));
                sb.append("+==================================================================+\n");

                for (int i = 0; i < doctors.size(); i++) {
                    sb.append(String.format("| %-8s | %-25s | %-25s |\n", (i + 1), ("Dr " + doctors.get(i).getDrName()),
                            doctors.get(i).getDrCode()));
                    sb.append("+------------------------------------------------------------------+\n");
                }
                System.out.println(sb);
                break;

            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    // 5) Book appointment
    static void bookAppointment() {

        // Prompt to enter dependent ID
        String dependentId;
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Please enter Dependent ID: ");
            dependentId = sc.nextLine().trim();

            // Check if the dependent ID exist
            Dependent selectedDependent = null;
            for (Dependent dpt : dependents) {
                if (dpt.getDependentId().equalsIgnoreCase(dependentId)) {
                    selectedDependent = dpt;
                    break;
                }
            }

            if (dependentId.equalsIgnoreCase("*")) {
                break;
            } else if (selectedDependent == null) {
                System.out.println("Invalid Dependent ID.");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (dependentId.equalsIgnoreCase("*")) {
            backToMenu(dependentId);
            return;
        }

        // Auto increment appointment code by 1 (A + three digits numbers)
        int maxNum = 0;
        for (Appointment apt : appointments) {
            String aptCode = apt.getAptCode();
            if (aptCode != null && aptCode.length() > 1 && aptCode.toUpperCase().startsWith("A")) {
                try {
                    int num = Integer.parseInt(aptCode.substring(1)); // get numeric part
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException ignored) {
                    // Skip bad codes like "AABC"
                }
            }
        }

        String aptCode = String.format("A%03d", maxNum + 1);

        // Check doctor availability
        if (doctors.isEmpty()) {
            System.out.println("No doctors are available.");
            return;
        }

        // Print doctor list
        System.out.println("Available doctors: ");
        StringBuilder sb = new StringBuilder();
        sb.append("+=======================================================+\n");
        sb.append(String.format("| %-25s | %-25s |\n", "Doctor Code", "Doctor Name"));
        sb.append("+=======================================================+");

        System.out.println(sb);

        for (Doctor dc : doctors) {
            System.out.println(dc.toString());
        }

        // Select doctor
        String drCode;
        Doctor selectedDr = null;

        // Prompt to enter doctor code
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Please select a doctor (e.g. D001): ");
            drCode = sc.nextLine().trim();

            // Check if doctor code exist
            for (Doctor dc : doctors) {
                if (dc.getDrCode().equalsIgnoreCase(drCode)) {
                    selectedDr = dc;
                    break;
                }
            }

            if (drCode.equalsIgnoreCase("*")) {
                break;
            } else if (selectedDr == null) {
                System.out.println("Invalid Doctor ID.");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (drCode.equalsIgnoreCase("*")) {
            backToMenu(drCode);
            return;
        }

        // Prompt to choose appointment type
        String aptType = "";
        String choice;
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Appointment Type: \n1. VIDEO \n2. CLINIC \nEnter Appointment Type (1/2): ");
            choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("*")) {
                break;
            } else if (choice.equalsIgnoreCase("1")) {
                aptType = "VIDEO";
                break;
            } else if (choice.equalsIgnoreCase("2")) {
                aptType = "CLINIC";
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        // Back to menu if enter "*"
        if (choice.equalsIgnoreCase("*")) {
            backToMenu(choice);
            return;
        }

        // Prompt to enter appointment time
        String aptTime;
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print("Enter Appointment Time (e.g. 23:30): ");
            aptTime = sc.nextLine().trim();

            // Back to menu if enter "*"
            if (aptTime.equalsIgnoreCase("*")) {
                backToMenu(aptTime);
                return;
            }

            // Validate time in HH:mm format (00:00 to 23:59)
            if (!aptTime.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                System.out.println("Invalid time format! Please enter in HH:mm format (e.g. 09:30 or 21:45).");
            } else {
                break;
            }

            // Reject duplicate appointment time
            boolean isTimeClashed = false;
            for (Appointment apt : appointments) {
                if (apt.getPatientId().equalsIgnoreCase(dependentId) &&
                        apt.getAptTime().equals(aptTime) &&
                        apt.getStatus() != Appointment.Status.CANCELLED) {
                    isTimeClashed = true;
                    break;
                }
            }
            if (isTimeClashed) {
                System.out.println("Error: Duplicate appointment time ( " + aptTime
                        + " ) found for patient. Please enter another time. ");
                return;
            }
        }

        // Create and add new appointment
        Appointment newAppointment = new Appointment(aptCode, aptType, dependentId, drCode, selectedDr.getDrName(),
                aptTime);
        appointments.add(newAppointment);

        // Print appointment added line
        System.out.println("Appointment booked successfully.\n" + newAppointment.toString());

        // Save appointment data
        FileStore.saveAppointments(appointments);
    }

    // 6) Update appointment status
    static void updateAptStatus() {

        // Print appointment list
        showAppointments();

        // Prompt to enter appointment code to update
        System.out.println("\nPress \"*\" to return to Main Menu\n");
        System.out.print("Enter appointment code to update: ");
        String aptCode = sc.nextLine().trim();

        // Back to menu if enter "*"
        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Check if appointment code exists
        Appointment selectedApt = null;
        for (Appointment apt : appointments) {
            if (apt.getAptCode().equalsIgnoreCase(aptCode)) {
                selectedApt = apt;
                break;
            }
        }

        if (selectedApt == null) {
            System.out.println("Invalid appointment code.");
            return;
        }

        // Print current appointment status
        Appointment.Status current = selectedApt.getStatus();
        System.out.println("Current appointment status: " + current);

        while (true) {
            String choice = "";
            String choice1 = "";
            switch (current) {
                // If status is REQUESTED, prompt to choose appointment status (CONFIRM/CANCEL)
                case REQUESTED:
                    System.out.print(
                            "\nPress \"*\" to return to Main Menu\n\nNew appointment status: \n1. Confirm appointment \n2. Cancel appointment \nEnter new status (1/2): ");
                    choice = sc.nextLine().trim();

                    // Back to menu if enter "*"
                    if (choice.equalsIgnoreCase("*")) {
                        break;
                    }

                    if (choice.equalsIgnoreCase("1")) {
                        selectedApt.setStatus(Appointment.Status.CONFIRMED);
                    } else if (choice.equalsIgnoreCase("2")) {
                        selectedApt.setStatus(Appointment.Status.CANCELLED);
                    } else
                        System.out.println("\nInvalid choice. Status remains as " + current + ". \n");
                    break;

                // If status is CONFIRMED, prompt to choose appointment status (COMPLETE/CANCEL)
                case CONFIRMED:
                    System.out.print(
                            "\nPress \"*\" to return to Main Menu\n\nNew appointment status: \n1. Complete appointment \n2. Cancel appointment \nEnter new status (1/2): ");
                    choice1 = sc.nextLine().trim();

                    // Back to menu if enter "*"
                    if (choice1.equalsIgnoreCase("*")) {
                        break;
                    }

                    if (choice1.equalsIgnoreCase("1")) {
                        selectedApt.setStatus(Appointment.Status.COMPLETED);
                    } else if (choice1.equalsIgnoreCase("2")) {
                        selectedApt.setStatus(Appointment.Status.CANCELLED);
                    } else
                        System.out.println("Invalid option. PLease enter 1 or 2");
                    break;

                // If status is COMPLETED/CANCELLED, cannot be changed anymore
                case COMPLETED:
                case CANCELLED:
                    System.out.println("\nThis appointment is already " + current + ". No further changes allowed.\n");
                    break;
            }

            // Back to menu if enter "*"
            if (choice.equalsIgnoreCase("*") || choice1.equalsIgnoreCase("*")) {
                backToMenu(choice);
                break;
            }
            // Print updated appointment status
            System.out.println("Updated appointment status: \n" + selectedApt);
            FileStore.saveAppointments(appointments);
            break;
        }
    }

    // 7) Show appointments
    static void showAppointments() {

        // Check appointments arraylist is empty or not
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        // Print appointment list
        for (Appointment apt : appointments) {
            System.out.println(apt.toString());
        }

    }

    // 8) Add prescription
    static void addPrescription() {
        // Prompt to enter appointment code
        System.out.println("\nPress \"*\" to return to Main Menu\n");
        System.out.print("Enter Appointment Code (must be COMPLETED):");
        String aptCode = sc.nextLine().trim();

        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Check if appointment code exist
        Appointment selectedApt = null;
        for (Appointment apt : appointments) {
            if (apt.getAptCode().equalsIgnoreCase(aptCode)) {
                selectedApt = apt;
                break;
            }
        }

        if (selectedApt == null) {
            System.out.println("Invalid Appointment Code.");
            return;
        }

        // Make sure only COMPLETED appointment can add prescription
        if (selectedApt.getStatus() != Appointment.Status.COMPLETED) {
            System.out.println("Prescription can only be added to COMPLETED appointments.");
            return;
        }

        // Prompt to enter prescription details

        // Prompt to enter medicine name
        String medName;
        while (true) {
            System.out.print("Enter medicine name: ");
            medName = sc.nextLine().trim();

            // Check for empty input
            if (medName.isEmpty()) {
                System.out.println("Medicine name is compulsory. Please fill it in. ");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (medName.equalsIgnoreCase("*")) {
            backToMenu(medName);
            return;
        }

        // Prompt to enter medicine dosage
        String dosage;
        while (true) {
            System.out.print("Enter dosage (e.g. 1 times/day): ");
            dosage = sc.nextLine().trim();

            // Check for empty input
            if (dosage.isEmpty()) {
                System.out.println("Dosage is compulsory. Please fill it in. ");
            } else {
                break;
            }
        }

        // Back to menu if enter "*"
        if (dosage.equalsIgnoreCase("*")) {
            backToMenu(dosage);
            return;
        }

        // Prompt to enter medicine quantity
        int quantity;
        while (true) {
            System.out.print("Enter quantity: ");
            try {
                quantity = sc.nextInt();
                sc.nextLine();

                // Check if quantity falls in logical range (must be at least 1)
                if (quantity < 1) {
                    System.out.println("Invalid quantity.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) { // Check data type
                System.out.println("Please enter a number.");
                sc.nextLine();
            }
        }

        // Prompt to enter medicine unit price
        double unitPrice;
        while (true) {
            System.out.print("Enter Unit Price (RM): ");
            try {
                unitPrice = sc.nextDouble();
                sc.nextLine();
                // Check if unit price falls in logical range (must be +ve)
                if (unitPrice <= 0) {
                    System.out.println("Invalid unit price.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) { // Check data type, must be number
                System.out.println("Please enter a number.");
                sc.nextLine();
            }
        }

        // Create new prescription
        Prescription newPrescription = new Prescription(aptCode, medName, dosage, quantity, unitPrice);

        // Check if has the same medicine name and dosage under a single appointment
        boolean isMerged = false;
        Prescription existingPrescription = null;

        for (Prescription p : prescriptions) {
            if (p.getAptCode().equalsIgnoreCase(aptCode) &&
                    p.getMedName().equalsIgnoreCase(medName) &&
                    p.getDosage().equalsIgnoreCase(dosage)) {
                existingPrescription = p;
                isMerged = true;
                break;
            }
        }

        // Print confirmation for prescription
        if (!isMerged) {
            System.out.println("\nPrescription added successfully. \n" + newPrescription + "\n");
            // Add prescriptions
            prescriptions.add(newPrescription);
        } else {
            // Merge quantity of same medicine in existing prescription and new prescription
            existingPrescription.mergeQuantity(newPrescription);
            System.out.println("\nPrescription quantities merged due to same medicine name and dosage.\n"
                    + existingPrescription + "\n");
        }

        // Save prescription data
        FileStore.savePrescriptions(prescriptions);
    }

    // 9) Calculate Bill
    static void calcBill() {

        // Prompt to enter appointment code
        System.out.println("\nPress \"*\" to return to Main Menu\n");
        System.out.print("Enter Appointment Code: ");
        String aptCode = sc.nextLine().trim();

        // Back to menu if enter "*"
        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Check if appointment code exists
        Appointment selectedApt = findAppointmentByCode(aptCode);
        if (selectedApt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Check if appointment is completed
        if (selectedApt.getStatus() != Appointment.Status.COMPLETED) {
            System.out.println("Bill can only be generated when appointments are COMPLETED.");
            return;
        }

        // Create new bill
        Bill bill = new Bill(selectedApt, prescriptions);
        System.out.println("\n" + bill + "\n");
    }

    // 10) Take Payment
    static void takePayment() {

        // Prompt to enter appointment code
        System.out.println("\nPress \"*\" to return to Main Menu\n");
        System.out.print("Enter Appointment Code: ");
        String aptCode = sc.nextLine().trim();

        // Back to menu if enter "*"
        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Check if appointment code exists
        Appointment selectedApt = findAppointmentByCode(aptCode);
        if (selectedApt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Check if appointment is completed
        if (selectedApt.getStatus() != Appointment.Status.COMPLETED) {
            System.out.println("Payment can only be made when appointments are COMPLETED");
            return;
        }

        // Check if appointment is paid
        if (selectedApt.getIsPaid()) {
            System.out.println("The payment has been processed previously.");
            return;
        }

        // Create new bill
        Bill bill = new Bill(selectedApt, prescriptions);

        // Calculate bill total
        double total = bill.calcGrandTotal();
        if (total <= 0) {
            System.out.println("Invalid bill amount.");
            return;
        }

        // Print bill preview
        System.out.println(bill.toString());
        System.out.printf("Total amount: RM %.2f%n", total);

        // Prompt to choose payment method
        while (true) {
            System.out.println("\nPress \"*\" to return to Main Menu\n");
            System.out.print(
                    "Available payment methods: \n1. Card/E-Wallet \n2. Insurance \nEnter preferred payment method (1/2): ");
            String choice = sc.nextLine().trim();

            // Back to menu if enter "*"
            if (choice.equalsIgnoreCase("*")) {
                backToMenu(choice);
                break;
            }

            Payment payment;
            if (choice.equalsIgnoreCase("1")) {
                payment = new CardEWalletPayment();
                payment.process(total);
                selectedApt.setIsPaid(true);
                selectedApt.setPaymentMethod("Card / EWallet");
                FileStore.saveAppointments(appointments);
                System.out.println("\nPayment successfully. \n");
                return;
            } else if (choice.equalsIgnoreCase("2")) {
                payment = new InsurancePayment();
                payment.process(total);
                selectedApt.setIsPaid(true);
                selectedApt.setPaymentMethod("Insurance");
                FileStore.saveAppointments(appointments);
                System.out.println("\nPayment successfully. \n");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // 11) Print Receipt
    static void printReceipt() {
        // Prompt to enter appointment code
        System.out.println("\nPress \"*\" to return to Main Menu\n");
        System.out.print("Enter Appointment Code: ");
        String aptCode = sc.nextLine().trim();

        // Back to menu if enter "*"
        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Check if appointment exists
        Appointment selectedApt = findAppointmentByCode(aptCode);
        if (selectedApt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Check if payment is completed
        if (!selectedApt.getIsPaid()) {
            System.out.println("Receipt can only be printed when payment is made");
            return;
        }

        // Create new bill
        Bill bill = new Bill(selectedApt, prescriptions);

        // Print receipt
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        sb.append("+===========================================================================+\n");
        sb.append("|                                 RECEIPT                                   |\n");
        sb.append("+===========================================================================+\n");
        sb.append(String.format("| %s %-50s|%n", "Date                  :", LocalDateTime.now().format(dateTime)));
        sb.append(String.format("| %s %-50s|%n", "Appointment Code      :", selectedApt.getAptCode()));
        sb.append(String.format("| %s %-50s|%n", "Patient ID            :", selectedApt.getMaskedPatientId()));
        sb.append(String.format("| %s %-50s|%n", "Visit Type            :", selectedApt.getAptType()));

        sb.append("-----------------------------------------------------------------------------\n");
        sb.append("|                                     Fees                                  |\n");
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append(String.format("| %s %73s%n", " ", "(RM) |"));
        sb.append(String.format("| %-20s  %42s %8.2f |\n", "Consultation Fee :", "", 50.00));
        sb.append(String.format("| %-20s %-53s| \n", "Prescriptions    :", ""));

        for (Prescription p : prescriptions) {
            if (p.getAptCode().equals(selectedApt.getAptCode())) {
                sb.append(String.format("| %-6s %-15s  %3d x RM%6.2f %34.2f |\n",
                        " ", p.getMedName(), p.getQuantity(), p.getUnitPrice(), p.calcLineTotal()));
            }
        }

        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-36s %13.2f |\n", "Subtotal", "", bill.calcSubtotal()));
        sb.append(String.format("| %-20s | %-36s %13.2f |\n", "SST (6%)", "", bill.calcSST()));
        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-36s %13.2f |\n", "Grand Total", "", bill.calcGrandTotal()));
        sb.append("+---------------------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s : %-50s |\n", "Payment Method", selectedApt.getPaymentMethod()));
        sb.append("+---------------------------------------------------------------------------+\n\n");

        System.out.println(sb);
    }

    // 12) Fulfil Medicine
    static void fulfilMedicine() {

        String aptCode;
        Appointment selectedApt = null;
        while (true) {
            // Prompt to choose appointment code
            System.out.print("\nPress * to return to Main Menu\n");
            System.out.print("Enter Appointment Code (must be COMPLETED):");
            aptCode = sc.nextLine().trim();

            // Back to menu if enter "*"
            if (aptCode.equalsIgnoreCase("*")) {
                break;
            }

            // Check if appointment code exists
            for (Appointment apt : appointments) {
                if (apt.getAptCode().equalsIgnoreCase(aptCode)) {
                    selectedApt = apt;
                    break;
                }
            }

            if (selectedApt == null) {
                System.out.println("Appointment not found.");
            } else {
                // Check if medicine fulfilled
                if (selectedApt.getMedFulfilled()) {
                    System.out.println("Medicine fulfilled");
                    return;
                }

                // Check if payment is completed
                if (!selectedApt.getIsPaid()) {
                    System.out.println("Medicine can only be fulfilled when payment is made");
                    return;
                }
                break;
            }
        }

        // Back to menu if enter "*"
        if (aptCode.equalsIgnoreCase("*")) {
            backToMenu(aptCode);
            return;
        }

        // Prompt to choose fulfilment method
        String choice;
        if (selectedApt != null) {
            while (true) {
                System.out.println("\nPress * to return to Main Menu\n");
                System.out.print(
                        "Fulfilment Method: \n1. Counter Pickup \n2. Home Delivery \n\nEnter Fulfilment Method (1/2):");
                choice = sc.nextLine().trim();

                // Back to menu if enter "*"
                if (choice.equalsIgnoreCase("*")) {
                    break;
                }

                Fulfilment fulfilment;

                if (choice.equalsIgnoreCase("1")) {
                    fulfilment = new CounterPickup();
                    fulfilment.fulfil(aptCode);
                    selectedApt.setMedFulfilled(true);
                    FileStore.saveAppointments(appointments);
                    break;
                } else if (choice.equalsIgnoreCase("2")) {
                    fulfilment = new HomeDelivery();
                    fulfilment.fulfil(aptCode);
                    selectedApt.setMedFulfilled(true);
                    FileStore.saveAppointments(appointments);
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2. ");
                }
            }
        }
    }

    public static void main(String[] args) {

        // When program starts, load saved data
        loadAllFiles();

        // If nothing exists, seed doctor
        seedDoctor();

        while (true) {
            // Print menu and prompt to enter main menu choice
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    registerCaregiver();
                    break;

                case "2":
                    addDependent();
                    break;

                case "3":
                    listFamily();
                    break;

                case "4":
                    addOrListDoctors();
                    break;

                case "5":
                    bookAppointment();
                    break;

                case "6":
                    updateAptStatus();
                    break;

                case "7":
                    showAppointments();
                    break;

                case "8":
                    addPrescription();
                    break;

                case "9":
                    calcBill();
                    break;

                case "10":
                    takePayment();
                    break;

                case "11":
                    printReceipt();
                    break;

                case "12":
                    fulfilMedicine();
                    break;

                case "13":
                    saveAllFiles();
                    sc.close();
                    System.out.println("Goodbye :)");
                    System.exit(0); // Exit program
                    break;

                default:
                    System.out.println("Invalid choice. Please try again. ");
            }
        }
    }
}
