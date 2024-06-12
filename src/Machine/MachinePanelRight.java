package Machine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Action.AddCanFrame;
import Action.AddCoinFrame;
import Action.ActionPwChange;
import Action.ActionCollectMoney;
import Can.CanArray;
import Coin.CoinArray;
import Coin.Coin;
import Person.Admin;


public class MachinePanelRight extends JPanel implements ActionListener {

	JTextField adminPass, changePW, collectMoney;
	JPanel canAdminPanel, moneyAdminPanel, moneyTotalPanel, PWPanel, collectPanel;
	JButton btnAdminIn, btnAddCanStart, btnAddCan;
	JLabel label;
	String password;
	DefaultTableModel canModel, moneyModel;
	public static JLabel totalMoneyLabel, PWLabel, collectLabel, totalBalanceLabel;
	public static JTable canTable, moneyTable;
	MachinePanelLeft panelLeft;

	public MachinePanelRight(String password, MachinePanelLeft panelLeft) {
		this.password = password;
		this.panelLeft = panelLeft;

		setPreferredSize(new Dimension(280, 630));

		label = new JLabel("�����ڸ�� - ��й�ȣ�� �Է����ּ���");
		label.setVisible(true);

		// ------------ <������� ���� �ǳ�> ------------ //
		canAdminPanel = new JPanel(new BorderLayout());

		String[] canColName = {"�����̸�", "���", "���� �ǸŰ���"};
		canModel = new DefaultTableModel(canColName, 0);
		canTable = new JTable(canModel);
		JScrollPane canScrollPanel = new JScrollPane(canTable);
		canScrollPanel.setPreferredSize(new Dimension(230, 150));

		btnAddCan = new JButton("�����߰�");
		btnAddCan.addActionListener(new AddCanFrame(canTable));

		canAdminPanel.add(new JLabel("�������"), BorderLayout.WEST);
		canAdminPanel.add(btnAddCan, BorderLayout.EAST);
		canAdminPanel.add(canScrollPanel, BorderLayout.SOUTH);
		canAdminPanel.setVisible(false);

		for (int i = 0; i < CanArray.canList.size(); i++) {
			String arr[] = {CanArray.canList.get(i).getCanName(),
					Integer.toString(CanArray.canList.get(i).getCanNum()),
					Integer.toString(CanArray.canList.get(i).getCanPrice())};
			canModel.addRow(arr);
		}

		// ------------ <�ܵ� ���� �ǳ�> ------------ //
		moneyAdminPanel = new JPanel(new BorderLayout());

		String[] moneyColName = {"���� ����", "���� ����",};
		moneyModel = new DefaultTableModel(moneyColName, 0);
		moneyTable = new JTable(moneyModel);
		JScrollPane moneyScrollPanel = new JScrollPane(moneyTable);
		moneyScrollPanel.setPreferredSize(new Dimension(230, 150));

		JButton btnAddMoney = new JButton("�ܵ��߰�");
		btnAddMoney.addActionListener(new AddCoinFrame(moneyTable));

		moneyAdminPanel.add(new JLabel("�ܵ�����"), BorderLayout.CENTER);
		moneyAdminPanel.add(btnAddMoney, BorderLayout.EAST);
		moneyAdminPanel.add(moneyScrollPanel, BorderLayout.SOUTH);
		moneyAdminPanel.setVisible(false);

		moneyModel = (DefaultTableModel) moneyTable.getModel();

		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			String arr[] = {CoinArray.coinList.get(i).getCoinName(),
					Integer.toString(CoinArray.coinList.get(i).getCoinNum())};
			moneyModel.addRow(arr);
		}

		// ------------ <�� ����� �ǳ�> ------------ //
		moneyTotalPanel = new JPanel(new BorderLayout());
		totalMoneyLabel = new JLabel("�� ����� : " + Admin.getTotalMoney());
		moneyTotalPanel.add(totalMoneyLabel);

		// ���Ǳ� �� �ܰ� �� �߰�
		totalBalanceLabel = new JLabel("���Ǳ� �� �ܰ� : " + calculateTotalBalance());
		moneyTotalPanel.add(totalBalanceLabel, BorderLayout.SOUTH);

		moneyTotalPanel.setVisible(false);

		// ------------ <��й�ȣ ���� �ǳ�> ----------- //
		PWPanel = new JPanel(new BorderLayout());

		JPanel PW1 = new JPanel();
		JPanel PW2 = new JPanel();
		PWLabel = new JLabel("(Ư������ �� ���� �ϳ� �̻� ����, 8�ڸ� �̻�)");
		changePW = new JTextField(10);
		changePW.setText("ex) Ogu1208!");
		JButton PWButton = new JButton("��й�ȣ ����");

