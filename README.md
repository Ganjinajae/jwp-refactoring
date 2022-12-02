# 키친포스

## 요구 사항

### 이름

- [ ] 이름을 생성한다.
    - [ ] 이름은 null 일 수 없다.
    - [ ] 이름은 공백일 수 없다.

### 가격

- [ ] 가격을 생성한다.
    - [ ] 메뉴의 가격이 음수일 수 없다.
    - [ ] 메뉴의 가격은 필수값이다.

### 메뉴

#### 메뉴 그룹

- [ ] [x] 메뉴 그룹을 생성한다.
    - [ ] [ ] 이름을 갖는다.
- [ ] [ ] 메뉴 그룹 목록을 조회한다.

#### 메뉴

- [ ] [ ] 메뉴를 생성한다.
    - [ ] [ ] 메뉴 그룹을 필수값으로 갖는다.
    - [ ] [ ] 이름을 갖는다.
    - [ ] [ ] 가격을 갖는다.
    - [ ] [ ] 메뉴의 가격이 메뉴 상품의 합보다 클 수 없다.
    - [ ] [ ] 메뉴 상품 리스트를 갖는다.
- [ ] [ ] 메뉴 목록을 조회한다.

#### 메뉴 상품

- [ ] [ ] 메뉴 상품을 생성한다.
    - [ ] [ ] 메뉴를 필수로 갖는다.
    - [ ] [ ] 상품을 필수로 갖는다.
    - [ ] [ ] 갯수를 필수로 갖는다.

### 주문테이블

#### 테이블 그룹

- [ ] [ ] 테이블 그룹을 생성한다.
    - [ ] [ ] 주문 테이블의 갯수가 2보다 작을 수 없다.
    - [ ] [ ] 주문 테이블이 비어있을 수 없다.
    - [ ] [ ] 생성 시간을 필수로 갖는다.
- [ ] [ ] 테이블 그룹을 해제한다.
    - [ ] [ ] 요리중일 경우 해제할 수 없다.
    - [ ] [ ] 식사중일 경우 해제할 수 없다.
- [ ] [ ] 테이블 그룹을 조회한다.

#### 주문 테이블

- [ ] [ ] 주문 테이블을 생성한다.
    - [ ] [ ] 주문 테이블 그룹을 필수값으로 갖는다.
    - [ ] [ ] 고객수를 필수 값으로 갖는다.
    - [ ] [ ] 착석 여부를 필수값으로 갖는다.
- [ ] [ ] 공석 상태로 변경한다.
    - [ ] [ ] 테이블 그룹이 없을 수 없다.
    - [ ] [ ] 요리중일 경우 변경할 수 없다.
    - [ ] [ ] 식사중일 경우 변경할 수 없다.
- [ ] [ ] 손님수를 변경한다.
    - [ ] [ ] 0명보다 작을 수 없다.
    - [ ] [ ] 주문테이블이 없을 수 없다.
- [ ] [ ] 주문 테이블을 조회한다.
- [ ] [ ] 공석으로 변경한다.

### 상품

- [ ] [ ] 상품을 생성한다.
    - [ ] [ ] 가격을 필수로 갖는다.
    - [ ] [ ] 이름을 필수로 갖는다.
- [ ] [ ] 상품 목록을 조회한다.

### 주문

- [ ] [x] 주문을 생성한다.
    - [ ] [x] 주문 항목이 비어있을 수 없다.
    - [ ] [x] 주문 항목의 수와 메뉴의 수는 같아야 한다.
    - [ ] [x] 주문 테이블은 비어있을 수 없다.
    - [ ] [x] 주문 상태는 요리중 상태를 가진다.
    - [ ] [x] 주문 시간을 갖는다.
    - [ ] [x] 주문 테이블을 가진다.
    - [ ] [x] 주문 항목을 갖는다.
- [ ] [ ] 주문상태를 식사중으로 변경한다.
    - [ ] [ ] 주문완료일 경우 주문상태를 변경할 수 없다.
- [ ] [ ] 주문상태를 완료로 변경한다.
- [ ] [ ] 주문을 조회한다.

### 주문 항목

- [ ] [ ] 주문 항목을 생성한다.
    - [ ] [ ] 주문을 갖는다.
    - [ ] [ ] 메뉴를 갖는다.
    - [ ] [ ] 갯수를 갖는다.

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
