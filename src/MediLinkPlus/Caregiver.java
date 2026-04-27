package MediLinkPlus;

public class Caregiver extends Patient {

    public Caregiver(String careGiverId, String careGiverIdType, String careGiverName) {
        super(careGiverId, careGiverIdType, careGiverName);
    }

    // Getter
    public String getCareGiverId() {
        return getPatientId();
    }

    public String getCareGiverIdType(){
        return getPatientIdType();
    }

    public String getCareGiverName() {
        return getPatientName();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+===================================================================================+\n");
        sb.append(String.format("| %-25s | %-25s | %-25s |\n", "Caregiver ID", "ID Type", "Caregiver Name"));
        sb.append("+===================================================================================+\n");
        sb.append(String.format("| %-25s | %-25s | %-25s |\n", getMaskedId(), getCareGiverIdType(), getCareGiverName()));
        sb.append("+-----------------------------------------------------------------------------------+\n");

        return sb.toString();
    }

}
