package Action;

/**
 *  ���Ǳ⿡�� ���� �����ϴ� �׼� ó�� Ŭ����
 *  VendingMachine ��ü���� ���� �����ϰ�, �� �ݾ��� �޽����� ǥ��
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
	// ���� Ŭ���� ������
	public ActionCollectMoney(JTextField money) {
		super();
		this.money = money;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int Money = Integer.parseInt(money.getText());  // int������ ��ȯ

		// �ּ����� ȭ�� ����
		int[] minimumCoins = {5, 5, 5, 5, 5}; // 1000��, 500��, 100��, 50��, 10�� �ּ� 5���� ���ܵα�
		int[] coinValues = {1000, 500, 100, 50, 10};
		int[] coinsToCollect = new int[coinValues.length];
		int remainingAmount = Money;

		// ���� �ܰ� ���
		int totalBalance = calculateTotalBalance();

		// �ּ� ȭ�� ���� ���¿��� ���� ������ �ִ� �ݾ� ���
		int maxCollectableAmount = totalBalance - calculateMinimumBalance(minimumCoins, coinValues);

		if (Money > maxCollectableAmount) {
			JOptionPane.showMessageDialog(null, "��ȯ�� ���� �ּ����� ȭ�� ���ܾ� �մϴ�.");
			return;
		}

		// 1000������ ����
		for (int i = 0; i < coinValues.length; i++) {
			int availableCoins = CoinArray.coinList.get(i).getCoinNum() - minimumCoins[i];
			int coinsNeeded = Math.min(remainingAmount / coinValues[i], availableCoins);
			coinsToCollect[i] = coinsNeeded;
			remainingAmount -= coinsNeeded * coinValues[i];
		}

		for (int i = 0; i < coinValues.length; i++) {
			CoinArray.coinList.get(i).setCoinNum(CoinArray.coinList.get(i).getCoinNum() - coinsToCollect[i]);
		}

		// �ܵ� ���̺� ������Ʈ
		DefaultTableModel moneyModel = (DefaultTableModel) MachinePanelRight.moneyTable.getModel();
		moneyModel.setRowCount(0);
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			String arr[] = {CoinArray.coinList.get(i).getCoinName(), Integer.toString(CoinArray.coinList.get(i).getCoinNum())};
			moneyModel.addRow(arr);
		}

		MachinePanelRight.updateTotalBalanceLabel();

		JOptionPane.showMessageDialog(new JFrame(), Money + "���� �����߽��ϴ�.");
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
