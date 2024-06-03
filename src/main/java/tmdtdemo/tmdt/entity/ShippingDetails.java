package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "shippingdetail")
public class ShippingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;

    private Date createdAt;
    private String expected;
    private String code;
    private String status;

    private Long total_bill;
    private Long fee_ship;
    private String phone;

    private String service;
}
