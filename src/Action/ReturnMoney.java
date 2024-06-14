package Action;

// 금액 반환 클래스
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
		int originalReturnMoney = returnMoney;  // 원래 반환 금액을 저장

		// 반환 동전 목록들
		int return500 = 0;
		int return100 = 0;
		int return50 = 0;
		int return10 = 0;

		if (returnMoney > 0) {
			// 총액에서 반환금을 빼고 RightPanel에서 총매출액 text 다시 설정
			Admin.setTotalMoney(Admin.getTotalMoney() - returnMoney);
			MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + Admin.getTotalMoney());

			return500 = returnMoney / 500;
			returnMoney %= 500;
			return100 = returnMoney / 100;
			returnMoney %= 100;
			return50 = returnMoney / 50;
			returnMoney %= 50;
			return10 = returnMoney / 10;

			// 반환 동전 재고 업데이트
			returnMoney = originalReturnMoney;

			// 500원 동전 반환
			while (return500 > 0 && CoinArray.coinList.get(1).getCoinNum() > 0) {
				CoinArray.coinList.get(1).setCoinNum(CoinArray.coinList.get(1).getCoinNum() - 1);
				returnMoney -= 500;
				return500--;
			}

			// 100원 동전 반환
			while (return100 > 0 && CoinArray.coinList.get(2).getCoinNum() > 0) {
				CoinArray.coinList.get(2).setCoinNum(CoinArray.coinList.get(2).getCoinNum() - 1);
				returnMoney -= 100;
				return100--;
			}

			// 50원 동전 반환
			while (return50 > 0 && CoinArray.coinList.get(3).getCoinNum() > 0) {
				CoinArray.coinList.get(3).setCoinNum(CoinArray.coinList.get(3).getCoinNum() - 1);
				returnMoney -= 50;
				return50--;
			}

			// 10원 동전 반환
			while (return10 > 0 && CoinArray.coinList.get(4).getCoinNum() > 0) {
				CoinArray.coinList.get(4).setCoinNum(CoinArray.coinList.get(4).getCoinNum() - 1);
				returnMoney -= 10;
				return10--;
			}

			panelLeft.resetInsertedMoney();
			BillButtonAction.resetTotalBillAmount();
			panelRight.updateTotalBalance(-originalReturnMoney + returnMoney); // 자판기 총 잔고에서 반환 금액만큼 차감
			panelRight.updateMoneyTable(); // 오른쪽 테이블 업데이트

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			if (returnMoney > 0) {
				JOptionPane.showMessageDialog(new JFrame(), "동전이 부족하여 일부 금액만 반환되었습니다. 반환된 금액: " + (originalReturnMoney - returnMoney) + "원");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "반환된 금액: " + originalReturnMoney + "원");
			}

			takeMoneytext.setText("0");
			panelLeft.setCurrentMoney(0);  // currentMoney 초기화

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "반환할 돈이 없습니다.");
		}
	}
}