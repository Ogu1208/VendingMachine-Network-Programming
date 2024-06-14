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

        // 테이블 모델 설정
        tableModel = new SalesTableModel();
        salesTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        salesTable.setRowSorter(sorter);

        // 날짜 열 정렬을 위해 커스텀 Comparator 추가
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

        // 데이터 채우기
        List<SalesData> salesDataList = salesManager.getSalesList();
        for (SalesData salesData : salesDataList) {
            tableModel.addRow(new Object[]{salesData.getDate(), salesData.getCanName(), salesData.getTotalSales(), salesData.getQuantitySold()});
        }

        // 스크롤 팬에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(salesTable);
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton sortByDateButton = new JButton("날짜순 정렬");
        JButton sortByPriceButton = new JButton("매출액순 정렬");
        JButton sortByVolumeButton = new JButton("판매량순 정렬");
        buttonPanel.add(sortByDateButton);
        buttonPanel.add(sortByPriceButton);
        buttonPanel.add(sortByVolumeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 버튼 이벤트 리스너 추가
        sortByDateButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING))));
        sortByPriceButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(2, SortOrder.DESCENDING))));
        sortByVolumeButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING))));
    }
}