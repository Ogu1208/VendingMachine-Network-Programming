package server;

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
    private static final int PORT = 12345;
    private static final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private static final DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Client", "Message"}, 0);

    public ServerMain() {
        setTitle("자판기 관리 서버");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerMain::new);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 시작되었습니다. 포트: " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + socket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateTable(String clientName, String message) {
        SwingUtilities.invokeLater(() -> {
            tableModel.addRow(new Object[]{clientName, message});
        });
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "MS949"));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "MS949"), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                clientName = in.readLine();
                clients.put(clientName, this);
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("클라이언트로부터 메시지: " + message);
                    updateTable(clientName, message);
                    processMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clients.remove(clientName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processMessage(String message) {
            String[] parts = message.split(":");
            String command = parts[0];

            switch (command) {
                case "SALE":
                    handleSale(parts[1], Integer.parseInt(parts[2]));
                    break;
                // 다른 명령어 처리 추가
            }
        }

        private void handleSale(String canName, int quantity) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String logMessage = String.format("판매 - 음료: %s, 수량: %d, 날짜: %s", canName, quantity, date);
            updateTable(clientName, logMessage);
            // 추가 처리 로직
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}