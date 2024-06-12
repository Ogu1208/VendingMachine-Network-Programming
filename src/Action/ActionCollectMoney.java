package Action;

/**
 *  자판기에서 돈을 수금하는 액션 처리 클래스
 *  VendingMachine 객체에서 돈을 수금하고, 그 금액을 메시지로 표시
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.util.regex.Pattern;

import Coin.CoinArray;
import Machine.MachineFrame;
import Machine.MachinePanelRight;
import Machine.MachineMain;
import Person.Admin;

public class ActionCollectMoney implements ActionListener{
	JTextField money;
	// 수금 클래스 생성자
	public ActionCollectMoney(JTextField money) {
		super();
		this.money = money;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int Money = Integer.parseInt(money.getText());  // int형으로 변환

		// 최소한의 화폐 수량
		int[] minimumCoins = {5, 5, 5, 5, 5}; // 1000원, 500원, 100원, 50원, 10원 최소 5개씩 남겨두기
		int[] coinValues = {1000, 500, 100, 50, 10};
		int[] coinsToCollect = new int[coinValues.length];
		int remainingAmount = Money;

		// 현재 잔고 계산
		int totalBalance = calculateTotalBalance();

		// 최소 화폐를 남긴 상태에서 수금 가능한 최대 금액 계산
		int maxCollectableAmount = totalBalance - calculateMinimumBalance(minimumCoins, coinValues);

		if (Money > maxCollectableAmount) {
			JOptionPane.showMessageDialog(null, "반환을 위한 최소한의 화폐를 남겨야 합니다.");
			return;
		}

		// 1000원부터 수금
		for (int i = 0; i < coinValues.length; i++) {
			int availableCoins = CoinArray.coinList.get(i).getCoinNum() - minimumCoins[i];
			int coinsNeeded = Math.min(remainingAmount / coinValues[i], availableCoins);
			coinsToCollect[i] = coinsNeeded;
			remainingAmount -= coinsNeeded * coinValues[i];
		}

		for (int i = 0; i < coinValues.length; i++) {
			CoinArray.coinList.get(i).setCoinNum(CoinArray.coinList.get(i).getCoinNum() - coinsToCollect[i]);
		}

		// 잔돈 테이블 업데이트
		DefaultTableModel moneyModel = (DefaultTableModel) MachinePanelRight.moneyTable.getModel();
		moneyModel.setRowCount(0);
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			String arr[] = {CoinArray.coinList.get(i).getCoinName(), Integer.toString(CoinArray.coinList.get(i).getCoinNum())};
			moneyModel.addRow(arr);
		}

		MachinePanelRight.updateTotalBalanceLabel();

		JOptionPane.showMessageDialog(new JFrame(), Money + "원을 수금했습니다.");
	}

	private int calculateTotalBalance() {
		int totalBalance = 0;
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			totalBalance += CoinArray.coinList.get(i).getCoinNum() * Integer.parseInt(CoinArray.coinList.get(i).getCoinName());
		}
		return totalBalance;
	}

	private int calculateMinimumBalance(int[] minimumCoins, int[] coinValues) {
		int minimumBalance = 0;
		for (int i = 0; i < coinValues.length; i++) {
			minimumBalance += minimumCoins[i] * coinValues[i];
		}
		return minimumBalance;
	}
}
