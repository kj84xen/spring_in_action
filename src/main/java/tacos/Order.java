package tacos;

import lombok.Data;

/**
 * 타코 주문 정보
 */
@Data
public class Order {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
}
