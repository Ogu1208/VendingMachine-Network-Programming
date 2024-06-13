package Action;

// ��ư �׼� Ŭ����
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Can.CanArray;
import Can.Can;
import Machine.MachinePanelRight;
import Person.Admin;
import util.SalesData;
import util.SalesManager;

public class ButtonAction implements ActionListener {

	JTextField takeMoneytext;
	JButton getCan;
	List<JButton> blist;
	SalesManager salesManager;

	// ��ư �׼� Ŭ���� ������
	public ButtonAction(JTextField takeMoneytext, JButton getCan, List<JButton> blist, SalesManager salesManager) {
		super();
		this.takeMoneytext = takeMoneytext;
		this.getCan = getCan;
		this.blist = blist;
		this.salesManager = salesManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		JButton b = (JButton) obj;

		// �Ĵ� ������� �� ���� �� ����� ���� ����
		int minPrice = 450;
		for (Can can : CanArray.canList) {
			if (minPrice > can.getCanPrice()) {
				minPrice = can.getCanPrice();
			}
		}

		if (minPrice <= Integer.parseInt(takeMoneytext.getText())) {

			int selectCanPrice = 0;
			for (Can can : CanArray.canList) {
				if (can.getCanName().equals(b.getText())) {
					selectCanPrice = can.getCanPrice();
					break;
				}
			}
			// ���Ǳ⿡ ���� ���� ������ ������� ���ݺ��� ������
			if (Integer.parseInt(takeMoneytext.getText()) >= selectCanPrice) {

				for (Can can : CanArray.canList) {
					if (b.getText().equals(can.getCanName())) {

						// ��� ������
						if (can.getCanNum() >= 1) {

							// �ܵ� ǥ��
							int returnMoney1 = Integer.parseInt(takeMoneytext.getText());
							int returnMoney2 = can.getCanPrice();
							takeMoneytext.setText(String.valueOf(returnMoney1 - returnMoney2));

							// (��ư��) ���� ������ ���� �� �ִ� ���� ǥ��
							for (int k = 0; k < blist.size(); k++) {
								JButton button = blist.get(k);
								if (button.getText().equals(CanArray.canList.get(k).getCanName())) {
									if (CanArray.canList.get(k).getCanNum() == 0) {
										// ��� 0�� -> ǰ��(������)
										button.setForeground(new Color(255, 255, 255));
										button.setBackground(new Color(204, 61, 61)); // ������
									} else if (CanArray.canList.get(k).getCanPrice() <= Integer.parseInt(takeMoneytext.getText())) {
										// ��� ����, ���� ������ �̱� O(�ʷϻ�)
										button.setForeground(new Color(255, 255, 255));
										button.setBackground(new Color(20, 175, 100)); // �ʷϻ�
									} else {
										// ��� ����, ���� ������ �̱� X (�Ͼ��)
										button.setForeground(new Color(0, 0, 0));
										button.setBackground(new Color(255, 255, 255)); // ���
									}
								}
							}

							// ���� ĵ ��� -1 & ĵ ��ȯ �̹���
							can.setCanNum(can.getCanNum() - 1);

							DefaultTableModel canModel = (DefaultTableModel) MachinePanelRight.canTable.getModel();
							canModel.setRowCount(0);
							for (Can c : CanArray.canList) {
								String arr[] = { c.getCanName(), Integer.toString(c.getCanNum()), Integer.toString(c.getCanPrice()) };
								canModel.addRow(arr);
							}
							getCan.setIcon(new ImageIcon("return" + CanArray.canList.indexOf(can) + ".png"));

							if (can.getCanNum() == 0) {
								blist.get(CanArray.canList.indexOf(can)).setForeground(new Color(255, 255, 255));
								blist.get(CanArray.canList.indexOf(can)).setBackground(new Color(204, 61, 61));
							}

							// ���� ������ ������Ʈ
							salesManager.addSale(new SalesData(can.getCanName(), 1, can.getCanPrice()));

						} else { // ��� ������
							JOptionPane.showMessageDialog(new JFrame(), "ǰ���Դϴ�");
						}
					}
				}

			} else { // ���Ǳ⿡ ���� ���� ������ ������� ���ݺ��� ���� ������
				JOptionPane.showMessageDialog(new JFrame(), "���� �����մϴ�");
			}

		} else { // ���� �� ����� ���ݺ��� ���� ���� �־��� ��
			JOptionPane.showMessageDialog(new JFrame(), "���� �����մϴ�");
		}
	}

}
