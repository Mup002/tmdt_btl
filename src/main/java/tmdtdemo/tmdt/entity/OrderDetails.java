package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdetail_id")
    private Long id;

    private Double total;
    private String status;

    @Column(name = "order_code")
    private String code;

    @ManyToMany
    @JoinTable(name = "order_productspu",
            joinColumns = @JoinColumn(name = "orderdetail_id",referencedColumnName = "orderdetail_id"),
            inverseJoinColumns = @JoinColumn(name = "productspu_id",referencedColumnName = "productspu_id"))
    private List<ProductSpu> productSpus;

    @ManyToMany
    @JoinTable(name = "order_productsku",
    joinColumns = @JoinColumn(name = "orderdetail_id",referencedColumnName = "orderdetail_id"),
    inverseJoinColumns = @JoinColumn(name = "productsku_id",referencedColumnName = "productsku_id"))
    private List<ProductSku> productSkus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
