package kitchenpos.menu.application;

import kitchenpos.ServiceTest;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menugroup.exception.NotFoundMenuGroupException;
import kitchenpos.product.exception.NotFoundProductException;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.dto.MenuRequestUtils;
import kitchenpos.menu.dto.MenuResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuServiceTest extends ServiceTest {

    @Autowired
    private MenuService service;

    private Product 강정치킨;
    private MenuGroup 신메뉴;

    @BeforeEach
    public void setUp(@Autowired ProductRepository productRepository,
                      @Autowired MenuGroupRepository menuGroupRepository) {
        강정치킨 = productRepository.save(new Product("강정치킨", BigDecimal.valueOf(15_000)));
        신메뉴 = menuGroupRepository.save(new MenuGroup("신메뉴"));
    }

    @DisplayName("메뉴를 생성한다.")
    @Test
    void create() {
        MenuRequest request = MenuRequestUtils.of("강정치킨", BigDecimal.valueOf(15_000), 강정치킨.getId(),
                                                  신메뉴.getId());

        MenuResponse response = service.create(request);

        assertThat(response.getId()).isNotNull();
    }

    @DisplayName("존재하지 않는 상품으로 메뉴를 생성한다.")
    @Test
    void createWithEmptyProduct() {
        long 존재하지_않는_상품_id = Long.MAX_VALUE;
        MenuRequest request = MenuRequestUtils.of("강정치킨", BigDecimal.valueOf(15_000), 존재하지_않는_상품_id,
                                                  신메뉴.getId());

        assertThatThrownBy(() -> {
            service.create(request);
        }).isInstanceOf(NotFoundProductException.class)
        .hasMessageContaining("상품을 찾을 수 없습니다.");
    }

    @DisplayName("존재하지 않는 메뉴그룹으로 메뉴를 생성한다.")
    @Test
    void createWithEmptyMenuGroup() {
        long 존재하지_않는_메뉴_그룹_id = Long.MAX_VALUE;
        MenuRequest request = MenuRequestUtils.of("강정치킨", BigDecimal.valueOf(15_000), 강정치킨.getId(),
                                                  존재하지_않는_메뉴_그룹_id);

        assertThatThrownBy(() -> {
            service.create(request);
        }).isInstanceOf(NotFoundMenuGroupException.class)
        .hasMessageContaining("메뉴 그룹을 찾을 수 없습니다.");
    }
}
