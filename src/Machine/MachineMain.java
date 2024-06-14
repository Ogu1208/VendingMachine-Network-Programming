package Machine;

import client.Client1;
import client.Client2;

import javax.swing.*;

public class MachineMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Client1();
			new Client2();
		});
	}
}
