# 키친포스

## 요구 사항

단체 지정
```
- 개별 주문 테이블을 그룹화할 수 있다. 
    * 주문 테이블은 최소 2개 이상이어야 한다.
    * 빈 테이블이나 이미 그룹화된 테이블은 사용할 수 없다.
- 주문 테이블 번호가 올바르지 않으면 등록할 수 없다.
    * 주문 테이블 번호는 실제로 존재하는 값이어야 한다.
- 단체 지정 테이블에 생성일자를 기록한다.
- 각각의 주문 테이블에 단체 번호를 부여한다. 
- 각각의 주문 테이블을 주문을 등록할 수 있는 상태로 변경한다.
- 그룹화된 주문 테이블 목록을 결과를 조회할 수 있다.
```

단체 지정 취소
```
- 단체 지정 테이블을 개별 주문 테이블로 변경할 수 있다.
- 단체로 지정된 주문 테이블을 조회한다.
- 주문 상태가 조리 또는 식사인 경우 개별 주문 테이블로 변경할 수 없다.
- 각각의 주문 테이블에 부여된 단체 번호를 삭제한다.
```

주문 테이블
```
- 초기 주문 테이블을 등록할 수 있다.
- 모든 주문 테이블을 조회할 수 있다.
- 주문 테이블을 빈 테이블로 변경할 수 있다.
    * 테이블이 단체 지정된 경우 변경할 수 없다.
    * 주문 상태가 조리 또는 식사인 경우 변경할 수 없다.
```

방문한 손님 수 
```
- 주문 테이블에 방문한 손님 수를 기록할 수 있다.
    * 방문한 손님 수가 0 보다 작을 수는 없다.
    * 주문 테이블 번호는 실제로 존재하는 값이어야 한다.
    * 주문 테이블이 빈 테이블이 아니어야 한다.
```

메뉴 그룹
```
- 메뉴 그룹을 등록할 수 있다.
- 모든 메뉴 그룹을 조회할 수 있다.
```

메뉴
```
- 메뉴를 등록할 수 있다.
    * 메뉴 가격은 0원 이상이어야 한다.
    * 메뉴 그룹 번호는 실제로 존재하는 값이어야 한다.
    * 메뉴 가격이 상품 가격 * 수량의 합계와 일치하는지 확인한다.
- 메뉴를 조회할 수 있다.
```

상품
```
- 상품을 등록할 수 있다.
    * 상품 가격은 0원 이상이어야 한다.
- 모든 상품을 조회할 수 있다.
```

주문
```
- 주문을 등록할 수 있다.
    * 메뉴 번호는 실제로 존재하는 값이어야 한다.
    * 테이블은 주문을 등록할 수 없는 빈 테이블이 아니어야 한다.
- 주문에 테이블 번호를 부여한다.
- 주문 상태를 조리로 변경한다.
- 주문 테이블에서 요청된 주문 항목에 주문 번호를 부여한다.
- 모든 주문을 조회할 수 있다.
- 주문 상태를 변경할 수 있다.
    * 계산 완료된 주문은 변경할 수 없다.
```

## 테이블 설명

menu_group : 메뉴를 하나로 묶어주는 단위를 관리하는 테이블

menu : 메뉴를 관리하는 테이블

menu_product : 메뉴, 상품 연결 테이블, 상품 수량을 추가함

product : 메뉴를 구성하는 상품을 관리하는 테이블

table_group : 개별 주문 테이블을 하나로 묶어주는 단위를 관리하는 테이블

order_table : 단체 번호, 방문한 손님 수, 주문 등록 상태를 관리하는 테이블

orders : 테이블 번호, 주문 상태를 관리하는 테이블

order_line_item : 주문, 메뉴 연결 테이블 테이블, 주문 수량을 추가함

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
