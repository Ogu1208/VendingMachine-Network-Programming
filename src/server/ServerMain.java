package server;

import Can.CanArray;
import util.SalesData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMain extends JFrame {
    private ServerSocket serverSocket;
    private JTextArea logArea;
    private ServerDataManager dataManager;

    public ServerMain() {
        setTitle("자판기 관리 서버");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        dataManager = new ServerDataManager();

        JPanel buttonPanel = new JPanel();
        JButton client1Button = new JButton("Client1 데이터 확인");
        JButton client2Button = new JButton("Client2 데이터 확인");
        buttonPanel.add(client1Button);
        buttonPanel.add(client2Button);

        client1Button.addActionListener(e -> showClientData("Client1"));
        client2Button.addActionListener(e -> showClientData("Client2"));

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12345);
                log("서버 시작됨: 포트 12345");

                while (true) {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket, dataManager, logArea).start();
                }
            } catch (IOException e) {
                log("서버 오류: " + e.getMessage());
            }
        }).start();
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    private void showClientData(String clientId) {
        JFrame dataFrame = new JFrame(clientId + " 데이터");
        dataFrame.setSize(600, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 재고 현황
        String[] inventoryColumns = {"음료 이름", "재고 수량"};
        DefaultTableModel inventoryModel = new DefaultTableModel(inventoryColumns, 0);
        JTable inventoryTable = new JTable(inventoryModel);

        VendingMachineData data = dataManager.getClientData(clientId);
        for (String canName : data.inventory.keySet()) {
            int quantity = data.inventory.get(canName);
            inventoryModel.addRow(new Object[]{canName, quantity});
        }
        tabbedPane.addTab("재고 현황", new JScrollPane(inventoryTable));

        // 일별 매출
        String[] dailySalesColumns = {"날짜", "음료 이름", "판매량"};
        DefaultTableModel dailySalesModel = new DefaultTableModel(dailySalesColumns, 0);
        JTable dailySalesTable = new JTable(dailySalesModel);

        for (SalesData salesData : data.getDailySalesList()) {
            dailySalesModel.addRow(new Object[]{salesData.getDate(), salesData.getCanName(), salesData.getQuantitySold()});
        }
        tabbedPane.addTab("일별 매출", new JScrollPane(dailySalesTable));

        // 월별 매출
        String[] monthlySalesColumns = {"월", "음료 이름", "판매량"};
        DefaultTableModel monthlySalesModel = new DefaultTableModel(monthlySalesColumns, 0);
        JTable monthlySalesTable = new JTable(monthlySalesModel);

        for (SalesData salesData : data.getMonthlySalesList()) {
            monthlySalesModel.addRow(new Object[]{salesData.getDate().substring(0, 7), salesData.getCanName(), salesData.getQuantitySold()});
        }
        tabbedPane.addTab("월별 매출", new JScrollPane(monthlySalesTable));

        dataFrame.add(tabbedPane);
        dataFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerMain::new);
    }
}