package kitchenpos.dto;

import kitchenpos.domain.Menu;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class OrderRequest {
    private Long orderTableId;
    private OrderStatus orderStatus;
    private List<OrderLineItemRequest> orderLineItems;

    public OrderRequest() {}

    public OrderRequest(Long orderTableId, OrderStatus orderStatus, List<OrderLineItemRequest> orderLineItems) {
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
        this.orderLineItems = orderLineItems;
    }

    public Order toOrder(OrderTable orderTable, OrderStatus orderStatus, List<Menu> menus) {
        Order order = new Order(orderTable, orderStatus);
        List<OrderLineItem> items = orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.toOrderLineItem(order, menus))
                .collect(toList());
        order.order(items);

        return order;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public List<Long> findAllMenuIds() {
        return orderLineItems.stream()
                .map(OrderLineItemRequest::getMenuId)
                .collect(toList());
    }
}
