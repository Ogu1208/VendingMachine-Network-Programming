package Machine;

import client.Client1;

import javax.swing.*;

public class MachineMain {

	// ���Ǳ� Main�Լ�
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Client1 client = new Client1();
			new MachineFrame("Ogu1208!", client);
		});
	}

}
