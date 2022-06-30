package kitchenpos.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderTableIdIn(List<Long> orderTableIds);
    List<Order> findAllByOrderTableId(Long orderTableId);
}
