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
        updateButtonColors(currentMoney);
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
