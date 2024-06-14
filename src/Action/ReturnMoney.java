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
import Coin.Coin;
import Machine.MachinePanelLeft;
import Machine.MachinePanelRight;
import Person.Admin;

public class ReturnMoney implements ActionListener {

	JTextField takeMoneytext;
	JButton getCan;
	List<JButton> blist;
	MachinePanelRight panelRight;
	MachinePanelLeft panelLeft;

	public ReturnMoney(JTextField takeMoneytext, JButton getCan, List<JButton> blist, MachinePanelRight panelRight, MachinePanelLeft panelLeft) {
		super();
		this.takeMoneytext = takeMoneytext;
		this.getCan = getCan;
		this.blist = blist;
		this.panelRight = panelRight;
		this.panelLeft = panelLeft;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int returnMoney = Integer.parseInt(takeMoneytext.getText());
		int totalReturnMoney = returnMoney; // ������ ��ȯ�� �ݾ��� ����
		int remainingMoney = returnMoney;

		if (returnMoney > 0) {  // ��ȯ�� ���� ������
			// ���� ���Ե� �ݾ��� 0���� ����
			takeMoneytext.setText("0");

			// ��ȯ ���� ��ϵ� (���� ����)
			int[] coinValues = {500, 100, 50, 10};
			int[] returnCoins = new int[coinValues.length];

			// �� ������ ������ ����Ͽ� ��ȯ
			for (int i = 0; i < coinValues.length; i++) {
				for (int j = 0; j < CoinArray.coinList.size(); j++) {
					if (CoinArray.coinList.get(j).getCoinName().equals(String.valueOf(coinValues[i]))) {
						int availableCoins = CoinArray.coinList.get(j).getCoinNum();
						int neededCoins = remainingMoney / coinValues[i];
						int coinsToReturn = Math.min(availableCoins, neededCoins);
						returnCoins[i] = coinsToReturn;
						remainingMoney -= coinsToReturn * coinValues[i];
					}
				}
			}

			// CoinArray���� ���� ���� ����
			for (int i = 0; i < coinValues.length; i++) {
				for (int j = 0; j < CoinArray.coinList.size(); j++) {
					if (CoinArray.coinList.get(j).getCoinName().equals(String.valueOf(coinValues[i]))) {
						CoinArray.coinList.get(j).setCoinNum(CoinArray.coinList.get(j).getCoinNum() - returnCoins[i]);
					}
				}
			}

			// moneyTable ������Ʈ
			panelRight.updateMoneyTable();

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			int successfullyReturned = totalReturnMoney - remainingMoney;
			Admin.setTotalMoney(Admin.getTotalMoney() - successfullyReturned); // ����׿��� ��ȯ�� �ݾ��� ����
			MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + Admin.getTotalMoney());

			if (remainingMoney > 0) {
				JOptionPane.showMessageDialog(new JFrame(), "�Ž������� �����Ͽ� �Ϻθ� ��ȯ�Ǿ����ϴ�. ���Ǳ� �� ��ȣ�� ��ȭ ��Ź�帳�ϴ�.\n ��ȯ�� �ݾ�: " + successfullyReturned + "��");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� �ݾ�: " + totalReturnMoney + "��");
			}

			// ���� ���� ���� �ʱ�ȭ
			BillButtonAction.resetTotalBillAmount();
			panelLeft.setBillCount(0);
			panelLeft.setCurrentMoney(0);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� ���� �����ϴ�.");
		}
	}
}