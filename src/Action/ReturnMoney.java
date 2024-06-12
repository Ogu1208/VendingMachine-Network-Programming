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
import Machine.MachinePanelRight;
import Person.Admin;

public class ReturnMoney implements ActionListener {

	JTextField takeMoneytext;
	JButton getCan;
	List<JButton> blist;

	// 금액 반환 클래스 생성자
	public ReturnMoney(JTextField takeMoneytext, JButton getCan, List<JButton> blist) {
		super();
		this.takeMoneytext = takeMoneytext;
		this.getCan = getCan;
		this.blist = blist;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int returnMoney = Integer.parseInt(takeMoneytext.getText());
		// 반환 금액을 정수형으로 변환
		int totalReturnMoney = returnMoney; // 실제로 반환된 금액을 저장

		if (returnMoney > 0) {  // 반환할 돈이 있으면
			// 총 매출액에서 반환금을 빼고 RightPanel에서 총 매출액 text 다시 설정
			Admin.setTotalMoney(Admin.getTotalMoney() - returnMoney);
			MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + Admin.getTotalMoney());

			// 현재 투입된 금액을 0으로 설정
			takeMoneytext.setText("0");

			// 반환 동전 목록들 (지폐 제외)
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
								JOptionPane.showMessageDialog(new JFrame(), coinValues[i] + "원 동전 부족");
								break;
							}
						}
					}
				}
			}

			// moneyTable 업데이트
			updateMoneyTable();

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			JOptionPane.showMessageDialog(new JFrame(), "반환된 금액: " + totalReturnMoney + "원");
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "반환할 돈이 없습니다.");
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

