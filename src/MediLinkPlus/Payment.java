package MediLinkPlus;

public interface Payment {
    void process(double amount);
    String method();
}
