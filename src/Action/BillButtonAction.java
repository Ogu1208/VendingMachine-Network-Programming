package Action;

import Can.CanArray;
import Coin.Coin;
import Coin.CoinArray;
import Machine.MachinePanelRight;
import Person.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BillButtonAction implements ActionListener {


    int billValue;
    JTextField takeMoneytext;
    List<JButton> blist;
    private static int totalBillAmount = 0;

    public BillButtonAction(int billValue, JTextField takeMoneytext, List<JButton> blist) {
        this.billValue = billValue;
        this.takeMoneytext = takeMoneytext;
        this.blist = blist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (totalBillAmount + billValue > 5000) {
            JOptionPane.showMessageDialog(null, "지폐로 투입할 수 있는 최대 금액은 5000원입니다.");
            return;
        }

        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        currentMoney += billValue;
        totalBillAmount += billValue;
        takeMoneytext.setText(String.valueOf(currentMoney));

        // Update total money
        int totalMoney = Admin.getTotalMoney() + billValue;
        Admin.setTotalMoney(totalMoney);
        MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + totalMoney);

        // Add the bill to CoinArray
        addCoinToCoinArray(billValue);

        // Update the coin table in the right panel
        updateCoinTable();
        MachinePanelRight.updateTotalBalanceLabel();

        updateButtonColors(currentMoney);
    }

    private void addCoinToCoinArray(int billValue) {
        for (Coin coin : CoinArray.coinList) {
            if (coin.getCoinName().equals(String.valueOf(billValue))) {
                coin.setCoinNum(coin.getCoinNum() + 1);
                break;
            }
        }
    }

    private void updateCoinTable() {
        DefaultTableModel moneyModel = (DefaultTableModel) MachinePanelRight.moneyTable.getModel();
        moneyModel.setRowCount(0);
        for (Coin coin : CoinArray.coinList) {
            String[] row = {coin.getCoinName(), String.valueOf(coin.getCoinNum())};
            moneyModel.addRow(row);
        }
    }

    private void updateButtonColors(int currentMoney) {
        for (int i = 0; i < blist.size(); i++) {
            JButton button = blist.get(i);
            int canPrice = CanArray.canList.get(i).getCanPrice();
            int canNum = CanArray.canList.get(i).getCanNum();

            if (canNum == 0) {
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(204, 61, 61)); // 빨간색
            } else if (canPrice <= currentMoney) {
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(20, 175, 100)); // 초록색
            } else {
                button.setForeground(new Color(0, 0, 0));
                button.setBackground(new Color(255, 255, 255)); // 흰색
            }
        }
    }

    public static void resetTotalBillAmount() {
        totalBillAmount = 0;
    }
}
