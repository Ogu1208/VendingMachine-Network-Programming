package client;

import Machine.MachineFrame;
import util.SalesData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client2 {

    private static final String SERVER_IP = "127.0.0.1"; // 서버 IP 주소
    private static final int SERVER_PORT = 12345; // 서버 포트 번호

    public static void main(String[] args) {
        new Client2().start();
    }

    public void start() {
        // MachineFrame을 실행
        new MachineFrame("Ogu1208!", "client2");

        // 서버와의 통신 설정
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // 예시: 자판기 판매 데이터 전송
            SalesData salesData = new SalesData("client2", "커피", 5, 500);
            out.writeObject(salesData);
            out.flush();

            // 서버로부터 업데이트된 데이터 수신
            SalesData updatedData = (SalesData) in.readObject();
            System.out.println("서버로부터 받은 데이터: " + updatedData);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}