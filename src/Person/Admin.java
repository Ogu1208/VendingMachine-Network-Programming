package Person;

// ������ Ŭ����
public class Admin {
	
	public static int totalMoney = 10000;  // �� ����� �⺻�� ����(��������)
	public static String password;  // ��й�ȣ(��������)
	
	public Admin(){}  // �⺻������

	public static int getTotalMoney() {
		return totalMoney;
	}  // �� �ݾ� Get

	public static void setTotalMoney(int totalMoney) {  // �� �ݾ� Set
		Admin.totalMoney = totalMoney;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String password) {  // ��й�ȣ ����
		// ��й�ȣ ����
		Admin.password = password;
	}
}
