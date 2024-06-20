**4학년 2학기 네트워크 프로그래밍**

[자판기관리프로그램_보고서(설계서 포함).pdf](https://prod-files-secure.s3.us-west-2.amazonaws.com/a1b66f85-a0eb-4149-b91f-458ea42172a3/fc9dc737-5ded-43e3-a773-ea00875e7162/%EC%9E%90%ED%8C%90%EA%B8%B0%EA%B4%80%EB%A6%AC%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8_%EB%B3%B4%EA%B3%A0%EC%84%9C(%EC%84%A4%EA%B3%84%EC%84%9C_%ED%8F%AC%ED%95%A8).pdf)

- Java, Swing을 이용한 자판기 관리 GUI 프로그램 구현
- 멀티스레드와 소켓 프로그래밍을 통한 클라이언트-서버 (Server, Client1, Client2) 통신 구현
- 음료 구매, 재고 관리, 관리자 기능 구현


## 개요
### 1.1 프로그램 개요
- 작성자 : 김민아
- 프로그램명 : 자판기 관리 프로그램

### 1.2 개발 환경
- 프로그램 동작 환경 : Windows
- 사용 컴파일러 : IDE : IntelliJ
- 사용 언어 : Java 11

## 상위 설계서 
### 기능
[사용자 입장]
- 금액 투입: 자판기에 5000원 이하의 금액을 투입할 수 있습니다. 투입 금액은 10원, 50원, 100원, 500원, 1000원 단위로 가능하다.
- 구입 가능한 음료 선택: 투입한 금액으로 구입 가능한 항목 중에서 원하는 음료를 선택할 수 있다.
- 음료는 재고 여부와 투입 금액에 따라 버튼의 색깔이 바뀐다.
- 구입 가능 표시: 투입 금액이 음료 가격 이상이고 재고가 있는 경우 초록색으로 표시된다.
- 품절 표시: 재고가 없는 경우 빨간색으로 표시되어 구입이 불가능하다.
- 잔돈 반환: 잔돈 반환 버튼을 누르면 남은 금액을 반환받을 수 있습니다. 반환은 큰 금액의 동전부터 차례대로 이루어진다.

[관리자 입장]
- 관리자 모드 접속: 자판기 비밀번호를 입력하여 관리자 모드에 접속할 수 있다.
- 비밀번호는 특수문자와 숫자가 하나 이상 포함된 8자리 이상이어야 하며, 필요 시 변경할 수 있다.
- 음료 재고 관리: 음료 재고를 조회하고 추가할 수 있다.
- 잔돈 재고 관리: 잔돈 재고를 조회하고 추가할 수 있다.
- 총 매출액 조회 및 수금: 총 매출액을 조회하고 수금할 수 있습니다. 수금 시에는 반환을 위한 최소한의 화폐를 남겨두어야 한다.
- 비밀번호 변경: 관리자 모드에서 비밀번호를 변경할 수 있다.
- 일별/월별 매출 조회: 일별 및 월별 매출 현황을 조회할 수 있습니다. 매출 데이터는 파일로 저장 및 불러올 수 있다.

[주요 기능]
- 재고 및 매출 관리: 일별/월별 매출 데이터와 재고 데이터는 파일로 저장 및 불러오기가 가능하다.
- 동적 할당 및 해제: 금액을 투입할 때는 동적 할당을 사용하며, 반환 또는 판매가 완료되면 동적 할당을 해제한다.
- 화폐 및 재고 관리: 잔돈은 각 동전별로 10개를 기본 값으로 하여 관리하며, 판매에 따른 동전의 가감이 구현한다.
- 음료 이름 및 가격 변경: 관리자는 각 음료의 판매 이름과 가격을 변경할 수 있다.
- 실시간 재고 현황 파악: 각 자판기의 실시간 재고 현황을 파악할 수 있다.
- 멀티 스레드: 판매 및 관리자 기능은 멀티 스레드를 통해 독립적으로 동작한다.
- Socket 프로그래밍: 자판기 관리 프로그램에서 생성/저장된 모든 데이터는 Socket 프로그래밍을 통해 서버로 전송되고 관리된다.


### 아키텍쳐
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/8a5febd1-46ae-4dc9-8c3e-ac85f9ce92a6)


### 상태 다이어그램
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/9a54fd99-03cb-4b70-9644-7abea177c97c)

### 유스케이스 다이어그램
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/525e2ace-43f1-4686-8454-2aab3ae348d3)

![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/43fde1e3-4557-40ce-ac57-5296044097ea)

## 최종
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/cddbbf6e-1216-48ad-b8c2-b64562901884)

![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/1c58a5b7-e3b9-4a8b-bb4b-d55ae58e1a59)

![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/9cad1bab-726a-44fd-8d0f-9f9dec87cc97)


### 매출 보고서 일별 + 월
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/be412475-7c5f-4d4f-9c7b-661b379c871f)
![image](https://github.com/Ogu1208/VendingMachine-Network-Programming/assets/76902448/04aea197-a66d-48ac-b64f-69c11103f316)
