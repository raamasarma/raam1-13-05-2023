package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class userLog {

    @Id
    @GeneratedValue
    private int id;
    private String mobile;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate creationDate;
    private String ipAddress;
    private String status;


}
