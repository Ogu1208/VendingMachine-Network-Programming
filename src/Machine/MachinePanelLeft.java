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
	MachinePanelRight panelRight;
	private int currentMoney = 0;
	private int billCount = 0;
	private static final int MAX_BILLS = 5;

	public MachinePanelLeft(SalesManager salesManager, ClientInterface client, MachinePanelRight panelRight) {
		this.salesManager = salesManager;
		this.client = client;
		this.panelRight = panelRight;

		// ���� ���Ǳ� �ǳ�
		setPreferredSize(new Dimension(320, 630));
		blist = new ArrayList<>();   // ��ư ����Ʈ
		canLabels = new ArrayList<>(); // �� ����Ʈ

		// ------------<�� ���ⱸ>-----------//
		JPanel moneyPanel = new JPanel();   // �� ���Ա� �ǳ�(���� ū Ʋ)
		// ��ȯ��(��ȯ ��ư)
		JPanel takeMoneyPanel = new JPanel();
		takeMoneytext = new JTextField(6);
		takeMoneytext.setText("0"); // 0���� �ʱ�ȭ
		JButton takeMoneyButton = new JButton(new ImageIcon("return.png"));  // ��ȯ ��ư �̹���������
		takeMoneyButton.setBorder(BorderFactory.createEmptyBorder());
		takeMoneyButton.setContentAreaFilled(false);
		takeMoneyButton.addActionListener(e -> {
			currentMoney = 0;
			billCount = 0;
			takeMoneytext.setText("0");
			updateButtonColors();
		}); // ��ȯ ��ư �׼�

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
			canButton.setForeground(new Color(0, 0, 0));  // ���� �ؽ�Ʈ ����

			int index = i;  // �߰��� �κ�: �ε����� finaló�� ����ϱ� ����

			// �Ǹ� ��ư Ŭ�� �� Ŭ���̾�Ʈ�� �Ǹ� ���� ���� �� ��� ����
			canButton.addActionListener(e -> {
				int quantity = 1; // ���÷� 1�� �Ǹ�
				Can can = CanArray.canList.get(index);
				int currentStock = can.getCanNum();
				int canPrice = can.getCanPrice();
				if (currentMoney >= canPrice && currentStock >= quantity) {
					can.setCanNum(currentStock - quantity);
					currentMoney -= canPrice;
					takeMoneytext.setText(String.valueOf(currentMoney));
					salesManager.addSales(new SalesData(can.getCanName(), quantity, canPrice));
					client.sendSale(can.getCanName(), quantity);
					panelRight.updateCanTable();  // ������ �г� ������Ʈ
					panelRight.updateCanLabels(); // ������ �г� �� ������Ʈ
					panelRight.updateTotalBalanceLabel(); // ������ �г� �� �ܰ� ������Ʈ
					updateButtonColors();  // ��ư ���� ������Ʈ
				} else if (currentMoney < canPrice) {
					JOptionPane.showMessageDialog(this, "������ �ݾ��� �����մϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "��� �����մϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				}
			});

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

		updateButtonColors();
	}

	private List<JButton> createCoinButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] coinValues = {10, 50, 100, 500};

		for (int value : coinValues) {
			JButton button = new JButton(value + "��");
			button.addActionListener(e -> {
				currentMoney += value;
				takeMoneytext.setText(String.valueOf(currentMoney));
				updateButtonColors();
			});
			buttons.add(button);
		}

		return buttons;
	}

	private List<JButton> createBillButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] billValues = {1000};

		for (int value : billValues) {
			JButton button = new JButton(value + "��");
			button.addActionListener(e -> {
				if (billCount < MAX_BILLS) {
					currentMoney += value;
					billCount++;
					takeMoneytext.setText(String.valueOf(currentMoney));
					updateButtonColors();
				} else {
					JOptionPane.showMessageDialog(this, "1000�� ����� 5�������� ���� �����մϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				}
			});
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

	public void updateButtonColors() {
		for (int i = 0; i < blist.size(); i++) {
			JButton button = blist.get(i);
			int stock = CanArray.canList.get(i).getCanNum();
			int price = CanArray.canList.get(i).getCanPrice();
			if (stock == 0) {
				button.setBackground(new Color(204, 61, 61));
			} else if (currentMoney >= price) {
				button.setBackground(new Color(20, 175, 100));
			} else {
				button.setBackground(Color.WHITE);
			}
		}
	}
}