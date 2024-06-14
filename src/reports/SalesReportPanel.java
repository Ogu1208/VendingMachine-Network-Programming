package reports;

import util.SalesData;
import util.SalesManager;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SalesReportPanel extends JPanel {

    private SalesTableModel tableModel;
    private JTable salesTable;
    private TableRowSorter<SalesTableModel> sorter;

    public SalesReportPanel(SalesManager salesManager) {
        setLayout(new BorderLayout());

        // ���̺� �� ����
        tableModel = new SalesTableModel();
        salesTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        salesTable.setRowSorter(sorter);

        // ��¥ �� ������ ���� Ŀ���� Comparator �߰�
        sorter.setComparator(0, new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(String date1, String date2) {
                try {
                    return dateFormat.parse(date1).compareTo(dateFormat.parse(date2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        // ������ ä���
        List<SalesData> salesDataList = salesManager.getSalesList();
        for (SalesData salesData : salesDataList) {
            tableModel.addRow(new Object[]{salesData.getDate(), salesData.getCanName(), salesData.getTotalSales(), salesData.getQuantitySold()});
        }

        // ��ũ�� �ҿ� ���̺� �߰�
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);

        // ��ư �г� ����
        JPanel buttonPanel = new JPanel();
        JButton sortByDateButton = new JButton("��¥�� ����");
        JButton sortByPriceButton = new JButton("����׼� ����");
        JButton sortByVolumeButton = new JButton("�Ǹŷ��� ����");
        buttonPanel.add(sortByDateButton);
        buttonPanel.add(sortByPriceButton);
        buttonPanel.add(sortByVolumeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // ��ư �̺�Ʈ ������ �߰�
        sortByDateButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING))));
        sortByPriceButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(2, SortOrder.DESCENDING))));
        sortByVolumeButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING))));
    }
}