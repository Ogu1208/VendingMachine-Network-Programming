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
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            log("서버에 연결됨: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            sendRequest("REGISTER:Client1");

        } catch (IOException e) {
            log("서버 연결 오류: " + e.getMessage());
        }
    }

    private void sendRequest(String request) {
        try {
            out.writeObject(request);
            out.flush();
            log("서버로 요청 전송: " + request);

            String response = (String) in.readObject();
            log("서버로부터 응답 받음: " + response);

        } catch (IOException | ClassNotFoundException e) {
            log("서버 요청 처리 중 오류: " + e.getMessage());
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    @Override
    public void sendSale(String canName, int quantity) {
        sendRequest("SALE:" + canName + ":" + quantity);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MachineFrame("Ogu1208!", new Client1());
        });
    }
}