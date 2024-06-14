package server;

import Can.CanArray;

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
        setTitle("���Ǳ� ���� ����");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        dataManager = new ServerDataManager();

        JPanel buttonPanel = new JPanel();
        JButton client1Button = new JButton("Client1 ������ Ȯ��");
        JButton client2Button = new JButton("Client2 ������ Ȯ��");
        buttonPanel.add(client1Button);
        buttonPanel.add(client2Button);

        client1Button.addActionListener(e -> showClientData("Client1"));
        client2Button.addActionListener(e -> showClientData("Client2"));

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(12345);
                log("���� ���۵�: ��Ʈ 12345");

                while (true) {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket, dataManager, logArea).start();
                }
            } catch (IOException e) {
                log("���� ����: " + e.getMessage());
            }
        }).start();
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    private void showClientData(String clientId) {
        JFrame dataFrame = new JFrame(clientId + " ������");
        dataFrame.setSize(600, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ��� ��Ȳ
        String[] inventoryColumns = {"���� �̸�", "��� ����"};
        DefaultTableModel inventoryModel = new DefaultTableModel(inventoryColumns, 0);
        JTable inventoryTable = new JTable(inventoryModel);

        VendingMachineData data = dataManager.getClientData(clientId);
        for (String canName : data.inventory.keySet()) {
            int quantity = data.inventory.get(canName);
            inventoryModel.addRow(new Object[]{canName, quantity});
        }
        tabbedPane.addTab("��� ��Ȳ", new JScrollPane(inventoryTable));

        // �Ϻ� ����
        String[] dailySalesColumns = {"��¥", "���� �̸�", "�Ǹŷ�"};
        DefaultTableModel dailySalesModel = new DefaultTableModel(dailySalesColumns, 0);
        JTable dailySalesTable = new JTable(dailySalesModel);

        for (String date : data.dailySales.keySet()) {
            for (String canName : data.dailySales.get(date).keySet()) {
                int quantity = data.dailySales.get(date).get(canName);
                dailySalesModel.addRow(new Object[]{date, canName, quantity});
            }
        }
        tabbedPane.addTab("�Ϻ� ����", new JScrollPane(dailySalesTable));

        // ���� ����
        String[] monthlySalesColumns = {"��", "���� �̸�", "�Ǹŷ�"};
        DefaultTableModel monthlySalesModel = new DefaultTableModel(monthlySalesColumns, 0);
        JTable monthlySalesTable = new JTable(monthlySalesModel);

        for (String month : data.monthlySales.keySet()) {
            for (String canName : data.monthlySales.get(month).keySet()) {
                int quantity = data.monthlySales.get(month).get(canName);
                monthlySalesModel.addRow(new Object[]{month, canName, quantity});
            }
        }
        tabbedPane.addTab("���� ����", new JScrollPane(monthlySalesTable));

        dataFrame.add(tabbedPane);
        dataFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerMain::new);
    }
}