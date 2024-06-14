package client;

public interface ClientInterface {
    void sendSale(String canName, int quantity);
    void sendInventoryUpdate(String canName, int quantity);
    void requestInventory();
    void requestDailySales();
    void requestMonthlySales();
}