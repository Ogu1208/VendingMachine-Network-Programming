package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SalesManager {

    private static final String SALES_DATA_FILE = "sales_data.dat";
    private List<SalesData> salesList;

    public SalesManager() {
        salesList = new ArrayList<>();
        loadSalesData();
    }

    public List<SalesData> getSalesList() {
        return salesList;
    }

    public void addSale(SalesData sale) {
        salesList.add(sale);
        saveSalesData();
    }

    @SuppressWarnings("unchecked")
    public void loadSalesData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SALES_DATA_FILE))) {
            salesList = (List<SalesData>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveSalesData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_DATA_FILE))) {
            oos.writeObject(salesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
