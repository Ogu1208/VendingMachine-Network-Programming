package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SalesManager {



    private static final String SALES_DATA_FILE = "sales_data.dat";
    private static final String INITIAL_SALES_DATA_FILE = "initial_sales_data.dat";
    private List<SalesData> salesList;

    public SalesManager() {
        salesList = new ArrayList<>();
        loadInitialSalesData();
        loadSalesData();
    }

    public void addSales(SalesData salesData) {
        salesList.add(salesData);
        saveSalesData();
    }

    public List<SalesData> getSalesList() {
        return salesList;
    }

    public void saveSalesData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_DATA_FILE))) {
            oos.writeObject(salesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadSalesData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SALES_DATA_FILE))) {
            salesList = (List<SalesData>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadInitialSalesData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INITIAL_SALES_DATA_FILE))) {
            List<SalesData> initialData = (List<SalesData>) ois.readObject();
            salesList.addAll(initialData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
