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
    private static final int SERVER_PORT = 12345; // 서버 포트 번호
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
            System.out.println("서버가 시작되었습니다.");
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
                    // 클라이언트로부터 데이터 수신
                    SalesData salesData = (SalesData) in.readObject();
                    System.out.println("클라이언트로부터 받은 데이터: " + salesData);

                    // 데이터 처리
                    synchronized (server.salesDataList) {
                        server.salesDataList.add(salesData);
                    }

                    server.saveSalesData();

                    // 클라이언트에 업데이트된 데이터 전송
                    out.writeObject(salesData);
                    out.flush();

                    // GUI 갱신
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