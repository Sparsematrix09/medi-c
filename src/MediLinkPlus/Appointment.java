package MediLinkPlus;

public class Appointment {

    // Enum acting as constructor
    public enum Status {
        REQUESTED,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    private String aptCode;
    private String aptType;
    private String patientId;
    private String drCode;
    private String drName;
    private String aptTime;
    private Status status;
    private boolean isPaid;
    private boolean medFulfilled;
    private String paymentMethod;

    // Default constructor
    public Appointment(String aptCode, String aptType, String patientId, String drCode, String drName, String aptTime) {
        this.aptCode = aptCode;
        this.aptType = aptType.toUpperCase();
        this.patientId = patientId;
        this.drCode = drCode;
        this.drName = drName;
        this.aptTime = aptTime;
        this.status = Status.REQUESTED; // default
        this.isPaid = false;
        this.medFulfilled = false;
        this.paymentMethod = "-";

    }

    // Loaded constructor (updated status)
    public Appointment(String aptCode, String aptType, String patientId, String drCode, String drName, String aptTime, Status status, boolean isPaid, boolean medFulfilled, String paymentMethod) {
        this.aptCode = aptCode;
        this.aptType = aptType;
        this.patientId = patientId;
        this.drCode = drCode;
        this.drName = drName;
        this.aptTime = aptTime;
        this.status = status;
        this.isPaid = isPaid;
        this.medFulfilled = medFulfilled;
        this.paymentMethod = paymentMethod;
    }

    // Getter and Setter
    public String getAptCode() {
        return aptCode;
    }

    public void setAptCode(String aptCode) {
        this.aptCode = aptCode;
    }

    public String getAptType() {
        return aptType;
    }

    public void setAptType(String aptType) {
        this.aptType = aptType;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getAptTime() {
        return aptTime;
    }

    public void setAptTime(String aptTime) {
        this.aptTime = aptTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getMedFulfilled() {
        return medFulfilled;
    }

    public void setMedFulfilled(boolean medFulfilled) {
        this.medFulfilled = medFulfilled;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMaskedPatientId() {
        if (patientId.length() <= 4) {
            return "*".repeat(patientId.length());
        } else {
            int visible = 4;
            int masked = patientId.length() - visible;
            return "*".repeat(masked) + patientId.substring(patientId.length() - visible);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-35s |\n", "Field", "Value"));
        sb.append("+------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-35s |\n", "Appointment Code", aptCode));
        sb.append(String.format("| %-20s | %-35s |\n", "Appointment Type", aptType));
        sb.append(String.format("| %-20s | %-35s |\n", "Patient ID", getMaskedPatientId()));
        sb.append(String.format("| %-20s | %-35s |\n", "Doctor Code", drCode));
        sb.append(String.format("| %-20s | %-35s |\n", "Doctor Name", ("Dr " + drName)));
        sb.append(String.format("| %-20s | %-35s |\n", "Date/Time", aptTime));
        sb.append(String.format("| %-20s | %-35s |\n", "Status", status));
        sb.append("+------------------------------------------------------------+\n");

        return sb.toString();
    }

}

