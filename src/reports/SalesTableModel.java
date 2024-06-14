package reports;

import util.SalesData;

import javax.swing.table.DefaultTableModel;

public class SalesTableModel extends DefaultTableModel {
    private static final String[] COLUMN_NAMES = {"��¥", "����", "�����", "�Ǹŷ�"};

    public SalesTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class; // ��¥
            case 1:
                return String.class; // ���� �̸�
            case 2:
                return Integer.class; // �����
            case 3:
                return Integer.class; // �Ǹŷ�
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