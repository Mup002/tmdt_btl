package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "importProducts")
public class ImportProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "importhistory_id")
    private ImportHistory importHistory;

    @ManyToOne
    @JoinColumn(name = "spu_id")
    private ProductSpu productSpu;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private ProductSku productSku;
}
