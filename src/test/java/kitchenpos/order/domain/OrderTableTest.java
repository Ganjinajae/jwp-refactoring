package kitchenpos.order.domain;

import kitchenpos.JpaEntityTest;
import kitchenpos.order.repository.OrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("테이블 도메인 테스트")
public class OrderTableTest extends JpaEntityTest {
    @Autowired
    private OrderTableRepository orderTableRepository;

    @DisplayName("테이블 생성 테스트")
    @Test
    void createOrderTable() {
        // given
        OrderTable orderTable = new OrderTable(4, true);

        // when
        OrderTable savedOrderTable = orderTableRepository.save(orderTable);

        // then
        assertThat(savedOrderTable).isNotNull();
    }

    @DisplayName("테이블의 손님 숫자 변경")
    @Test
    void changeOrderTableNumberOfGuests() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, false));

        // when
        savedOrderTable.changeNumberOfGuests(2);

        // then
        assertThat(savedOrderTable.getNumberOfGuests()).isEqualTo(2);
    }

    @DisplayName("테이블의 손님 숫자 음수로 변경시 예외")
    @Test
    void changeOrderTableNumberOfGuestsException1() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, false));

        // when / then
        assertThatThrownBy(() -> savedOrderTable.changeNumberOfGuests(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈 테이블의 손님 숫자 변경시 예외")
    @Test
    void changeOrderTableNumberOfGuestsException2() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, true));

        // when / then
        assertThatThrownBy(() -> savedOrderTable.changeNumberOfGuests(2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("빈테이블 여부 변경 테스트")
    @Test
    void changeEmpty() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, true));

        // when
        savedOrderTable.changeEmpty(false);

        // then
        assertThat(savedOrderTable.isEmpty()).isFalse();
    }

    @DisplayName("빈테이블 여부 변경 테스트 예외 - 단체테이블에 속해있는 경우")
    @Test
    void changeEmptyException1() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, true));
        // TODO : 단체테이블로 group

        // when

        // then
    }

    @DisplayName("빈테이블 여부 변경 테스트 예외 - 속해있는 주문이 요리 / 식사 상태중일 경우")
    @Test
    void changeEmptyException2() {
        // given
        OrderTable savedOrderTable = orderTableRepository.save(new OrderTable(1, true));
        // TODO : 단체테이블로 group

        // when

        // then
    }
}
