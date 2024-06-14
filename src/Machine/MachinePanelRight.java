package Machine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Action.AddCanFrame;
import Action.AddCoinFrame;
import Action.ActionPwChange;
import Action.ActionCollectMoney;
import Can.CanArray;
import Can.Can;
import Coin.CoinArray;
import Coin.Coin;
import Person.Admin;
import reports.SalesTableModel;
import util.SalesData;
import util.SalesManager;


public class MachinePanelRight extends JPanel implements ActionListener {

	private static final String INVENTORY_DATA_FILE = "inventory_data.dat";
	private SalesManager salesManager;
	JTextField adminPass, changePW, collectMoney;
	JPanel canAdminPanel, moneyAdminPanel, moneyTotalPanel, PWPanel, collectPanel;
	JButton btnAdminIn, btnAddCanStart, btnAddCan, salesReportButton;
	JLabel label;
	String password;
	DefaultTableModel canModel, moneyModel;
	public static JLabel totalMoneyLabel, PWLabel, collectLabel, totalBalanceLabel;
	public static JTable canTable, moneyTable;
	MachinePanelLeft panelLeft;
	private SalesTableModel tableModel;
	private JTable salesTable;

	public MachinePanelRight(String password, MachinePanelLeft panelLeft, SalesManager salesManager) {
		this.password = password;
		this.panelLeft = panelLeft;
		this.salesManager = salesManager;

		setPreferredSize(new Dimension(280, 630));

		label = new JLabel("관리자모드 - 비밀번호를 입력해주세요");
		label.setVisible(true);

		// ------------ <음료재고 관리 판넬> ------------ //
		canAdminPanel = new JPanel(new BorderLayout());

		String[] canColName = {"음료이름", "재고", "개당 판매가격"};
		canModel = new DefaultTableModel(canColName, 0);
		canTable = new JTable(canModel);
		JScrollPane canScrollPanel = new JScrollPane(canTable);
		canScrollPanel.setPreferredSize(new Dimension(230, 130));

		btnAddCan = new JButton("음료추가");
		btnAddCan.addActionListener(new AddCanFrame(canTable));

		canAdminPanel.add(new JLabel("음료관리"), BorderLayout.WEST);
		canAdminPanel.add(btnAddCan, BorderLayout.EAST);
		canAdminPanel.add(canScrollPanel, BorderLayout.SOUTH);
		canAdminPanel.setVisible(false);

		for (int i = 0; i < CanArray.canList.size(); i++) {
			String arr[] = {CanArray.canList.get(i).getCanName(),
					Integer.toString(CanArray.canList.get(i).getCanNum()),
					Integer.toString(CanArray.canList.get(i).getCanPrice())};
			canModel.addRow(arr);
		}

		// ------------ <잔돈 관리 판넬> ------------ //
		moneyAdminPanel = new JPanel(new BorderLayout());

		String[] moneyColName = {"동전 종류", "남은 갯수",};
		moneyModel = new DefaultTableModel(moneyColName, 0);
		moneyTable = new JTable(moneyModel);
		JScrollPane moneyScrollPanel = new JScrollPane(moneyTable);
		moneyScrollPanel.setPreferredSize(new Dimension(230, 120));

		JButton btnAddMoney = new JButton("잔돈추가");
		btnAddMoney.addActionListener(new AddCoinFrame(moneyTable));

		moneyAdminPanel.add(new JLabel("잔돈관리"), BorderLayout.CENTER);
		moneyAdminPanel.add(btnAddMoney, BorderLayout.EAST);
		moneyAdminPanel.add(moneyScrollPanel, BorderLayout.SOUTH);
		moneyAdminPanel.setVisible(false);

		moneyModel = (DefaultTableModel) moneyTable.getModel();

		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			String arr[] = {CoinArray.coinList.get(i).getCoinName(),
					Integer.toString(CoinArray.coinList.get(i).getCoinNum())};
			moneyModel.addRow(arr);
		}

		// ------------ <총 매출액 판넬> ------------ //
		moneyTotalPanel = new JPanel(new BorderLayout());
		totalMoneyLabel = new JLabel("총 매출액 : " + Admin.getTotalMoney());
		moneyTotalPanel.add(totalMoneyLabel);

		// 자판기 총 잔고 라벨 추가
		totalBalanceLabel = new JLabel("자판기 총 잔고 : " + calculateTotalBalance());
		moneyTotalPanel.add(totalBalanceLabel, BorderLayout.SOUTH);

		moneyTotalPanel.setVisible(false);

		// ------------ <비밀번호 변경 판넬> ----------- //
		PWPanel = new JPanel(new BorderLayout());

