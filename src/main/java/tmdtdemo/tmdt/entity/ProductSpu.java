package tmdtdemo.tmdt.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productspus")
public class ProductSpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productspu_id")
    private Long id;

    @Column(name = "productspu_name")
    private String name ;

    private String description;

    @Nullable
    private Double rate;

    // tinh trạng còn hàng hay không?
    @Nullable
    private boolean status;

    // có được hiển thị không ?
    @Nullable
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String type;
}
