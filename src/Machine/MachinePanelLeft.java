package Machine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Action.*;
import Can.Can;
import Can.CanArray;
import client.ClientInterface;
import util.SalesData;
import util.SalesManager;

public class MachinePanelLeft extends JPanel {

	JButton getCan, canButton;
	JTextField takeMoneytext;
	List<JButton> blist;
	List<JLabel> canLabels;
	SalesManager salesManager;
	ClientInterface client;

	public MachinePanelLeft(SalesManager salesManager, ClientInterface client) {
		this.salesManager = salesManager;
		this.client = client;

		// ���� ���Ǳ� �ǳ�
		setPreferredSize(new Dimension(320, 630));
		blist = new ArrayList<JButton>();   // ��ư ����Ʈ
		canLabels = new ArrayList<JLabel>(); // �� ����Ʈ

		// ------------<�� ���ⱸ>-----------//
		JPanel moneyPanel = new JPanel();   // �� ���Ա� �ǳ�(���� ū Ʋ)
		// ��ȯ��(��ȯ ��ư)
		JPanel takeMoneyPanel = new JPanel();
		takeMoneytext = new JTextField(6);
		takeMoneytext.setText("0"); // 0���� �ʱ�ȭ
		JButton takeMoneyButton = new JButton(new ImageIcon("return.png"));  // ��ȯ ��ư �̹���������
		takeMoneyButton.setBorder(BorderFactory.createEmptyBorder());
		takeMoneyButton.setContentAreaFilled(false);
		takeMoneyButton.addActionListener(new ReturnMoney(takeMoneytext, getCan, blist)); // ��ư �׼�

		// ���Ա�
		JPanel putMoneyPanel = new JPanel();
		putMoneyPanel.setLayout(new GridLayout(2, 3)); // 2�� 3�� �׸��� ���̾ƿ�

		// ���� �� ���� ��ư �߰�
		List<JButton> coinButtons = createCoinButtons(blist);
		List<JButton> billButtons = createBillButtons(blist);

		for (JButton button : coinButtons) {
			putMoneyPanel.add(button);
		}
		for (JButton button : billButtons) {
			putMoneyPanel.add(button);
		}

		takeMoneytext.setEditable(false);   // ��ȯ�� JTextField�� ���� �Ұ���(�⺻���� ���� ����)
		takeMoneyPanel.add(takeMoneytext);  // ��ȯ �ǳڿ� �߰�
		takeMoneyPanel.add(takeMoneyButton);

		moneyPanel.add(takeMoneyPanel);  // �� ���Ա� �ǳ�(���� ū Ʋ)�� ��ȯ, ���� �ǳ� �߰�
		moneyPanel.add(putMoneyPanel);

		// ----------<�����ȯ��>----------------//
		JPanel getCanPanel = new JPanel();
		getCan = new JButton("");  // ���� ��ȯ ��ư(������ ���� ���ⱸ)
		getCan.addActionListener(new TakeCan(getCan)); // ��ư �׼�
		getCan.setIcon(new ImageIcon("canreturn.png"));
		getCan.setBorder(BorderFactory.createEmptyBorder());
		getCan.setContentAreaFilled(false);

		getCanPanel.add(getCan);

		// ------------<���ἱ��>----------//
		JPanel selectCan = new JPanel(new GridLayout(2, 1));
		selectCan.setPreferredSize(new Dimension(310, 330));  // ��� �κ�

		for (int i = 0; i < CanArray.canList.size(); i++) {
			JPanel canEach = new JPanel();
			JLabel canLabel = new JLabel(CanArray.canList.get(i).getCanPrice() + "��");
			canLabels.add(canLabel); // �� ����Ʈ�� �߰�
			canButton = new JButton(CanArray.canList.get(i).getCanName());
			int index = i; // ���� ĸ��
			canButton.addActionListener(new ButtonAction(takeMoneytext, getCan, blist, salesManager) {
				@Override
				public void actionPerformed(ActionEvent e) {
					int currentMoney = Integer.parseInt(takeMoneytext.getText());
					Can can = CanArray.canList.get(index);
					if (currentMoney >= can.getCanPrice()) {
						int quantity = 1; // ���÷� 1�� �Ǹ�
						can.setCanNum(can.getCanNum() - quantity);
						salesManager.addSales(new SalesData(can.getCanName(), quantity, can.getCanPrice()));
						client.sendSale(can.getCanName(), quantity);
						takeMoneytext.setText(String.valueOf(currentMoney - can.getCanPrice()));
					} else {
						JOptionPane.showMessageDialog(null, "���Ե� ���� �����մϴ�.");
					}
				}
			});
			canButton.setForeground(new Color(0, 0, 0));  // ���� �ؽ�Ʈ ����
			canButton.setBackground(new Color(255, 255, 255));  // ���� ��ư ����
			canEach.add(new JLabel(new ImageIcon(i + ".png")));  // ���� �̹���
			canEach.add(canLabel);  // ���� �̸�
			canEach.add(canButton);
			selectCan.add(canEach);

			blist.add(canButton);  // ��ư ����Ʈ�� ��ư�� �߰�
		}

		add(selectCan, BorderLayout.NORTH);  // ���� ���� ����
		add(moneyPanel, BorderLayout.CENTER);  // �� ���ⱸ �߾�
		add(getCanPanel, BorderLayout.SOUTH);  // ���� ��ȯ�� ����

		// �� �г� ��� ���� ����
		moneyPanel.setBackground(new Color(70, 152, 64));
		takeMoneyPanel.setBackground(new Color(70, 152, 64));
		putMoneyPanel.setBackground(new Color(70, 152, 64));
		getCanPanel.setBackground(new Color(70, 152, 64));
		setBackground(new Color(70, 152, 64));
	}

	private List<JButton> createCoinButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] coinValues = {10, 50, 100, 500};

		for (int value : coinValues) {
			JButton button = new JButton(value + "��");
			button.addActionListener(new CoinButtonAction(value, takeMoneytext, blist));
			buttons.add(button);
		}

		return buttons;
	}

	private List<JButton> createBillButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] billValues = {1000};

		for (int value : billValues) {
			JButton button = new JButton(value + "��");
			button.addActionListener(new BillButtonAction(value, takeMoneytext, blist));
			buttons.add(button);
		}

		return buttons;
	}

	public void updateCanLabels() {
		for (int i = 0; i < canLabels.size(); i++) {
			JLabel canLabel = canLabels.get(i);
			int canPrice = CanArray.canList.get(i).getCanPrice();
			canLabel.setText(canPrice + "��");
		}
	}

	public void updateCanButtons() {
		for (int i = 0; i < blist.size(); i++) {
			JButton button = blist.get(i);
			String canName = CanArray.canList.get(i).getCanName();
			button.setText(canName);
		}
	}
}