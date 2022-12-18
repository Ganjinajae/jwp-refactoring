# 키친포스

## 요구 사항
### 메뉴 그룹
1. 메뉴 그룹을 생성한다.
2. 메뉴 그룹을 조회한다.
### 메뉴
1. 메뉴 생성 ( 메뉴는 다음과 같은 제약사항을 가진다. )
- 메뉴의 가격은 0원 이상이여야 한다.
- 메뉴는 메뉴 그룹에 속해야 한다.
- 메뉴 상품은 등록된 상품이여야 한다.
- 메뉴의 가격은 메뉴 상품 가격의 합보다 작다.
2. 메뉴 조회
### 주문 테이블
1. 주문 테이블을 생성할 수 있다.
2. 주문 테이블을 조회할 수 있다.
3. 주문 테이블은 빈테이블로 상태를 변경할 수 있다. ( 다음과 같은 제약사항이 존재한다. )
- 주문 테이블은 등록되어 있어야 한다.
- 단체 지정된 주문 테이블은 변경할 수 없다.
- 주문 테이블의 조리, 식사 중인 주문이 있으면 변경할 수 없다.
4. 주문 테이블의 방문한 손님 수를 변경할 수 있다. ( 다음과 같은 제약사항이 존재한다. )
- 변경하려는 손님 수는 0명 이상이어야 한다.
- 존재하는 주문 테이블이어야 한다.
- 빈 테이블은 변경할 수 없다.
### 주문
1. 주문을 생성할 수 있다. ( 주문을 생성하기에 다음과 같은 제약사항을 가진다. )
- 주문 메뉴들은 등록되어 있어야 한다.
- 주문 테이블에 해당 사항이 등록되어 있어야 한다.
- 주문 테이블은 빈 상태가 아니여야 한다.
2. 주문의 상태를 수정할 수 있다.
- 주문이 존재해야 한다.
- 계산 완료된 주문이라면 수정할 수 없다.
3. 주문을 조회할 수 있다.
### 상품
1. 상품을 생성할 수 있다.
- 상품의 가격은 0원 이상이여야 한다.
2. 상품을 조회할 수 있다.
### 단체 지정
1. 단체 지정을 생성할 수 있다.
- 단체 지정된 테이블은 등록된 테이블이어야 한다.
- 주문 테이블들이 2개 이상이어야 단체 지정이 가능하다.
- 빈 주문 테이블만 단체 지정을 할 수 있다.
- 주문 테이블이 다른 단체에 지정되지 않아야 한다.
2. 단체 지정을 해제할 수 있다.
- 주문 테이블들의 상태가 조리, 식사 상태라면 단체 지정을 해제할 수 있다.

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

## Step1 - 테스트를 통한 코드 보호
#### 요구사항 1
- kitchenpos 패키지의 코드를 보고 키친포스의 요구 사항을 README.md에 작성한다. 미션을 진행함에 있어 아래 문서를 적극 활용한다.
#### 요구사항 2
- 정리한 키친포스의 요구 사항을 토대로 테스트 코드를 작성한다. 모든 Business Object에 대한 테스트 코드를 작성한다. @SpringBootTest를 이용한 통합 테스트 코드 또는 @ExtendWith(MockitoExtension.class)를 이용한 단위 테스트 코드를 작성한다.
#### 구현 리스트
- [ ] 키친포스 요구 사항 정리
* 최단 경로 조회 기능 구현하기 ( Outside In으로 구현 )
1. Product
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성
2. Menu Group
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성
3. Menu
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성
4. Order Table
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성
5. Table group
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성
6. Order
- [x] 인수테스트 작성
- [x] 비즈니스 테스트 작성