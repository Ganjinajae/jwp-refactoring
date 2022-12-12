package kitchenpos.menu.application;

import kitchenpos.menu.dao.MenuProductDao;
import kitchenpos.menu.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuGroupRepository menuGroupRepository;
    @Mock
    private MenuProductDao menuProductDao;
    @Mock
    private ProductService productService;

    @InjectMocks
    private MenuService service;

    private Long product1Id = 100L;
    private Long product2Id = 101L;
    private Long menuId = 1L;
    private Long menuGroupId = 1L;

    private MenuGroup menuGroup;

    @BeforeEach
    void setUp() {
        menuGroup = MenuGroup.of(1L,"test menu group");
    }

    @Test
    @DisplayName("메뉴 가격이 null이면 exception이 발생함")
    void throwExceptionWhenMenuPriceIsNull() {
        Menu menu = getMenu(null);

        assertThatThrownBy(() -> service.create(menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 음수이면 exception이 발생함")
    void throwExceptionWhenMenuPriceIsNegative() {
        Menu menu = getMenu(-1);

        assertThatThrownBy(() -> service.create(menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 그룹 아이디가 존재하지 않으면 exception이 발생함")
    void throwExceptionWhenMenuGroupIdNotExist() {
        given(menuGroupRepository.existsById(anyLong())).willReturn(false);

        Menu menu = getMenu(10000, getTwoMenuProducts(menuId,product1Id,3, product2Id, 5));

        assertThatThrownBy(() -> service.create(menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 그룹이 가진 상품의 아이디가 존재하지 않으면 exception이 발생함")
    void throwExceptionWhenIdOfProductContainedByMenuGroupNotExist() {
        Menu menu = getMenu(10000,  getTwoMenuProducts(menuId,product1Id,3, product2Id, 5));

        given(menuGroupRepository.existsById(menuId)).willReturn(true);
        given(productService.findById(product1Id)).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> service.create(menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격은 각 상품의 합보다 클 수 없다")
    void menuPriceCanNotBeBiggerThanSumOfProductPriceMultipliedByQuantity() {
        Product 상품1 = Product.of(product1Id, "상품1", BigDecimal.valueOf(1000L));
        Product 상품2 = Product.of(product2Id, "상품2", BigDecimal.valueOf(1000L));
        Menu menu = getMenu(10000,  getTwoMenuProducts(menuId,product1Id,3, product2Id, 5));

        given(menuGroupRepository.existsById(menuGroupId)).willReturn(true);
        given(productService.findById(product1Id)).willReturn(상품1);
        given(productService.findById(product2Id)).willReturn(상품2);

        assertThatThrownBy(() -> service.create(menu)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 조회하고, 메뉴 상품을 가지고 있다")
    void menuHasMenuProducts() {
        Menu menu = getMenu(8000);
        List<MenuProduct> menuProducts = getTwoMenuProducts(menuId,product1Id,3, product2Id, 5);

        given(menuRepository.findAll()).willReturn(Arrays.asList(menu));
        given(menuProductDao.findAllByMenuId(menuId)).willReturn(menuProducts);

        List<Menu> list = service.list();

        menu.setMenuProducts(menuProducts);
        assertThat(list).isEqualTo(Arrays.asList(menu));
    }

    private Menu getMenu(Integer price) {
        return Menu.of(menuId, "메뉴", price == null ? null : BigDecimal.valueOf(price));
    }

    private Menu getMenu(int price, List<MenuProduct> menuProducts) {
        return Menu.of(menuId, "메뉴", BigDecimal.valueOf(price), menuGroup, menuProducts);
    }

    private List<MenuProduct> getTwoMenuProducts(Long menuId, Long product1Id, long product1Quantity, Long product2Id, long product2Quantity) {
        return Arrays.asList(
                MenuProduct.of(menuId, product1Id, product1Quantity),
                MenuProduct.of(menuId, product2Id, product2Quantity)
        );
    }
}