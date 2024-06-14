package server;

import util.SalesData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import Can.Can;
import Can.CanArray;

public class VendingMachineServerGUI extends JFrame {
    private JTextArea textArea;
    private JTabbedPane tabbedPane;
    private JTable salesTable;
    private JTable stockTable;
    private DefaultTableModel salesTableModel;
    private DefaultTableModel stockTableModel;
    private Server1 server;

    public VendingMachineServerGUI(Server1 server) {
        this.server = server;
        setTitle("Vending Machine Server");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Sales Data Panel
        JPanel salesPanel = new JPanel(new BorderLayout());
        salesTableModel = new DefaultTableModel(new String[]{"Client ID", "날짜", "음료", "매출액", "판매량"}, 0);
        salesTable = new JTable(salesTableModel);
        salesPanel.add(new JScrollPane(salesTable), BorderLayout.CENTER);

        JPanel salesButtonPanel = new JPanel();
        JButton btnRefreshSales = new JButton("Refresh Sales Data");
        btnRefreshSales.addActionListener(e -> refreshSalesData());
        salesButtonPanel.add(btnRefreshSales);
        salesPanel.add(salesButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Sales Data", salesPanel);

        // Stock Data Panel
        JPanel stockPanel = new JPanel(new BorderLayout());
        stockTableModel = new DefaultTableModel(new String[]{"음료", "재고"}, 0);
        stockTable = new JTable(stockTableModel);
        stockPanel.add(new JScrollPane(stockTable), BorderLayout.CENTER);

        JPanel stockButtonPanel = new JPanel();
        JButton btnRefreshStock = new JButton("Refresh Stock Data");
        btnRefreshStock.addActionListener(e -> refreshStockData());
        stockButtonPanel.add(btnRefreshStock);
        stockPanel.add(stockButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Stock Data", stockPanel);

        // Notification Panel
        JPanel notificationPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        notificationPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        tabbedPane.addTab("Notifications", notificationPanel);

        // Load initial data
        refreshSalesData();
        refreshStockData();

        setVisible(true);
    }

    public void refreshSalesData() {
        List<SalesData> salesDataList = server.getSalesDataList();
        salesTableModel.setRowCount(0); // Clear existing data

        // Aggregate sales data
        Map<String, int[]> aggregatedData = new TreeMap<>();
        for (SalesData salesData : salesDataList) {
            String key = salesData.getClientId() + "_" + salesData.getDate() + "_" + salesData.getCanName();
            aggregatedData.putIfAbsent(key, new int[2]);
            aggregatedData.get(key)[0] += salesData.getTotalSales();
            aggregatedData.get(key)[1] += salesData.getQuantitySold();
        }

        // Add aggregated data to table
        for (Map.Entry<String, int[]> entry : aggregatedData.entrySet()) {
            String[] keyParts = entry.getKey().split("_");
            String clientId = keyParts[0];
            String date = keyParts[1];
            String canName = keyParts[2];
            int totalSales = entry.getValue()[0];
            int quantitySold = entry.getValue()[1];
            salesTableModel.addRow(new Object[]{clientId, date, canName, totalSales, quantitySold});
        }
    }

    public void refreshStockData() {
        List<Can> canList = CanArray.canList;
        stockTableModel.setRowCount(0); // Clear existing data

        for (Can can : canList) {
            stockTableModel.addRow(new Object[]{can.getCanName(), can.getCanNum()});
        }

        // Check for low stock and notify
        for (Can can : canList) {
            if (can.getCanNum() < 5) { // Threshold for low stock
                textArea.append("Warning: Low stock for " + can.getCanName() + "\n");
            }
        }
    }
}