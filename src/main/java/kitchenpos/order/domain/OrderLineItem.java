package kitchenpos.order.domain;

import kitchenpos.menu.domain.Menu;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    private Long menuId;
    private long quantity;

    protected OrderLineItem() {
    }

    private OrderLineItem(OrderLineItemBuilder builder) {
        this.seq = builder.seq;
        this.order = builder.order;
        this.quantity = builder.quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public Long getOrderId() {
        return Objects.isNull(order) ? null : order.getId();
    }

    public long getQuantity() {
        return quantity;
    }

    public Long getMenuId() {
        return menuId;
    }

    public static OrderLineItemBuilder builder() {
        return new OrderLineItemBuilder();
    }

    public static class OrderLineItemBuilder {
        private Long seq;
        private Order order;
        private Menu menu;
        private long quantity;

        public OrderLineItemBuilder seq(Long seq) {
            this.seq = seq;
            return this;
        }

        public OrderLineItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderLineItemBuilder menu(Menu menu) {
            this.menu = menu;
            return this;
        }

        public OrderLineItemBuilder quantity(long quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderLineItem build() {
            return new OrderLineItem(this);
        }
    }
}
