package kitchenpos.table.application;

import java.util.Arrays;
import java.util.List;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.OrderTables;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.table.domain.TableGroupCreateValidator;
import kitchenpos.table.domain.TableGroupRepository;
import kitchenpos.table.domain.TableGroupUnGroupValidator;
import kitchenpos.table.dto.TableGroupRequest;
import kitchenpos.table.dto.TableGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableGroupService {

    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;
    private final OrderRepository orderRepository;

    public TableGroupService(final OrderTableRepository orderTableRepository, final TableGroupRepository tableGroupRepository,
            final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupRequest tableGroupRequest) {
        List<Long> orderTableIds = tableGroupRequest.getOrderTableIds();
        List<OrderTable> savedOrderTables = orderTableRepository.findAllById(orderTableIds);
        TableGroupCreateValidator.validate(orderTableIds, savedOrderTables);
        TableGroup tableGroup = tableGroupRepository.save(tableGroupRequest.toTableGroup(savedOrderTables));
        return TableGroupResponse.from(tableGroup);
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        List<OrderTable> orderTableList = orderTableRepository.findAllByTableGroupId(tableGroupId);
        boolean completedOrderTable = orderRepository.existsByOrderTableInAndOrderStatusIn(orderTableList,
                Arrays.asList(OrderStatus.COOKING, OrderStatus.MEAL));
        TableGroupUnGroupValidator.validate(completedOrderTable);
        OrderTables orderTables = OrderTables.from(orderTableList);
        orderTables.unTableGroup();
    }
}
