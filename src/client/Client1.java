package client;

import Machine.MachineFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client1 extends JFrame implements ClientInterface {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private JTextArea logArea;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private MachineFrame machineFrame;

    public Client1() {
        setTitle("자판기 클라이언트 1");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
        setVisible(true);

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "MS949"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "MS949"), true);
            log("서버에 연결됨: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            out.println("Client1");
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        log("서버로부터 메시지: " + message);
                    }
                } catch (IOException e) {
                    log("서버 연결 오류: " + e.getMessage());
                }
            }).start();

            SwingUtilities.invokeLater(() -> {
                machineFrame = new MachineFrame("Ogu1208!", this);
            });

        } catch (IOException e) {
            log("서버 연결 오류: " + e.getMessage());
        }
    }

    @Override
    public void sendSale(String canName, int quantity) {
        out.println("SALE:" + canName + ":" + quantity);
        log("판매 정보 전송: " + canName + ", 수량: " + quantity);
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client1::new);
    }
}