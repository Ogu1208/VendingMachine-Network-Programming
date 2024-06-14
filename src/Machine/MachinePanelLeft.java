package Machine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Action.*;
import Can.Can;
import Can.CanArray;
import client.ClientInterface;
import util.SalesData;
import util.SalesManager;

public class MachinePanelLeft extends JPanel {

	JButton getCan, canButton;
	JTextField takeMoneytext;
	List<JButton> blist;
	List<JLabel> canLabels;
	SalesManager salesManager;
	ClientInterface client;
	MachinePanelRight panelRight;
	private int currentMoney = 0;
	private int billCount = 0;
	private static final int MAX_BILLS = 5;

	public MachinePanelLeft(SalesManager salesManager, ClientInterface client, MachinePanelRight panelRight) {
		this.salesManager = salesManager;
		this.client = client;
		this.panelRight = panelRight;

		// 좌측 자판기 판넬
		setPreferredSize(new Dimension(320, 630));
		blist = new ArrayList<>();   // 버튼 리스트
		canLabels = new ArrayList<>(); // 라벨 리스트

		// ------------<돈 입출구>-----------//
		JPanel moneyPanel = new JPanel();   // 돈 출입구 판넬(가장 큰 틀)
		// 반환구(반환 버튼)
		JPanel takeMoneyPanel = new JPanel();
		takeMoneytext = new JTextField(6);
		takeMoneytext.setText("0"); // 0으로 초기화
		JButton takeMoneyButton = new JButton(new ImageIcon("return.png"));  // 반환 버튼 이미지아이콘
		takeMoneyButton.setBorder(BorderFactory.createEmptyBorder());
		takeMoneyButton.setContentAreaFilled(false);
		takeMoneyButton.addActionListener(e -> {
			currentMoney = 0;
			billCount = 0;
			takeMoneytext.setText("0");
			updateButtonColors();
		}); // 반환 버튼 액션

		// 투입구
		JPanel putMoneyPanel = new JPanel();
		putMoneyPanel.setLayout(new GridLayout(2, 3)); // 2행 3열 그리드 레이아웃

		// 동전 및 지폐 버튼 추가
		List<JButton> coinButtons = createCoinButtons(blist);
		List<JButton> billButtons = createBillButtons(blist);

		for (JButton button : coinButtons) {
			putMoneyPanel.add(button);
		}
		for (JButton button : billButtons) {
			putMoneyPanel.add(button);
		}

		takeMoneytext.setEditable(false);   // 반환구 JTextField는 수정 불가능(기본값은 수정 가능)
		takeMoneyPanel.add(takeMoneytext);  // 반환 판넬에 추가
		takeMoneyPanel.add(takeMoneyButton);

		moneyPanel.add(takeMoneyPanel);  // 돈 출입구 판넬(가장 큰 틀)에 반환, 투입 판넬 추가
		moneyPanel.add(putMoneyPanel);

		// ----------<음료반환구>----------------//
		JPanel getCanPanel = new JPanel();
		getCan = new JButton("");  // 음료 반환 버튼(검은색 음료 배출구)
		getCan.addActionListener(new TakeCan(getCan)); // 버튼 액션
		getCan.setIcon(new ImageIcon("canreturn.png"));
		getCan.setBorder(BorderFactory.createEmptyBorder());
		getCan.setContentAreaFilled(false);

		getCanPanel.add(getCan);

		// ------------<음료선택>----------//
		JPanel selectCan = new JPanel(new GridLayout(2, 1));
		selectCan.setPreferredSize(new Dimension(310, 330));  // 흰색 부분

		for (int i = 0; i < CanArray.canList.size(); i++) {
			JPanel canEach = new JPanel();
			JLabel canLabel = new JLabel(CanArray.canList.get(i).getCanPrice() + "원");
			canLabels.add(canLabel); // 라벨 리스트에 추가
			canButton = new JButton(CanArray.canList.get(i).getCanName());
			canButton.setForeground(new Color(0, 0, 0));  // 음료 텍스트 색상

			int index = i;  // 추가된 부분: 인덱스를 final처럼 사용하기 위해

			// 판매 버튼 클릭 시 클라이언트에 판매 정보 전송 및 재고 감소
			canButton.addActionListener(e -> {
				int quantity = 1; // 예시로 1개 판매
				Can can = CanArray.canList.get(index);
				int currentStock = can.getCanNum();
				int canPrice = can.getCanPrice();
				if (currentMoney >= canPrice && currentStock >= quantity) {
					can.setCanNum(currentStock - quantity);
					currentMoney -= canPrice;
					takeMoneytext.setText(String.valueOf(currentMoney));
					salesManager.addSales(new SalesData(can.getCanName(), quantity, canPrice));
					client.sendSale(can.getCanName(), quantity);
					panelRight.updateCanTable();  // 오른쪽 패널 업데이트
					panelRight.updateCanLabels(); // 오른쪽 패널 라벨 업데이트
					panelRight.updateTotalBalanceLabel(); // 오른쪽 패널 총 잔고 업데이트
					updateButtonColors();  // 버튼 색상 업데이트
				} else if (currentMoney < canPrice) {
					JOptionPane.showMessageDialog(this, "투입한 금액이 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "재고가 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			});

			canEach.add(new JLabel(new ImageIcon(i + ".png")));  // 음료 이미지
			canEach.add(canLabel);  // 음료 이름
			canEach.add(canButton);
			selectCan.add(canEach);

			blist.add(canButton);  // 버튼 리스트에 버튼들 추가
		}

		add(selectCan, BorderLayout.NORTH);  // 음료 선택 북쪽
		add(moneyPanel, BorderLayout.CENTER);  // 돈 입출구 중앙
		add(getCanPanel, BorderLayout.SOUTH);  // 음료 반환구 남쪽

		// 각 패널 배경 색상 설정
		moneyPanel.setBackground(new Color(70, 152, 64));
		takeMoneyPanel.setBackground(new Color(70, 152, 64));
		putMoneyPanel.setBackground(new Color(70, 152, 64));
		getCanPanel.setBackground(new Color(70, 152, 64));
		setBackground(new Color(70, 152, 64));

		updateButtonColors();
	}

	private List<JButton> createCoinButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] coinValues = {10, 50, 100, 500};

		for (int value : coinValues) {
			JButton button = new JButton(value + "원");
			button.addActionListener(e -> {
				currentMoney += value;
				takeMoneytext.setText(String.valueOf(currentMoney));
				updateButtonColors();
			});
			buttons.add(button);
		}

		return buttons;
	}

