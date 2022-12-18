package kitchenpos.dto;

import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MenuResponse {
    private Long id;
    private Long menuGroupId;
    private String name;
    private BigDecimal price;
    private List<MenuProductResponse> menuProducts;


    protected MenuResponse() {}

    private MenuResponse(Long id, Long menuGroupId, String name, BigDecimal price, List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.menuGroupId = menuGroupId;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
    }

    public static MenuResponse from(Menu menu) {
        List<MenuProduct> menuProduct = menu.getMenuProducts();
        List<MenuProductResponse> menuProductResponse = menuProduct.stream().map(MenuProductResponse::from).collect(Collectors.toList());
        return new MenuResponse(
                menu.getId(),
                menu.getMenuGroup().getId(),
                menu.getName(),
                menu.getPrice().getPrice(),
                menuProductResponse
        );
    }

    public Long getId() {
        return id;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }
}
