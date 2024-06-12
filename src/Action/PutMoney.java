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
import Machine.MachinePanelRight;
import Person.Admin;

public class PutMoney implements ActionListener {

	JTextField putMoneytext, takeMoneytext;
	List<JButton> blist;

	// �ݾ� ���� Ŭ���� ������
	public PutMoney(JTextField putMoneytext, JTextField takeMoneytext, List<JButton> blist) {
		super();
		this.putMoneytext = putMoneytext;
		this.takeMoneytext = takeMoneytext;
		this.blist = blist;
	}
	
	public PutMoney(int i, int count) {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// ���Խ� ���ǹ�
		if (putMoneytext.getText().equals("")) {
			JOptionPane.showMessageDialog(new JFrame(), "���� ���� �ʾҽ��ϴ�.");
		} 
		else if (Pattern.matches("^[0-9]*$", putMoneytext.getText())) {

			boolean pattern;

			if (Integer.parseInt(putMoneytext.getText()) < 100) {
				// 100�� ���ϸ�
				pattern = Pattern.matches("[0,5]?0$", putMoneytext.getText());
			} 
			else if (Integer.parseInt(putMoneytext.getText()) <= 5000) {
				//5000�� ���ϸ�
				pattern = Pattern.matches("[1-9]((\\d){0,2}[0,5])?0$", putMoneytext.getText());
			} 
			else {
				// ���� ���� �ݾ� �ʰ���
				pattern = false;
			}

			if (pattern == false) { // ���� ���� �ݾ� �ʰ���
				JOptionPane.showMessageDialog(new JFrame(), "5000�� ���Ϸ� ������ �� �ֽ��ϴ�.");
			} else {

				takeMoneytext.setText(String
						.valueOf(Integer.parseInt(takeMoneytext.getText()) + Integer.parseInt(putMoneytext.getText())));
				for (int i = 0; i < blist.size(); i++) {
					if (blist.get(i).getLabel().equals(CanArray.canList.get(i).getCanName())) {
						if(CanArray.canList.get(i).getCanNum() == 0){  // ��� ������
							blist.get(i).setForeground(new Color(255, 255, 255));
							blist.get(i).setBackground(new Color(204, 61, 61)); // ������
						}
						if (CanArray.canList.get(i).getCanPrice() <= Integer.parseInt(takeMoneytext.getText())
								&& CanArray.canList.get(i).getCanNum() > 0) {  // ��� �ְ� ���Աݾ��� ����ϸ�
							blist.get(i).setForeground(new Color(255, 255, 255));
							blist.get(i).setBackground(new Color(20, 175, 100));
						}
					}
				}
				
				// �Ѹ���� �ٽ� Admin���� ���� �� �� RightPanel�� �Ѹ���� �ٽ� ����
				Admin.setTotalMoney(Admin.getTotalMoney() + Integer.parseInt(putMoneytext.getText()));
				MachinePanelRight.totalMoneyLabel.setText("�� ����� : " + Admin.getTotalMoney());

				putMoneytext.setText("");
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "���� �������� �Է��ϼ���");
		}
	}
}
