package kitchenpos.order.acceptance;

import static kitchenpos.menu.acceptance.MenuAcceptanceTest.메뉴_등록되어_있음;
import static kitchenpos.menu.dto.MenuProductRequestTest.메뉴상품_요청_객체_생성;
import static kitchenpos.menugroup.acceptance.MenuGroupAcceptanceTest.메뉴그룹_등록되어_있음;
import static kitchenpos.order.dto.OrderLineItemRequestTest.주문_항목_요청_객체_생성;
import static kitchenpos.product.acceptance.ProductAcceptanceTest.상품_등록되어_있음;
import static kitchenpos.table.acceptance.TableAcceptanceTest.주문_테이블_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kitchenpos.AcceptanceTest;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.menu.dto.MenuGroupResponse;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.product.dto.ProductResponse;
import kitchenpos.table.dto.OrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("주문 관련 기능")
public class OrderAcceptanceTest extends AcceptanceTest {

    private ProductResponse 소머리국밥;
    private ProductResponse 순대국밥;
    private MenuGroupResponse 식사;
    private MenuResponse 소머리국밥_메뉴;
    private MenuResponse 순대국밥_메뉴;

    private OrderTableResponse 주문_테이블_1;
    private OrderTableResponse 주문_테이블_2;

    @BeforeEach
    public void setUp() {
        super.setUp();

        소머리국밥 = 상품_등록되어_있음("소머리국밥", BigDecimal.valueOf(8000)).as(ProductResponse.class);
        순대국밥 = 상품_등록되어_있음("순대국밥", BigDecimal.valueOf(7000)).as(ProductResponse.class);
        식사 = 메뉴그룹_등록되어_있음("식사").as(MenuGroupResponse.class);

        MenuProductRequest 소머리국밥_메뉴상품 = 메뉴상품_요청_객체_생성(소머리국밥.getId(), 1L);
        소머리국밥_메뉴 = 메뉴_등록되어_있음("소머리국밥", BigDecimal.valueOf(8000), 식사.getId(), Arrays.asList(소머리국밥_메뉴상품)).as(MenuResponse.class);

        MenuProductRequest 순대국밥_메뉴상품 = 메뉴상품_요청_객체_생성(순대국밥.getId(), 2L);
        순대국밥_메뉴 = 메뉴_등록되어_있음("순대국밥", BigDecimal.valueOf(7000), 식사.getId(), Arrays.asList(순대국밥_메뉴상품)).as(MenuResponse.class);

        주문_테이블_1 = 주문_테이블_등록되어_있음(2, false).as(OrderTableResponse.class);
        주문_테이블_2 = 주문_테이블_등록되어_있음(4, false).as(OrderTableResponse.class);
    }


    @Test
    @DisplayName("주문을 등록할 수 있다.")
    void name() {
        // when
        OrderLineItemRequest 주문_항목 = 주문_항목_요청_객체_생성(소머리국밥_메뉴.getId(), 2L);
        ExtractableResponse<Response> response = 주문_등록_요청(주문_테이블_1.getId(), Arrays.asList(주문_항목));

        // then
        주문_등록됨(response);
    }

    @Test
    @DisplayName("주문 목록을 조회할 수 있다.")
    void list() {
        // given
        OrderLineItemRequest 주문_항목 = 주문_항목_요청_객체_생성(소머리국밥_메뉴.getId(), 2L);
        주문_등록_요청(주문_테이블_1.getId(), Arrays.asList(주문_항목));

        // when
        ExtractableResponse<Response> response = 주문_목록_조회_요청();

        // then
        주문_목록_조회됨(response);
    }

    @Test
    @DisplayName("주문 상태를 변경할 수 있다.")
    void changeOrderStatus() {
        // given
        OrderLineItemRequest 주문_항목 = 주문_항목_요청_객체_생성(소머리국밥_메뉴.getId(), 2L);
        OrderResponse 주문 = 주문_등록_요청(주문_테이블_1.getId(), Arrays.asList(주문_항목)).as(OrderResponse.class);

        // when
        ExtractableResponse<Response> response = 주문_상태_변경_요청(주문.getId(), OrderStatus.MEAL.name());

        // then
        주문_상태_변경됨(response);
    }

    public static ExtractableResponse<Response> 주문_등록_요청(Long orderTableId, List<OrderLineItemRequest> orderLineItemRequests) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderTableId", orderTableId);
        params.put("orderLineItems", orderLineItemRequests);

        return RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/orders/")
                .then().log().all()
                .extract();
    }

    public static void 주문_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }

    public static ExtractableResponse<Response> 주문_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/api/orders/")
                .then().log().all()
                .extract();
    }

    public static void 주문_목록_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 주문_상태_변경_요청(Long orderId, String orderStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("orderStatus", orderStatus);

        return RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/api/orders/{orderId}/order-status", orderId)
                .then().log().all()
                .extract();
    }

    public static void 주문_상태_변경됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
