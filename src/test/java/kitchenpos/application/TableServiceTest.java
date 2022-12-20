package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import kitchenpos.table.application.TableGroupService;
import kitchenpos.table.application.TableService;
import kitchenpos.table.dao.OrderTableDao;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.menu.application.ProductService;
import kitchenpos.menu.domain.Product;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.table.dto.OrderTableRequest;
import kitchenpos.menu.dto.ProductRequest;
import kitchenpos.table.dto.TableGroupRequest;
import kitchenpos.table.dto.TableRequest;
import kitchenpos.order.application.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class TableServiceTest {

    @Autowired
    OrderTableDao orderTableDao;

    @Autowired
    TableService tableService;

    @Autowired
    TableGroupService tableGroupService;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuGroupService menuGroupService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("테이블 생성 테스트")
    void createTest(){
        // given

        // when
        OrderTable orderTable = tableService.create(new OrderTableRequest(0, true));

        // then
        assertThat(orderTable.getId()).isNotNull();
        assertThat(orderTable.getTableGroup()).isNull();
    }

    @Test
    @DisplayName("테이블 상태변경 : empty false -> true")
    void changeEmptyTest(){
        // given
        OrderTable orderTable = tableService.create(new OrderTableRequest(false));

        // when
        OrderTable emptyTable = tableService.changeEmpty(orderTable.getId(), new OrderTableRequest(true));

        // then
        assertThat(emptyTable.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("그룹핑된 테이블의 상태를 변경할 수 없습니다.")
    void changeEmptyFailTest1(){
        // given
        OrderTable orderTable1 = orderTableDao.save(new OrderTable(2, true));
        OrderTable orderTable2 = orderTableDao.save(new OrderTable(3, true));
        tableGroupService.create(
                new TableGroupRequest(
                        Arrays.asList(new TableRequest(orderTable1.getId()), new TableRequest(orderTable2.getId()))
                )
        );

        // when
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeEmpty(orderTable1.getId(), new OrderTableRequest(true))
        );

        // then
    }

    @Test
    @DisplayName("주문이 조리 상태면 테이블 상태를 empty 로 변경 할 수 없습니다.")
    void changeEmptyFailTest2(){
        // given
        OrderTable orderTable = tableService.create(new OrderTableRequest(2, false));

        Product product1 = productService.create(new ProductRequest("상품1", new BigDecimal(1000)));
        Product product2 = productService.create(new ProductRequest("상품2", new BigDecimal(2000)));
        MenuGroup group1 = menuGroupService.create(new MenuGroup("그룹1"));
        Menu menu1 = menuService.create(new MenuRequest("메뉴1", new BigDecimal(1000), group1.getId(), Arrays.asList(
                new MenuProductRequest(product1.getId(), 1),
                new MenuProductRequest(product2.getId(), 1)
        )));
        orderService.create(new OrderRequest(orderTable.getId(),
                Collections.singletonList(new OrderLineItemRequest(menu1.getId(), 1))));

        // when
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeEmpty(orderTable.getId(), new OrderTableRequest(true))
        );

        // then
    }

    @Test
    @DisplayName("테이블 인원 수 변경 테스트")
    void changeNumberOfGuestsTest(){
        // given
        OrderTable orderTable = tableService.create(new OrderTableRequest(0, false));

        // when
        OrderTable table = tableService.changeNumberOfGuests(orderTable.getId(), new OrderTableRequest(2));

        // then
        assertThat(table.getNumberOfGuests()).isEqualTo(2);
    }

    @Test
    @DisplayName("인원 수 변경 시, 음수로 변경 할 수 없다.")
    void changeNumberOfGuestsFailTest(){
        // given
        OrderTable orderTable = tableService.create(new OrderTableRequest(0, false));

        // when
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeNumberOfGuests(orderTable.getId(), new OrderTableRequest(-2))
        );

        // then
    }

    @Test
    @DisplayName("비어있는 테이블의 인원 수는 변경할 수 없다.")
    void changeNumberOfGuestsFail2Test(){
        // given
        OrderTable orderTable = tableService.create(new OrderTableRequest(0, true));

        // when
        assertThatIllegalArgumentException().isThrownBy(
                () -> tableService.changeNumberOfGuests(orderTable.getId(), new OrderTableRequest(2))
        );

        // then
    }
}