package kitchenpos.table.application;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.order.domain.*;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.product.domain.Product;
import kitchenpos.table.application.TableService;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.OrderTables;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.table.dto.OrderTableRequest;
import kitchenpos.table.dto.OrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderTableRepository orderTableRepository;

    @InjectMocks
    private TableService tableService;

    private Product 후라이드치킨;
    private MenuProduct 후라이드치킨상품;
    private MenuGroup 치킨단품;
    private Menu 후라이드치킨단품;
    private OrderLineItemRequest 후라이드치킨세트주문요청;
    private Order 주문;
    private OrderTable 주문테이블1;
    private OrderTable 주문테이블2;

    @BeforeEach
    void setUp() {
        후라이드치킨 = Product.of(1L, "후라이드치킨", BigDecimal.valueOf(15000));
        후라이드치킨상품 = MenuProduct.of(후라이드치킨, 1L);
        치킨단품 = MenuGroup.of(1L, "치킨단품");
        후라이드치킨단품 = Menu.of(1L, "후라이드치킨세트", BigDecimal.valueOf(15000), 치킨단품.getId(), Collections.singletonList(후라이드치킨상품));
        후라이드치킨세트주문요청 = new OrderLineItemRequest(후라이드치킨단품.getId(), 2);
        주문테이블1 = OrderTable.of(1L, null, 5, false);
        주문테이블2 = OrderTable.of(2L, null, 4, false);
        주문 = Order.of(1L, 주문테이블2.getId(), OrderLineItems.of(Collections.singletonList(후라이드치킨세트주문요청.toOrderLineItem(OrderMenu.of( 후라이드치킨단품)))));
    }

    @DisplayName("주문 테이블을 생성한다.")
    @Test
    void 주문테이블_생성() {
        // given
        when(orderTableRepository.save(any(OrderTable.class))).thenReturn(주문테이블1);

        OrderTableResponse response = tableService.create(
                new OrderTableRequest(주문테이블1.getNumberOfGuests(), 주문테이블1.isEmpty()));

        assertAll(
                () -> assertThat(주문테이블1.getId()).isEqualTo(response.getId()),
                () -> assertThat(주문테이블1.getNumberOfGuests()).isEqualTo(response.getNumberOfGuests())
        );
    }

    @DisplayName("주문 테이블 목록을 조회한다.")
    @Test
    void 주문테이블_목록_조회() {
        // given
        when(orderTableRepository.findAll()).thenReturn(Arrays.asList(주문테이블1, 주문테이블2));

        List<OrderTableResponse> responses = tableService.list();

        assertAll(
                () -> assertThat(responses).hasSize(2),
                () -> assertThat(responses.stream().map(OrderTableResponse::getId).collect(toList()))
                        .containsExactly(주문테이블1.getId(), 주문테이블2.getId())
        );
    }

    @DisplayName("주문 테이블 빈자리 여부를 변경한다.")
    @Test
    void 주문테이블_빈자리_여부_변경() {
        // given
        주문.changeOrderStatus(OrderStatus.COMPLETION);
        boolean isEmpty = 주문테이블1.isEmpty();
        int numberOfGuests = 주문테이블1.getNumberOfGuests();
        OrderTableRequest changeOrderTableRequest = new OrderTableRequest(numberOfGuests, !isEmpty);
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));
        when(orderRepository.findAllByOrderTableId(주문테이블1.getId())).thenReturn(Collections.singletonList(주문));

        // when
        OrderTableResponse resultOrderTable = tableService.changeEmpty(주문테이블1.getId(), changeOrderTableRequest);

        // then
        assertThat(resultOrderTable.isEmpty()).isEqualTo(!isEmpty);
    }

    @DisplayName("등록되지 않은 주문 테이블은 빈자리 여부를 변경할 수 없다.")
    @Test
    void 등록되지_않은_주문테이블_빈자리_여부_변경() {
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> tableService.changeEmpty(주문테이블1.getId(), new OrderTableRequest(0, true))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("단체 지정이 된 주문 테이블은 빈자리 여부를 변경할 수 없다.")
    @Test
    void 단체_주문테이블_빈자리_여부_변경() {
        // given
        TableGroup tableGroup = TableGroup.of(1L);
        ReflectionTestUtils.setField(주문테이블1, "empty", true);
        ReflectionTestUtils.setField(주문테이블2, "empty", true);
        OrderTables orderTables = OrderTables.of(Arrays.asList(주문테이블1, 주문테이블2));
        orderTables.group(tableGroup.getId());

        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));
        when(orderRepository.findAllByOrderTableId(any())).thenReturn(Collections.emptyList());

        assertThatThrownBy(() ->
            tableService.changeEmpty(주문테이블1.getId(), new OrderTableRequest(0, true))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("조리중/식사중인 주문 테이블은 빈자리 여부를 변경할 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = { "COOKING", "MEAL" })
    void 조리중_식사중인_주문테이블_빈자리_여부_변경(OrderStatus orderStatus) {
        OrderTableRequest changeOrderTableRequest = new OrderTableRequest(3, true);
        // given
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));
        when(orderRepository.findAllByOrderTableId(주문테이블1.getId()))
                .thenReturn(Collections.singletonList(주문));

        assertThatThrownBy(() ->
            tableService.changeEmpty(주문테이블1.getId(), changeOrderTableRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블의 손님 수를 변경한다.")
    @Test
    void 주문테이블_손님수_변경() {
        // given
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));
        when(orderTableRepository.save(any(OrderTable.class))).thenReturn(주문테이블1);

        OrderTableResponse response = tableService.changeNumberOfGuests(주문테이블1.getId(),
                new OrderTableRequest(5, false));

        assertThat(response.getNumberOfGuests()).isEqualTo(5);
    }

    @DisplayName("주문 테이블의 손님 수를 음수로 변경할 수 없다.")
    @Test
    void 음수_주문테이블_손님수_변경() {
        // given
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));

        // when / then
        assertThatIllegalArgumentException().isThrownBy(() -> tableService.changeNumberOfGuests(주문테이블1.getId(),
                new OrderTableRequest(-5, false)));
    }

    @DisplayName("등록되지 않은 주문 테이블은 손님 수를 변경할 수 없다.")
    @Test
    void 등록되지_않은_주문테이블_손님수_변경() {
        // given
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            tableService.changeNumberOfGuests(주문테이블1.getId(), new OrderTableRequest(5, false))
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("빈 주문 테이블은 손님 수를 변경할 수 없다.")
    @Test
    void 빈_주문테이블_손님수_변경() {
        // given
        주문테이블1.changeEmpty(true);
        when(orderTableRepository.findById(주문테이블1.getId())).thenReturn(Optional.of(주문테이블1));

        assertThatThrownBy(() ->
            tableService.changeNumberOfGuests(주문테이블1.getId(), new OrderTableRequest(5, false))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
