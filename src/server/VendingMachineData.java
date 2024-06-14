package server;

import Can.Can;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VendingMachineData {
    Map<String, Integer> inventory = new HashMap<>();
    Map<String, Map<String, Integer>> dailySales = new HashMap<>();
    Map<String, Map<String, Integer>> monthlySales = new HashMap<>();

    // �ʱ�ȭ �޼���
    public void initializeInventory(List<Can> cans) {
        for (Can can : cans) {
            inventory.put(can.getCanName(), can.getCanNum());
        }
    }

    // �Ǹ� ��� �߰� �޼���
    public void addSale(String canName, int quantity, String date) {
        // ��� ������Ʈ
        inventory.put(canName, inventory.getOrDefault(canName, 0) - quantity);

        // �Ϻ� ����
        dailySales.putIfAbsent(date, new HashMap<>());
        dailySales.get(date).put(canName, dailySales.get(date).getOrDefault(canName, 0) + quantity);

        // ���� ����
        String month = date.substring(0, 7); // "yyyy-MM"
        monthlySales.putIfAbsent(month, new HashMap<>());
        monthlySales.get(month).put(canName, monthlySales.get(month).getOrDefault(canName, 0) + quantity);
    }
}