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
		int originalReturnMoney = returnMoney;  // 원래 반환 금액을 저장

		// 반환 금액을 정수형으로 변환

		// 반환 동전 목록들
		int return500 = 0;
		int return100 = 0;
		int return50 = 0;
		int return10 = 0;

		if (returnMoney > 0) {  // 반환할 돈이 있으면
			// 총액에서 반환금을 빼고  RightPanel에서 총매출액 text 다시 설정
			Admin.setTotalMoney(Admin.getTotalMoney() - returnMoney);
			MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + Admin.getTotalMoney());

			return500 = returnMoney / 500;
			return100 = (returnMoney % 500) / 100;
			return50 = ((returnMoney % 500) % 100) / 50;
			return10 = (((returnMoney % 500) % 100) % 50) / 10;

			for (int i = 0; i < return500; i++) {
				if (CoinArray.coinList.get(1).getCoinNum() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "500원 동전 부족");
					return100 = returnMoney / 100;
					break;
				} else {
					CoinArray.coinList.get(1).setCoinNum(CoinArray.coinList.get(1).getCoinNum() - 1);
					returnMoney -= 500;
					takeMoneytext.setText(Integer.toString(returnMoney));
				}
			}
			for (int i = 0; i < return100; i++) {
				if (CoinArray.coinList.get(2).getCoinNum() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "100원 동전 부족");
					return50 = returnMoney / 50;
					break;
				} else {
					CoinArray.coinList.get(2).setCoinNum(CoinArray.coinList.get(2).getCoinNum() - 1);
					returnMoney -= 100;
					takeMoneytext.setText(Integer.toString(returnMoney));
				}
			}
			for (int i = 0; i < return50; i++) {
				if (CoinArray.coinList.get(3).getCoinNum() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "50원 동전 부족");
					return10 = returnMoney / 10;
					break;
				} else {
					CoinArray.coinList.get(3).setCoinNum(CoinArray.coinList.get(3).getCoinNum() - 1);
					returnMoney -= 50;
					takeMoneytext.setText(Integer.toString(returnMoney));
				}
			}
			for (int i = 0; i < return10; i++) {
				if (CoinArray.coinList.get(4).getCoinNum() == 0) {
					JOptionPane.showMessageDialog(new JFrame(), "동전 부족");
					takeMoneytext.setText(Integer.toString(returnMoney));
					break;
				} else {
					CoinArray.coinList.get(4).setCoinNum(CoinArray.coinList.get(4).getCoinNum() - 1);
					returnMoney -= 10;
					takeMoneytext.setText(Integer.toString(returnMoney));
				}
			}

			BillButtonAction.resetTotalBillAmount();  // 지폐 제한 초기화

			MachinePanelRight.updateTotalBalanceLabel();  // 자판기 총 잔고 업데이트
			MachinePanelRight.updateMoneyTable(); // 오른쪽 테이블 업데이트

			for (int k = 0; k < blist.size(); k++) {
				if (blist.get(k).getLabel().equals(CanArray.canList.get(k).getCanName())) {
					blist.get(k).setForeground(new Color(0, 0, 0));
					blist.get(k).setBackground(new Color(255, 255, 255));
				}
			}

			JOptionPane.showMessageDialog(new JFrame(), "반환된 금액: " + originalReturnMoney + "원");

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "반환할 돈이없습니다.");
		}
	}
}