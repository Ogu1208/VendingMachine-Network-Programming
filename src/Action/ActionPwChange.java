package Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.util.regex.Pattern;

import Machine.MachineFrame;
import Machine.MachinePanelRight;
import Machine.MachineMain;
import Person.Admin;
// ��й�ȣ ���� Ŭ����
public class ActionPwChange implements ActionListener{
	
	JTextField password;
	// ��й�ȣ ���� ������
	public ActionPwChange(JTextField password) {
		super();
		this.password = password;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if (password.getText().equals("")) {
			// �ƹ��͵� �Է����� �ʾ��� ���
			JOptionPane.showMessageDialog(new JFrame(), "�ٲٽ� ��й�ȣ�� �Է����ּ���");
		} 
		else if(password.getText().length() < 8) {
			//8�ڸ� �̻� �Է����� �ʾ��� ���
			JOptionPane.showMessageDialog(new JFrame(), "8�ڸ� �̻� �Է����ּ���");
		}
		
		else {
			// 8�ڸ� �̻��̰�
			boolean pattern = Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", password.getText());
			if(pattern == false) {  // ���Խ� ���ǿ� �������� ������
				// Ư�����ڳ����ڰ� ���Ե��� ������ ���Ե��� �ʾ����� �˸�
				JOptionPane.showMessageDialog(new JFrame(), "Ư�����ڳ� ���ڸ� �ϳ� �̻� ������ �ּ���");
			}
			else {
				// 8�ڸ� �̻��̰� ���Խ� ���ǿ� �����ϸ�
				Admin.setPassword(password.getText());  // ��й�ȣ�� ����
				JOptionPane.showMessageDialog(new JFrame(),   // ���������� �˸�
						password.getText() + " �� �����߽��ϴ�");
			}
			
		}
	}
}
