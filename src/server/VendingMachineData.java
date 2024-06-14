package server;

import Can.Can;
import Can.CanArray;
import util.SalesData;

import java.util.*;

import java.util.HashMap;
import java.util.Map;

public class VendingMachineData {
    public Map<String, Integer> inventory;
    public Map<String, Map<String, Integer>> dailySales;
    public Map<String, Map<String, Integer>> monthlySales;

    public VendingMachineData() {
        inventory = new HashMap<>();
        dailySales = new HashMap<>();
        monthlySales = new HashMap<>();
    }

    public void addSale(String canName, int quantity, String date) {
        int canPrice = getCanPrice(canName);

        // ��� ����
        inventory.put(canName, inventory.getOrDefault(canName, 10) - quantity);

        // ��� ������ ���� �ʵ��� ����
        if (inventory.get(canName) < 0) {
            inventory.put(canName, 0);
        }

        // �Ϻ� ���� �߰�
        dailySales.putIfAbsent(date, new HashMap<>());
        dailySales.get(date).put(canName, dailySales.get(date).getOrDefault(canName, 0) + quantity);

        // ���� ���� �߰�
        String month = date.substring(0, 7);
        monthlySales.putIfAbsent(month, new HashMap<>());
        monthlySales.get(month).put(canName, monthlySales.get(month).getOrDefault(canName, 0) + quantity);
    }

    public void updateInventory(String canName, int quantity) {
        inventory.put(canName, quantity);
    }

    public List<SalesData> getDailySalesList() {
        List<SalesData> dailySalesList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : dailySales.entrySet()) {
            String date = entry.getKey();
            for (Map.Entry<String, Integer> sale : entry.getValue().entrySet()) {
                dailySalesList.add(new SalesData(sale.getKey(), sale.getValue(), getCanPrice(sale.getKey()), date));
            }
        }
        return dailySalesList;
    }

    public List<SalesData> getMonthlySalesList() {
        List<SalesData> monthlySalesList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : monthlySales.entrySet()) {
            String month = entry.getKey();
            for (Map.Entry<String, Integer> sale : entry.getValue().entrySet()) {
                monthlySalesList.add(new SalesData(sale.getKey(), sale.getValue(), getCanPrice(sale.getKey()), month));
            }
        }
        return monthlySalesList;
    }

    private int getCanPrice(String canName) {
        for (Can can : CanArray.canList) {
            if (can.getCanName().equals(canName)) {
                return can.getCanPrice();
            }
        }
        return 0; // ������ ã�� �� ���� ��� �⺻�� ��ȯ
    }
}