package Action;

// 금액 투입 클래스 
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

	// 금액 투입 클래스 생성자
	public PutMoney(JTextField putMoneytext, JTextField takeMoneytext, List<JButton> blist, MachinePanelLeft panelLeft) {
		super();
		this.putMoneytext = putMoneytext;
		this.takeMoneytext = takeMoneytext;
		this.blist = blist;
		this.panelLeft = panelLeft;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 정규식 조건문
		if (putMoneytext.getText().equals("")) {
			JOptionPane.showMessageDialog(new JFrame(), "돈을 넣지 않았습니다.");
		} else if (Pattern.matches("^[0-9]*$", putMoneytext.getText())) {
			boolean pattern;

			if (Integer.parseInt(putMoneytext.getText()) < 100) {
				// 100원 이하면
				pattern = Pattern.matches("[0,5]?0$", putMoneytext.getText());
			} else if (Integer.parseInt(putMoneytext.getText()) <= 5000) {
				// 5000원 이하면
				pattern = Pattern.matches("[1-9]((\\d){0,2}[0,5])?0$", putMoneytext.getText());
			} else {
				// 투입 가능 금액 초과면
				pattern = false;
			}

			if (pattern == false) { // 투입 가능 금액 초과면
				JOptionPane.showMessageDialog(new JFrame(), "5000원 이하로 투입할 수 있습니다.");
			} else {
				int currentMoney = Integer.parseInt(putMoneytext.getText());
				if (panelLeft.getTotalInsertedMoney() + currentMoney > 7000) {
					JOptionPane.showMessageDialog(new JFrame(), "총 투입 금액이 7000원을 초과할 수 없습니다.");
					return;
				}
				int totalMoney = Admin.getTotalMoney() + currentMoney;
				Admin.setTotalMoney(totalMoney);
				MachinePanelRight.totalMoneyLabel.setText("총 매출액 : " + totalMoney);

				int newTotal = Integer.parseInt(takeMoneytext.getText()) + currentMoney;
				takeMoneytext.setText(String.valueOf(newTotal));
				putMoneytext.setText("");

				panelLeft.addInsertedMoney(currentMoney);
				updateButtonColors(newTotal);
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "숫자 형식으로 입력하세요");
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
}