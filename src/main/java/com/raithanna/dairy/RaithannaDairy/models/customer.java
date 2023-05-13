package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class customer {
    @Id
    @GeneratedValue
    private Integer id;
    private String custName;
    private String custCode;
    @Max(10)
    private String mobileNo;
    private String Email;
    private int custSeries;
    private String custAddr1;
    private String custAddr2;//Area Code-Destination
    private String custAddr3;
    private String custPostCode;
    private String custGst;

}
