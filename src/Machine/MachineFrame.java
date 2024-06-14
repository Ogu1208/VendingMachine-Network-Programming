package Machine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Can.Can;
import Can.CanArray;
import Coin.Coin;
import Coin.CoinArray;
import Person.Admin;
import client.Client1;
import client.ClientInterface;
import util.SalesManager;

public class MachineFrame extends JFrame {
	private ClientInterface client;

	// ���Ǳ��� ��ü���� Ʋ
	public MachineFrame(String password, ClientInterface client) {
		this.client = client;
		Admin.setPassword(password);
		SalesManager salesManager = new SalesManager();

		// ���� �ʱ�ȭ
		CanArray.canList.add(new Can("��", 10, 450));
		CanArray.canList.add(new Can("Ŀ��", 10, 500));
		CanArray.canList.add(new Can("�̿�����", 10, 550));
		CanArray.canList.add(new Can("���Ŀ��", 10, 700));
		CanArray.canList.add(new Can("ź������", 10, 750));
		CanArray.canList.add(new Can("Ưȭ����", 10, 800));
		// �ܵ� �ʱ�ȭ
		CoinArray.coinList.add(new Coin("1000", 10));
		CoinArray.coinList.add(new Coin("500", 10));
		CoinArray.coinList.add(new Coin("100", 10));
		CoinArray.coinList.add(new Coin("50", 10));
		CoinArray.coinList.add(new Coin("10", 10));

		// ������ �̺�Ʈ ��� �� ����
		setTitle("���Ǳ� ���� ���α׷�");
		setPreferredSize(new Dimension(650, 630));
		setLocation(400, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���Ǳ� ���� �ǳڰ� ������ �ǳ� ����
		Container contentPanel = getContentPane();

		MachinePanelRight panelRight = new MachinePanelRight(password, null, salesManager, client); // �ӽ÷� null ����
		MachinePanelLeft panelLeft = new MachinePanelLeft(salesManager, client, panelRight);

		panelRight.setPanelLeft(panelLeft); // ������ �гο� ���� �г� ����

		contentPanel.add(panelRight, BorderLayout.EAST);
		contentPanel.add(panelLeft, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}
}