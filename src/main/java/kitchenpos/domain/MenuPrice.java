package kitchenpos.domain;

import kitchenpos.utils.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

import static kitchenpos.utils.Message.INVALID_PRICE;


@Embeddable
public class MenuPrice {

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    protected MenuPrice() {
    }

    private MenuPrice(BigDecimal price) {
        this.price = price;
    }

    public static MenuPrice from(BigDecimal price) {
        Validator.checkPriceOverZero(price, INVALID_PRICE);
        return new MenuPrice(price);
    }

    public BigDecimal getPrice() {
        return price;
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
        return price.equals(menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
