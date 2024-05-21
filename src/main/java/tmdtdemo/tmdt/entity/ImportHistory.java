package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "importhistories")
public class ImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date importAt;
    private Long total_price;
    private boolean payment_status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
