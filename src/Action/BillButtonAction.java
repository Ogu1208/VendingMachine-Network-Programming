package Action;

import Can.CanArray;
import Coin.Coin;
import Coin.CoinArray;
import Machine.MachinePanelLeft;
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
    MachinePanelRight panelRight;
    MachinePanelLeft panelLeft;
    private static int totalBillAmount = 0;

    public BillButtonAction(int billValue, JTextField takeMoneytext, List<JButton> blist, MachinePanelRight panelRight, MachinePanelLeft panelLeft) {
        this.billValue = billValue;
        this.takeMoneytext = takeMoneytext;
        this.blist = blist;
        this.panelRight = panelRight;
        this.panelLeft = panelLeft;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (totalBillAmount + billValue > 5000) {
            JOptionPane.showMessageDialog(null, "지폐로 투입할 수 있는 최대 금액은 5000원입니다.");
            return;
        }
        if (panelLeft.getTotalInsertedMoney() + billValue > 7000) {
            JOptionPane.showMessageDialog(null, "총 투입 금액이 7000원을 초과할 수 없습니다.");
            return;
        }

        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        currentMoney += billValue;
        totalBillAmount += billValue;
        panelLeft.addInsertedMoney(billValue);
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

        panelLeft.updateButtonColors(currentMoney);
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

    public static void resetTotalBillAmount() {
        totalBillAmount = 0;
    }
}