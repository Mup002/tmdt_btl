package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "coupon_name")
    private String name;

    @Column(name = "coupon_code")
    private String code;

    private String description;
    private boolean available;
    private Long user_use_max;

    @Column(name = "coupon_type")
    private String type;

    private Date startDate;
    private Date endDate;
    private Double min_order_need;

    @Column(name = "coupon_value")
    private String value;

    @Column(name = "coupon_quantity")
    private Long quantity;

    @Column(updatable = false)
    private Date createdDate;
    private Date updatedDate;

    @ManyToMany
    @JoinTable(name = "coupon_product",
            joinColumns = @JoinColumn(name = "coupon_id",referencedColumnName = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "productspu_id",referencedColumnName = "productspu_id"))
    private List<ProductSpu> productSpuList;
    @PrePersist
    public void onCreate(){
        this.createdDate = new Date();
        this.available = false;
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedDate = new Date();
    }

    @ManyToMany
    @JoinTable(name = "coupon_user",
    joinColumns = @JoinColumn(name = "coupon_id",referencedColumnName = "coupon_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"))
    private List<User> userList;
}
