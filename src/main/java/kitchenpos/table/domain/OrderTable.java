package kitchenpos.table.domain;

import java.util.Objects;

public class OrderTable {
    public static final String TABLE_GROUP_EMPTY_EXCEPTION_MESSAGE = "테이블 그룹이 존재하지 않습니다.";
    public static final String NUMBER_OF_GUESTS_MINIMUM_NUMBER_EXCEPTION_MESSAGE = "0명보다 작을 수 없다.";
    public static final String ORDER_TABLE_NULL_EXCEPTION_MESSAGE = "주문 테이블이 없을 경우 손님수를 변경할 수 없습니다.";
    public static final String EMPTY_EXCEPTION_MESSAGE = "공석일 경우 손님수를 변경할 수 없습니다.";
    private Long id;
    private Long tableGroupId;
    private int numberOfGuests;
    private boolean empty;

    public OrderTable() {
        this.empty = false;
    }

    public OrderTable(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public OrderTable(Long tableGroupId, boolean empty) {
        this.tableGroupId = tableGroupId;
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public void setTableGroupId(final Long tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(final boolean empty) {
        this.empty = empty;
    }

    public void empty() {
        if (this.tableGroupId == null) {
            throw new IllegalArgumentException(TABLE_GROUP_EMPTY_EXCEPTION_MESSAGE);
        }
        this.empty = true;
    }

    public void unGroup() {
        this.tableGroupId = null;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException(NUMBER_OF_GUESTS_MINIMUM_NUMBER_EXCEPTION_MESSAGE);
        }
        if (Objects.isNull(tableGroupId)) {
            throw new IllegalArgumentException(ORDER_TABLE_NULL_EXCEPTION_MESSAGE);
        }
        if (isEmpty()) {
            throw new IllegalArgumentException(EMPTY_EXCEPTION_MESSAGE);
        }
    }
}
