package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuPrice {
    private static final int ZERO = 0;
    private static final String REQUIRED_PRICE = "메뉴 가격은 필수 값 입니다.";
    private static final String INVALID_PRICE = "메뉴 가격은 0원 미만 일 수 없습니다.";

    @Column(name = "price")
    private BigDecimal value;

    protected MenuPrice() {}

    public MenuPrice(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(REQUIRED_PRICE);
        }
        if (value.compareTo(BigDecimal.ZERO) < ZERO) {
            throw new IllegalArgumentException(INVALID_PRICE);
        }
    }

    public MenuPrice add(MenuPrice price) {
        return new MenuPrice(this.value.add(price.value));
    }

    public boolean greaterThan(MenuPrice price) {
        return value.compareTo(price.value) > ZERO;
    }

    public BigDecimal value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(value, menuPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
