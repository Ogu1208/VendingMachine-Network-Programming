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
import util.SalesManager;

public class MachineFrame extends JFrame {
	// ���Ǳ��� ��ü���� Ʋ
	public MachineFrame(String password) {
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
		WindowListener win = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		};
		addWindowListener(win);

		// ���Ǳ� ���� �ǳڰ� ������ �ǳ� ����
		Container contentPanel = getContentPane();

		MachinePanelLeft panelLeft = new MachinePanelLeft(salesManager);
		MachinePanelRight panelRight = new MachinePanelRight(password, panelLeft, salesManager);

		contentPanel.add(panelRight, BorderLayout.EAST);
		contentPanel.add(panelLeft, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}
}
