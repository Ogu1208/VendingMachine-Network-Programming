package server;

import Can.Can;

import java.util.HashMap;
import java.util.List;
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
        // 재고 감소
        inventory.put(canName, inventory.getOrDefault(canName, 10) - quantity);

        // 재고가 음수가 되지 않도록 조정
        if (inventory.get(canName) < 0) {
            inventory.put(canName, 0);
        }

        // 일별 매출 추가
        dailySales.putIfAbsent(date, new HashMap<>());
        dailySales.get(date).put(canName, dailySales.get(date).getOrDefault(canName, 0) + quantity);

        // 월별 매출 추가
        String month = date.substring(0, 7);
        monthlySales.putIfAbsent(month, new HashMap<>());
        monthlySales.get(month).put(canName, monthlySales.get(month).getOrDefault(canName, 0) + quantity);
    }
}