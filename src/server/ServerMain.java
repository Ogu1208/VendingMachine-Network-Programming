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

    private void createGUI() {
        setTitle("자판기 관리 서버");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        // 클라이언트 데이터 확인 버튼
        JButton checkDataButton = new JButton("클라이언트 데이터 확인");
        checkDataButton.addActionListener(e -> showClientData());
        add(checkDataButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showClientData() {
        JFrame dataFrame = new JFrame("클라이언트 데이터");
        dataFrame.setSize(600, 400);
        JTextArea dataArea = new JTextArea();
        dataArea.setEditable(false);
        dataFrame.add(new JScrollPane(dataArea), BorderLayout.CENTER);

        for (String clientId : dataManager.clientData.keySet()) {
            VendingMachineData data = dataManager.getClientData(clientId);
            dataArea.append("클라이언트: " + clientId + "\n");
            dataArea.append("재고 현황:\n");
            for (String canName : data.inventory.keySet()) {
                dataArea.append(canName + ": " + data.inventory.get(canName) + "\n");
            }
            dataArea.append("일별 매출:\n");
            for (String date : data.dailySales.keySet()) {
                dataArea.append(date + ": " + data.dailySales.get(date).toString() + "\n");
            }
            dataArea.append("월별 매출:\n");
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