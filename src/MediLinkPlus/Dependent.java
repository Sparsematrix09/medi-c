package MediLinkPlus;

public class Dependent extends Patient{

    private String careGiverId;

    public Dependent(String dependentId, String dependentIdType, String dependentName, String careGiverId) {
        super(dependentId, dependentIdType, dependentName);
        this.careGiverId = careGiverId;
    }

    // Getter
    public String getDependentId() {
        return getPatientId();
    }

    public String getDependentIdType() {
        return getPatientIdType();
    }

    public String getDependentName() {
        return getPatientName();
    }

    public String getCareGiverId() {
        return careGiverId;
    }

    // Masked Caregiver ID for privacy
    public String getMaskedCgId() {
        if (careGiverId.length() <= 4) {
            return "*".repeat(careGiverId.length());
        } else {
            int visible = 4;
            int masked = careGiverId.length() - visible;
            return "*".repeat(masked) + careGiverId.substring(careGiverId.length() - visible);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+===============================================================================================================+\n");
        sb.append(String.format("| %-25s | %-25s | %-25s | %-25s |\n", "Dependent ID", "ID Type", "Dependent Name", "Caregiver Id"));
        sb.append("+===============================================================================================================+\n");
        sb.append(String.format("| %-25s | %-25s | %-25s | %-25s |\n", getMaskedId(), getDependentIdType(), getDependentName(), getMaskedCgId()));
        sb.append("+---------------------------------------------------------------------------------------------------------------+\n");

        return sb.toString();
    }

}
