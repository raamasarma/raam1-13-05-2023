package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class company {
    @Id
    @GeneratedValue
    private Integer id;
    private String orgName;
    private String orgCode;
    private String orgAddress1;
    private String orgAddress2;
    private String orgAddress3;
    private String orgEmail;
    private String orgMobile;
    private String pinCode;
    private String active = "";
    private String remove;
    private String sourcedata;
    private Integer orgSerial = 0;
    private String GST = "";
    private String orgBankAcctNo;
    private String orgBankName;

    private String orgBankIFSC;

    private String orgBankBranch;
    private String orgBankSWIFT;
    private File orgLogo;
    private String orgAuthorised;




}
