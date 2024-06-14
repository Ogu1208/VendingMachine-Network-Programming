package server;

import Can.CanArray;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerDataManager dataManager;
    private JTextArea logArea;
    private String clientId;

    public ClientHandler(Socket socket, ServerDataManager dataManager, JTextArea logArea) {
        this.socket = socket;
        this.dataManager = dataManager;
        this.logArea = logArea;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            log("Ŭ���̾�Ʈ ó�� �� ����: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = (String) in.readObject();
                log("��û ����: " + request);

                if (request.startsWith("REGISTER:")) {
                    clientId = request.split(":")[1];
                    dataManager.registerClient(clientId);
                    log(clientId + " ��ϵ�");

                } else if (request.startsWith("SALE:")) {
                    String[] parts = request.split(":");
                    String canName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    VendingMachineData clientData = dataManager.getClientData(clientId);
                    clientData.addSale(canName, quantity, date);
                    log(clientId + " �Ǹ� - ����: " + canName + ", ����: " + quantity + ", ��¥: " + date);

                } else if (request.startsWith("INVENTORY_UPDATE:")) {
                    String[] parts = request.split(":");
                    String canName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);

                    VendingMachineData clientData = dataManager.getClientData(clientId);
                    clientData.updateInventory(canName, quantity);
                    log(clientId + " ��� ������Ʈ - ����: " + canName + ", ����: " + quantity);
                }

                out.writeObject("OK");
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            log("Ŭ���̾�Ʈ ó�� �� ����: " + e.getMessage());
        }
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }
}