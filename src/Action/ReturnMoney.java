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
		int originalReturnMoney = returnMoney;  // ���� ��ȯ �ݾ��� ����

		// ��ȯ ���� ��ϵ�
		int return500 = 0;
		int return100 = 0;
		int return50 = 0;
		int return10 = 0;

		if (returnMoney > 0) {
			// �Ѿ׿��� ��ȯ���� ���� RightPanel���� �Ѹ���� text �ٽ� ����
			Admin.setTotalMoney(Admin.getTotalMoney() - returnMoney);
			MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + Admin.getTotalMoney());

			return500 = returnMoney / 500;
			returnMoney %= 500;
			return100 = returnMoney / 100;
			returnMoney %= 100;
			return50 = returnMoney / 50;
			returnMoney %= 50;
			return10 = returnMoney / 10;

			// ��ȯ ���� ��� ������Ʈ
			returnMoney = originalReturnMoney;

			// 500�� ���� ��ȯ
			while (return500 > 0 && CoinArray.coinList.get(1).getCoinNum() > 0) {
				CoinArray.coinList.get(1).setCoinNum(CoinArray.coinList.get(1).getCoinNum() - 1);
				returnMoney -= 500;
				return500--;
			}

			// 100�� ���� ��ȯ
			while (return100 > 0 && CoinArray.coinList.get(2).getCoinNum() > 0) {
				CoinArray.coinList.get(2).setCoinNum(CoinArray.coinList.get(2).getCoinNum() - 1);
				returnMoney -= 100;
				return100--;
			}

			// 50�� ���� ��ȯ
			while (return50 > 0 && CoinArray.coinList.get(3).getCoinNum() > 0) {
				CoinArray.coinList.get(3).setCoinNum(CoinArray.coinList.get(3).getCoinNum() - 1);
				returnMoney -= 50;
				return50--;
			}

			// 10�� ���� ��ȯ
			while (return10 > 0 && CoinArray.coinList.get(4).getCoinNum() > 0) {
				CoinArray.coinList.get(4).setCoinNum(CoinArray.coinList.get(4).getCoinNum() - 1);
				returnMoney -= 10;
				return10--;
			}

			panelLeft.resetInsertedMoney();
			BillButtonAction.resetTotalBillAmount();
			panelRight.updateTotalBalance(-originalReturnMoney + returnMoney); // ���Ǳ� �� �ܰ��� ��ȯ �ݾ׸�ŭ ����
			panelRight.updateMoneyTable(); // ������ ���̺� ������Ʈ

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			if (returnMoney > 0) {
				JOptionPane.showMessageDialog(new JFrame(), "������ �����Ͽ� �Ϻ� �ݾ׸� ��ȯ�Ǿ����ϴ�. ��ȯ�� �ݾ�: " + (originalReturnMoney - returnMoney) + "��");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� �ݾ�: " + originalReturnMoney + "��");
			}

			takeMoneytext.setText("0");
			panelLeft.setCurrentMoney(0);  // currentMoney �ʱ�ȭ

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "��ȯ�� ���� �����ϴ�.");
		}
	}
}