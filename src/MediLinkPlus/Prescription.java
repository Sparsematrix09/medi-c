package MediLinkPlus;

public class Prescription {
    private String aptCode;
    private String medName;
    private String dosage;
    private int quantity;
    private double unitPrice;

    public Prescription(String aptCode, String medName, String dosage, int quantity, double unitPrice) {
        this.aptCode = aptCode;
        this.medName = medName;
        this.dosage = dosage;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getAptCode() {
        return aptCode;
    }
    public void setAptCode(String aptCode) {
        this.aptCode = aptCode;
    }

    public String getMedName() {
        return medName;
    }
    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getDosage(){
        return dosage;
    }
    public void setDosage(String dosage){
        this.dosage = dosage;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice){
        this.unitPrice = unitPrice;
    }

    public double calcLineTotal() {
        return quantity * unitPrice;
    }

    public void mergeQuantity(Prescription prescription) {
        if (this.medName.equalsIgnoreCase(prescription.medName) && this.dosage.equalsIgnoreCase(prescription.dosage)) {
            this.quantity += prescription.getQuantity();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-35s |\n", "Field", "Value"));
        sb.append("+------------------------------------------------------------+\n");
        sb.append(String.format("| %-20s | %-35s |\n", "Appoinment Code", aptCode));
        sb.append(String.format("| %-20s | %-35s |\n", "Medicine Name", medName));
        sb.append(String.format("| %-20s | %-35s |\n", "Dosage", dosage));
        sb.append(String.format("| %-20s | %-35s |\n", "Quantity", quantity));
        sb.append(String.format("| %-20s | %-35s |\n", "Unit Price", String.format("RM %.2f", unitPrice)));
        sb.append(String.format("| %-20s | %-35s |\n", "Line Total", String.format("RM %.2f", calcLineTotal())));
        sb.append("+------------------------------------------------------------+\n");

        return sb.toString();
    }
}

