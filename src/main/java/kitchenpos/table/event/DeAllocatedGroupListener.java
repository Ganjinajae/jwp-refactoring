package kitchenpos.table.event;

import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableAsync
@Component
public class DeAllocatedGroupListener implements ApplicationListener<DeAllocatedGroupEvent> {

    private final OrderTableRepository orderTableRepository;

    public DeAllocatedGroupListener(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    @Async
    @Override
    public void onApplicationEvent(DeAllocatedGroupEvent event) {
        List<OrderTable> orderTableList = orderTableRepository.findAllByTableGroupId(event.getTableGroupId());
        orderTableList.forEach(it -> it.updateGroup(null));
    }
}
