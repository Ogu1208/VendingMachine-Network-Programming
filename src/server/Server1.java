package server;

import util.SalesData;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server1 {
    private static final int SERVER_PORT = 12345; // ���� ��Ʈ ��ȣ
    private final List<SalesData> salesDataList = Collections.synchronizedList(new ArrayList<>());
    private final List<ClientHandler> clientHandlers = new ArrayList<>();
    private VendingMachineServerGUI serverGUI;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Server1().start());
    }

    public void start() {
        serverGUI = new VendingMachineServerGUI(this);

        // Load initial sales data
        loadInitialSalesData();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("������ ���۵Ǿ����ϴ�.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInitialSalesData() {
        File salesDataFile = new File("sales_data.dat");
        if (salesDataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(salesDataFile))) {
                List<SalesData> initialSalesData = (List<SalesData>) ois.readObject();
                salesDataList.addAll(initialSalesData);
                SwingUtilities.invokeLater(() -> {
                    serverGUI.refreshSalesData();
                    serverGUI.refreshStockData();
                });
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveSalesData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("sales_data.dat"))) {
            synchronized (salesDataList) {
                oos.writeObject(new ArrayList<>(salesDataList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SalesData> getSalesDataList() {
        return salesDataList;
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private final Server1 server;

        public ClientHandler(Socket clientSocket, Server1 server) {
            this.clientSocket = clientSocket;
            this.server = server;
        }

        @Override
        public void run() {
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                while (true) {
                    // Ŭ���̾�Ʈ�κ��� ������ ����
                    SalesData salesData = (SalesData) in.readObject();
                    System.out.println("Ŭ���̾�Ʈ�κ��� ���� ������: " + salesData);

                    // ������ ó��
                    synchronized (server.salesDataList) {
                        server.salesDataList.add(salesData);
                    }

                    server.saveSalesData();

                    // Ŭ���̾�Ʈ�� ������Ʈ�� ������ ����
                    out.writeObject(salesData);
                    out.flush();

                    // GUI ����
                    SwingUtilities.invokeLater(() -> {
                        server.serverGUI.refreshSalesData();
                        server.serverGUI.refreshStockData();
                    });
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}