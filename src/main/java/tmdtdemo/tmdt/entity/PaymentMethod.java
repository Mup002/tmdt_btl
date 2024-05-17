package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="paymentMethods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean status;
}
