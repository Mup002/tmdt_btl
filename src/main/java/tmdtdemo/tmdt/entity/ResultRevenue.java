package tmdtdemo.tmdt.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Entity
@Table
public class ResultRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cost;
    private Long revenue;
    private Long profit;
    @Column(updatable = false)
    private Date resultDate;

    @Nullable
    private String note;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
