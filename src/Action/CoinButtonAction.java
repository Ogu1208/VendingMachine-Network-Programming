package Action;

import Can.CanArray;
import Coin.CoinArray;
import Coin.Coin;
import Machine.MachinePanelLeft;
import Machine.MachinePanelRight;
import Person.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CoinButtonAction implements ActionListener {

    int coinValue;
    JTextField takeMoneytext;
    List<JButton> blist;
    MachinePanelRight panelRight;
    MachinePanelLeft panelLeft;

    public CoinButtonAction(int coinValue, JTextField takeMoneytext, List<JButton> blist, MachinePanelRight panelRight, MachinePanelLeft panelLeft) {
        this.coinValue = coinValue;
        this.takeMoneytext = takeMoneytext;
        this.blist = blist;
        this.panelRight = panelRight;
        this.panelLeft = panelLeft;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        if (panelLeft.getTotalInsertedMoney() + coinValue > 7000) {
            JOptionPane.showMessageDialog(null, "�� ���� �ݾ��� 7000���� �ʰ��� �� �����ϴ�.");
            return;
        }
        currentMoney += coinValue;
        panelLeft.addInsertedMoney(coinValue);
        takeMoneytext.setText(String.valueOf(currentMoney));

        // Update total money
        int totalMoney = Admin.getTotalMoney() + coinValue;
        Admin.setTotalMoney(totalMoney);
        MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + totalMoney);

        // Add the coin to CoinArray
        addCoinToCoinArray(coinValue);

        // Update the coin table and total balance in the right panel
        updateCoinTable();
        MachinePanelRight.updateTotalBalanceLabel();

        panelLeft.updateButtonColors(currentMoney);
    }

    private void addCoinToCoinArray(int coinValue) {
        for (Coin coin : CoinArray.coinList) {
            if (coin.getCoinName().equals(String.valueOf(coinValue))) {
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
}