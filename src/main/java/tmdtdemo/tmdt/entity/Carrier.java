package tmdtdemo.tmdt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "carriers")
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String rate;
    private String carrier;
    private String shortname;
//    private String service;
//    private String expected;
//    private String carrier_logo;
//    private String code_id;
}
