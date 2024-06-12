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

    private Long total;
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
    @JoinColumn(name = "method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = new Date();
//    }


}
