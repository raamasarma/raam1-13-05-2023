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
public class bank {
    @Id
    @GeneratedValue
    private Integer id;
    private String bankName;
    private String ifsc;
    private String branch;
    private String swift;
    private String remove;
}
