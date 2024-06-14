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
import Person.Admin;
import client.ClientInterface;
import util.SalesData;
import util.SalesManager;

public class MachinePanelLeft extends JPanel {

	JButton getCan, canButton;
	JTextField takeMoneytext;
	List<JButton> blist;
	List<JLabel> canLabels;
	private int currentMoney = 0;
	private int billCount = 0;
	private int totalInsertedMoney = 0;  // 동전과 지폐의 총 합계 금액을 저장
	private MachinePanelRight panelRight;
	private SalesManager salesManager;
	private ClientInterface client;

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
		takeMoneyButton.addActionListener(new ReturnMoney(takeMoneytext, getCan, blist, panelRight, this)); // 버튼 액션

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
			canButton.addActionListener(new ButtonAction(takeMoneytext, getCan, blist, salesManager));
			canButton.setForeground(new Color(0, 0, 0));  // 음료 텍스트 색상
			canButton.setBackground(new Color(255, 255, 255));  // 음료 버튼 색상
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
	}

	private List<JButton> createCoinButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] coinValues = {10, 50, 100, 500};

		for (int value : coinValues) {
			JButton button = new JButton(value + "원");
			button.addActionListener(new CoinButtonAction(value, takeMoneytext, blist, panelRight, this));
			buttons.add(button);
		}

		return buttons;
	}

	private List<JButton> createBillButtons(List<JButton> blist) {
		List<JButton> buttons = new ArrayList<>();
		int[] billValues = {1000};

		for (int value : billValues) {
			JButton button = new JButton(value + "원");
			button.addActionListener(new BillButtonAction(value, takeMoneytext, blist, panelRight, this));
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

	public void updateButtonColors(int currentMoney) {
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

	public int getTotalInsertedMoney() {
		return totalInsertedMoney;
	}

	public void addInsertedMoney(int amount) {
		totalInsertedMoney += amount;
	}

	public void resetInsertedMoney() {
		totalInsertedMoney = 0;
	}

	public int getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(int currentMoney) {
		this.currentMoney = currentMoney;
	}

	public void setBillCount(int billCount) {
		this.billCount = billCount;
	}
}