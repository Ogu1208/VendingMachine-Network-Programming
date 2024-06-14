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
        createGUI();
        dataManager = new ServerDataManager();

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

    private void createGUI() {
        setTitle("���Ǳ� ���� ����");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        // Ŭ���̾�Ʈ ������ Ȯ�� ��ư
        JButton checkDataButton = new JButton("Ŭ���̾�Ʈ ������ Ȯ��");
        checkDataButton.addActionListener(e -> showClientData());
        add(checkDataButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showClientData() {
        JFrame dataFrame = new JFrame("Ŭ���̾�Ʈ ������");
        dataFrame.setSize(600, 400);
        JTextArea dataArea = new JTextArea();
        dataArea.setEditable(false);
        dataFrame.add(new JScrollPane(dataArea), BorderLayout.CENTER);

        for (String clientId : dataManager.clientData.keySet()) {
            VendingMachineData data = dataManager.getClientData(clientId);
            dataArea.append("Ŭ���̾�Ʈ: " + clientId + "\n");
            dataArea.append("��� ��Ȳ:\n");
            for (String canName : data.inventory.keySet()) {
                dataArea.append(canName + ": " + data.inventory.get(canName) + "\n");
            }
            dataArea.append("�Ϻ� ����:\n");
            for (String date : data.dailySales.keySet()) {
                dataArea.append(date + ": " + data.dailySales.get(date).toString() + "\n");
            }
            dataArea.append("���� ����:\n");
            for (String month : data.monthlySales.keySet()) {
                dataArea.append(month + ": " + data.monthlySales.get(month).toString() + "\n");
            }
            dataArea.append("\n");
        }

        dataFrame.setVisible(true);
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerMain::new);
    }
}