		JPanel PW1 = new JPanel();
		JPanel PW2 = new JPanel();
		PWLabel = new JLabel("(특수문자 및 숫자 하나 이상 포함, 8자리 이상)");
		changePW = new JTextField(10);
		changePW.setText("ex) Ogu1208!");
		JButton PWButton = new JButton("비밀번호 변경");

		changePW.addActionListener(new ActionPwChange(changePW));
		PWButton.addActionListener(new ActionPwChange(changePW));

		PW1.add(PWLabel);
		PW2.add(changePW);
		PW2.add(PWButton);

		PWPanel.add(PW2, BorderLayout.NORTH);
		PWPanel.add(PW1, BorderLayout.SOUTH);

		PWPanel.setVisible(false);

		// ------------ <수금 판넬> ----------- //
		collectPanel = new JPanel(new BorderLayout());

		JPanel collect1 = new JPanel();
		JPanel collect2 = new JPanel();
		collectLabel = new JLabel("수금할 금액을 입력하세요(원)");
		collectMoney = new JTextField(10);
		JButton collectButton = new JButton("수금");

		collectMoney.addActionListener(new ActionCollectMoney(collectMoney));
		collectButton.addActionListener(new ActionCollectMoney(collectMoney));

		collect1.add(collectLabel);
		collect2.add(collectMoney);
		collect2.add(collectButton);

		// 레이아웃 설정
		collectPanel.add(collect2, BorderLayout.NORTH);
		collectPanel.add(collect1, BorderLayout.SOUTH);

		collectPanel.setVisible(false);

		// ------------ <관리자 접속 판넬> ----------- //
		JPanel inAdminPanel = new JPanel();

		adminPass = new JTextField(10);
		btnAdminIn = new JButton("접속");
		adminPass.addActionListener(this);

		btnAdminIn.addActionListener(this);

		inAdminPanel.add(new JLabel("관리자"));
		inAdminPanel.add(adminPass);
		inAdminPanel.add(btnAdminIn);

