package com.chris.booking.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_booking")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Basic
    @Column(name = "pickup_date_time", nullable = false)
    private LocalDateTime pickupDateTime;

    @Basic
    @Column(name = "pickup_locality", nullable = false, length = 100)
    private String pickupLocality;

    @Basic
    @Column(name = "drop_off_locality", nullable = false, length = 100)
    private String dropOffLocality;

    @Basic
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

}
