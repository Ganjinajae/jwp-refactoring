package kitchenpos.ordertable.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.exception.ExceptionMessage;
import kitchenpos.ordertable.exception.CannotGroupOrderTablesException;
import kitchenpos.tablegroup.domain.TableGroup;

public class OrderTables {

    private static final int MIN_ORDER_TABLE_SIZE = 2;

    private List<OrderTable> orderTables;

    private OrderTables(List<OrderTable> orderTables) {
        this.orderTables = orderTables;
    }

    public static OrderTables from(List<OrderTable> orderTables) {
        return new OrderTables(orderTables);
    }

    public List<OrderTable> getOrderTables() {
        return Collections.unmodifiableList(orderTables);
    }

    public void registerTableGroup(TableGroup tableGroup) {
        checkOrderTableSizeGreaterThanMinSize();
        checkAllOrderTablesAreEmpty();
        checkAllOrderTablesNotYetRegisteredTableGroup();
        orderTables.forEach(it -> it.registerTableGroup(tableGroup.getId()));
    }

    public void unGroup() {
        orderTables.forEach(OrderTable::unGroup);
    }

    private void checkOrderTableSizeGreaterThanMinSize() {
        if (orderTables.isEmpty() || orderTables.size() < MIN_ORDER_TABLE_SIZE) {
            throw new CannotGroupOrderTablesException(ExceptionMessage.INVALID_ORDER_TABLE_SIZE);
        }
    }

    private void checkAllOrderTablesAreEmpty() {
        boolean isNotEmpty = orderTables.stream().anyMatch(it -> !it.isEmpty());

        if (isNotEmpty) {
            throw new CannotGroupOrderTablesException(ExceptionMessage.NOT_EMPTY_ORDER_TABLE_EXIST);
        }
    }

    private void checkAllOrderTablesNotYetRegisteredTableGroup() {
        boolean alreadyRegistered = orderTables.stream().anyMatch(it -> Objects.nonNull(it.getTableGroupId()));

        if (alreadyRegistered) {
            throw new CannotGroupOrderTablesException(ExceptionMessage.ALREADY_GROUPED_ORDER_TABLE_EXIST);
        }
    }

    public List<Long> getOrderTableIds() {
        return orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());
    }
}
