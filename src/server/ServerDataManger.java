package server;

import Can.Can;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ServerDataManager {
    Map<String, VendingMachineData> clientData = new HashMap<>();

    public void registerClient(String clientId, List<Can> initialInventory) {
        VendingMachineData data = new VendingMachineData();
        data.initializeInventory(initialInventory);
        clientData.put(clientId, data);
    }

    public VendingMachineData getClientData(String clientId) {
        return clientData.get(clientId);
    }
}