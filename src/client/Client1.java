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
        setTitle("���Ǳ� Ŭ���̾�Ʈ 1");
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
            log("������ �����: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            out.println("Client1");
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        log("�����κ��� �޽���: " + message);
                    }
                } catch (IOException e) {
                    log("���� ���� ����: " + e.getMessage());
                }
            }).start();

            SwingUtilities.invokeLater(() -> {
                machineFrame = new MachineFrame("Ogu1208!", this);
            });

        } catch (IOException e) {
            log("���� ���� ����: " + e.getMessage());
        }
    }

    @Override
    public void sendSale(String canName, int quantity) {
        out.println("SALE:" + canName + ":" + quantity);
        log("�Ǹ� ���� ����: " + canName + ", ����: " + quantity);
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client1::new);
    }
}