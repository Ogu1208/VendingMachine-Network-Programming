package server;

import Can.Can;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServerDataManager {
    private Map<String, VendingMachineData> clientData;

    public ServerDataManager() {
        clientData = new HashMap<>();
    }

    public void registerClient(String clientId) {
        clientData.put(clientId, new VendingMachineData());
    }

    public VendingMachineData getClientData(String clientId) {
        return clientData.get(clientId);
    }

    public Set<String> getClientIds() {
        return clientData.keySet();
    }
}