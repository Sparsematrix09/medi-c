package MediLinkPlus;

public class InsurancePayment implements Payment {
    @Override
    public void process(double amount) {
        System.out.printf("\n[INSURANCE] Approved. Charged RM %.2f%n", amount);
    }

    @Override
    public String method() {
        return "Insurance";
    }
}
