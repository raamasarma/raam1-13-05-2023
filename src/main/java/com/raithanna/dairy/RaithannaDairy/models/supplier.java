package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class supplier {
    @Id
@GeneratedValue
    private Integer id;
    private String supplierName;
   private String supplierCode;
   private String supAddress1;
    private String supAddress2;//Area Code - Destination
    private String supAddress3;
   private String SupEmail;
   private String supMobile;
    private String pinCode;
    private Integer distance;
    private String active = "";
    private String remove;

    private Integer suplSerial = 0;
    private String GST = "";

    private String shipAddress1;
    private String shipAddress2;//Area Code - Destination
    private String shipAddress3;

    private String shipMobile;
    private String shippinCode;
    private Integer shipdistance;

}
