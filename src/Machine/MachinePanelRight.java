package Machine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Action.AddCanFrame;
import Action.AddCoinFrame;
import Action.ActionPwChange;
import Action.ActionCollectMoney;
import Can.CanArray;
import Coin.CoinArray;
import Person.Admin;


public class MachinePanelRight extends JPanel implements ActionListener {

	JTextField adminPass, changePW, collectMoney;
	JPanel canAdminPanel, moneyAdminPanel, moneyTotalPanel, PWPanel, collectPanel;
	JButton btnAdminIn, btnAddCanStart, btnAddCan;
	JLabel label;
	String password;
	DefaultTableModel canModel;
	public static JLabel totalMoneyLabel, PWLabel, collectLabel;
	public static JTable canTable, moneyTable;

	public MachinePanelRight(String password) {
		// ���� ������ �ǳ�
		this.password = password;
		
		setPreferredSize(new Dimension(280, 630)); //������â ũ�� ����

		label = new JLabel("�����ڸ�� - ��й�ȣ�� �Է����ּ���");  // �����ǳ� �⺻��
		label.setVisible(true);  // visible true�� �ʱ�ȭ

		// ------------ <������� ���� �ǳ�> ------------ //
		canAdminPanel = new JPanel(new BorderLayout());

		String[] canColName = { "�����̸�", "���", "���� �ǸŰ���" };  // ���̺� ���
		canModel = new DefaultTableModel(canColName, 0); // JTable�� ����� ����
		canTable = new JTable(canModel);  // JTable
		JScrollPane canScrollPanel = new JScrollPane(canTable);  // ��ũ��
		canScrollPanel.setPreferredSize(new Dimension(230, 150));

		btnAddCan = new JButton("�����߰�");  // �����߰� ��ư
		btnAddCan.addActionListener(new AddCanFrame(canTable));  // �����߰� ��ư �׼�

		// ������� ���� �ǳ� ���̾ƿ� ��ġ ����
		canAdminPanel.add(new JLabel("�������"), BorderLayout.WEST);
		canAdminPanel.add(btnAddCan, BorderLayout.EAST);
		canAdminPanel.add(canScrollPanel, BorderLayout.SOUTH);
		canAdminPanel.setVisible(false);  // �⺻���� �Ⱥ���

		// �����̸�|���|�����ǸŰ��� ǥ ���
		for (int i = 0; i < CanArray.canList.size(); i++) {
			String arr[] = { CanArray.canList.get(i).getCanName(),
					Integer.toString(CanArray.canList.get(i).getCanNum()),
					Integer.toString(CanArray.canList.get(i).getCanPrice()) };
			canModel.addRow(arr);
		}

		// ------------ <�ܵ� ���� �ǳ�> ------------ //
		moneyAdminPanel = new JPanel(new BorderLayout());

		String[] moneyColName = { "���� ����", "���� ����", };// ���̺� ���
		DefaultTableModel moneyModel = new DefaultTableModel(moneyColName, 0);// JTable�� ����� ����
		moneyTable = new JTable(moneyModel);
		JScrollPane moneyScrollPanel = new JScrollPane(moneyTable); // ��ũ��
		moneyScrollPanel.setPreferredSize(new Dimension(230, 150));

		JButton btnAddMoney = new JButton("�ܵ��߰�"); // �ܵ��߰� ��ư
		btnAddMoney.addActionListener(new AddCoinFrame(moneyTable));  // �ܵ��߰� ��ư �׼�
		
		// �ܵ� ���� �ǳ� ���̾ƿ� ����
		moneyAdminPanel.add(new JLabel("�ܵ�����"), BorderLayout.CENTER);
		moneyAdminPanel.add(btnAddMoney, BorderLayout.EAST);
		moneyAdminPanel.add(moneyScrollPanel, BorderLayout.SOUTH);
		moneyAdminPanel.setVisible(false);   // �⺻���� �Ⱥ���

		moneyModel = (DefaultTableModel) moneyTable.getModel();

		// ���� ���� | ���� ���� ���
		for (int i = 0; i < CoinArray.coinList.size(); i++) {
			String arr[] = { CoinArray.coinList.get(i).getCoinName(),
					Integer.toString(CoinArray.coinList.get(i).getCoinNum()) };
			moneyModel.addRow(arr);
		}

		// ------------ <�� ����� �ǳ�> ------------ //
		moneyTotalPanel = new JPanel(new BorderLayout());
		totalMoneyLabel = new JLabel("�� ����� : " + Admin.getTotalMoney());
		moneyTotalPanel.add(totalMoneyLabel);
		moneyTotalPanel.setVisible(false);  // �⺻���� �Ⱥ���
		

		// ------------ <��й�ȣ ���� �ǳ�> ----------- //
		PWPanel = new JPanel(new BorderLayout());  // ��ü �ǳ�
		
		JPanel PW1 = new JPanel();  // �ȳ��� �ǳ�
		JPanel PW2 = new JPanel();  // �Էºκ� �ǳ�
		PWLabel = new JLabel("(Ư������ �� ���� �ϳ� �̻� ����, 8�ڸ� �̻�)");  // �ȳ� ��
		changePW = new JTextField(10);  // ��й�ȣ �Է� JTextField(��������)
		changePW.setText("ex) Ogu1208!");  // ���� ����
		//changePW.setEditable(true);
		JButton PWButton = new JButton("��й�ȣ ����");
		
		
		changePW.addActionListener(new ActionPwChange(changePW));  // �Է��ؽ�Ʈ�ʵ� �׼�
		PWButton.addActionListener(new ActionPwChange(changePW));  // ��й�ȣ ���� ��ư �׼�

		PW1.add(PWLabel);  // PW1�� �ȳ�
		PW2.add(changePW);  //PW2�� ����κ�
		PW2.add(PWButton);
		
		// ��й�ȣ ���� �ǳ� ���̾ƿ� ����
		PWPanel.add(PW2, BorderLayout.NORTH);
		PWPanel.add(PW1, BorderLayout.SOUTH);
		
		PWPanel.setVisible(false);  // �⺻���� �Ⱥ���
		
		// ------------ <���� �ǳ�> ----------- //
		collectPanel = new JPanel(new BorderLayout()); // ��ü �ǳ�
		
		JPanel collect1 = new JPanel(); // �ȳ��� �ǳ�
		JPanel collect2 = new JPanel(); // �Էºκ� �ǳ�
		collectLabel = new JLabel("������ �ݾ��� �Է��ϼ���(��)");
		collectMoney = new JTextField(10);
		//changePW.setEditable(true);
		JButton collectButton = new JButton("����");
		
		
		collectMoney.addActionListener(new ActionCollectMoney(collectMoney)); // �Է��ؽ�Ʈ�ʵ� �׼�
		collectButton.addActionListener(new ActionCollectMoney(collectMoney)); // ���� ��ư �׼�

		collect1.add(collectLabel); // collect1�� �ȳ�
		collect2.add(collectMoney); // collect2�� ����
		collect2.add(collectButton);
		
		// ���̾ƿ� ����
		collectPanel.add(collect2, BorderLayout.NORTH);
		collectPanel.add(collect1, BorderLayout.SOUTH);
		
		collectPanel.setVisible(false); // �⺻���� �Ⱥ���
		
		// ------------ <������ ���� �ǳ�> ----------- //
		JPanel inAdminPanel = new JPanel();  // ū Ʋ

		adminPass = new JTextField(10);  // ��й�ȣ �ؽ�Ʈfield
		btnAdminIn = new JButton("����");
		adminPass.addActionListener(this);  // ��й�ȣ �ؽ�Ʈfield dortus
		
		btnAdminIn.addActionListener(this);  // ���� ��ư �׼�

		inAdminPanel.add(new JLabel("������"));
		inAdminPanel.add(adminPass);
		inAdminPanel.add(btnAdminIn);

		// ������ �ǳڿ� �� �ǳڵ� �߰�
		add(inAdminPanel);
		add(canAdminPanel);
		add(label);
		add(moneyAdminPanel);
		add(moneyTotalPanel);
		add(PWPanel);
		add(collectPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ������ ���� ��й�ȣȮ��
				if(!canAdminPanel.isVisible()){  // canAdminPanel�� ������ ���� ���(���� �͵� �� canAdminpanel�� �������� ��)
					if(adminPass.getText().equals(Admin.password)){  // ��й�ȣ�� ��ġ�� ���
						
						label.setVisible(false);
						// �Ⱥ����� �ǳڵ� ���̵��� ����
						canAdminPanel.setVisible(true);
						moneyAdminPanel.setVisible(true);
						moneyTotalPanel.setVisible(true);
						PWPanel.setVisible(true);
						collectPanel.setVisible(true);
						
						btnAdminIn.setText("��������");
						adminPass.setText("");
						adminPass.setVisible(false);
						
					} 
					else if(adminPass.getText().equals("")) {  // ��й�ȣ�� �Է����� �ʾ��� ���
						JOptionPane.showMessageDialog(new JFrame(), "��й�ȣ�� �Է����ּ���");
					} 
					else {  // ��й�ȣ�� Ʋ���� ���
						JOptionPane.showMessageDialog(new JFrame(), "��й�ȣ�� Ʋ�Ƚ��ϴ�!");
					}
				} else if(canAdminPanel.isVisible()){ // canAdminPanel�� ���� ���
					
					// �� �ǳڵ��� ������ �ʵ��� ����
					label.setVisible(true);
					canAdminPanel.setVisible(false);
					moneyAdminPanel.setVisible(false);
					moneyTotalPanel.setVisible(false);
					PWPanel.setVisible(false);
					collectPanel.setVisible(false);
					
					btnAdminIn.setText("����");
					adminPass.setVisible(true);
				}
	}

}
