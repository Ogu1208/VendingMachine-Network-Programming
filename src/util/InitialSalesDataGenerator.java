package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InitialSalesDataGenerator {

    private static final String INITIAL_SALES_DATA_FILE = "initial_sales_data.dat";

    public static void main(String[] args) {
        List<SalesData> initialSalesData = new ArrayList<>();

        // 4��, 5���� �Ϻ� ���� ������ ����
        String[] months = {"2024-04", "2024-05"};
        String[] canNames = {"��", "Ŀ��", "�̿�����", "���Ŀ��", "ź������", "Ưȭ����"};
        int[] prices = {450, 500, 550, 700, 750, 800};

        for (String month : months) {
            for (int day = 1; day <= 30; day++) {
                String date = month + "-" + (day < 10 ? "0" + day : day);
                for (int i = 0; i < canNames.length; i++) {
                    int quantitySold = (int) (Math.random() * 10) + 1; // 1���� 10 ������ ������ �Ǹŷ�
                    int totalSales = quantitySold * prices[i];
                    initialSalesData.add(new SalesData(canNames[i], quantitySold, totalSales, date));
                }
            }
        }

        // �ʱ� ������ ���Ͽ� ����
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INITIAL_SALES_DATA_FILE))) {
            oos.writeObject(initialSalesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Initial sales data generated and saved to " + INITIAL_SALES_DATA_FILE);
    }
}
