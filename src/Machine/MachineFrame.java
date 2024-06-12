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

public class MachineFrame extends JFrame {
	// ���Ǳ��� ��ü����Ʋ
	public MachineFrame(String password){
		Admin.setPassword(password);
		// ���� �ʱ�ȭ
		CanArray.canList.add(new Can("��", 5, 450));
		CanArray.canList.add(new Can("Ŀ��", 5, 500));
		CanArray.canList.add(new Can("�̿�����", 5, 550));
		CanArray.canList.add(new Can("���Ŀ��", 5, 700));
		CanArray.canList.add(new Can("ź������", 5, 750));
		// �ܵ� �ʱ�ȭ
		CoinArray.coinList.add(new Coin("1000",10));
		CoinArray.coinList.add(new Coin("500",10));
		CoinArray.coinList.add(new Coin("100",10));
		CoinArray.coinList.add(new Coin("50",10));
		CoinArray.coinList.add(new Coin("10",10));
		
		// ������ �̺�Ʈ ��� �� ����
		setTitle("���Ǳ� ���� ���α׷�");
		setPreferredSize(new Dimension(650,630));
		setLocation(400,150);
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
		
		JPanel panelRight = new MachinePanelRight(password);
		JPanel panelLeft = new MachinePanelLeft();
		
		contentPanel.add(panelRight,BorderLayout.EAST);
		contentPanel.add(panelLeft,BorderLayout.CENTER);

		pack();
		setVisible(true);
		
	}
}
