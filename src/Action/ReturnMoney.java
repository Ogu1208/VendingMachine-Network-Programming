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
		int totalReturnMoney = returnMoney; // 실제로 반환된 금액을 저장
		int remainingMoney = returnMoney;

		if (returnMoney > 0) {  // 반환할 돈이 있으면
			// 현재 투입된 금액을 0으로 설정
			takeMoneytext.setText("0");

			// 반환 동전 목록들 (지폐 제외)
			int[] coinValues = {500, 100, 50, 10};
			int[] returnCoins = new int[coinValues.length];

			// 각 동전의 개수를 고려하여 반환
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

			// CoinArray에서 동전 개수 차감
			for (int i = 0; i < coinValues.length; i++) {
				for (int j = 0; j < CoinArray.coinList.size(); j++) {
					if (CoinArray.coinList.get(j).getCoinName().equals(String.valueOf(coinValues[i]))) {
						CoinArray.coinList.get(j).setCoinNum(CoinArray.coinList.get(j).getCoinNum() - returnCoins[i]);
					}
				}
			}

			// moneyTable 업데이트
			panelRight.updateMoneyTable();

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			int successfullyReturned = totalReturnMoney - remainingMoney;
			Admin.setTotalMoney(Admin.getTotalMoney() - successfullyReturned); // 매출액에서 반환된 금액을 차감
			MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + Admin.getTotalMoney());

			if (remainingMoney > 0) {
				JOptionPane.showMessageDialog(new JFrame(), "거스름돈이 부족하여 일부만 반환되었습니다. 자판기 옆 번호로 전화 부탁드립니다.\n 반환된 금액: " + successfullyReturned + "원");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "반환된 금액: " + totalReturnMoney + "원");
			}

			// 지폐 투입 총합 초기화
			BillButtonAction.resetTotalBillAmount();
			panelLeft.setBillCount(0);
			panelLeft.setCurrentMoney(0);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "반환할 돈이 없습니다.");
		}
	}
}