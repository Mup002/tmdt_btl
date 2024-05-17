package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "productskus")
@Entity
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productsku_id")
    private Long id;

    private String color;
    private Long quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name ="productspu_id")
    private ProductSpu productSpu;

}
