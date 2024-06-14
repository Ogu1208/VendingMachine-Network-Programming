package reports;

import util.SalesData;

import javax.swing.table.DefaultTableModel;

public class SalesTableModel extends DefaultTableModel {
    private static final String[] COLUMN_NAMES = {"날짜", "음료", "매출액", "판매량"};

    public SalesTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class; // 날짜
            case 1:
                return String.class; // 음료 이름
            case 2:
                return Integer.class; // 매출액
            case 3:
                return Integer.class; // 판매량
            default:
                return Object.class;
        }
    }

    public void addSalesData(SalesData salesData) {
        addRow(new Object[]{
                salesData.getDate(),
                salesData.getCanName(),
                salesData.getTotalSales(),
                salesData.getQuantitySold()
        });
    }
}