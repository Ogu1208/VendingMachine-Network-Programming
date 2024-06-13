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

        // 4월, 5월의 일별 매출 데이터 생성
        String[] months = {"2024-04", "2024-05"};
        String[] canNames = {"물", "커피", "이온음료", "고급커피", "탄산음료", "특화음료"};
        int[] prices = {450, 500, 550, 700, 750, 800};

        for (String month : months) {
            for (int day = 1; day <= 30; day++) {
                String date = month + "-" + (day < 10 ? "0" + day : day);
                for (int i = 0; i < canNames.length; i++) {
                    int quantitySold = (int) (Math.random() * 10) + 1; // 1에서 10 사이의 임의의 판매량
                    int totalSales = quantitySold * prices[i];
                    initialSalesData.add(new SalesData(canNames[i], quantitySold, totalSales, date));
                }
            }
        }

        // 초기 데이터 파일에 저장
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INITIAL_SALES_DATA_FILE))) {
            oos.writeObject(initialSalesData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Initial sales data generated and saved to " + INITIAL_SALES_DATA_FILE);
    }
}
