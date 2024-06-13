package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.mapstruct.Mapping;

@Data
@Entity
@Table(name = "orderskus")
public class OrderSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "sku_id")
    private ProductSku productSku;

    @ManyToOne
    @JoinColumn(name = "orderdetail_id")
    private OrderDetails orderDetails;
}
