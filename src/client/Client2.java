package client;

import Machine.MachineFrame;
import util.SalesData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client2 {

    private static final String SERVER_IP = "127.0.0.1"; // ���� IP �ּ�
    private static final int SERVER_PORT = 12345; // ���� ��Ʈ ��ȣ

    public static void main(String[] args) {
        new Client2().start();
    }

    public void start() {
        // MachineFrame�� ����
        new MachineFrame("Ogu1208!", "client2");

        // �������� ��� ����
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // ����: ���Ǳ� �Ǹ� ������ ����
            SalesData salesData = new SalesData("client2", "Ŀ��", 5, 500);
            out.writeObject(salesData);
            out.flush();

            // �����κ��� ������Ʈ�� ������ ����
            SalesData updatedData = (SalesData) in.readObject();
            System.out.println("�����κ��� ���� ������: " + updatedData);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}