		// 매출 보고서 버튼 추가
		salesReportButton = new JButton("일별/월별 매출 확인");
		salesReportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSalesReport();
			}
		});

		collectPanel.add(salesReportButton, BorderLayout.CENTER);

		add(inAdminPanel);
		add(canAdminPanel);
		add(label);
		add(moneyAdminPanel);
		add(moneyTotalPanel);
		add(PWPanel);
		add(collectPanel);

		addTableModelListeners();
		loadInventoryData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!canAdminPanel.isVisible()) {
			if (adminPass.getText().equals(Admin.password)) {
				label.setVisible(false);
				canAdminPanel.setVisible(true);
				moneyAdminPanel.setVisible(true);
				moneyTotalPanel.setVisible(true);
				PWPanel.setVisible(true);
				collectPanel.setVisible(true);

				btnAdminIn.setText("접속해제");
				adminPass.setText("");
				adminPass.setVisible(false);

			} else if (adminPass.getText().equals("")) {
				JOptionPane.showMessageDialog(new JFrame(), "비밀번호를 입력해주세요");
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "비밀번호가 틀렸습니다!");
			}
		} else if (canAdminPanel.isVisible()) {
			label.setVisible(true);
			canAdminPanel.setVisible(false);
			moneyAdminPanel.setVisible(false);
			moneyTotalPanel.setVisible(false);
			PWPanel.setVisible(false);
			collectPanel.setVisible(false);

			btnAdminIn.setText("접속");
			adminPass.setVisible(true);
		}

		salesManager.saveSalesData();
		saveInventoryData();
	}

	private void addTableModelListeners() {
		canModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 0) { // 이름 변경
					CanArray.canList.get(row).setCanName((String) canModel.getValueAt(row, column));
				} else if (column == 1) { // 재고 변경
					CanArray.canList.get(row).setCanNum(Integer.parseInt((String) canModel.getValueAt(row, column)));
				} else if (column == 2) { // 가격 변경
					CanArray.canList.get(row).setCanPrice(Integer.parseInt((String) canModel.getValueAt(row, column)));
				}

				panelLeft.updateCanLabels();
				panelLeft.updateCanButtons();
			}
		});

		moneyModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 1) { // 남은 갯수 변경
					CoinArray.coinList.get(row).setCoinNum(Integer.parseInt((String) moneyModel.getValueAt(row, column)));
				}

				updateTotalBalanceLabel();
			}
		});
	}

	public static void updateTotalBalanceLabel() {
		int totalBalance = 0;
		for (Coin coin : CoinArray.coinList) {
			totalBalance += coin.getCoinNum() * Integer.parseInt(coin.getCoinName());
		}
		totalBalanceLabel.setText("자판기 총 잔고 : " + totalBalance);
	}

	private int calculateTotalBalance() {
		int totalBalance = 0;
		for (Coin coin : CoinArray.coinList) {
			totalBalance += coin.getCoinNum() * Integer.parseInt(coin.getCoinName());
		}
		return totalBalance;
	}

	public static void updateMoneyTable() {
		DefaultTableModel moneyModel = (DefaultTableModel) moneyTable.getModel();
		moneyModel.setRowCount(0);
		for (Coin coin : CoinArray.coinList) {
			String[] row = {coin.getCoinName(), String.valueOf(coin.getCoinNum())};
			moneyModel.addRow(row);
		}
	}

	private void showSalesReport() {
		JDialog salesDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "매출 보고서", true);
		salesDialog.setSize(800, 600);
		salesDialog.setLayout(new BorderLayout());

		// 테이블 모델과 JTable 생성
		tableModel = new SalesTableModel();
		salesTable = new JTable(tableModel);
		TableRowSorter<SalesTableModel> sorter = new TableRowSorter<>(tableModel);
		salesTable.setRowSorter(sorter);

		// 데이터 채우기
		List<SalesData> salesDataList = salesManager.getSalesList();
		for (SalesData salesData : salesDataList) {
			tableModel.addSalesData(salesData);
		}

		JScrollPane scrollPane = new JScrollPane(salesTable);
		salesDialog.add(scrollPane, BorderLayout.CENTER);

		// 버튼 패널 생성
		JPanel buttonPanel = new JPanel();
		JButton sortByDateButton = new JButton("날짜순 정렬");
		JButton sortByPriceButton = new JButton("매출액순 정렬");
		JButton sortByVolumeButton = new JButton("판매량순 정렬");
		JButton monthlyReportButton = new JButton("월별 보고서");

		buttonPanel.add(sortByDateButton);
		buttonPanel.add(sortByPriceButton);
		buttonPanel.add(sortByVolumeButton);
		buttonPanel.add(monthlyReportButton);
		salesDialog.add(buttonPanel, BorderLayout.SOUTH);

		// 버튼 이벤트 리스너 추가
		sortByDateButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.DESCENDING))));
		sortByPriceButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(2, SortOrder.DESCENDING))));
		sortByVolumeButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING))));
		monthlyReportButton.addActionListener(e -> showMonthlyReport());

		salesDialog.setVisible(true);
	}

	private void showMonthlyReport() {
		JDialog monthlyDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "월별 매출 보고서", true);
		monthlyDialog.setSize(800, 600);
		monthlyDialog.setLayout(new BorderLayout());

		// 테이블 모델과 JTable 생성
		tableModel = new SalesTableModel();
		salesTable = new JTable(tableModel);
		TableRowSorter<SalesTableModel> sorter = new TableRowSorter<>(tableModel);
		salesTable.setRowSorter(sorter);

		// 월별 데이터 계산 및 채우기
		Map<String, Map<String, int[]>> monthlyData = new TreeMap<>(Collections.reverseOrder());

		for (SalesData salesData : salesManager.getSalesList()) {
			String month = salesData.getDate().substring(0, 7); // "yyyy-MM"
			monthlyData.putIfAbsent(month, new HashMap<>());
			monthlyData.get(month).putIfAbsent(salesData.getCanName(), new int[]{0, 0});
			monthlyData.get(month).get(salesData.getCanName())[0] += salesData.getTotalSales();
			monthlyData.get(month).get(salesData.getCanName())[1] += salesData.getQuantitySold();
		}

		for (String month : monthlyData.keySet()) {
			for (String canName : monthlyData.get(month).keySet()) {
				int[] data = monthlyData.get(month).get(canName);
				tableModel.addRow(new Object[]{month, canName, data[0], data[1]});
			}
		}

		JScrollPane scrollPane = new JScrollPane(salesTable);
		monthlyDialog.add(scrollPane, BorderLayout.CENTER);

		// 버튼 패널 생성
		JPanel buttonPanel = new JPanel();
		JButton sortByPriceButton = new JButton("매출액순 정렬");
		JButton sortByVolumeButton = new JButton("판매량순 정렬");

		buttonPanel.add(sortByPriceButton);
		buttonPanel.add(sortByVolumeButton);
		monthlyDialog.add(buttonPanel, BorderLayout.SOUTH);

		// 버튼 이벤트 리스너 추가
		sortByPriceButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(2, SortOrder.DESCENDING))));
		sortByVolumeButton.addActionListener(e -> sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(3, SortOrder.DESCENDING))));

		monthlyDialog.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void loadInventoryData() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_DATA_FILE))) {
			CanArray.canList = (java.util.List<Can>) ois.readObject();
			CoinArray.coinList = (java.util.List<Coin>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveInventoryData() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_DATA_FILE))) {
			oos.writeObject(CanArray.canList);
			oos.writeObject(CoinArray.coinList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
