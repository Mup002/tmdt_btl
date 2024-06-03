package tmdtdemo.tmdt.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Entity
@Table(name = "productspus")
@Document(indexName = "product_idx")
public class ProductSpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productspu_id")
    private Long id;

    @Field(type = FieldType.Text)
    @Column(name = "productspu_name")
    private String name ;

    @Field(type = FieldType.Text)
    private String description;

    @Nullable
    @Field(type = FieldType.Float)
    private Double rate;

    // tinh trạng còn hàng hay không?
    @Nullable
    @Field(type = FieldType.Boolean)
    private boolean status;

    // có được hiển thị không ?
    @Nullable
    @Field(type = FieldType.Boolean)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Field(type = FieldType.Text)
    private String type;
}
