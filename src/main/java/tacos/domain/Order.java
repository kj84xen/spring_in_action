package tacos.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 타코 주문 정보
 */
@Data
@Entity
@Table(name = "TACO_ORDER")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "placed_at")
    private Date placedAt;

    @NotBlank(message = "Name is required")
    @Column(name = "delivery_name")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    @Column(name = "delivery_street")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    @Column(name = "delivery_city")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    @Column(name = "delivery_state")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    @Column(name = "delivery_zip")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number")
    @Column(name = "cc_number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    @Column(name = "cc_expiration")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    @JoinTable(name = "TACO_ORDER_TACOS", joinColumns = @JoinColumn(name = "taco_order"), inverseJoinColumns = @JoinColumn(name = "taco"))
    private List<Taco> tacoList = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacoList.add(design);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    @ManyToOne
    private User user;
}
