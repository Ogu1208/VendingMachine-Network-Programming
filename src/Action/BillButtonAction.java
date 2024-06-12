package Action;

import Coin.CoinArray;
import Machine.MachinePanelRight;
import Person.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillButtonAction implements ActionListener {

    private int value;
    private JTextField takeMoneytext;
    private static int totalBillAmount = 0; // 지폐 투입 총합

    public BillButtonAction(int value, JTextField takeMoneytext) {
        this.value = value;
        this.takeMoneytext = takeMoneytext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        if (totalBillAmount + value <= 5000) { // 지폐 투입 상한선 체크
            if (currentMoney + value <= 7000) { // 총 투입 금액 상한선 체크
                currentMoney += value;
                takeMoneytext.setText(String.valueOf(currentMoney));
                totalBillAmount += value; // 지폐 투입 총합 업데이트

                // 총 매출액 업데이트
                Admin.setTotalMoney(Admin.getTotalMoney() + value);
                MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + Admin.getTotalMoney());

                // CoinArray에 지폐 추가
                for (int i = 0; i < CoinArray.coinList.size(); i++) {
                    if (CoinArray.coinList.get(i).getCoinName().equals(String.valueOf(value))) {
                        CoinArray.coinList.get(i).setCoinNum(CoinArray.coinList.get(i).getCoinNum() + 1);
                    }
                }

                // moneyTable 업데이트
                updateMoneyTable();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "총 투입 금액은 7,000원을 초과할 수 없습니다.");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "지폐 투입 금액은 5,000원을 초과할 수 없습니다.");
        }
    }

    public static int getTotalBillAmount() {
        return totalBillAmount;
    }

    public static void resetTotalBillAmount() {
        totalBillAmount = 0;
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
