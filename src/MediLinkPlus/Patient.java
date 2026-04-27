package MediLinkPlus;

public abstract class Patient {

    protected String patientId;
    protected String patientIdType;
    protected String patientName;

    public Patient(String patientId, String patientIdType, String patientName) {
        this.patientId = patientId;
        this.patientIdType = patientIdType;
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdType() {
        return patientIdType;
    }
    public void setPatientIdType(String patientIdType) {
        this.patientIdType = patientIdType;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    // Masked ID for privacy
    public String getMaskedId() {
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
        sb.append("+---------------------------------------------------------------+\n");
        sb.append(String.format("| %-15s | %-35s |\n", "Field", "Value"));
        sb.append("+---------------------------------------------------------------+\n");
        sb.append(String.format("| %-15s | %-35s |\n", "Patient ID", getMaskedId()));
        sb.append(String.format("| %-15s | %-35s |\n", "Patient ID Type", patientIdType));
        sb.append(String.format("| %-15s | %-35s |\n", "Patient Name", patientName));
        sb.append("+---------------------------------------------------------------+\n");

        return sb.toString();
    }

}

