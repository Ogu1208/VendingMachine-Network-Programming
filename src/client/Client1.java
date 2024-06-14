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
        setTitle("���Ǳ� Ŭ���̾�Ʈ 1");
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
            log("������ �����: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            sendRequest("REGISTER:Client1");

        } catch (IOException e) {
            log("���� ���� ����: " + e.getMessage());
        }
    }

    private void sendRequest(String request) {
        try {
            out.writeObject(request);
            out.flush();
            log("������ ��û ����: " + request);

            String response = (String) in.readObject();
            log("�����κ��� ���� ����: " + response);

        } catch (IOException | ClassNotFoundException e) {
            log("���� ��û ó�� �� ����: " + e.getMessage());
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