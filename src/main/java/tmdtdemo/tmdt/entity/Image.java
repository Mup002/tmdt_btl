package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String src;
    @ManyToOne
    @JoinColumn(name = "id_spu")
    private ProductSpu spu;

    @OneToOne
    @JoinColumn(name = "id_sku")
    private ProductSku sku;
}
