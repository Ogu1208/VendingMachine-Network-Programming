package Action;

// 버튼 액션 클래스
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

	// 버튼 액션 클래스 생성자
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

		// 파는 음료수들 중 제일 싼 음료수 가격 설정
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
			// 자판기에 들어온 돈이 선택한 음료수의 가격보다 많으면
			if (Integer.parseInt(takeMoneytext.getText()) >= selectCanPrice) {

				for (Can can : CanArray.canList) {
					if (b.getText().equals(can.getCanName())) {

						// 재고가 있으면
						if (can.getCanNum() >= 1) {

							// 잔돈 표시
							int returnMoney1 = Integer.parseInt(takeMoneytext.getText());
							int returnMoney2 = can.getCanPrice();
							takeMoneytext.setText(String.valueOf(returnMoney1 - returnMoney2));

							// (버튼색) 남은 돈으로 뽑을 수 있는 음료 표시
							for (int k = 0; k < blist.size(); k++) {
								JButton button = blist.get(k);
								if (button.getText().equals(CanArray.canList.get(k).getCanName())) {
									if (CanArray.canList.get(k).getCanNum() == 0) {
										// 재고 0개 -> 품절(빨간색)
										button.setForeground(new Color(255, 255, 255));
										button.setBackground(new Color(204, 61, 61)); // 빨간색
									} else if (CanArray.canList.get(k).getCanPrice() <= Integer.parseInt(takeMoneytext.getText())) {
										// 재고 있음, 남은 돈으로 뽑기 O(초록색)
										button.setForeground(new Color(255, 255, 255));
										button.setBackground(new Color(20, 175, 100)); // 초록색
									} else {
										// 재고 있음, 남은 돈으로 뽑기 X (하얀색)
										button.setForeground(new Color(0, 0, 0));
										button.setBackground(new Color(255, 255, 255)); // 흰색
									}
								}
							}

							// 뽑은 캔 재고 -1 & 캔 반환 이미지
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

							// 매출 데이터 업데이트
							salesManager.addSale(new SalesData(can.getCanName(), 1, can.getCanPrice()));

						} else { // 재고가 없으면
							JOptionPane.showMessageDialog(new JFrame(), "품절입니다");
						}
					}
				}

			} else { // 자판기에 들어온 돈이 선택한 음료수의 가격보다 많지 않으면
				JOptionPane.showMessageDialog(new JFrame(), "돈이 부족합니다");
			}

		} else { // 제일 싼 음료수 가격보다 적은 돈을 넣었을 때
			JOptionPane.showMessageDialog(new JFrame(), "돈이 부족합니다");
		}
	}

}
