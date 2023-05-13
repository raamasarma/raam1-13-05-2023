package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class vehicle {
    @Id
    @GeneratedValue
    private Integer id;
    private String vehicleNo;//Follows tandard Reg No - 10 Length - 2 Aplha 2 numbers 2 Aplha 4 digits

    private LocalDate vehStartDate;

    private LocalDate vehEndDate;

    private String suplAttached;

    private String suplAttached1;

    private String suplAttached2;
    private String suplAttached3;

    private String active = "";

    private String remove;
    private String chasisNumber;
    private String owner;
    private String capacity;//9 Kl , 15kl, 20 Kl
    private  String compartments;// 1 or 2
    private String rateperKm;


}
