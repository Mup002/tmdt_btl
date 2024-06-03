package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String district;
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
