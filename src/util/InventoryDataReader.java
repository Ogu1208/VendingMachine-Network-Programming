package util;

import Can.Can;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class InventoryDataReader {

    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("inventory_data.dat"))) {
            List<Can> canList = (List<Can>) ois.readObject();
            for (Can can : canList) {
                System.out.println(can);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
