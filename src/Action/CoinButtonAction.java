package Action;

import Can.CanArray;
import Coin.CoinArray;
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

    public CoinButtonAction(int coinValue, JTextField takeMoneytext, List<JButton> blist) {
        this.coinValue = coinValue;
        this.takeMoneytext = takeMoneytext;
        this.blist = blist;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        currentMoney += coinValue;
        takeMoneytext.setText(String.valueOf(currentMoney));

        // Update total money
        int totalMoney = Admin.getTotalMoney() + coinValue;
        Admin.setTotalMoney(totalMoney);
        MachinePanelRight.totalMoneyLabel.setText("ÃÑ ¸ÅÃâ¾× : " + totalMoney);

        updateButtonColors(currentMoney);
    }

    private void updateButtonColors(int currentMoney) {
        for (int i = 0; i < blist.size(); i++) {
            JButton button = blist.get(i);
            int canPrice = CanArray.canList.get(i).getCanPrice();
            int canNum = CanArray.canList.get(i).getCanNum();

            if (canNum == 0) {
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(204, 61, 61)); // »¡°£»ö
            } else if (canPrice <= currentMoney) {
                button.setForeground(new Color(255, 255, 255));
                button.setBackground(new Color(20, 175, 100)); // ÃÊ·Ï»ö
            } else {
                button.setForeground(new Color(0, 0, 0));
                button.setBackground(new Color(255, 255, 255)); // Èò»ö
            }
        }
    }
}
