package Action;

// �ݾ� ��ȯ Ŭ����
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Can.CanArray;
import Coin.CoinArray;
import Machine.MachinePanelRight;
import Person.Admin;

public class ReturnMoney implements ActionListener {

	JTextField takeMoneytext;
	JButton getCan;
	List<JButton> blist;

	// �ݾ� ��ȯ Ŭ���� ������
	public ReturnMoney(JTextField takeMoneytext, JButton getCan, List<JButton> blist) {
		super();
		this.takeMoneytext = takeMoneytext;
		this.getCan = getCan;
		this.blist = blist;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int returnMoney = Integer.parseInt(takeMoneytext.getText());
		// ��ȯ �ݾ��� ���������� ��ȯ
		int totalReturnMoney = returnMoney; // ������ ��ȯ�� �ݾ��� ����

		if (returnMoney > 0) {  // ��ȯ�� ���� ������
			// �� ����׿��� ��ȯ���� ���� RightPanel���� �� ����� text �ٽ� ����
			Admin.setTotalMoney(Admin.getTotalMoney() - returnMoney);
			MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + Admin.getTotalMoney());

			// ���� ���Ե� �ݾ��� 0���� ����
			takeMoneytext.setText("0");

			// ��ȯ ���� ��ϵ� (���� ����)
			int[] coinValues = {500, 100, 50, 10};
			int[] returnCoins = new int[coinValues.length];

			for (int i = 0; i < coinValues.length; i++) {
				returnCoins[i] = returnMoney / coinValues[i];
				returnMoney %= coinValues[i];
			}

			for (int i = 0; i < coinValues.length; i++) {
				for (int j = 0; j < returnCoins[i]; j++) {
					for (int k = 0; k < CoinArray.coinList.size(); k++) {
						if (CoinArray.coinList.get(k).getCoinName().equals(String.valueOf(coinValues[i]))) {
							if (CoinArray.coinList.get(k).getCoinNum() > 0) {
								CoinArray.coinList.get(k).setCoinNum(CoinArray.coinList.get(k).getCoinNum() - 1);
							} else {
								JOptionPane.showMessageDialog(new JFrame(), coinValues[i] + "�� ���� ����");
								break;
							}
						}
					}
				}
			}

			// moneyTable ������Ʈ
			updateMoneyTable();

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� �ݾ�: " + totalReturnMoney + "��");
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� ���� �����ϴ�.");
		}
	}

	private void updateMoneyTable() {
		DefaultTableModel moneyModel = (DefaultTableModel) MachinePanelRight.moneyTable.getModel();
		moneyModel.setRowCount(0);
		for (int j = 0; j < CoinArray.coinList.size(); j++) {
			String arr[] = { CoinArray.coinList.get(j).getCoinName(), Integer.toString(CoinArray.coinList.get(j).getCoinNum()) };
			moneyModel.addRow(arr);
		}
	}
}

