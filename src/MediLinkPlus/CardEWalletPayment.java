package MediLinkPlus;

public class CardEWalletPayment implements Payment {
    @Override
    public void process(double amount) {
        System.out.printf("\n[CARD/E-Wallet] Approved. Charged RM %.2f%n", amount);
    }

    @Override
    public String method() {
        return "Card/E-Wallet Payment";
    }
}
