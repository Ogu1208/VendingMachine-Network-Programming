package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SalesDataInitializer {

    private static final String SALES_DATA_FILE = "sales_data.dat";

    public static void main(String[] args) {
        List<SalesData> initialSalesData = new ArrayList<>();
        initialSalesData.add(new SalesData("��", 10, 4500));  // ���� ������
        initialSalesData.add(new SalesData("Ŀ��", 8, 4000));  // ���� ������
        initialSalesData.add(new SalesData("�̿�����", 5, 2750));  // ���� ������

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_DATA_FILE))) {
            oos.writeObject(initialSalesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Initial sales data has been written to sales_data.dat.");
    }
}
