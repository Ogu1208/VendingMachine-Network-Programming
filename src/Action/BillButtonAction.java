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
    private static int totalBillAmount = 0; // ���� ���� ����

    public BillButtonAction(int value, JTextField takeMoneytext) {
        this.value = value;
        this.takeMoneytext = takeMoneytext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int currentMoney = Integer.parseInt(takeMoneytext.getText());
        if (totalBillAmount + value <= 5000) { // ���� ���� ���Ѽ� üũ
            if (currentMoney + value <= 7000) { // �� ���� �ݾ� ���Ѽ� üũ
                currentMoney += value;
                takeMoneytext.setText(String.valueOf(currentMoney));
                totalBillAmount += value; // ���� ���� ���� ������Ʈ

                // �� ����� ������Ʈ
                Admin.setTotalMoney(Admin.getTotalMoney() + value);
                MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + Admin.getTotalMoney());

                // CoinArray�� ���� �߰�
                for (int i = 0; i < CoinArray.coinList.size(); i++) {
                    if (CoinArray.coinList.get(i).getCoinName().equals(String.valueOf(value))) {
                        CoinArray.coinList.get(i).setCoinNum(CoinArray.coinList.get(i).getCoinNum() + 1);
                    }
                }

                // moneyTable ������Ʈ
                updateMoneyTable();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "�� ���� �ݾ��� 7,000���� �ʰ��� �� �����ϴ�.");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "���� ���� �ݾ��� 5,000���� �ʰ��� �� �����ϴ�.");
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
