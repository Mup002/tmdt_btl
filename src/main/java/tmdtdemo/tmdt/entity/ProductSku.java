package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Table(name = "productskus")
@Entity
@Document(indexName = "product_idx")
public class ProductSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productsku_id")
    private Long id;
    @Field(type = FieldType.Text)
    private String color;
    @Field(type = FieldType.Long)
    private Long quantity;
    @Field(type = FieldType.Float)
    private Double price;
    @Field(type = FieldType.Long)
    private Long cost;
    @ManyToOne
    @JoinColumn(name ="productspu_id")
    private ProductSpu productSpu;

}