	private List<JButton> createBillButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] billValues = {1000};

		for (int value : billValues) {
			JButton button = new JButton(value + "원");
			button.addActionListener(e -> {
				if (billCount < MAX_BILLS) {
					currentMoney += value;
					billCount++;
					takeMoneytext.setText(String.valueOf(currentMoney));
					updateButtonColors();
				} else {
					JOptionPane.showMessageDialog(this, "1000원 지폐는 5개까지만 투입 가능합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			});
			buttons.add(button);
		}

		return buttons;
	}

	public void updateCanLabels() {
		for (int i = 0; i < canLabels.size(); i++) {
			JLabel canLabel = canLabels.get(i);
			int canPrice = CanArray.canList.get(i).getCanPrice();
			canLabel.setText(canPrice + "원");
		}
	}

	public void updateCanButtons() {
		for (int i = 0; i < blist.size(); i++) {
			JButton button = blist.get(i);
			String canName = CanArray.canList.get(i).getCanName();
			button.setText(canName);
		}
	}

	public void updateButtonColors() {
		for (int i = 0; i < blist.size(); i++) {
			JButton button = blist.get(i);
			int stock = CanArray.canList.get(i).getCanNum();
			int price = CanArray.canList.get(i).getCanPrice();
			if (stock == 0) {
				button.setBackground(new Color(204, 61, 61));
			} else if (currentMoney >= price) {
				button.setBackground(new Color(20, 175, 100));
			} else {
				button.setBackground(Color.WHITE);
			}
		}
	}
}