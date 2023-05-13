package com.raithanna.dairy.RaithannaDairy.models;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class productLog {
    @Id
    @GeneratedValue
    private int id;
    private String mobile;
   private Integer orderNo;
   private LocalDate orderDate;
   @CreationTimestamp
    @Column(updatable = false)
   private LocalDate entryDate;
    @UpdateTimestamp
    @Column(insertable = false)
   private LocalDate editDate;
    private Double qtyFrom;
    private Double qtyTo;

}
