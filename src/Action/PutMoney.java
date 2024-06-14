package Action;

// �ݾ� ���� Ŭ���� 
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Can.CanArray;
import Machine.MachinePanelLeft;
import Machine.MachinePanelRight;
import Person.Admin;

public class PutMoney implements ActionListener {

	JTextField putMoneytext, takeMoneytext;
	List<JButton> blist;
	MachinePanelLeft panelLeft;

	// �ݾ� ���� Ŭ���� ������
	public PutMoney(JTextField putMoneytext, JTextField takeMoneytext, List<JButton> blist, MachinePanelLeft panelLeft) {
		super();
		this.putMoneytext = putMoneytext;
		this.takeMoneytext = takeMoneytext;
		this.blist = blist;
		this.panelLeft = panelLeft;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ���Խ� ���ǹ�
		if (putMoneytext.getText().equals("")) {
			JOptionPane.showMessageDialog(new JFrame(), "���� ���� �ʾҽ��ϴ�.");
		} else if (Pattern.matches("^[0-9]*$", putMoneytext.getText())) {
			boolean pattern;

			if (Integer.parseInt(putMoneytext.getText()) < 100) {
				// 100�� ���ϸ�
				pattern = Pattern.matches("[0,5]?0$", putMoneytext.getText());
			} else if (Integer.parseInt(putMoneytext.getText()) <= 5000) {
				// 5000�� ���ϸ�
				pattern = Pattern.matches("[1-9]((\\d){0,2}[0,5])?0$", putMoneytext.getText());
			} else {
				// ���� ���� �ݾ� �ʰ���
				pattern = false;
			}

			if (pattern == false) { // ���� ���� �ݾ� �ʰ���
				JOptionPane.showMessageDialog(new JFrame(), "5000�� ���Ϸ� ������ �� �ֽ��ϴ�.");
			} else {
				int currentMoney = Integer.parseInt(putMoneytext.getText());
				if (panelLeft.getTotalInsertedMoney() + currentMoney > 7000) {
					JOptionPane.showMessageDialog(new JFrame(), "�� ���� �ݾ��� 7000���� �ʰ��� �� �����ϴ�.");
					return;
				}
				int totalMoney = Admin.getTotalMoney() + currentMoney;
				Admin.setTotalMoney(totalMoney);
				MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + totalMoney);

				int newTotal = Integer.parseInt(takeMoneytext.getText()) + currentMoney;
				takeMoneytext.setText(String.valueOf(newTotal));
				putMoneytext.setText("");

				panelLeft.addInsertedMoney(currentMoney);
				updateButtonColors(newTotal);
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "���� �������� �Է��ϼ���");
		}
	}

	private void updateButtonColors(int currentMoney) {
		for (int i = 0; i < blist.size(); i++) {
			JButton button = blist.get(i);
			int canPrice = CanArray.canList.get(i).getCanPrice();
			int canNum = CanArray.canList.get(i).getCanNum();

			if (canNum == 0) {
				button.setForeground(new Color(255, 255, 255));
				button.setBackground(new Color(204, 61, 61)); // ������
			} else if (canPrice <= currentMoney) {
				button.setForeground(new Color(255, 255, 255));
				button.setBackground(new Color(20, 175, 100)); // �ʷϻ�
			} else {
				button.setForeground(new Color(0, 0, 0));
				button.setBackground(new Color(255, 255, 255)); // ���
			}
		}
	}
}