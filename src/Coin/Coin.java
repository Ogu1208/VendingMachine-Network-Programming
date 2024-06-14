package Coin;

// �ܵ� Ŭ����

import java.io.Serial;
import java.io.Serializable;

public class Coin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String coinName;
	private int coinNum;

	public Coin(String coinName, int coinNum) {
		this.coinName = coinName;
		this.coinNum = coinNum;
	}

	@Override
	public String toString() {
		return "[���� �̸� : " + coinName + ", ���� ���� : " + coinNum + "]";
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public int getCoinNum() {
		return coinNum;
	}

	public void setCoinNum(int coinNum) {
		this.coinNum = coinNum;
	}
}