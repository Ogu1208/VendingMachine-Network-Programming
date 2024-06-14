package server;

import Can.Can;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VendingMachineData {
    Map<String, Integer> inventory = new HashMap<>();
    Map<String, Map<String, Integer>> dailySales = new HashMap<>();
    Map<String, Map<String, Integer>> monthlySales = new HashMap<>();

    // 초기화 메서드
    public void initializeInventory(List<Can> cans) {
        for (Can can : cans) {
            inventory.put(can.getCanName(), can.getCanNum());
        }
    }

    // 판매 기록 추가 메서드
    public void addSale(String canName, int quantity, String date) {
        // 재고 업데이트
        inventory.put(canName, inventory.getOrDefault(canName, 0) - quantity);

        // 일별 매출
        dailySales.putIfAbsent(date, new HashMap<>());
        dailySales.get(date).put(canName, dailySales.get(date).getOrDefault(canName, 0) + quantity);

        // 월별 매출
        String month = date.substring(0, 7); // "yyyy-MM"
        monthlySales.putIfAbsent(month, new HashMap<>());
        monthlySales.get(month).put(canName, monthlySales.get(month).getOrDefault(canName, 0) + quantity);
    }
}