		changePW.addActionListener(new ActionPwChange(changePW));
		PWButton.addActionListener(new ActionPwChange(changePW));

		PW1.add(PWLabel);
		PW2.add(changePW);
		PW2.add(PWButton);

		PWPanel.add(PW2, BorderLayout.NORTH);
		PWPanel.add(PW1, BorderLayout.SOUTH);

		PWPanel.setVisible(false);

		// ------------ <���� �ǳ�> ----------- //
		collectPanel = new JPanel(new BorderLayout());

		JPanel collect1 = new JPanel();
		JPanel collect2 = new JPanel();
		collectLabel = new JLabel("������ �ݾ��� �Է��ϼ���(��)");
		collectMoney = new JTextField(10);
		JButton collectButton = new JButton("����");

		collectMoney.addActionListener(new ActionCollectMoney(collectMoney));
		collectButton.addActionListener(new ActionCollectMoney(collectMoney));

		collect1.add(collectLabel);
		collect2.add(collectMoney);
		collect2.add(collectButton);

		// ���̾ƿ� ����
		collectPanel.add(collect2, BorderLayout.NORTH);
		collectPanel.add(collect1, BorderLayout.SOUTH);

		collectPanel.setVisible(false);

		// ------------ <������ ���� �ǳ�> ----------- //
		JPanel inAdminPanel = new JPanel();

		adminPass = new JTextField(10);
		btnAdminIn = new JButton("����");
		adminPass.addActionListener(this);

		btnAdminIn.addActionListener(this);

		inAdminPanel.add(new JLabel("������"));
		inAdminPanel.add(adminPass);
		inAdminPanel.add(btnAdminIn);

		add(inAdminPanel);
		add(canAdminPanel);
		add(label);
		add(moneyAdminPanel);
		add(moneyTotalPanel);
		add(PWPanel);
		add(collectPanel);

		addTableModelListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!canAdminPanel.isVisible()) {
			if (adminPass.getText().equals(Admin.password)) {
				label.setVisible(false);
				canAdminPanel.setVisible(true);
				moneyAdminPanel.setVisible(true);
				moneyTotalPanel.setVisible(true);
				PWPanel.setVisible(true);
				collectPanel.setVisible(true);

				btnAdminIn.setText("��������");
				adminPass.setText("");
				adminPass.setVisible(false);

			} else if (adminPass.getText().equals("")) {
				JOptionPane.showMessageDialog(new JFrame(), "��й�ȣ�� �Է����ּ���");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "��й�ȣ�� Ʋ�Ƚ��ϴ�!");
			}
		} else if (canAdminPanel.isVisible()) {
			label.setVisible(true);
			canAdminPanel.setVisible(false);
			moneyAdminPanel.setVisible(false);
			moneyTotalPanel.setVisible(false);
			PWPanel.setVisible(false);
			collectPanel.setVisible(false);

			btnAdminIn.setText("����");
			adminPass.setVisible(true);
		}
	}

	private void addTableModelListeners() {
		canModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 0) { // �̸� ����
					CanArray.canList.get(row).setCanName((String) canModel.getValueAt(row, column));
				} else if (column == 1) { // ��� ����
					CanArray.canList.get(row).setCanNum(Integer.parseInt((String) canModel.getValueAt(row, column)));
				} else if (column == 2) { // ���� ����
					CanArray.canList.get(row).setCanPrice(Integer.parseInt((String) canModel.getValueAt(row, column)));
				}

				panelLeft.updateCanLabels();
				panelLeft.updateCanButtons();
			}
		});

		moneyModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 1) { // ���� ���� ����
					CoinArray.coinList.get(row).setCoinNum(Integer.parseInt((String) moneyModel.getValueAt(row, column)));
				}

				updateTotalBalanceLabel();
			}
		});
	}

	public static void updateTotalBalanceLabel() {
		int totalBalance = 0;
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			totalBalance += CoinArray.coinList.get(i).getCoinNum() * Integer.parseInt(CoinArray.coinList.get(i).getCoinName());
		}
		totalBalanceLabel.setText("���Ǳ� �� �ܰ� : " + totalBalance);
	}

	private int calculateTotalBalance() {
		int totalBalance = 0;
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			totalBalance += CoinArray.coinList.get(i).getCoinNum() * Integer.parseInt(CoinArray.coinList.get(i).getCoinName());
		}
		return totalBalance;
	}

	public static void updateMoneyTable() {
		DefaultTableModel moneyModel = (DefaultTableModel) moneyTable.getModel();
		moneyModel.setRowCount(0);
		for (Coin coin : CoinArray.coinList) {
			String[] row = {coin.getCoinName(), String.valueOf(coin.getCoinNum())};
			moneyModel.addRow(row);
		}
	}
}
