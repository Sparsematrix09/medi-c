package MediLinkPlus;

public class Doctor {
    private String drCode;
    private String drName;

    // Constructor
    public Doctor(String drCode, String drName) {
        this.drCode = drCode;
        this.drName = drName;
    }

    // Getter and Setter
    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String doctorName) {
        this.drName = doctorName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %-25s | %-25s |\n", drCode, "Dr " + drName));
        sb.append("+-------------------------------------------------------+");

        return sb.toString();
    }
}
